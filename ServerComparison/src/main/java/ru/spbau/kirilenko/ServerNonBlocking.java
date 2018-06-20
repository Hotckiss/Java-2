package ru.spbau.kirilenko;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerNonBlocking {
    private static final Logger logger = Logger.getLogger("NIOLogger");
    private int port;
    private boolean isActive;
    private Selector readSelector;
    private Selector writeSelector;
    private ExecutorService threadPool = Executors.newFixedThreadPool(8);
    private ServerSocketChannel serverSocketChannel;
    private HandleSelector selectorWrite;

    public ServerNonBlocking(int port) {
        this.port = port;
        this.isActive = false;
    }

    public void stop() {
        isActive = false;
        try {
            serverSocketChannel.close();
            threadPool.shutdown();
        } catch (IOException e) {
            logger.info("cannot stop server" + e.getMessage());
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void start() {
        isActive = true;
        try {
            this.readSelector = Selector.open();
            this.writeSelector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            logger.info("cannot connect server to port " + e.getMessage());
        }

        HandleSelector selectorRead = new HandleSelector(readSelector, SelectionKey.OP_READ);
        selectorWrite = new HandleSelector(writeSelector, SelectionKey.OP_WRITE);
        new Thread(selectorRead).start();
        new Thread(selectorWrite).start();

        while (isActive) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                selectorRead.register(new ClientMessages(socketChannel));
                readSelector.wakeup();
            } catch (IOException e) {
                logger.info("cannot accept user " + e.getMessage());
                stop();
            }
        }
    }

    @RequiredArgsConstructor
    private class HandleSelector implements Runnable {
        private final Selector selectorHandler;
        private final int interestedOp;
        private final Queue<ClientMessages> queue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (isActive) {
                registerClients();

                try {
                    if (selectorHandler.select() > 0) {
                        handleReady(selectorHandler.selectedKeys().iterator());
                    }
                } catch (IOException e) {
                    stop();
                    logger.info("IO error while read or write message " + e.getMessage());
                }
            }
        }

        public void register(ClientMessages cli) {
            queue.add(cli);
        }

        private void registerClients() {
            while (!queue.isEmpty()) {
                ClientMessages client = queue.poll();
                SocketChannel socketChannel = client.getSocketChannel();
                try {
                    if (interestedOp == SelectionKey.OP_READ) {
                        socketChannel.configureBlocking(false);
                    }
                    socketChannel.register(selectorHandler, interestedOp, client);
                } catch (IOException e) {
                    stop();
                    logger.info("cannot register new client" + e.getMessage());
                }
            }
        }

        private void handleReady(Iterator<SelectionKey> iterator) throws IOException {
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                ClientMessages client = (ClientMessages) key.attachment();
                if ((interestedOp == SelectionKey.OP_READ)) {
                    client.readMessage();
                } else {
                    client.writeMessage();
                }

                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        new ServerNonBlocking(8228).start();
    }

    private class ClientMessages {
        @Getter private SocketChannel socketChannel;
        private final ByteBuffer bufRead = ByteBuffer.allocate(1000000);
        private final ByteBuffer bufWrite = ByteBuffer.allocate(1000000);
        private boolean startedReadingMessage = false;
        private byte[] message = null;
        private int completed = 0;
        private long qStart = 0;
        private boolean first = true;

        public ClientMessages(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
            bufWrite.flip();
        }

        public void writeMessage() throws IOException {
            synchronized (bufWrite) {
                if (bufWrite.remaining() > 0 && (socketChannel.write(bufWrite) > 0 && bufWrite.remaining() == 0)) {
                    first = true;
                }
            }
        }

        private void readAvailableFromChannel() throws IOException {
            while (socketChannel.read(bufRead) > 0);
            bufRead.flip();
        }

        public void readMessage() throws IOException {
            if (first) {
                qStart = System.currentTimeMillis();
                first = false;
            }
            readAvailableFromChannel();

            if (!startedReadingMessage) {
                readSize();
            }

            if (startedReadingMessage) {
                int len = bufRead.remaining();
                bufRead.get(message, completed, bufRead.remaining());
                completed += len;
            }

            bufRead.compact();
            if (startedReadingMessage && message.length == completed) {
                threadPool.submit(new SortTask(SortingMessage.Sort.parseFrom(message).getArrList().stream().mapToInt(n -> n).toArray(), this));
                startedReadingMessage = false;
                completed = 0;
            }
        }

        private void readSize() throws IOException {
            if (bufRead.remaining() >= 4) {
                int size = bufRead.getInt();
                if (size == 0) {
                    socketChannel.close();
                    return;
                }
                message = new byte[size];
                startedReadingMessage = true;
            }
        }

        private void writeResult(int[] array, int timeS) {
            SortingMessage.Sort.Builder builder = SortingMessage.Sort.newBuilder();

            for (int val : array) {
                builder.addArr(val);
            }
            builder.addArr(timeS);
            builder.addArr((int)(System.currentTimeMillis() - qStart));
            byte[] arrSerialized = builder.build().toByteArray();
            synchronized (bufWrite) {
                bufWrite.compact();
                bufWrite.putInt(arrSerialized.length);
                bufWrite.put(arrSerialized);
                bufWrite.flip();
            }
        }

        @AllArgsConstructor
        private class SortTask implements Runnable {
            private int[] arr;
            private ClientMessages client;
            @Override
            public void run() {
                int sortingTime = (int) Sorter.sort(arr);
                writeResult(arr, sortingTime);
                selectorWrite.register(client);
                writeSelector.wakeup();
            }
        }
    }
}

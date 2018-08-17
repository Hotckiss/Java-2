package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerWithThreadPool {
    private static final Logger logger = Logger.getLogger("PoolLogger");
    private final int port;
    private boolean isActive;
    private ServerSocket serverSocket;
    private ExecutorService threadPool = Executors.newFixedThreadPool(8);

    @SuppressWarnings("WeakerAccess")
    public ServerWithThreadPool(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.info("cannot run server with that port " + e.getMessage());
            return; //STOP server
        }
        isActive = true;
        new Thread(() -> {
            while (isActive) {
                Socket client;
                try {
                    client = serverSocket.accept();
                } catch (IOException e) {
                    stop(); //STOP server
                    logger.info("cannot accept new user " + e.getMessage());
                    return;
                }
                new Thread(new ServerWithThreadPool.ClientHandler(client)).start();
            }
        }).start();
    }

    public void stop() {
        isActive = false;
        threadPool.shutdown();
    }

    private class ClientHandler implements Runnable {
        @NotNull
        private final Socket client;
        private boolean error = false;
        private ExecutorService outputSender = Executors.newSingleThreadExecutor();

        @SuppressWarnings("WeakerAccess")
        public ClientHandler(@NotNull Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            try (DataInputStream queries = new DataInputStream(client.getInputStream());
                 DataOutputStream queryResult = new DataOutputStream(client.getOutputStream())) {
                while (isActive && !error) {
                    int requestLength = queries.readInt();

                    if (requestLength == 0) {
                        break;
                    }

                    long queryStartTime = System.currentTimeMillis();

                    byte[] data = new byte[requestLength];
                    int total = 0;
                    while (total < requestLength) {
                        int actualLen = queries.read(data, total, data.length - total);
                        total += actualLen;
                    }
                    int[] array = SortingMessage.Sort.parseFrom(data).getArrList().stream().mapToInt(n -> n).toArray();

                    threadPool.submit(new SortTask(array, queryResult, queryStartTime));
                }
            } catch (IOException e) {
                //client stop working
                try {
                    client.close();
                } catch (IOException e1) {
                    logger.info("cannot disconnect client " + e.getMessage());
                }
                logger.info("IO error while receiving message " + e.getMessage());
            }
        }

        @AllArgsConstructor
        private class SortTask implements Callable<Long> {
            @Getter private int[] arr;
            @Getter private DataOutputStream queryResult;
            @Getter private long queryStartTime;

            @Override
            public Long call() {
                long retVal =  Sorter.sort(arr);
                outputSender.submit(new Sender((int)retVal));
                return retVal;
            }

            @AllArgsConstructor
            private class Sender implements Runnable {
                private int sortTime;

                @Override
                public void run() {
                    try {
                        writeRequest(queryResult, arr, sortTime, (int)(System.currentTimeMillis() - queryStartTime));
                    } catch (IOException e) {
                        error = true; // stop client
                        logger.info("IO error while receiving message " + e.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new ServerWithThreadPool(8228).start();
    }

    private void writeRequest(DataOutputStream outputStream, int[] arr, int sortTime, int qTime) throws IOException {
        SortingMessage.Sort.Builder builder = SortingMessage.Sort.newBuilder();

        for (int val : arr) {
            builder.addArr(val);
        }

        builder.addArr(sortTime);
        builder.addArr(qTime);
        byte[] arrSerialized = builder.build().toByteArray();
        outputStream.writeInt(arrSerialized.length);
        outputStream.write(arrSerialized);
    }
}

package ru.spbau.kirilenko;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThreadForEachClient {
    private static final Logger logger = Logger.getLogger("SimpleServerLogger");
    private final int port;
    private boolean isActive;
    private ServerSocket serverSocket;


    @SuppressWarnings("WeakerAccess")
    public ServerThreadForEachClient(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.info("cannot run server with that port " + e.getMessage());
            return;
        }
        isActive = true;
        new Thread(() -> {
            while (isActive) {
                Socket client;
                try {
                    client = serverSocket.accept();
                } catch (IOException e) {
                    stop();
                    logger.info("cannot accept new user " + e.getMessage());
                    return;
                }
                new Thread(new ClientHandler(client)).start();
            }
        }).start();
    }

    public void stop() {
        isActive = false;
    }

    private class ClientHandler implements Runnable {
        @NotNull
        private final Socket client;

        @SuppressWarnings("WeakerAccess")
        public ClientHandler(@NotNull Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            try (DataInputStream queries = new DataInputStream(client.getInputStream());
                 DataOutputStream queryResult = new DataOutputStream(client.getOutputStream())) {
                while (isActive) {
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
                    int sortTime = (int) Sorter.sort(array);
                    int qTime = (int)(System.currentTimeMillis() - queryStartTime);
                    writeRequest(queryResult, array, sortTime, qTime);
                }
            } catch (IOException e) {
                logger.info("IO error while receiving message " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new ServerThreadForEachClient(8228).start();
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

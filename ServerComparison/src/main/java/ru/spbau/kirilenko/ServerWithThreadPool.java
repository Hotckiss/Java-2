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

public class ServerWithThreadPool {
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
            return;
        }
        isActive = true;
        new Thread(() -> {
            while (isActive) {
                Socket client;
                try {
                    client = serverSocket.accept();
                    //System.err.println("new client");
                } catch (IOException e) {
                    isActive = false;
                    return;
                }
                new Thread(new ServerWithThreadPool.ClientHandler(client)).start();
            }
        }).start();
    }

    public void stop() {
        isActive = false;
    }

    private class ClientHandler implements Runnable {
        @NotNull
        private final Socket client;
        //private int totalSorting = 0;
        //private int totalQueries = 0;
        //private int count = 0;
        private ExecutorService outputSender = Executors.newSingleThreadExecutor();
        //private ArrayList<Future<Long>> requests = new ArrayList<>();

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
                        //for (Future<Long> future : requests) {
                            //future.get();
                        //}
                        //outputSender.shutdown();
                        //outputSender.awaitTermination(5, TimeUnit.MINUTES);
                        //queryResult.writeInt(totalSorting);
                        //queryResult.writeInt(totalQueries);
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
                    //requests.add(threadPool.submit(new SortTask(array, queryResult, queryStartTime)));
                    //writeRequest(queryResult, array);
                    //System.err.println("answered");
                    //totalQueries += (System.currentTimeMillis() - queryStartTime);
                    //count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                //System.err.println("Failed to get data.");
            }
        }

        @AllArgsConstructor
        private class SortTask implements Callable<Long> {
            @Getter private int[] arr;
            @Getter private DataOutputStream queryResult;
            @Getter private long queryStartTime;

            @Override
            public Long call() throws Exception {
                long retVal =  Sorter.sort(arr);
                outputSender.submit(new Sender((int)retVal));
                //synchronized (ClientHandler.this) {
                  //  totalSorting += retVal;
                //}
                return retVal;
            }

            @AllArgsConstructor
            private class Sender implements Runnable {
                private int sortTime;

                @Override
                public void run() {
                    try {
                        writeRequest(queryResult, arr, sortTime, (int)(System.currentTimeMillis() - queryStartTime));
                        //synchronized (ClientHandler.this) {
                          //  totalQueries += System.currentTimeMillis() - queryStartTime;
                        //}
                    } catch (IOException e) {
                        e.printStackTrace();
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

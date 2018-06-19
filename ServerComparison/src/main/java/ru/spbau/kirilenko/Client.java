package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

@AllArgsConstructor
public class Client {
    private ClientBaggage baggage;

    public void runClient() throws IOException {
        int sumSortingTimes = 0;
        int sumSingleRequestTime = 0;
        long startTime = System.currentTimeMillis();
        Socket socket = new Socket(InetAddress.getByName(baggage.getHost()), baggage.getPort());
        try(DataInputStream fromServer = new DataInputStream(socket.getInputStream());
            DataOutputStream toServer = new DataOutputStream(socket.getOutputStream())) {
            for (int i = 0; i < baggage.getQueriesNumber(); i++) {
                int[] requestArr = genArray();
                writeRequest(toServer, requestArr);
                int answerLength = fromServer.readInt();
                byte[] data = new byte[answerLength];
                int total = 0;

                while (total < answerLength) {
                    int actualLen = fromServer.read(data, total, data.length - total);
                    total += actualLen;
                }

                int[] answeredArray = SortingMessage.Sort.parseFrom(data).getArrList().stream().mapToInt(n -> n).toArray();
                sumSortingTimes += answeredArray[answeredArray.length - 2];
                sumSingleRequestTime += answeredArray[answeredArray.length - 1];
                Thread.sleep(baggage.getDelay());
            }

            toServer.writeInt(0);

        } catch (InterruptedException | IOException ex) {
            System.err.println("I/O error while connected to server");
        }

        try {
            socket.close();
        } catch (IOException ex) {
            System.err.println("cannot close socket");
        }

        long totalTime = System.currentTimeMillis() - startTime;
        long avgTime = Math.round((double)totalTime / baggage.getQueriesNumber());

        StatisticCollector.writeReport(sumSortingTimes / baggage.getQueriesNumber(), sumSingleRequestTime / baggage.getQueriesNumber(), (int)avgTime, baggage.getKey());
    }

    private void writeRequest(DataOutputStream outputStream, int[] arr) throws IOException {
        SortingMessage.Sort.Builder builder = SortingMessage.Sort.newBuilder();

        for (int val : arr) {
            builder.addArr(val);
        }

        byte[] arrSerialized = builder.build().toByteArray();
        outputStream.writeInt(arrSerialized.length);
        outputStream.write(arrSerialized);
    }

    private int[] genArray() {
        Random random = new Random(System.currentTimeMillis());
        int[] result = new int[baggage.getArraySize()];
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt();
        }

        return result;
    }
}

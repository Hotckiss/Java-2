package ru.spbau.kirilenko.hw4SimpleFTP;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Console application for working with FTP
 * Provides three commands:
 * 0 - exit application
 * 1 - list all files in directory
 * 2 - get file from server
 */
public class Main {
    private static final int EXIT = 0;
    private static final int LIST = 1;
    private static final int GET = 2;

    private static final int SERVER_PORT = 8228;
    private static final String SERVER_HOST = "localhost";

    /**
     * Main method that waits for user commands
     *
     * @param args application args (not necessary)
     */
    public static void main(String[] args) {
        Client client;
        info();

        Scanner scanner = new Scanner(System.in);
        boolean notFinished = true;

        try {
           client = new Client(SERVER_HOST, SERVER_PORT);
        } catch (ConnectException ce) {
            System.out.println("Cannot connect to server!");
            return;
        }

        while (notFinished) {
            int command = scanner.nextInt();

            try {
                switch (command) {
                    case LIST:
                        String directoryName = scanner.next();
                        List<FileInfo> queryResult = client.list(directoryName);
                        System.out.println(queryResult.size() + " files total.");
                        queryResult.forEach(System.out::println);
                        break;

                    case GET:
                        String fileName = scanner.next();
                        byte[] content = client.get(fileName);
                        System.out.println("File was downloaded, total " + content.length + " bytes.");
                        System.out.println(Arrays.toString(content));
                        break;

                    case EXIT:
                        client.disconnect();
                        notFinished = false;
                        break;

                    default:
                        System.out.println("Unknown command.");
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private static void info() {
        System.out.println("Help:\n" +
                "<0: Int> - quit\n" +
                "<1: Int> <path: String> - lists all files and directories in path on server\n" +
                "<2: Int> <path: String> - download file from path of server\n");
    }
}
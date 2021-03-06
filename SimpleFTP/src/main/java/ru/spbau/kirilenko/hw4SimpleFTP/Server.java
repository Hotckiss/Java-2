package ru.spbau.kirilenko.hw4SimpleFTP;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.io.IOUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Simple FTP server implementation which waits for connections
 * and runs each client in their own threads and do their commands
 */
public class Server {
    private static final int LIST_COMMAND_ID = 1;
    private static final int GET_COMMAND_ID = 2;
    private static final int POOL_SIZE = 4;

    private final int port;
    private boolean isActive;
    private ServerSocket serverSocket;
    private final ExecutorService pool;
    /**
     * Constructs new server class with fixed port for users
     * @param port server port
     */
    @SuppressWarnings("WeakerAccess")
    public Server(int port) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(POOL_SIZE);
    }

    /**
     * Starts server in new thread that will wait for users
     */
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
                } catch (IOException e) {
                    isActive = false;
                    return;
                }
                pool.submit(new ClientHandler(client));
            }
        }).start();
    }

    /**
     * Stops server, is stops waiting for connections and do commands from connected users
     */
    public void stop() {
        isActive = false;
    }

    /**
     * Class that handles connection to user and runs its commands
     */
    private class ClientHandler implements Runnable {
        @NotNull private final Socket client;

        /**
         * Associates socket with current client
         *
         * @param socket client socket
         */
        @SuppressWarnings("WeakerAccess")
        public ClientHandler(@NotNull Socket socket) {
            this.client = socket;
        }

        /**
         * Method that waits for user commands while server is alive
         */
        @Override
        public void run() {
            try (DataInputStream queries = new DataInputStream(client.getInputStream());
                 DataOutputStream queryResult = new DataOutputStream(client.getOutputStream())) {
                while (isActive) {
                    int requestType;
                    try {
                        requestType = queries.readInt();
                    } catch (Exception e) {
                        System.err.println("Command not found.");
                        break;
                    }
                    if (requestType == LIST_COMMAND_ID) {
                        String directoryName = queries.readUTF();
                        new ListTask(queryResult, directoryName).execute();
                    } else if (requestType == GET_COMMAND_ID) {
                        String fileName = queries.readUTF();
                        new GetTask(queryResult, fileName).execute();
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to get data.");
            }
        }
    }

    /**
     * Server task of downloading file
     */
    private class GetTask {
        @NotNull private final DataOutputStream clientInput;
        @NotNull private final String fileName;

        /**
         * Constructor specifies file to download and output stream for server answer
         *
         * @param outputStream stream for writing data
         * @param fileName file to download
         */
        @SuppressWarnings("WeakerAccess")
        public GetTask(@NotNull DataOutputStream outputStream, @NotNull String fileName) {
            this.clientInput = outputStream;
            this.fileName = fileName;
        }

        /**
         * Process of file downloading
         */
        public void execute() {
            try {
                Path filePath = Paths.get(fileName);
                if (Files.isDirectory(filePath) || !Files.exists(filePath)) {
                    clientInput.writeLong(0);
                } else {
                    long total = Files.size(filePath);
                    clientInput.writeLong(total);
                    if (total == 0) {
                        return;
                    }

                    try (FileInputStream fileStream = new FileInputStream(filePath.toFile())) {
                        IOUtils.copyLarge(fileStream, clientInput);
                    } catch (IOException e) {
                        System.err.println("Error occurred while writing file.");
                        throw e;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error occurred while working with client's input.");
            }
        }
    }

    /**
     * Server task of listing all files and folders in directory
     */
    private class ListTask {
        @NotNull private final DataOutputStream clientInput;
        @NotNull private final String directoryName;

        /**
         * Constructor specifies directory to list and output stream for server answer
         *
         * @param outputStream stream for writing data
         * @param directoryName directory to list
         */
        @SuppressWarnings("WeakerAccess")
        public ListTask(@NotNull DataOutputStream outputStream, @NotNull String directoryName) {
            this.clientInput = outputStream;
            this.directoryName = directoryName;
        }

        /**
         * Process of directory listing
         */
        public void execute() {
            try {
                Path directoryPath = Paths.get(directoryName);
                if (Files.isDirectory(directoryPath)) {
                    List<Path> paths = Files.list(directoryPath).collect(Collectors.toList());
                    clientInput.writeInt(paths.size());
                    for (Path path : paths) {
                        clientInput.writeUTF(path.getFileName().toString());
                        clientInput.writeBoolean(Files.isDirectory(path));
                    }
                } else {
                    clientInput.writeInt(0);
                }
            } catch (IOException e) {
                System.err.println("Error while sending info.");
            }
        }
    }

    public static void main(String[] args) {
        new Server(8228).start();
    }
}

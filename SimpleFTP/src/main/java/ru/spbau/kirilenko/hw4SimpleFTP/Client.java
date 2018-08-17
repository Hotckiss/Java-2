package ru.spbau.kirilenko.hw4SimpleFTP;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides clients access to FTP server and do list and get commands
 */
public class Client {
    private static final int LIST_COMMAND_ID = 1;
    private static final int GET_COMMAND_ID = 2;
    private static final int BUFFER_SIZE = 1024;

    private Socket serverSocket;
    private DataOutputStream serverInput;
    private DataInputStream serverOutput;

    /**
     * Constructs new client with fixed host and port number
     *
     * @param serverHost server host
     * @param serverPort server port number
     * @throws ConnectException if client cannot make connection to server
     */
    @SuppressWarnings("WeakerAccess")
    public Client(@NotNull String serverHost, int serverPort) throws ConnectException {
        try {
            serverSocket = new Socket(serverHost, serverPort);
            serverInput = new DataOutputStream(serverSocket.getOutputStream());
            serverOutput = new DataInputStream(serverSocket.getInputStream());
        } catch (IOException e) {
            throw new ConnectException("No connection to server.");
        }
    }

    /**
     * Method that close all connections with server
     *
     * @throws ConnectException if connection was already lost and disconnection is not possible
     */
    public void disconnect() throws ConnectException {
        try {
            serverInput.close();
            serverOutput.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new ConnectException("Connection lost.");
        }
    }

    /**
     * Command that asking server to list all content from specified path and returns result in list representation
     *
     * @param path path to directory on server
     * @return list of all content specified with the path
     * @throws IOException if any error during reading or writing data occurred
     */
    @NotNull public List<FileInfo> list(@NotNull String path) throws IOException {
        writeCommandNotBuffered(LIST_COMMAND_ID, path);
        List<FileInfo> result = new ArrayList<>();
        int filesCount = serverOutput.readInt();

        for (int i = 0; i < filesCount; i++) {
            String fileName = serverOutput.readUTF();
            boolean isDirectory = serverOutput.readBoolean();
            result.add(new FileInfo(fileName, isDirectory));
        }

        return result;
    }

    /**
     * Command that downloads file that specified with the path from server
     *
     * @param fileName name of file to download
     * @return byte array with file content
     * @throws IOException if any error during reading or writing data occurred
     */
    @NotNull public byte[] get(@NotNull String fileName) throws IOException {
        writeCommandNotBuffered(GET_COMMAND_ID, fileName);
        long total = serverOutput.readLong();
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        long read = 0;

        while (read < total) {
            int count = (int) Math.min(BUFFER_SIZE, total - read);
            int readActual = serverOutput.read(buffer, 0, count);
            content.write(buffer, 0, readActual);
            read += readActual;
        }

        return content.toByteArray();
    }

    private void writeCommandNotBuffered(int commandID, @NotNull String path) throws IOException {
        serverInput.writeInt(commandID);
        serverInput.writeUTF(path);
        serverInput.flush();
    }
}
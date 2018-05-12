package ru.spbau.kirilenko.hw4SimpleFTP;

import org.jetbrains.annotations.NotNull;

/**
 * Class that contains current client of the application
 */
public class ClientManager {
    @SuppressWarnings("WeakerAccess")
    public static final ClientManager CLIENT = new ClientManager();
    private Client client;

    /**
     * Updates current client
     * @param newClient new connected client
     */
    public void setClient(@NotNull Client newClient) {
        client = newClient;
    }

    /**
     * Returns current connected client
     * @return current client
     */
    public Client getClient() {
        return client;
    }

    private ClientManager() {
    }
}

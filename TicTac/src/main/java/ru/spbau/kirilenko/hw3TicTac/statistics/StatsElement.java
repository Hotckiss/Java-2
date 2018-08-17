package ru.spbau.kirilenko.hw3TicTac.statistics;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class that represents table element.
 */
public class StatsElement {
    private SimpleStringProperty key;
    private SimpleIntegerProperty value;

    /**
     * Build new table element with key string and integer value
     * @param key element key
     * @param value element value
     */
    public StatsElement(String key, int value) {
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleIntegerProperty(value);
    }

    /**
     * Method that can change element key
     * @param key new element key
     */
    public void setKey(String key) {
        this.key.set(key);
    }

    /**
     * Method that can change element value
     * @param value new element value
     */
    public void setValue(int value) {
        this.value.set(value);
    }

    /**
     * Method that can get element key
     * @return current element key
     */
    public String getKey() {
        return key.get();
    }

    /**
     * Method that can get element value
     * @return current element value
     */
    public int getValue() {
        return value.get();
    }
}

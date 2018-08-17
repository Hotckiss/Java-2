package ru.spbau.kirilenko.hw4SimpleFTP;

import org.jetbrains.annotations.NotNull;

/**
 * Class that stores one entry of result of list command to server
 * Stores file(directory) name and flag that shows is this is file or folder
 */
public class FileInfo implements Comparable<FileInfo> {
    @NotNull private final String name;
    private final boolean isDirectory;

    /**
     * Constructs FileInfo with fixed name and folder flag
     *
     * @param fileName file(folder) name
     * @param isDirectory flag that {@code true} if it is directory {@code false} otherwise
     */
    @SuppressWarnings("WeakerAccess")
    public FileInfo(@NotNull String fileName, @NotNull Boolean isDirectory) {
        this.name = fileName;
        this.isDirectory = isDirectory;
    }

    /**
     * Returns name of file(folder)
     *
     * @return filename (folder name)
     */
    @NotNull public String getName() {
        return name;
    }

    /**
     * Returns folder flag
     * @return {@code true} if it is directory {@code false} otherwise
     */
    public boolean isDirectory() {
        return isDirectory;
    }

    /**
     * Returns string representation of file, contains name and folder flag
     *
     * @return string representation of file info
     */
    @Override
    public String toString() {
        return name + " :: " + (isDirectory ? "directory" : "file");
    }

    /**
     * Method that compares two entries by names and folder flags
     *
     * @param o other object to compare
     * @return {@code true} if all fields are equal {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof FileInfo) {
            FileInfo that = (FileInfo) o;
            return this.name.equals(that.name) && (this.isDirectory == that.isDirectory);
        }
        return false;
    }

    /**
     * Method that orders folders first and then files
     * @param o other object to compare
     * @return result of comparision
     */
    @Override
    public int compareTo(@NotNull FileInfo o) {
        if (isDirectory && !o.isDirectory) {
            return -1;
        }
        if (!isDirectory && o.isDirectory) {
            return 1;
        }

        return name.compareTo(o.name);
    }
}
package ru.spbau.kirilenko;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * Class that can compute directory MD5 hash in one thread or fork-join pool
 */
@SuppressWarnings("WeakerAccess")
public class DirsMD5 {
    private static final int BUFFER_SIZE = 1024;
    private final ForkJoinPool pool;

    /**
     * Creates single threaded directory hash
     */
    public DirsMD5() {
        pool = null;
    }

    /**
     * Creates concurrent directory hash
     * @param size size of pool
     */
    public DirsMD5(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }

        pool = new ForkJoinPool(size);
    }

    /**
     * Computing directory MD5 hash
     * Converts byte array with directory hash to string
     * @param path path to directory
     * @throws IOException if some errors with reading files occurred
     * @throws NoSuchAlgorithmException if platform does not supports MD5 hash
     * @return computed hash in string
     */
    @NotNull
    public String computeHash(@NotNull Path path) throws IOException, NoSuchAlgorithmException {
        return DatatypeConverter.printHexBinary(computeHashBytes(path));
    }

    /**
     * Computing directory MD5 hash
     * @param path path to directory
     * @throws IOException if some errors with reading files occurred
     * @throws NoSuchAlgorithmException if platform does not supports MD5 hash
     * @return computed hash bytes
     */
    @NotNull
    private byte[] computeHashBytes(@NotNull Path path) throws NoSuchAlgorithmException, IOException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        if (Files.isDirectory(path)) {
            digestDirectory(path, messageDigest);
        } else {
            digestFile(path, messageDigest);
        }

        return messageDigest.digest();
    }

    /**
     * Computing directory MD5 hash
     * @param path path to directory
     * @throws IOException if some errors with reading files occurred
     * @throws NoSuchAlgorithmException if platform does not supports MD5 hash
     */
    private void digestDirectory(Path path, MessageDigest messageDigest) throws IOException, NoSuchAlgorithmException {
        final List<Path> filePaths = Files.list(path).collect(Collectors.toList());
        messageDigest.update(path.getFileName().toString().getBytes());

        if (pool == null) {
            for (Path filePath : filePaths) {
                messageDigest.update(computeHashBytes(filePath));
            }
        } else {
            final ArrayList<RecursiveTask<byte[]>> tasks = new ArrayList<>();

            for (Path filePath : filePaths) {
                final RecursiveTask<byte[]> task = createRecursiveTask(filePath);
                tasks.add(task);
                task.fork();
            }

            for (RecursiveTask<byte[]> task : tasks) {
                messageDigest.update(task.join());
            }
        }
    }

    /**
     * Computing file MD5 hash
     * Converts byte array with directory hash to string
     * @param path path to directory
     * @throws IOException if some errors with reading files occurred
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private void digestFile(Path path, MessageDigest messageDigest) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        try (InputStream fileInputStream = Files.newInputStream(path);
             DigestInputStream inputStream = new DigestInputStream(fileInputStream, messageDigest)) {
            while (inputStream.read(buffer) != -1) {
            }
        }
    }

    /**
     * Fork join pool task with computing directory hash
     * @param path path to file or directory
     * @return created task
     */
    private RecursiveTask<byte[]> createRecursiveTask(@NotNull Path path) {
        return new RecursiveTask<byte[]>() {
            @Override
            protected byte[] compute() {
                try {
                    return computeHashBytes(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}

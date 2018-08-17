package ru.spbau.kirilenko;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Class that contains all tests of the hashers
 */
public class DirsMD5Test {
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    private Path rootPath;

    /**
     * Init test directory
     */
    @Before
    public void init() {
        rootPath = folder.getRoot().toPath();
    }

    /**
     * Simple test files
     */
    @Test
    public void testFiles() throws Exception {
        final Path file1 = Paths.get(rootPath.toString(), "test1.txt");
        Files.createFile(file1);
        Files.write(file1, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());

        final Path file2 = Paths.get(rootPath.toString(), "test2.txt");
        Files.createFile(file2);
        Files.write(file2, "aaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbb".getBytes());

        final Path file3 = Paths.get(rootPath.toString(), "test3.txt");
        Files.createFile(file3);

        final DirsMD5 hash = new DirsMD5();
        String result1 = hash.computeHash(file1);
        assertEquals("DC2F2F2462A0D72358B2F99389458606", result1);

        String result2 = hash.computeHash(file2);
        assertEquals("00ED4F0364E1B5740370FF647D401271", result2);

        String result3 = hash.computeHash(file3);
        assertEquals("D41D8CD98F00B204E9800998ECF8427E", result3);
    }

    /**
     * Testing concurrent hasher
     */
    @Test
    public void testFilesConcurrent() throws Exception {
        final Path file1 = Paths.get(rootPath.toString(), "test1.txt");
        Files.createFile(file1);
        Files.write(file1, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());

        final Path file2 = Paths.get(rootPath.toString(), "test2.txt");
        Files.createFile(file2);
        Files.write(file2, "aaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbb".getBytes());

        final Path file3 = Paths.get(rootPath.toString(), "test3.txt");
        Files.createFile(file3);

        final DirsMD5 hash = new DirsMD5(4);
        String result1 = hash.computeHash(file1);
        assertEquals("DC2F2F2462A0D72358B2F99389458606", result1);

        String result2 = hash.computeHash(file2);
        assertEquals("00ED4F0364E1B5740370FF647D401271", result2);

        String result3 = hash.computeHash(file3);
        assertEquals("D41D8CD98F00B204E9800998ECF8427E", result3);
    }

    /**
     * Testing hash big file
     */
    @Test
    public void testFilesBig() throws Exception {
        final Path file1 = Paths.get(rootPath.toString(), "test1.txt");
        Files.createFile(file1);
        for (int i = 0; i < 10000; i++) {
            Files.write(file1, "a".getBytes());
        }

        final DirsMD5 hash = new DirsMD5();
        String result1 = hash.computeHash(file1);
        System.out.println(result1);
    }
}
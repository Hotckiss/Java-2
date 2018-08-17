package ru.spbau.kirilenko.hw4SimpleFTP;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Class that tests usage of simple FTP server
 */
public class FTPTest {
    private static final int TEST_PORT = 8228;

    @Rule public TemporaryFolder rootPath = new TemporaryFolder();
    private static Server server;
    private Client client;

    /**
     * Simple listing directory test
     */
    @Test
    public void testList() throws IOException {
        rootPath.newFile("test1.txt");
        rootPath.newFile("test2.txt");
        rootPath.newFile("test3.txt");
        rootPath.newFolder("test", "test4.txt");
        List<FileInfo> expected = new ArrayList<>();
        expected.add(new FileInfo("test", true));
        expected.add(new FileInfo("test1.txt", false));
        expected.add(new FileInfo("test2.txt", false));
        expected.add(new FileInfo("test3.txt", false));
        List<FileInfo> actual = client.list(rootPath.getRoot().getAbsolutePath());

        assertEquals(actual.size(), 4);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    /**
     * Simple downloading file test
     */
    @Test
    public void testGet() throws IOException {
        File testFile = rootPath.newFile("test1.txt");
        Files.write(testFile.toPath(), "aaaaaaaaa".getBytes());

        assertArrayEquals("aaaaaaaaa".getBytes(), client.get(testFile.toPath().toString()));
    }

    /**
     * Test of downloading big file from server
     */
    @Test
    public void testGetBigFile() throws IOException {
        File testFile = rootPath.newFile("test1.txt");
        byte[] data = new byte[200000000];
        Random random = new Random();
        random.nextBytes(data);
        Files.write(testFile.toPath(), data);

        assertArrayEquals(data, client.get(testFile.toPath().toString()));
    }

    /**
     * Testing console application
     */
    @Test
    public void testApplication() throws IOException {
        File testFile = rootPath.newFile("test1.txt");
        Files.write(testFile.toPath(), "aaabbaaaa".getBytes());

        String data = "2 " + testFile.toPath().toString()+ " 0";
        InputStream testInput = new ByteArrayInputStream(data.getBytes("UTF-8"));
        System.setIn(testInput);
        Main.main(null);

        assertArrayEquals("aaabbaaaa".getBytes(), client.get(testFile.toPath().toString()));
    }

    /**
     * Testing download of non existing file
     */
    @Test
    public void testGetReturnZero() throws IOException {
        File testFile = rootPath.newFile("test1.txt");
        Files.write(testFile.toPath(), "aaaaaaaaa".getBytes());

        assertEquals(0, client.get("xxx_best_test_xxx.txt").length);
    }

    /**
     * Testing listing of non existing directory
     */
    @Test
    public void testListReturnZero() throws IOException {
        File testFile = rootPath.newFile("test1.txt");
        Files.write(testFile.toPath(), "aaaaaaaaa".getBytes());
        rootPath.newFile("test3.txt");
        rootPath.newFile("test4.txt");

        assertEquals(0, client.list("xxx_best_test_xxx_folder").size());
    }

    /**
     * Testing server with many different queries from client
     */
    @Test
    public void testManyQueries() throws IOException {
        File test1 = rootPath.newFile("test1.txt");
        File test2 = rootPath.newFile("test2.txt");
        File test3 = rootPath.newFile("test3.txt");
        rootPath.newFolder("test", "test4");

        Files.write(test1.toPath(), "aaabbaaaa".getBytes());
        Files.write(test2.toPath(), "xxxxxxxxx".getBytes());
        Files.write(test3.toPath(), "zzzzzzzzz".getBytes());

        List<FileInfo> expected = new ArrayList<>();
        expected.add(new FileInfo("test", true));
        expected.add(new FileInfo("test1.txt", false));
        expected.add(new FileInfo("test2.txt", false));
        expected.add(new FileInfo("test3.txt", false));
        List<FileInfo> actual = client.list(rootPath.getRoot().getAbsolutePath());

        assertEquals(actual.size(), 4);
        assertThat(actual, containsInAnyOrder(expected.toArray()));

        expected.clear();

        expected.add(new FileInfo("test4", true));

        actual = client.list(rootPath.getRoot().getAbsolutePath() + File.separator + "test");

        assertEquals(actual.size(), 1);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    /**
     * Complex test with many queries from different clients
     */
    @Test
    public void testManyConnections() throws IOException {
        Client client1 = new Client("localhost", TEST_PORT);
        Client client2 = new Client("localhost", TEST_PORT);

        List<FileInfo> expected = new ArrayList<>();
        List<String> allPaths = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String fileName = "test" + i + ".txt";
            File file = rootPath.newFile(fileName);
            Files.write(file.toPath(), String.valueOf(i).getBytes());
            expected.add(new FileInfo(fileName, false));
            allPaths.add(file.toPath().toString());
        }

        List<FileInfo> actual1 = client1.list(rootPath.getRoot().getAbsolutePath());
        List<FileInfo> actual2 = client2.list(rootPath.getRoot().getAbsolutePath());

        assertEquals(actual1.size(), 100);
        assertThat(actual1, containsInAnyOrder(expected.toArray()));

        assertEquals(actual2.size(), 100);
        assertThat(actual2, containsInAnyOrder(expected.toArray()));

        for (int i = 0; i < 100; i++) {
            assertArrayEquals(String.valueOf(i).getBytes(), client1.get(allPaths.get(i)));
            assertArrayEquals(String.valueOf(i).getBytes(), client2.get(allPaths.get(i)));
        }

        client1.disconnect();
        client2.disconnect();
    }

    /**
     * Init server before all tests
     */
    @BeforeClass
    public static void initServer() {
        server = new Server(TEST_PORT);
        server.start();
    }

    /**
     * Stop server after all tests
     */
    @AfterClass
    public static void stopServer() {
        server.stop();
    }

    /**
     * Create client and connection before each test
     */
    @Before
    public void connectClient() throws ConnectException {
        client = new Client("localhost", TEST_PORT);
    }

    /**
     * Disconnecting client after each test
     */
    @After
    public void disconnectClient() throws ConnectException {
        client.disconnect();
    }
}
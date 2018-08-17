package ru.spbau.kirilenko;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StatisticCollector {
    @Getter private static volatile HashMap<Integer, GraphPoint> plots = new HashMap<>();

    public static synchronized void writeReport(int sortSum, int reqSum, int avgClientTime, int key) {
        GraphPoint gp = plots.get(key);
        if (gp == null) {
            gp = new GraphPoint();
        }

        gp.sumSortingTimes += sortSum;
        gp.sumRequestTimes += reqSum;
        gp.sumAvgQueryTimes += avgClientTime;
        gp.clientsNumber++;

        plots.put(key, gp);
    }

    public static void writeToFile(String fileName) throws FileNotFoundException {
        PrintWriter writer1 = new PrintWriter(fileName + "_SortTime.txt");
        PrintWriter writer2 = new PrintWriter(fileName+ "_QueryTime.txt");
        PrintWriter writer3 = new PrintWriter(fileName+ "_ClientTime.txt");
        writeHdr(writer1);
        writeHdr(writer2);
        writeHdr(writer3);

        ArrayList<MyEntry> first = new ArrayList<>();
        ArrayList<MyEntry> second = new ArrayList<>();
        ArrayList<MyEntry> third = new ArrayList<>();
        plots.forEach((integer, graphPoint) -> {
            int delimiter = graphPoint.clientsNumber;
            first.add(new MyEntry(integer, graphPoint.sumSortingTimes / delimiter));
            second.add(new MyEntry(integer, graphPoint.sumRequestTimes / delimiter));
            third.add(new MyEntry(integer, graphPoint.sumAvgQueryTimes / delimiter));
        });

        Collections.sort(first);
        Collections.sort(second);
        Collections.sort(third);

        first.forEach(myEntry -> {
            writer1.print(myEntry.key + " ");
            writer1.println(myEntry.value);
        });


        second.forEach(myEntry -> {
            writer2.print(myEntry.key + " ");
            writer2.println(myEntry.value);
        });

        third.forEach(myEntry -> {
            writer3.print(myEntry.key + " ");
            writer3.println(myEntry.value);
        });

        writer1.close();
        writer2.close();
        writer3.close();
    }

    private static void writeHdr(PrintWriter writer) {
        writer.println("Configuration:");

        BenchmarkSettings config = BenchmarkSettings.getConfig();
        writer.println("Arch type: " + config.getType().toString());
        writer.println("Number of queries from each client: " + config.getQueriesNumber());
        writer.println("Variable: " + config.getBoundedValue().getType());
        writer.println("Range: from " + config.getBoundedValue().getLowerBound() + " to " + config.getBoundedValue().getUpperBound() + " with step " + config.getBoundedValue().getStep());

        switch (config.getBoundedValue().getType()) {
            case NUMBERS_IN_ARRAY:
                writer.println("Clients online: " + config.getClientsOnline());
                writer.println("Delay: " + config.getDelta());
                break;
            case CLIENTS_ONLINE:
                writer.println("Arrays size: " + config.getArraysSize());
                writer.println("Delay: " + config.getDelta());
                break;
            case QUERY_DELAY:
                writer.println("Arrays size: " + config.getArraysSize());
                writer.println("Clients online: " + config.getClientsOnline());
                break;
        }
    }

    public static class GraphPoint {
        @Getter private int sumSortingTimes = 0;
        @Getter private int sumRequestTimes = 0;
        @Getter private int sumAvgQueryTimes = 0;
        @Getter private int clientsNumber = 0;
    }

    @AllArgsConstructor
    public static class MyEntry implements Comparable<MyEntry> {
        @Getter @Setter private int key;
        @Getter @Setter private int value;

        @Override
        public int compareTo(@NotNull MyEntry o) {
            return key - o.key;
        }
    }
}

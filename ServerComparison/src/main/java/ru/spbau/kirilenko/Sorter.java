package ru.spbau.kirilenko;

public class Sorter {
    public static long sort(int[] array) {
        long sortingStartTime = System.currentTimeMillis();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i] < array[j]) {
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }

        return System.currentTimeMillis() - sortingStartTime;
    }
}

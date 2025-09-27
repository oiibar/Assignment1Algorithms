package org.example;
import org.example.algorithms.ClosestPairOfPoints;
import org.example.algorithms.MergeSort;
import org.example.algorithms.QuickSort;
import org.example.algorithms.DeterministicSelect;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final Random rnd = new Random();

    public static void main(String[] args) throws IOException {
        try (FileWriter csv = new FileWriter("metrics.csv")) {
            csv.write("Algorithm,ArraySize,Time(ms),Comparisons,Allocations,MaxDepth\n");

            testSorting(csv);
            testSelection(csv);
            testClosestPoints(csv);
        }
    }

    private static void testSorting(FileWriter csv) throws IOException {
        int[] sizes = {10, 100, 1000, 5000};

        for (int n : sizes) {
            int[] arr1 = randomArray(n);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);

            Metrics m1 = new Metrics();
            m1.startTimer();
            QuickSort.sort(arr1, m1);
            m1.stopTimer();
            csv.write(String.format("QuickSort,%d,%d,%d,%d,%d\n",
                    n, m1.getTime(), m1.getComparisons(), m1.getAllocations(), m1.getMaxDepth()));

            Metrics m2 = new Metrics();
            m2.startTimer();
            MergeSort.sort(arr2, m2);
            m2.stopTimer();
            csv.write(String.format("MergeSort,%d,%d,%d,%d,%d\n",
                    n, m2.getTime(), m2.getComparisons(), m2.getAllocations(), m2.getMaxDepth()));
        }
    }

    private static void testSelection(FileWriter csv) throws IOException {
        DeterministicSelect sel = new DeterministicSelect();

        for (int trial = 0; trial < 100; trial++) {
            int n = 50;
            int[] arr = rnd.ints(n, 0, 1000).toArray();
            Arrays.sort(Arrays.copyOf(arr, arr.length));

            int k = rnd.nextInt(n) + 1;
            Metrics m = new Metrics();

            m.startTimer();
            sel.select(Arrays.copyOf(arr, arr.length), k, m);
            m.stopTimer();

            csv.write(String.format("Selection,%d,%d,%d,%d,%d\n",
                    n, m.getTime(), m.getComparisons(), m.getAllocations(), m.getMaxDepth()));
        }
    }

    private static void testClosestPoints(FileWriter csv) throws IOException {
        int[] sizes = {100, 500, 2000};

        for (int n : sizes) {
            double[][] points = randomPoints(n);
            Metrics m = new Metrics();

            m.startTimer();
            ClosestPairOfPoints.minDistance(Arrays.copyOf(points, points.length), m);
            m.stopTimer();

            csv.write(String.format("ClosestPoints,%d,%d,%d,%d,%d\n",
                    n, m.getTime(), m.getComparisons(), m.getAllocations(), m.getMaxDepth()));
        }
    }

    private static int[] randomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(100000);
        return arr;
    }

    private static double[][] randomPoints(int n) {
        double[][] pts = new double[n][2];
        for (int i = 0; i < n; i++) {
            pts[i][0] = rnd.nextDouble() * 1000;
            pts[i][1] = rnd.nextDouble() * 1000;
        }
        return pts;
    }
}
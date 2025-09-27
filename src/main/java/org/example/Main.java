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
            csv.write("Algorithm,ArraySize,Time(ns),Comparisons,Allocations,MaxDepth\n");

            testSorting(csv);
            testSelection(csv);
            testClosestPoints(csv);
        }
    }

    private static void testSorting(FileWriter csv) throws IOException {
        int[] sizes = {10, 100, 1000, 5000};
        int trials = 100;

        for (int n : sizes) {
            long quickTime = 0, mergeTime = 0;
            long quickComparisons = 0, mergeComparisons = 0;
            long quickAllocations = 0, mergeAllocations = 0;
            long quickDepth = 0, mergeDepth = 0;

            for (int t = 0; t < trials; t++) {
                int[] arr1 = randomArray(n);
                int[] arr2 = Arrays.copyOf(arr1, arr1.length);

                Metrics m1 = new Metrics();
                m1.startTimer();
                QuickSort.sort(arr1, m1);
                m1.stopTimer();
                quickTime += m1.getTime();
                quickComparisons += m1.getComparisons();
                quickAllocations += m1.getAllocations();
                quickDepth += m1.getMaxDepth();

                Metrics m2 = new Metrics();
                m2.startTimer();
                MergeSort.sort(arr2, m2);
                m2.stopTimer();
                mergeTime += m2.getTime();
                mergeComparisons += m2.getComparisons();
                mergeAllocations += m2.getAllocations();
                mergeDepth += m2.getMaxDepth();
            }

            csv.write(String.format("QuickSort,%d,%d,%d,%d,%d\n",
                    n,
                    quickTime / trials,
                    quickComparisons / trials,
                    quickAllocations / trials,
                    quickDepth / trials));

            csv.write(String.format("MergeSort,%d,%d,%d,%d,%d\n",
                    n,
                    mergeTime / trials,
                    mergeComparisons / trials,
                    mergeAllocations / trials,
                    mergeDepth / trials));
        }
    }

    private static void testSelection(FileWriter csv) throws IOException {
        DeterministicSelect sel = new DeterministicSelect();
        int trials = 100;
        int n = 50;

        long totalTime = 0, totalComparisons = 0, totalAllocations = 0, totalDepth = 0;

        for (int t = 0; t < trials; t++) {
            int[] arr = rnd.ints(n, 0, 1000).toArray();
            int k = rnd.nextInt(n) + 1;

            Metrics m = new Metrics();
            m.startTimer();
            sel.select(Arrays.copyOf(arr, arr.length), k, m);
            m.stopTimer();

            totalTime += m.getTime();
            totalComparisons += m.getComparisons();
            totalAllocations += m.getAllocations();
            totalDepth += m.getMaxDepth();
        }

        csv.write(String.format("Selection,%d,%d,%d,%d,%d\n",
                n,
                totalTime / trials,
                totalComparisons / trials,
                totalAllocations / trials,
                totalDepth / trials));
    }

    private static void testClosestPoints(FileWriter csv) throws IOException {
        int[] sizes = {100, 500, 2000};
        int trials = 20;

        for (int n : sizes) {
            long totalTime = 0, totalComparisons = 0, totalAllocations = 0, totalDepth = 0;

            for (int t = 0; t < trials; t++) {
                double[][] points = randomPoints(n);

                Metrics m = new Metrics();
                m.startTimer();
                ClosestPairOfPoints.minDistance(Arrays.copyOf(points, points.length), m);
                m.stopTimer();

                totalTime += m.getTime();
                totalComparisons += m.getComparisons();
                totalAllocations += m.getAllocations();
                totalDepth += m.getMaxDepth();
            }

            csv.write(String.format("ClosestPoints,%d,%d,%d,%d,%d\n",
                    n,
                    totalTime / trials,
                    totalComparisons / trials,
                    totalAllocations / trials,
                    totalDepth / trials));
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

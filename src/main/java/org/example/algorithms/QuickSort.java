package org.example.algorithms;
import org.example.Metrics;
import java.util.Random;

public final class QuickSort {
    private static final Random rnd = new Random();

    private QuickSort() {}

    public static void sort(int[] arr, Metrics metrics) {
        sort(arr, 0, arr.length - 1, metrics);
    }

    private static void sort(int[] arr, int l, int r, Metrics metrics) {
        while (l < r) {
            metrics.enterRecursion();
            try {
                int pivotIndex = l + rnd.nextInt(r - l + 1);
                int pivot = arr[pivotIndex];
                swap(arr, pivotIndex, r);

                int p = partition(arr, l, r, pivot, metrics);

                if (p - l < r - p) {
                    sort(arr, l, p - 1, metrics);
                    l = p + 1;
                } else {
                    sort(arr, p + 1, r, metrics);
                    r = p - 1;
                }
            } finally {
                metrics.leaveRecursion();
            }
        }
    }

    private static int partition(int[] arr, int l, int r, int pivot, Metrics metrics) {
        int i = l;
        for (int j = l; j < r; j++) {
            metrics.incComparisons(1);
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, r);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
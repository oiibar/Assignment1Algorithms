package org.example.algorithms;

import org.example.Metrics;

public final class MergeSort {

    private MergeSort() {}

    public static void sort(int[] arr, Metrics metrics) {
        int[] buf = new int[arr.length];
        metrics.incAlloc(arr.length);
        sort(arr, buf, 0, arr.length - 1, metrics, 16);
    }

    private static void sort(int[] arr, int[] buf, int l, int r, Metrics metrics, int cutoff) {
        if (l >= r) return;
        metrics.start();
        try {
            if (r - l + 1 <= cutoff) {
                insertionSort(arr, l, r, metrics);
                return;
            }
            int mid = (l + r) / 2;
            sort(arr, buf, l, mid, metrics, cutoff);
            sort(arr, buf, mid + 1, r, metrics, cutoff);

            metrics.incComparisons(1);
            if (arr[mid] <= arr[mid + 1]) return;

            merge(arr, buf, l, mid, r, metrics);
        } finally {
            metrics.end();
        }
    }

    private static void insertionSort(int[] arr, int l, int r, Metrics metrics) {
        for (int i = l + 1; i <= r; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= l) {
                metrics.incComparisons(1);
                if (arr[j] <= key) break;
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
            metrics.incAlloc(1);
        }
    }

    private static void merge(int[] arr, int[] buf, int l, int mid, int r, Metrics metrics) {
        for (int t = l; t <= r; t++) {
            buf[t] = arr[t];
            metrics.incAlloc(1);
        }

        int i = l, j = mid + 1, k = l;
        while (i <= mid && j <= r) {
            metrics.incComparisons(1);
            if (buf[i] <= buf[j]) {
                arr[k++] = buf[i++];
            } else {
                arr[k++] = buf[j++];
            }
        }
        while (i <= mid) {
            arr[k++] = buf[i++];
        }
        while (j <= r) {
            arr[k++] = buf[j++];
        }
    }
}
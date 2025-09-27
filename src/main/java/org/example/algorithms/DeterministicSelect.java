package org.example.algorithms;

import org.example.Metrics;

public class DeterministicSelect {

    public int select(int[] arr, int k, Metrics metrics) {
        if (arr == null || arr.length == 0) return Integer.MIN_VALUE;
        if (k <= 0 || k > arr.length) throw new IllegalArgumentException();

        return select(arr, k, 0, arr.length - 1, metrics);
    }

    private int select(int[] arr, int k, int start, int end, Metrics metrics) {
        metrics.start();
        try {
            if (start == end) return arr[start];

            int pivot = findPivot(arr, start, end, metrics);

            int pivotIndex = start;
            for (int i = start; i <= end; i++) {
                metrics.incComparisons(1);
                if (arr[i] == pivot) {
                    swap(arr, i, end);
                    pivotIndex = end;
                    break;
                }
            }

            pivotIndex = partition(arr, start, end, metrics);

            int order = pivotIndex - start + 1;
            if (k == order) {
                return arr[pivotIndex];
            } else if (k < order) {
                return select(arr, k, start, pivotIndex - 1, metrics);
            } else {
                return select(arr, k - order, pivotIndex + 1, end, metrics);
            }
        } finally {
            metrics.end();
        }
    }

    private int findPivot(int[] arr, int start, int end, Metrics metrics) {
        int length = end - start + 1;
        if (length <= 5) {
            insertionSort(arr, start, end, metrics);
            return arr[start + length / 2];
        }

        int numGroups = (int) Math.ceil((double) length / 5);
        int[] medians = new int[numGroups];
        metrics.incAlloc(numGroups);

        for (int i = 0; i < numGroups; i++) {
            int gStart = start + i * 5;
            int gEnd = Math.min(gStart + 4, end);
            insertionSort(arr, gStart, gEnd, metrics);
            medians[i] = arr[gStart + (gEnd - gStart) / 2];
        }

        return select(medians, (numGroups + 1) / 2, 0, numGroups - 1, metrics);
    }

    private void insertionSort(int[] arr, int l, int r, Metrics metrics) {
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
        }
    }

    private int partition(int[] arr, int start, int end, Metrics metrics) {
        int pivot = arr[end];
        int i = start;
        for (int j = start; j < end; j++) {
            metrics.incComparisons(1);
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, end);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
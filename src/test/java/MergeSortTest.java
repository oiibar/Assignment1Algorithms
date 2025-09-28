import org.example.Metrics;
import org.example.algorithms.MergeSort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeSortTest {

    @Test
    void testSmallArray() {
        int[] arr = {5, 2, 9, 1, 3};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertTrue(isSorted(arr));
    }

    @Test
    void testLargeArray() {
        int[] arr = new java.util.Random().ints(1000, 0, 10000).toArray();
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertTrue(isSorted(arr));
    }

    private boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }
}

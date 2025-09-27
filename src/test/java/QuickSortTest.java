import java.util.Random;
import org.example.Metrics;
import org.example.algorithms.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    private final Random rnd = new Random(12345);

    @Test
    public void testQuickSortRandomAndAdversarialAndDepth() {
        int[] sizes = {10, 100, 1000};

        for (int n : sizes) {
            int[] a = rnd.ints(n, 0, 100000).toArray();
            int[] expected = Arrays.copyOf(a, a.length);
            Arrays.sort(expected);

            Metrics m = new Metrics();
            QuickSort.sort(a, m);
            assertArrayEquals(expected, a, "QuickSort failed on random array n=" + n);

            int[] sorted = new int[n];
            for (int i = 0; i < n; i++) sorted[i] = i;
            Metrics m2 = new Metrics();
            QuickSort.sort(sorted, m2);
            int[] expectedSorted = new int[n];
            for (int i = 0; i < n; i++) expectedSorted[i] = i;
            assertArrayEquals(expectedSorted, sorted, "QuickSort failed on sorted array n=" + n);

            int[] rev = new int[n];
            for (int i = 0; i < n; i++) rev[i] = n - i;
            Metrics m3 = new Metrics();
            QuickSort.sort(rev, m3);
            int[] expectedRev = Arrays.copyOf(rev, rev.length);
            Arrays.sort(expectedRev);
            assertArrayEquals(expectedRev, rev, "QuickSort failed on reverse-sorted array n=" + n);

            int allowed = 2 * (int) Math.floor(Math.log(n) / Math.log(2)) + 10;
            assertTrue(m3.getMaxDepth() <= allowed,
                    String.format("QuickSort recursion depth too large: %d > %d for n=%d", m3.getMaxDepth(), allowed, n));
        }
    }
}
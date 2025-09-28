import org.example.Metrics;
import org.example.algorithms.DeterministicSelect;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    private final Random rnd = new Random(2024);

    @Test
    public void testSelectAgainstSort100Trials() {
        DeterministicSelect sel = new DeterministicSelect();
        int trials = 100;

        for (int t = 0; t < trials; t++) {
            int n = 50;
            int[] a = rnd.ints(n, 0, 1000).toArray();
            int[] sorted = Arrays.copyOf(a, a.length);
            Arrays.sort(sorted);

            int k = rnd.nextInt(n) + 1;
            Metrics m = new Metrics();
            int pick = sel.select(Arrays.copyOf(a, a.length), k, m);
            int expected = sorted[k - 1];
            assertEquals(expected, pick, String.format("Select mismatch on trial %d (k=%d)", t, k));
        }
    }
}
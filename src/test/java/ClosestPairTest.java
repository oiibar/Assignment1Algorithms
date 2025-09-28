import org.example.Metrics;
import org.example.algorithms.ClosestPairOfPoints;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class ClosestPairTest {
    private final Random rnd = new Random(42);

    private double bruteForce(double[][] pts) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i][0] - pts[j][0];
                double dy = pts[i][1] - pts[j][1];
                double d = Math.sqrt(dx * dx + dy * dy);
                if (d < min) min = d;
            }
        }
        return min;
    }

    @Test
    public void testClosestPairSmall() {
        int[] sizes = {100, 500, 2000};
        for (int n : sizes) {
            double[][] pts = randomPoints(n);
            Metrics m = new Metrics();
            double fast = ClosestPairOfPoints.minDistance(copyPoints(pts), m);
            double brute = bruteForce(pts);
            assertEquals(brute, fast, 1e-9, "Closest pair mismatch for n=" + n);
        }
    }

    @Test
    public void testClosestPairLargeNonNegative() {
        int n = 10000;
        double[][] pts = randomPoints(n);
        Metrics m = new Metrics();
        double fast = ClosestPairOfPoints.minDistance(copyPoints(pts), m);
        assertTrue(Double.isFinite(fast) && fast >= 0, "Closest distance should be non-negative finite");
    }

    private double[][] randomPoints(int n) {
        double[][] pts = new double[n][2];
        for (int i = 0; i < n; i++) {
            pts[i][0] = rnd.nextDouble() * 10000;
            pts[i][1] = rnd.nextDouble() * 10000;
        }
        return pts;
    }

    private double[][] copyPoints(double[][] pts) {
        double[][] cp = new double[pts.length][2];
        for (int i = 0; i < pts.length; i++) {
            cp[i][0] = pts[i][0];
            cp[i][1] = pts[i][1];
        }
        return cp;
    }
}
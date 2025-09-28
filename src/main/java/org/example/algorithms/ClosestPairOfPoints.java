package org.example.algorithms;
import org.example.Metrics;
import java.util.*;

public class ClosestPairOfPoints {

    public static double minDistance(double[][] points, Metrics metrics) {
        double[][] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(p -> p[0]));
        double[][] pointsByY = points.clone();
        Arrays.sort(pointsByY, Comparator.comparingDouble(p -> p[1]));

        return minDistUtil(pointsByX, pointsByY, 0, points.length, metrics);
    }

    private static double minDistUtil(double[][] pointsByX, double[][] pointsByY, int left, int right, Metrics metrics) {
        metrics.enterRecursion();
        try {
            int n = right - left;
            if (n <= 3) {
                double minDist = Double.MAX_VALUE;
                for (int i = left; i < right; i++) {
                    for (int j = i + 1; j < right; j++) {
                        minDist = Math.min(minDist, distance(pointsByX[i], pointsByX[j], metrics));
                    }
                }
                Arrays.sort(pointsByX, left, right, Comparator.comparingDouble(p -> p[1]));
                return minDist;
            }

            int mid = (left + right) / 2;
            double midX = pointsByX[mid][0];

            double[][] leftY = new double[mid - left][];
            double[][] rightY = new double[right - mid][];
            int li = 0, ri = 0;
            for (double[] p : pointsByY) {
                if (p[0] <= midX && li < leftY.length) leftY[li++] = p;
                else rightY[ri++] = p;
            }

            double dl = minDistUtil(pointsByX, leftY, left, mid, metrics);
            double dr = minDistUtil(pointsByX, rightY, mid, right, metrics);
            double d = Math.min(dl, dr);

            List<double[]> strip = new ArrayList<>();
            for (double[] p : pointsByY) {
                if (Math.abs(p[0] - midX) < d) strip.add(p);
            }

            double minDist = d;
            for (int i = 0; i < strip.size(); i++) {
                for (int j = i + 1; j < strip.size() && j <= i + 7; j++) {
                    minDist = Math.min(minDist, distance(strip.get(i), strip.get(j), metrics));
                }
            }
            return minDist;
        } finally {
            metrics.leaveRecursion();
        }
    }

    private static double distance(double[] p1, double[] p2, Metrics metrics) {
        metrics.incComparisons(1);
        double dx = p1[0] - p2[0];
        double dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }
}

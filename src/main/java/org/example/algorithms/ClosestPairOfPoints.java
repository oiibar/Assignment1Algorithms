package org.example.algorithms;
import org.example.Metrics;

import java.util.*;

public class ClosestPairOfPoints {

    private static double distance(double[] p1, double[] p2, Metrics metrics) {
        metrics.incComparisons(1);
        return Math.sqrt((p1[0] - p2[0]) * (p1[0] - p2[0]) +
                (p1[1] - p2[1]) * (p1[1] - p2[1]));
    }

    private static double minDistUtil(double[][] points, int left, int right, Metrics metrics) {
        metrics.start();
        try {
            int n = right - left;
            if (n <= 3) {
                double minDist = Double.MAX_VALUE;
                for (int i = left; i < right; i++) {
                    for (int j = i + 1; j < right; j++) {
                        minDist = Math.min(minDist, distance(points[i], points[j], metrics));
                    }
                }
                return minDist;
            }

            int mid = (left + right) / 2;
            double midX = points[mid][0];

            double dl = minDistUtil(points, left, mid, metrics);
            double dr = minDistUtil(points, mid, right, metrics);
            double d = Math.min(dl, dr);

            List<double[]> strip = new ArrayList<>();
            for (int i = left; i < right; i++) {
                if (Math.abs(points[i][0] - midX) < d) {
                    strip.add(points[i]);
                }
            }

            strip.sort(Comparator.comparingDouble(p -> p[1]));

            double minDist = d;
            for (int i = 0; i < strip.size(); i++) {
                for (int j = i + 1; j < strip.size() && (strip.get(j)[1] - strip.get(i)[1]) < minDist; j++) {
                    minDist = Math.min(minDist, distance(strip.get(i), strip.get(j), metrics));
                }
            }
            return minDist;
        } finally {
            metrics.end();
        }
    }

    public static double minDistance(double[][] points, Metrics metrics) {
        Arrays.sort(points, Comparator.comparingDouble(p -> p[0]));
        return minDistUtil(points, 0, points.length, metrics);
    }
}
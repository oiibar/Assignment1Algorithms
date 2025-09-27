package org.example;

public final class Metrics {
    private long time = 0;
    private long startTime = 0;
    private long comparisons = 0;
    private long allocations = 0;
    private int currDepth = 0;
    private int maxDepth = 0;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        time += System.nanoTime() - startTime;
    }

    public void start() {
        currDepth++;
        if (currDepth > maxDepth) {
            maxDepth = currDepth;
        }
    }

    public void end() {
        currDepth--;
    }

    public void incComparisons(long n) { comparisons += n; }
    public void incAlloc(long n) { allocations += n; }

    public long getComparisons() { return comparisons; }
    public long getAllocations() { return allocations; }
    public int getMaxDepth() { return maxDepth; }
    public long getTime() { return time; }

    @Override
    public String toString() {
        return "Metrics{" +
                "time=" + time +
                ", comparisons=" + comparisons +
                ", allocations=" + allocations +
                ", maxDepth=" + maxDepth +
                '}';
    }
}
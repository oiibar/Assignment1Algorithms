package org.example;

public final class Metrics {
    private long totalTime = 0;
    private long startTime = 0;
    private long comparisons = 0;
    private long allocations = 0;
    private int currDepth = 0;
    private int maxDepth = 0;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        totalTime += System.nanoTime() - startTime;
    }

    public void enterRecursion() {
        currDepth++;
        if (currDepth > maxDepth) {
            maxDepth = currDepth;
        }
    }

    public void leaveRecursion() {
        currDepth--;
    }

    public void incComparisons(long n) { comparisons += n; }
    public void incAllocations(long n) { allocations += n; }

    public long getComparisons() { return comparisons; }
    public long getAllocations() { return allocations; }
    public int getMaxDepth() { return maxDepth; }
    public long getTime() { return totalTime; }

    @Override
    public String toString() {
        return "Metrics{" +
                "time=" + totalTime +
                ", comparisons=" + comparisons +
                ", allocations=" + allocations +
                ", maxDepth=" + maxDepth +
                '}';
    }
}

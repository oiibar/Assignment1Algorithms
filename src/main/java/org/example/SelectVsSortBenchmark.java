package org.example;

import org.example.algorithms.DeterministicSelect;
import org.openjdk.jmh.annotations.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class SelectVsSortBenchmark {

    @Param({"1000", "10000", "50000"})
    private int n;

    private int[] data;
    private int k;
    private DeterministicSelect selector;
    private Random rnd;

    @Setup(Level.Iteration)
    public void setup() {
        rnd = new Random(12345);
        data = rnd.ints(n, 0, 1_000_000).toArray();
        k = rnd.nextInt(n) + 1;
        selector = new DeterministicSelect();
    }

    @Benchmark
    public int selectMedianOfMedians() {
        int[] copy = Arrays.copyOf(data, data.length);
        return selector.select(copy, k, new org.example.Metrics());
    }

    @Benchmark
    public int sortAndPick() {
        int[] copy = Arrays.copyOf(data, data.length);
        Arrays.sort(copy);
        return copy[k - 1];
    }
}

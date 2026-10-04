package daa.bench;

import daa.ds.DynamicArray;
import daa.ds.IntList;
import daa.ds.MinHeap;
import daa.ds.MyLinkedList;
import daa.metrics.CsvWriter;
import daa.metrics.Metrics;
import daa.metrics.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1_000, 10_000, 100_000};
    private static final int RUNS = 5;
    private static final int WARMUP_RUNS = 1;

    private final List<Result> results = new ArrayList<>();

    public void runAll() throws IOException {
        results.clear();

        for (int n : SIZES) {
            int[] data = generateData(n);

            runW1(n, data);
            runW2(n, data);
            runW3(n, data, "head");
            runW3(n, data, "middle");
            runW4(n, data);
        }

        CsvWriter.write("results/results.csv", results);
        System.out.println("Benchmark complete.");
        System.out.println("Results saved to results/results.csv");
    }

    private int[] generateData(int n) {
        Random random = new Random(42);
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt();
        }

        return data;
    }

    // W1 - Random Access
    private void runW1(int n, int[] data) {
        int[] indexes = new int[10_000];
        Random random = new Random(42);

        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = random.nextInt(n);
        }

        benchmarkW1Structure("DynamicArray", n, data, indexes);
        benchmarkW1Structure("MyLinkedList", n, data, indexes);
    }

    private void benchmarkW1Structure(String structureName, int n,
                                      int[] data, int[] indexes) {

        for (int warmup = 0; warmup < WARMUP_RUNS; warmup++) {
            IntList list = createList(structureName, data);

            for (int index : indexes) {
                list.get(index);
            }
        }

        double[] times = new double[RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            IntList list = createList(structureName, data);
            Metrics metrics = getMetrics(list);
            metrics.reset();

            long start = System.nanoTime();

            for (int index : indexes) {
                list.get(index);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        results.add(new Result(
                "W1", "-", structureName, n, median(times),
                steps, moves, comparisons
        ));
    }

    // W2 - Search
    private void runW2(int n, int[] data) {
        int[] queries = createSearchQueries(data);

        benchmarkW2Structure("DynamicArray", n, data, queries);
        benchmarkW2Structure("MyLinkedList", n, data, queries);
    }

    private int[] createSearchQueries(int[] data) {
        int[] queries = new int[1_000];

        for (int i = 0; i < 500; i++) {
            queries[i] = data[i % data.length];
        }

        // Values guaranteed not to occur in data.
        for (int i = 500; i < 1_000; i++) {
            queries[i] = findAbsentValue(data, i);
        }

        return queries;
    }

    private int findAbsentValue(int[] data, int seed) {
        int candidate = Integer.MIN_VALUE + seed;

        while (containsInArray(data, candidate)) {
            candidate++;
        }

        return candidate;
    }

    private boolean containsInArray(int[] data, int value) {
        for (int element : data) {
            if (element == value) {
                return true;
            }
        }

        return false;
    }

    private void benchmarkW2Structure(String structureName, int n,
                                      int[] data, int[] queries) {

        for (int warmup = 0; warmup < WARMUP_RUNS; warmup++) {
            IntList list = createList(structureName, data);

            for (int query : queries) {
                list.contains(query);
            }
        }

        double[] times = new double[RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            IntList list = createList(structureName, data);
            Metrics metrics = getMetrics(list);
            metrics.reset();

            long start = System.nanoTime();

            for (int query : queries) {
                list.contains(query);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        results.add(new Result(
                "W2", "-", structureName, n, median(times),
                steps, moves, comparisons
        ));
    }

    // W3 - Insert & Remove
    private void runW3(int n, int[] data, String variant) {
        benchmarkW3Structure("DynamicArray", n, data, variant);
        benchmarkW3Structure("MyLinkedList", n, data, variant);
    }

    private void benchmarkW3Structure(String structureName, int n,
                                      int[] data, String variant) {

        for (int warmup = 0; warmup < WARMUP_RUNS; warmup++) {
            IntList list = createList(structureName, data);
            performW3(list, n, variant);
        }

        double[] times = new double[RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            IntList list = createList(structureName, data);
            Metrics metrics = getMetrics(list);
            metrics.reset();

            long start = System.nanoTime();

            performW3(list, n, variant);

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        results.add(new Result(
                "W3", variant, structureName, n, median(times),
                steps, moves, comparisons
        ));
    }

    private void performW3(IntList list, int n, String variant) {
        if ("head".equals(variant)) {
            for (int i = 0; i < 1_000; i++) {
                list.add(0, i);
            }

            for (int i = 0; i < 1_000; i++) {
                list.remove(0);
            }
        } else {
            int index = n / 2;

            for (int i = 0; i < 1_000; i++) {
                list.add(index, i);
            }

            for (int i = 0; i < 1_000; i++) {
                list.remove(index);
            }
        }
    }

    // W4 - Priority Processing
    private void runW4(int n, int[] data) {

        for (int warmup = 0; warmup < WARMUP_RUNS; warmup++) {
            MinHeap heap = createHeap(data);
            extractAndCheck(heap);
        }

        double[] times = new double[RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            MinHeap heap = new MinHeap(Math.max(1, n));
            heap.resetMetrics();

            long start = System.nanoTime();

            for (int value : data) {
                heap.insert(value);
            }

            extractAndCheck(heap);

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = heap.getMetrics().getSteps();
                moves = heap.getMetrics().getMoves();
                comparisons = heap.getMetrics().getComparisons();
            }
        }

        results.add(new Result(
                "W4", "-", "MinHeap", n, median(times),
                steps, moves, comparisons
        ));
    }

    private void extractAndCheck(MinHeap heap) {
        if (heap.isEmpty()) {
            return;
        }

        int previous = heap.extractMin();

        while (!heap.isEmpty()) {
            int current = heap.extractMin();

            if (current < previous) {
                throw new IllegalStateException(
                        "MinHeap output is not non-decreasing"
                );
            }

            previous = current;
        }
    }

    private IntList createList(String structureName, int[] data) {
        IntList list;

        if ("DynamicArray".equals(structureName)) {
            list = new DynamicArray(Math.max(1, data.length));
        } else {
            list = new MyLinkedList();
        }

        for (int value : data) {
            list.add(value);
        }

        return list;
    }

    private MinHeap createHeap(int[] data) {
        MinHeap heap = new MinHeap(Math.max(1, data.length));

        for (int value : data) {
            heap.insert(value);
        }

        return heap;
    }

    private Metrics getMetrics(IntList list) {
        if (list instanceof DynamicArray) {
            return ((DynamicArray) list).getMetrics();
        }

        return ((MyLinkedList) list).getMetrics();
    }

    private double median(double[] values) {
        double[] copy = values.clone();
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }
}
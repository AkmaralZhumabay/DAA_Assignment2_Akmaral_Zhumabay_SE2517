package daa.metrics;

public class Result {
    private final String workload;
    private final String variant;
    private final String structure;
    private final int n;
    private final double timeMs;
    private final long steps;
    private final long moves;
    private final long comparisons;

    public Result(String workload, String variant, String structure, int n,
                  double timeMs, long steps, long moves, long comparisons) {
        this.workload = workload;
        this.variant = variant;
        this.structure = structure;
        this.n = n;
        this.timeMs = timeMs;
        this.steps = steps;
        this.moves = moves;
        this.comparisons = comparisons;
    }

    public String getWorkload() {
        return workload;
    }

    public String getVariant() {
        return variant;
    }

    public String getStructure() {
        return structure;
    }

    public int getN() {
        return n;
    }

    public double getTimeMs() {
        return timeMs;
    }

    public long getSteps() {
        return steps;
    }

    public long getMoves() {
        return moves;
    }

    public long getComparisons() {
        return comparisons;
    }
}
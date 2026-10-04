package daa;

import daa.bench.Benchmark;

public class Main {

    public static void main(String[] args) {
        try {
            Benchmark benchmark = new Benchmark();
            benchmark.runAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
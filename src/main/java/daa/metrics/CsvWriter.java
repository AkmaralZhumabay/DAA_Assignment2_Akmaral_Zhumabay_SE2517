package daa.metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void write(String filename, List<Result> results) throws IOException {
        File file = new File(filename);
        File parent = file.getParentFile();

        if (parent != null) {
            parent.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");

            for (Result result : results) {
                writer.write(
                        result.getWorkload() + "," +
                                result.getVariant() + "," +
                                result.getStructure() + "," +
                                result.getN() + "," +
                                result.getTimeMs() + "," +
                                result.getSteps() + "," +
                                result.getMoves() + "," +
                                result.getComparisons() + "\n"
                );
            }
        }
    }
}
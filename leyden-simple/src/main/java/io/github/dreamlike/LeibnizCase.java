package io.github.dreamlike;

/*
COPYRIGHT
https://github.com/Glavo/leibniz-benchmark/blob/main/src/leibniz2.java
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class LeibnizCase implements LeydenCase {
    @Override
    public void run() {
        int count = 1_0000;
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        CompletableFuture<double[]>[] allTask = new CompletableFuture[availableProcessors];

        for (int i = 0; i < availableProcessors; i++) {
            allTask[i] = CompletableFuture.supplyAsync(() -> {
                double[] doubles = new double[count];
                for (int j = 0; j < count; j++) {
                    doubles[j] = singeCal();
                }
                return doubles;
            });
        }

        double[] doubles = Arrays.stream(allTask)
                .map(CompletableFuture::join)
                .flatMapToDouble(Arrays::stream)
                .toArray();

        System.out.println(Arrays.stream(doubles).sum() / (count * availableProcessors));
    }

    public double singeCal() {
        int rounds = 10_0000;
        rounds += 2;

        double pi = 1;
        double x = 1;

        for (int i = 2; i < rounds; i++) {
            x *= -1;
            pi += (x / (2 * i - 1));
        }
        pi *= 4;
        return pi;
    }
}

package org.might.lambda.functional.implementation;

import java.util.stream.IntStream;

public class Chapter6 {

    public static int sequentialSumOfSquares(IntStream range) {
        return range
                .map(x -> x * x)
                .sum();
    }

    public static int parallelSumOfSquares(IntStream range) {
        return range
                .parallel()
                .map(x -> x * x)
                .sum();
    }
}

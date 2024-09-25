package org.might.lambda.functional.implementation;

import java.util.List;
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

    public static int multiplyThrough(List<Integer> linkedListOfNumbers) {
        return linkedListOfNumbers.stream()
                .reduce(5, (acc, x) -> acc * x);
    }

    public static int multiplyThroughParallel(List<Integer> linkedListOfNumbers) {
        return 5 * linkedListOfNumbers
                .parallelStream()
                .reduce(1, (acc, x) -> x * acc);
    }

    public static int slowSumOfSquares(List<Integer> values) {
        return values.parallelStream()
                .map(x -> x * x)
                .reduce(0, (acc, x) -> acc + x);
    }

    public static int fastSumOfSquares(List<Integer> values) {
        return values.parallelStream()
                .mapToInt(x -> x * x)
                .sum();
    }
}

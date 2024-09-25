import org.junit.jupiter.api.Test;
import org.might.lambda.functional.implementation.Fibonacci;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 1/11/2024
 * Time: 2:34 PM
 */
public class FibonacciTest {
    @Test
    public void fibonacciMatchesOpeningSequence() {
        List<Long> fibonacciSequence = Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L);
        Fibonacci fibonacci = new Fibonacci();
        IntStream.range(0, fibonacciSequence.size())
                .forEach(x -> {
                    long result = fibonacci.fibonacci(x);
                    long expectedResult = fibonacciSequence.get(x);
                    assertEquals(expectedResult, result);
                });
    }

}
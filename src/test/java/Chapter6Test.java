import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.might.lambda.functional.implementation.Chapter6;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class Chapter6Test {

    @Test
    public void sequentialSumOfSquaresTest() {
        assertEquals(5, Chapter6.sequentialSumOfSquares(IntStream.range(1, 3)));
        assertEquals(328350, Chapter6.sequentialSumOfSquares(IntStream.range(1, 100)));
    }

    @Test
    public void parallelSumOfSquaresTest() {
        assertEquals(5, Chapter6.parallelSumOfSquares(IntStream.range(1, 3)));
        assertEquals(328350, Chapter6.parallelSumOfSquares(IntStream.range(1, 100)));
    }

    @Test
    public void multipleThrowTest() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int resultSequential = Chapter6.multiplyThrough(numbers);
        int resultParallel = Chapter6.multiplyThroughParallel(numbers);
        assertEquals(30, resultSequential);
        assertEquals(30, resultParallel);
    }
}

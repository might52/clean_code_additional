import org.junit.jupiter.api.Test;
import org.might.lambda.functional.implementation.GroupingBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 1/11/2024
 * Time: 9:34 AM
 */
public class GroupingByTest {
    @Test
    public void stringsByLength() {
        GroupingBy<String, Integer> stringIntegerGroupingBy = new GroupingBy<>(String::length);
        Map<Integer, List<String>> results = Stream.of("a", "b", "cc", "dd")
                .collect(stringIntegerGroupingBy);
        System.out.println(String.format("Result for grouping string by length: %s", results));
        assertEquals(2, results.size());
        assertEquals(asList("a", "b"), results.get(1));
        assertEquals(asList("cc", "dd"), results.get(2));
    }

}
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.might.lambda.functional.implementation.Questions5;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 1/10/2024
 * Time: 1:53 PM
 */
@ExtendWith(MockitoExtension.class)
public class TestQuestion5 {

    private Stream<String> getFullNameStream() {
        return Stream.of("John Lennon", "Paul McCartney", "George Harrison",
                "Ringo Starr", "Pete Best", "Stuart Sutcliffe");
    }

    private Stream<String> getNameStream() {
        return Stream.of("John", "Paul", "George", "John", "Paul", "John");
    }

    @Test
    public void testGetLongestNameReduce() {
        String longestName = new Questions5().getLongestNameReduce(getFullNameStream());
        System.out.println(String.format("Test result for %s: %s", "testGetLongestNameReduce", longestName));
        assertEquals("Stuart Sutcliffe", longestName);
    }

    @Test
    public void testGetLongestNameCollector() {
        String longestName = new Questions5().getLongestNameCollector(getFullNameStream());
        System.out.println(String.format("Test result for %s: %s", "testGetLongestNameCollector", longestName));
        assertEquals("Stuart Sutcliffe", longestName);
    }

    @Test
    public void testGetNamesCountMap() {
        Map<String, Long> calculatedNames = new Questions5().getNameAmountMap(getNameStream());
        System.out.println(String.format("Calculated names: %s", calculatedNames));
        assertEquals(3, calculatedNames.size());
        assertEquals(Long.valueOf(3), calculatedNames.get("John"));
        assertEquals(Long.valueOf(2), calculatedNames.get("Paul"));
        assertEquals(Long.valueOf(1), calculatedNames.get("George"));
    }
}

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlgorithmsTest {

    @Test
    public void binarySearchTest() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        Assertions.assertEquals(69, binarySearch(list, 69));

    }

    private Integer binarySearch(List<Integer> list, Integer item) {
        int low = 0;
        int counter = 0;
        int high = list.size();
        while (low <= high) {
            System.out.printf("Iteration: %s\n\r", counter);
            counter++;
            int midle = (low + high) / 2;
            int guess = list.get(midle);
            if (guess == item) {
                return guess;
            }

            if (guess > item) {
                high = midle - 1;
            } else {
                low = midle + 1;
            }
        }
        return null;
    }
}

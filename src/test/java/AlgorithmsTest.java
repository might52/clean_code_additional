import java.lang.reflect.Field;
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

    @Test
    public void SelectionSortTest() {
        List<Integer> list = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 99; i >= 0; i--) {
            list.add(i);
        }
        for (int i = 0; i < 100; i++) {
            expected.add(i);
        }

        Assertions.assertArrayEquals(expected.toArray(), selectionSort(list).toArray());
    }

    private List<Integer> selectionSort(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int smallestIndex = findMinInteger(list);
            result.add(list.get(smallestIndex));
            list.remove(smallestIndex);
        }
        return result;
    }

    private Integer findMinInteger(List<Integer> list) {
        int smallIndex = 0;
        int smallValue = list.get(smallIndex);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < smallValue) {
                smallValue = list.get(i);
                smallIndex = i;
            }
        }
        return smallIndex;
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

    @Test
    public void testPrivateAccess() {
        class Victim {
            private int field = 42;
        }

        Victim victim = new Victim();
        try {
            Field field = Victim.class.getDeclaredField("field");
            field.setAccessible(true);
            int fieldValue = (int) field.get(victim);
            System.out.println(fieldValue);
            Assertions.assertEquals(42, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testGenerics() {
        List<String> strings = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        objects = (List) strings;
        System.out.println(objects);
        objects.add(Integer.valueOf(1));
        System.out.println(objects);
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlgorithmsChapter4 {

    @Test
    public void sumTest() {
        int result = 8;
        int sum = sumArrayRecursive(new int[]{1, 2, 3, 2}, 0);
        Assertions.assertEquals(result, sum);
    }

    private int sumArrayRecursive(int[] ints, int sum) {
        if (ints.length == 0) {
            return 0;
        }
        sum = sum + ints[0];
        int[] newInt = Arrays.copyOfRange(ints, 1, ints.length);

        if (newInt.length == 0) {
            return sum;
        }

        return sumArrayRecursive(newInt, sum);
    }

    @Test
    public void maxValueTest() {
        int result = 3;
        int sum = maxValueArrayRecursive(new int[]{1, 2, 3, 2}, 0);
        Assertions.assertEquals(result, sum);
    }

    private int maxValueArrayRecursive(int[] ints, int maxValue) {
        if (ints.length == 0) {
            return 0;
        }
        if (ints[0] > maxValue) {
            maxValue = ints[0];
        }
        int[] newInt = Arrays.copyOfRange(ints, 1, ints.length);

        if (newInt.length == 0) {
            return maxValue;
        }

        return maxValueArrayRecursive(newInt, maxValue);
    }

    @Test
    public void quickSort() {
        List<Integer> arr = new ArrayList<>(Arrays.asList(1, 3, 5, 66, 8, 0, 2, 4, 234));
        List<Integer> result = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 8, 66, 234));
        Assertions.assertArrayEquals(result.toArray(), quickSortRecursive(arr).toArray());
    }

    private List<Integer> quickSortRecursive(List<Integer> array) {
        if (array.size() < 2) {
            return array;
        }
        Integer pivot = array.getFirst();
        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) < pivot) {
                less.add(array.get(i));
            }
        }
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) > pivot) {
                greater.add(array.get(i));
            }
        }
        return Stream.of(quickSortRecursive(less), Collections.singletonList(pivot), quickSortRecursive(greater))
                     .flatMap(Collection::stream).collect(Collectors.toList());
    }
}

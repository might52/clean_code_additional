import java.util.Arrays;
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
}

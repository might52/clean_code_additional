import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NeetCodeTest {

    @Test
    public void checkTheTargetGoalIntegerSum() {
        int[] nums = new int[]{3, 4, 5, 6};
        int target = 7;
        int[] expected = new int[]{0, 1};
        Assertions.assertTrue(Arrays.equals(expected, twoSum(nums, target)));
        nums = new int[]{4, 5, 6};
        target = 10;
        expected = new int[]{0, 2};
        Assertions.assertTrue(Arrays.equals(expected, twoSum(nums, target)));
        nums = new int[]{5, 5};
        target = 10;
        expected = new int[]{0, 1};
        Assertions.assertTrue(Arrays.equals(expected, twoSum(nums, target)));
    }


    private int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> keyMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (keyMap.containsKey(target - nums[i])) {
                return new int[]{keyMap.get(target - nums[i]), i};
            }
            keyMap.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

}

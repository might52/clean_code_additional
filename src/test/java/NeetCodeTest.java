import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NeetCodeTest {

    @Test
    public void checkTheTargetGoalIntegerSum() {
        int[] nums = new int[]{3, 4, 5, 6};
        Assertions.assertArrayEquals(new int[]{0, 1}, twoSum(nums, 7));
        nums = new int[]{4, 5, 6};
        Assertions.assertArrayEquals(new int[]{0, 2}, twoSum(nums, 10));
        nums = new int[]{5, 5};
        Assertions.assertArrayEquals(new int[]{0, 1}, twoSum(nums, 10));
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

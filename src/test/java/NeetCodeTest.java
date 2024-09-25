import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Test
    public void checkTheAnagramGroups() {
//        String[] words = new String[]{"act", "pots", "tops", "cat", "stop", "hat"};
//        List<List<String>> expected = new ArrayList<>();
//        expected.add(Arrays.asList("act","cat"));
//        expected.add(Arrays.asList("stop", "pots", "tops"));
//        expected.add(Arrays.asList("hat"));
//        Assertions.assertArrayEquals(expected.toArray(), getAnagramGroups(words).toArray());
//        words = new String[]{"x"};
//        expected.clear();
//        expected.add(new ArrayList() {{
//            add("x");
//        }});
//        Assertions.assertEquals(expected, getAnagramGroups(words));
//        words = new String[]{""};
//        expected.clear();
//        expected.add(new ArrayList<>() {{
//            add("x");
//        }});
//        Assertions.assertEquals(expected, getAnagramGroups(words));
    }

    private List<List<String>> getAnagramGroups(String[] words) {
        Map<String, List<String>> ans = new HashMap<>();
        for (String s : words) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String key = Arrays.toString(count);
            if (!ans.containsKey(key)) {
                ans.put(key, new ArrayList<>());
            }
            ans.get(key).add(s);
        }
        return new ArrayList<>(ans.values());
    }
}

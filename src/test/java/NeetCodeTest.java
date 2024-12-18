import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String[] words = new String[]{"act", "pots", "tops", "cat", "stop", "hat"};
        List<List<String>> expected = new ArrayList<>();
        expected.add(List.of("act", "cat"));
        expected.add(List.of("pots", "tops", "stop"));
        expected.add(List.of("hat"));
        expected.sort(Comparator.comparingInt(List::size));
        List<List<String>> result = new ArrayList<>();
        result = getAnagramGroups(words);
        result.sort(Comparator.comparingInt(List::size));
        Assertions.assertArrayEquals(expected.toArray(), result.toArray());
        words = new String[]{"x"};
        expected.clear();
        expected.add(List.of("x"));
        Assertions.assertArrayEquals(expected.toArray(), getAnagramGroups(words).toArray());
        words = new String[]{""};
        expected.clear();
        expected.add(List.of(""));
        Assertions.assertArrayEquals(expected.toArray(), getAnagramGroups(words).toArray());
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

    @Test
    public void frequentKElementsTest() {
        int[] nums = new int[]{1, 2, 2, 3, 3, 3};
        int[] expected = new int[]{3, 2};
        int expectedCount = 2;
        Assertions.assertArrayEquals(expected, frequentKElements(nums, expectedCount));
        nums = new int[]{7, 7};
        expected = new int[]{7};
        expectedCount = 1;
        Assertions.assertArrayEquals(expected, frequentKElements(nums, expectedCount));
    }

    private int[] frequentKElements(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        List<int[]> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            result.add(new int[]{entry.getKey(), entry.getValue()});
        }
        result.sort((a, b) -> b[1] - a[1]);
        return result.stream().mapToInt(el -> el[0]).limit(k).toArray();
    }
}

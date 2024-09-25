import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class InterviewTest {

    @Test
    public void getSequenceinItialization() {
        System.out.println(TestClass.v);
        new TestClass().a();
        System.out.println(TestClass.v);
    }

    /**
     * Read more: https://www.baeldung.com/java-collections
     */
    @Test
    public void collectionFramework_Demo() {
        // iterable -> collection -> list, queue, set
        // map not in collections

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> vector = new Vector<>();
        List<Integer> stack = new Stack<>();

        Queue<Integer> priorityQueue = new PriorityQueue<>();
        Queue<Integer> linkedListQueue = new LinkedList<>();
        Queue<Integer> arrayListQueue = new ArrayDeque<>();

        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        Set<Integer> treeSet = new TreeSet<>();

        // map is not in collections
        Map<Integer, Integer> hashMap = new HashMap<>();
        Map<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        Map<Integer, Integer> treeMap = new TreeMap<>();
        // specific map for enum values
        //Map<Integer, Integer> enumMap = new EnumMap<>();

        List<String> list = LongStream.range(0, 16)
                                      .boxed()
                                      .map(Long::toHexString)
                                      .collect(toCollection(ArrayList::new));
        List<String> stringsToSearch = new ArrayList<>(list);
        stringsToSearch.addAll(list);

        Set<String> matchingStrings = new HashSet<>(Arrays.asList("a", "c", "9"));

        List<String> result = stringsToSearch
            .stream()
            .filter(matchingStrings::contains)
            .collect(toCollection(ArrayList::new));

        Assertions.assertEquals(6, result.size());
        Assertions.assertEquals(10, stringsToSearch.indexOf("a"));
        Assertions.assertEquals(26, stringsToSearch.lastIndexOf("a"));

        List<String> copy = new ArrayList<>(stringsToSearch);
        Collections.sort(copy);
        int index = Collections.binarySearch(copy, "f");
        Assertions.assertNotEquals(-1, index);
    }

    @Test
    public void givenUsingTheJdk_whenUnmodifiableListIsCreated_thenNotModifiable() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("four"));
    }

    @Test
    public final void givenUsingTheJava9_whenUnmodifiableListIsCreated_thenNotModifiable() {
        final List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        final List<String> unmodifiableList = List.of(list.toArray(new String[]{}));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("four"));
    }

    @Test
    public final void givenIterationWithCopyOnWriteArrayList() {
        CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);
        List<Integer> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);
        Assertions.assertFalse(result.contains(10));
        Iterator<Integer> iterator2 = numbers.iterator();
        result = new ArrayList<>();
        iterator2.forEachRemaining(result::add);
        Assertions.assertTrue(result.contains(10));
    }

    @Test
    public void whenIterateOverItAndTryToRemoveElement_thenShouldThrowException() {
        CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        Iterator<Integer> iterator = numbers.iterator();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            while (iterator.hasNext()) {
                iterator.remove();
            }
        });
    }

    @Test
    public void get2and3dDimentionalArrayLists() {
        int vertexCount = 3;
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);

        graph.get(1).add(0);
        graph.get(2).add(1);
        graph.get(0).add(2);

        vertexCount = graph.size();
        for (int i = 0; i < vertexCount; i++) {
            int edgeCount = graph.get(i).size();
            for (int j = 0; j < edgeCount; j++) {
                Integer startVertex = i;
                Integer endVertex = graph.get(i).get(j);
                System.out.printf("Vertex %d is connected to vertex %d%n", startVertex, endVertex);
            }
        }

        ////////////////////////////// 3rd array list /////////////////////////////////

        int xAxisLength = 2;
        int yAxisLength = 2;
        int zAxisLength = 2;
        ArrayList<ArrayList<ArrayList<String>>> space = new ArrayList<>(xAxisLength);
        for (int i = 0; i < xAxisLength; i++) {
            space.add(new ArrayList<ArrayList<String>>(yAxisLength));
            for (int j = 0; j < yAxisLength; j++) {
                space.get(i).add(new ArrayList<String>(zAxisLength));
            }
        }
        space.get(0).get(0).add(0, "Red");
        space.get(0).get(0).add(1, "Red");
        space.get(0).get(1).add(0, "Blue");
        space.get(0).get(1).add(1, "Blue");

        for (int i = 0; i < space.size(); i++) {
            for (int j = 0; j < space.get(i).size(); j++) {
                for (int k = 0; k < space.get(i).get(j).size(); k++) {
                    System.out.printf("Vertex %d is connected to vertex %d connected to %d and color %s %n", i, j, k,
                        space.get(i).get(j).get(k));
                }
            }
        }

    }

    @Test
    public void givenList_whenParitioningIntoSublistsUsingPartitionBy_thenCorrect() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Map<Boolean, List<Integer>> groups = intList.stream().collect(Collectors.partitioningBy(s -> s > 6));
        List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
        List<Integer> lastPartition = subSets.get(1);
        List<Integer> expectedLastPartition = Arrays.asList(7, 8);
        Assertions.assertEquals(subSets.size(), 2);
        Assertions.assertEquals(expectedLastPartition, lastPartition);
    }

    @Test
    public final void givenList_whenParitioningIntoNSublistsUsingGroupingBy_thenCorrect() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        Map<Integer, List<Integer>> groups = integerList.stream().collect(Collectors.groupingBy(s -> (s - 1) / 3));
        System.out.println(groups);
        List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
        List<Integer> lastPartition = subSets.get(2);
        List<Integer> expectedLastPartition = Arrays.asList(7, 8);
        System.out.println(subSets.size());
        System.out.println(subSets);
        Assertions.assertEquals(subSets.size(), 3);
        Assertions.assertEquals(expectedLastPartition, lastPartition);
    }

    @Test
    public void givenList_whenSplittingBySeparator_thenCorrect() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 0, 4, 5, 6, 0, 7, 8);
        int[] indexes = Stream.of(IntStream.of(-1), IntStream.range(0, intList.size())
                                                             .filter(i -> intList.get(i) == 0),
            IntStream.of(intList.size())
        ).flatMapToInt(s -> s).toArray();
        List<List<Integer>> subSets =
            IntStream.range(0, indexes.length - 1)
                     .mapToObj(i -> intList.subList(indexes[i] + 1, indexes[i + 1]))
                     .toList();

        List<Integer> lastPartition = subSets.get(2);
        List<Integer> expectedLastPartition = new ArrayList<>(Arrays.asList(7, 8));
        Assertions.assertEquals(3, subSets.size());
        Assertions.assertEquals(expectedLastPartition, lastPartition);
    }

    // remove null
    @Test
    public void givenListContainsNulls_whenRemovingNullsWithPlainJava_thenCorrect() {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        list.add(1);
        list.add(null);
        while (list.remove(null))
            ;
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void givenListContainsNulls_whenRemovingNullsWithPlainJavaAlternative_thenCorrect() {
        List<Integer> list = new ArrayList<>(Arrays.asList(null, 1, null));
        list.removeAll(Collections.singleton(null));
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void givenListContainsNulls_whenFilteringParallel_thenCorrect() {
        List<Integer> list = Arrays.asList(null, 1, 2, null, 3, null);
        List<Integer> listWithoutNulls = list.parallelStream()
                                             .filter(Objects::nonNull)
                                             .toList();
        Assertions.assertEquals(3, listWithoutNulls.size());
    }

    @Test
    public void givenListContainsNulls_whenFilteringSerial_thenCorrect() {
        List<Integer> list = Arrays.asList(null, 1, 2, null, 3, null);
        List<Integer> listWithoutNulls = list.stream()
                                             .filter(Objects::nonNull)
                                             .toList();
        Assertions.assertEquals(3, listWithoutNulls.size());
    }

    @Test
    public void givenListContainsNulls_whenRemovingNullsWithRemoveIf_thenCorrect() {
        List<Integer> listWithoutNulls = new ArrayList<>();
        listWithoutNulls.add(null);
        listWithoutNulls.add(1);
        listWithoutNulls.add(2);
        listWithoutNulls.add(null);
        listWithoutNulls.add(3);
        listWithoutNulls.add(null);
        listWithoutNulls.removeIf(Objects::isNull);
        Assertions.assertEquals(3, listWithoutNulls.size());
    }

    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithJava8_thenCorrect() {
        List<Integer> listWithDuplicates = Arrays.asList(5, 0, 3, 1, 2, 3, 0, 0);
        List<Integer> listWithoutDuplicates = listWithDuplicates.stream()
                                                                .distinct()
                                                                .toList();
        Assertions.assertEquals(5, listWithoutDuplicates.size());
        Assertions.assertTrue(listWithDuplicates.containsAll(Arrays.asList(5, 0, 3, 1, 2)));
    }

    @Test
    void givenList_whenUsingCollectionsFrequency_thenReturnDuplicateElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        List<Integer> duplicates = listDuplicateUsingCollectionsFrequency(list);
        Assertions.assertEquals(2, duplicates.size());
        Assertions.assertTrue(duplicates.contains(3));
        Assertions.assertTrue(duplicates.contains(4));
        Assertions.assertFalse(duplicates.contains(1));
    }

    private List<Integer> listDuplicateUsingCollectionsFrequency(List<Integer> list) {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> set = list.stream()
                               .filter(i -> Collections.frequency(list, i) > 1)
                               .collect(Collectors.toSet());
        duplicates.addAll(set);
        return duplicates;
    }

    @Test
    void givenList_whenUsingMapAndCollectorsGroupingBy_thenReturnDuplicateElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        List<Integer> duplicates = listDuplicateUsingMapAndCollectorsGroupingBy(list);
        Assertions.assertEquals(duplicates.size(), 2);
        Assertions.assertTrue(duplicates.contains(3));
        Assertions.assertTrue(duplicates.contains(4));
        Assertions.assertFalse(duplicates.contains(1));
    }

    List<Integer> listDuplicateUsingMapAndCollectorsGroupingBy(List<Integer> list) {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> set = list.stream()
                               .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                               .entrySet()
                               .stream()
                               .filter(m -> m.getValue() > 1)
                               .map(Map.Entry::getKey)
                               .collect(Collectors.toSet());
        duplicates.addAll(set);
        return duplicates;
    }

    @Test
    public void testFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            IntStream.range(0, 10).boxed().forEach(System.out::println);
            return 0;
        });
        try {
            task.run();
            task.get();
        } catch (CancellationException | InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.completedFuture(getCount());
        System.out.println(integerCompletableFuture.get());

    }

    private int getCount() {
        return (int) (Math.random() * 1000);
    }

    @Test()
    public void checkedException_Demo() {
        Assertions.assertThrows(InterruptedException.class, () -> {
            throw new InterruptedException();
        });
    }

    @Test
    public void unCheckedException_Demo() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException();
        });
    }

    class TestClass {

        public static String v = "Initial val";

        static {
            System.out.println("!!! Static initializer");
            v = "Some val";
        }

        public TestClass() {
            System.out.println("!!! Non-static initializer");
            v = "Val from non-static";
            ;
        }

        public void a() {
            System.out.println("!!! a() called");
        }
    }

    @Test
    public void testDuplicateArraysElements() {
        int[] numsCase1 = {1, 2, 3, 3};
        int[] numsCase2 = {1, 2, 3, 4};
        Assertions.assertTrue(hasDuplicate(numsCase1));
        Assertions.assertFalse(hasDuplicate(numsCase2));
    }

    private boolean hasDuplicate(int[] nums) {
        return IntStream.of(nums).distinct().toArray().length < nums.length;
    }

}

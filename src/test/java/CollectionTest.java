import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import resources.ListNode;


public class CollectionTest {

    @Test
    public void reverseLinkedList() {
        final List<Integer> reversedListItalon = new LinkedList<>(Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1));
        List<Integer> initialList = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> reversedList = new LinkedList<>();
        ListIterator<Integer> iterator = initialList.listIterator(initialList.size());
        while (iterator.hasPrevious()) {
            reversedList.add(iterator.previous());
        }
        Assertions.assertArrayEquals(reversedListItalon.toArray(), reversedList.toArray());
    }

    @Test
    public void reverseLinkedListWithoutIterator() {
        final List<Integer> reversedListItalon = new LinkedList<>(Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1));
        List<Integer> initialList = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> reversedList = new LinkedList<>();
        int currentPosition = initialList.size() - 1;
        while (currentPosition >= 0) {
            reversedList.add(initialList.get(currentPosition));
            --currentPosition;
        }

        Assertions.assertArrayEquals(reversedListItalon.toArray(), reversedList.toArray());
    }

    @Test
    public void reverseLinkedListCustom() {
        final List<Integer> reversedListItalon = new LinkedList<>(Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1));
        List<Integer> initialList = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ListNode<Integer> node = new ListNode<>(0);
        ListNode<Integer> node2 = new ListNode<>(1);
        node.next = node2;
        ListNode<Integer> node3 = new ListNode<>(2);
        node2.next = node3;
        ListNode<Integer> node4 = new ListNode<>(3);
        node3.next = node4;
        ListNode<Integer> result = reverseList(node);
        System.out.println(result);

//        Assertions.assertArrayEquals(reversedListItalon.toArray(), reversedList.toArray());
    }

    private ListNode<Integer> reverseList(ListNode<Integer> node) {
        ListNode<Integer> current = node;
        ListNode<Integer> previous = null;
        ListNode<Integer> nextCurrent = null;
        while (current != null) {
            nextCurrent = current.next;

            current.next = previous;
            previous = current;

            current = nextCurrent;
        }

        return previous;
    }
}

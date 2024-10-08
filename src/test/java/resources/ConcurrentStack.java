package resources;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class ConcurrentStack<E> {

    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

    public void push(E item) {
        Node<E> oldHead;
        Node<E> newHead = new Node<>(item);
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));

    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {

        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

    private static class NodeUpdater<E> {

        public final E item;
        public volatile NodeUpdater<E> next;

        public NodeUpdater(E item) {
            this.item = item;
        }
    }

    private static final AtomicReferenceFieldUpdater<NodeUpdater, NodeUpdater> nextUpdater =
        AtomicReferenceFieldUpdater.newUpdater(NodeUpdater.class, NodeUpdater.class, "next");
}

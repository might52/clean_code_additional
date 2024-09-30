package resources;

import java.util.concurrent.Semaphore;

public class BoundedBuffer<E> {
    private final Semaphore availaleItems, availableSpaces;
    private final E[] items;
    private int putPosition = 0, takePosition = 0;

    public BoundedBuffer(int capacity) {
        items = (E[]) new Object[capacity];
        availaleItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
    }

    public boolean isEmpty() {
        return availaleItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availaleItems.release();
    }

    public E take() throws InterruptedException {
        availaleItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : i;
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }
}

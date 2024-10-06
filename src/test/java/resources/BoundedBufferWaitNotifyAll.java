package resources;

public class BoundedBufferWaitNotifyAll<V> extends BaseBoundedBuffer<V> {
    public BoundedBufferWaitNotifyAll(int size) {
        super(size);
    }

    // before: not full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    // before: not empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}

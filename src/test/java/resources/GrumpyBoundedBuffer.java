package resources;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws BufferOverflowException {
        if (isFull()) {
            throw new BufferOverflowException();
        }
        doPut(v);
    }

    public synchronized V take() throws BufferUnderflowException {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return doTake();
    }
}

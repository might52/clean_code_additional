package resources;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        protected int tryAcquireShared(int ignored) {
            // успешно, если защелка открыта состояние == 1, иначе сбой.
            return getState() == 1 ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignored) {
            setState(1); //защелка теперь открыта
            return true;
        }
    }
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import resources.BoundedBuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ConcurrentTest {

    private static final int NUMBER_OF_THREADS = 10;
    private static final ExecutorService exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final ExecutorService cachedExec = Executors.newCachedThreadPool();
    private static final ExecutorService singleExec = Executors.newSingleThreadExecutor();
    private static final ExecutorService scheduledExec = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);
    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

    @Test
    public void testPerTask() throws InterruptedException {
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int finalInt = i;
            tasks.add(() ->
                System.out.println("Number of the task: {" + finalInt + "} " +
                            Thread.currentThread().getName() + ": " +
                    Thread.currentThread().threadId() +
                    ", datetime: " + System.currentTimeMillis())
            );
        }
        tasks.forEach(exec::submit);
        exec.awaitTermination(5, SECONDS);
        exec.shutdownNow();
    }

    @Test
    public void futureTestTasks() throws InterruptedException {
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int finalInt = i;
            tasks.add(() -> String.format("Callable number: {%d} with return value: {%d} %s:%d, datetime: %s",
                finalInt, finalInt, Thread.currentThread().getName(),
                Thread.currentThread().threadId(), System.currentTimeMillis()));
        }
        tasks.forEach(task -> {
            Future<String> future = exec.submit(task);
            try {
                String value = future.get();
                System.out.printf("Future value: %S state: %s, isDone: %s, isCancelled: %s%n", value, future.state(),
                    future.isDone(), future.isCancelled());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                future.cancel(true);
            } catch (ExecutionException e) {
                System.out.println("Something went wrong during execution process: " + e.getCause());
            }
        });
        exec.awaitTermination(5, SECONDS);
        exec.shutdownNow();
    }

    @Test
    public void completionServiceTestTasks() throws InterruptedException {
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int finalInt = i;
            tasks.add(() -> String.format("Callable number: {%d} with return value: {%d} %s:%d, datetime: %s",
                finalInt, finalInt, Thread.currentThread().getName(),
                Thread.currentThread().threadId(), System.currentTimeMillis()));
        }
        CompletionService<String> completionService = new ExecutorCompletionService<>(exec);
        tasks.forEach(completionService::submit);
        tasks.forEach(task -> {
            try {
                Future<String> future = completionService.take();
                String value = future.get();
                System.out.printf("Future value: %S state: %s, isDone: %s, isCancelled: %s%n", value, future.state(),
                    future.isDone(), future.isCancelled());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                System.out.println("Something went wrong during execution process: " + e.getCause());
            }
        });
        exec.awaitTermination(5, SECONDS);
        exec.shutdownNow();

    }

    @Test
    public void invokeAllTestTasks() throws InterruptedException {
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int finalInt = i;
            tasks.add(() -> String.format("Callable number: {%d} with return value: {%d} %s:%d, datetime: %s",
                finalInt, finalInt, Thread.currentThread().getName(),
                Thread.currentThread().threadId(), System.currentTimeMillis()));
        }
        List<Future<String>> futureTasks = exec.invokeAll(tasks, 30, TimeUnit.SECONDS);
        futureTasks.forEach(future -> {
            try {
                String value = future.get();
                System.out.printf("Future value: %S state: %s, isDone: %s, isCancelled: %s%n", value, future.state(),
                    future.isDone(), future.isCancelled());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                System.out.println("Something went wrong during execution process: " + e.getCause());
            }
        });

        exec.awaitTermination(5, SECONDS);
        exec.shutdownNow();
    }

    // Try to check and save the interrupted exception to stop the Thread.
    private FutureTask<Void> getNextTask(BlockingQueue<FutureTask<Void>> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                    // skip the issue and try again.
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void timedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {

            private volatile Throwable t;

            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            public void rethrow() {
                System.out.println("Check the runtime error");
                if (t != null) {
                    throw new RuntimeException(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            public void run() {
                System.out.println("Perform interrupt");
                taskThread.interrupt();
            }
        }, timeout, unit);
        System.out.println("Perform joining");
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }

    @Test
    public void testInterrupt() throws InterruptedException {
        timedRun(
            () -> System.out.printf("Callable number: {%d} with return value: {%d} %s:%d, datetime: %s%n",
                1, 1, Thread.currentThread().getName(), Thread.currentThread().threadId(),
                System.currentTimeMillis()),
            10, TimeUnit.SECONDS);
    }

    private static void cancelTimedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = exec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException task");
            // cancellation of the task will be perform later
        } catch (ExecutionException e) {
            System.out.println("ExecutionException task");
            throw new AssertionError(e);
        } finally {
            // close the task when it finished.
            System.out.println("Cancelled task");
            task.cancel(true);
        }
    }

    @Test
    public void testCancellationByTimeout() throws InterruptedException {
        cancelTimedRun(
            () -> System.out.printf("Callable number: {%d} with return value: {%d} %s:%d, datetime: %s%n",
                1, 1, Thread.currentThread().getName(), Thread.currentThread().threadId(),
                System.currentTimeMillis()),
            10, TimeUnit.SECONDS);
    }

    private boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final AtomicBoolean hasNewMail = new AtomicBoolean(false);
        try {
            for (final String host : hosts) {
                exec.execute(new Runnable() {
                    public void run() {
                        if (checkMail(host)) {
                            hasNewMail.set(true);
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
            exec.awaitTermination(timeout, unit);
        }
        return hasNewMail.get();
    }

    private boolean checkMail(String host) {
        return true;
    }

    @Test
    public void addHookTest() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutdown hook triggered");
            }
        });
        System.out.println("Shutdown hook exit");
    }

    @Test
    public void createThreadPoolExecutorWithCallRunsPolicy() {
        try (ThreadPoolExecutor executor = new ThreadPoolExecutor(
            NUMBER_OF_THREADS,
            NUMBER_OF_THREADS,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(NUMBER_OF_THREADS))) {
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void changeCorePoolSize() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        if (executorService instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) executorService).setCorePoolSize(10);
        } else {
            throw new AssertionError("Something went wrong during change the core pool size");
        }
    }


    @Test
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Assertions.assertTrue(bb.isEmpty());
        Assertions.assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        Assertions.assertTrue(bb.isFull());
        Assertions.assertFalse(bb.isEmpty());
    }

    @Test
    public void testTakeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    int unused = bb.take();
                    Assertions.fail();
                } catch (InterruptedException e) {
                }
            }
        });
        try {
            taker.start();
            Thread.sleep(10000);
            taker.interrupt();
            taker.join(10000);
            Assertions.assertFalse(taker.isAlive());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void testPutTake() {
        PutTakeTest test = new PutTakeTest(10, 10, 100000);
        test.testProducerConsumerConcurrent();
        test.poolShutdown();
    }

    @Test
    public void testTimedPutTakeViaDriver() throws Exception {
        int tpd = 100000;
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                PutTakeTest t = new PutTakeTest(cap, pairs, tpd);
                System.out.println("Pairs: " + pairs + "\t");
                t.testProducerConsumerConcurrent();
                System.out.println("\t");
                Thread.sleep(1000);
                t.testProducerConsumerConcurrent();
                System.out.println();
                Thread.sleep(1000);
            }
        }
        /*
            Capacity: 1
            Pairs: 1
            Start first await
            Start second await
            Throughput: 31299 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 32063 ns/item
            Start assertions
            Shutting down pool

            Pairs: 2
            Start first await
            Start second await
            Throughput: 50033 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 50985 ns/item
            Start assertions
            Shutting down pool

            Pairs: 4
            Start first await
            Start second await
            Throughput: 54570 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 52346 ns/item
            Start assertions
            Shutting down pool

            Pairs: 8
            Start first await
            Start second await
            Throughput: 53546 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 52520 ns/item
            Start assertions
            Shutting down pool

            Pairs: 16
            Start first await
            Start second await
            Throughput: 53307 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 53333 ns/item
            Start assertions
            Shutting down pool

            Pairs: 32
            Start first await
            Start second await
            Throughput: 52198 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 51750 ns/item
            Start assertions
            Shutting down pool

            Pairs: 64
            Start first await
            Start second await
            Throughput: 51810 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 52545 ns/item
            Start assertions
            Shutting down pool

            Pairs: 128
            Start first await
            Start second await
            Throughput: 54931 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 55525 ns/item
            Start assertions
            Shutting down pool

            Capacity: 10
            Pairs: 1
            Start first await
            Start second await
            Throughput: 2081 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 2238 ns/item
            Start assertions
            Shutting down pool

            Pairs: 2
            Start first await
            Start second await
            Throughput: 4818 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 4893 ns/item
            Start assertions
            Shutting down pool

            Pairs: 4
            Start first await
            Start second await
            Throughput: 4278 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 4322 ns/item
            Start assertions
            Shutting down pool

            Pairs: 8
            Start first await
            Start second await
            Throughput: 4447 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 4525 ns/item
            Start assertions
            Shutting down pool

            Pairs: 16
            Start first await
            Start second await
            Throughput: 4579 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 4458 ns/item
            Start assertions
            Shutting down pool

            Pairs: 32
            Start first await
            Start second await
            Throughput: 4482 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 4507 ns/item
            Start assertions
            Shutting down pool

            Pairs: 64
            Start first await
            Start second await
            Throughput: 4702 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 4767 ns/item
            Start assertions
            Shutting down pool

            Pairs: 128
            Start first await
            Start second await
            Throughput: 4731 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 4711 ns/item
            Start assertions
            Shutting down pool

            Capacity: 100
            Pairs: 1
            Start first await
            Start second await
            Throughput: 273 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 272 ns/item
            Start assertions
            Shutting down pool

            Pairs: 2
            Start first await
            Start second await
            Throughput: 436 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 492 ns/item
            Start assertions
            Shutting down pool

            Pairs: 4
            Start first await
            Start second await
            Throughput: 455 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 434 ns/item
            Start assertions
            Shutting down pool

            Pairs: 8
            Start first await
            Start second await
            Throughput: 489 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 450 ns/item
            Start assertions
            Shutting down pool

            Pairs: 16
            Start first await
            Start second await
            Throughput: 519 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 480 ns/item
            Start assertions
            Shutting down pool

            Pairs: 32
            Start first await
            Start second await
            Throughput: 464 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 521 ns/item
            Start assertions
            Shutting down pool

            Pairs: 64
            Start first await
            Start second await
            Throughput: 471 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 487 ns/item
            Start assertions
            Shutting down pool

            Pairs: 128
            Start first await
            Start second await
            Throughput: 464 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 465 ns/item
            Start assertions
            Shutting down pool

            Capacity: 1000
            Pairs: 1
            Start first await
            Start second await
            Throughput: 265 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 321 ns/item
            Start assertions
            Shutting down pool

            Pairs: 2
            Start first await
            Start second await
            Throughput: 331 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 206 ns/item
            Start assertions
            Shutting down pool

            Pairs: 4
            Start first await
            Start second await
            Throughput: 182 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 179 ns/item
            Start assertions
            Shutting down pool

            Pairs: 8
            Start first await
            Start second await
            Throughput: 161 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 159 ns/item
            Start assertions
            Shutting down pool

            Pairs: 16
            Start first await
            Start second await
            Throughput: 201 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 198 ns/item
            Start assertions
            Shutting down pool

            Pairs: 32
            Start first await
            Start second await
            Throughput: 225 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 218 ns/item
            Start assertions
            Shutting down pool

            Pairs: 64
            Start first await
            Start second await
            Throughput: 201 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 200 ns/item
            Start assertions
            Shutting down pool

            Pairs: 128
            Start first await
            Start second await
            Throughput: 228 ns/item
            Start assertions
            Shutting down pool

            Start first await
            Start second await
            Throughput: 212 ns/item
            Start assertions
            Shutting down pool

        */

    }

    @Test
    public void testReentrantLock() {
        Lock lock = new ReentrantLock();
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }


    @Test
    public void testReentrantReadWriteLock() {
        class ReadWriteMap<K, V> {
            private final Map<K, V> map;
            private final ReadWriteLock lock = new ReentrantReadWriteLock();
            private final Lock readLock = lock.readLock();
            private final Lock writeLock = lock.writeLock();

            public ReadWriteMap(Map<K, V> map) {
                this.map = map;
            }

            public V put(K key, V value) {
                writeLock.lock();
                System.out.println("acquire writeock");
                try {
                    return map.put(key, value);
                } finally {
                    System.out.println("release writeock");
                    writeLock.unlock();
                }
            }

            public V get(Object key) {
                readLock.lock();
                System.out.println("acquire readlock");
                try {
                    return map.get(key);
                } finally {
                    System.out.println("release readlock");
                    readLock.unlock();
                }
            }
        }

        ReadWriteMap<String, String> map = new ReadWriteMap<>(new HashMap<>());
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.get("1");
        map.get("2");
        map.get("3");
    }


    private void stateDependentWait() throws InterruptedException {
        Object lock = new Object();
        boolean condition = true;
        // Условный предикат должен быть защищен замком.
        synchronized (lock) {
            while (!condition) {
                lock.wait();
                // объект в нужном состоянии.
            }

            condition = false;
        }
    }
}

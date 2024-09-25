import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

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
}

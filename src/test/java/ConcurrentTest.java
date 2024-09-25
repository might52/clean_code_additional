import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class ConcurrentTest {

    private static final int NUMBER_OF_THREADS = 10;
    private static final ExecutorService exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final ExecutorService cachedExec = Executors.newCachedThreadPool();
    private static final ExecutorService singleExec = Executors.newSingleThreadExecutor();
    private static final ExecutorService scheduledExec = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);

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

}

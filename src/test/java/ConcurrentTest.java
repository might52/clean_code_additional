import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConcurrentTest {
    private static final int NUMBER_OF_THREADS = 4;
    private static final Executor exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private boolean isActive;

    @Test
    public void testPerTask() throws InterruptedException {
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int finalInt = i;
            tasks.add(() ->
                    System.out.println("Number of the task: {" + finalInt + "}" +
                            Thread.currentThread().getName() + ": " +
                            Thread.currentThread().threadId())
            );
        }
        startServer(tasks);
        stopServer();
    }

    @Test
    public void testWithinTask() {

    }

    private void startServer(List<Runnable> tasks) {
        isActive = true;
        while (isActive) {
            tasks.forEach(exec::execute);
        }
    }

    private void stopServer() {
        isActive = false;
    }
}

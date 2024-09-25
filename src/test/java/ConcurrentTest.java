import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class ConcurrentTest {

    private static final int NUMBER_OF_THREADS = 10;
    private static final ExecutorService exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
        exec.awaitTermination(5, TimeUnit.SECONDS);
        exec.shutdownNow();
    }

}

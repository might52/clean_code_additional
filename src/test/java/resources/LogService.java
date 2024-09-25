package resources;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;


public class LogService {

    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter printWriter;
    private boolean isShutdown;
    private int reservations;

    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public LogService(BlockingQueue<String> queue, PrintWriter printWriter) {
        this.queue = queue;
        this.loggerThread = new LoggerThread();
        this.printWriter = printWriter;
    }

    public void start() {
//        loggerThread.start();
    }

    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            printWriter.close();
        }
//        synchronized (this) {
//            isShutdown = true;
//            loggerThread.interrupt();
//        }
    }

    public void log(String message) throws InterruptedException {
        try {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    //do something
                }
            });
        } catch (RejectedExecutionException ignored) {
        }
//        synchronized (this) {
//            if (isShutdown) {
//                throw new InterruptedException();
//            }
//            ++reservations;
//        }
//        queue.put(message);
    }

    private class LoggerThread extends Thread {

        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String message = queue.take();
                        synchronized (this) {
                            --reservations;
                        }
                        printWriter.println(message);
                    } catch (InterruptedException e) {
                        //try again
                    } finally {
                        printWriter.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

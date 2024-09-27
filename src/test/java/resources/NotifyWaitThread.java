package resources;

public class NotifyWaitThread extends Thread {

    private final Object lock = new Object();

    public void run() {
        synchronized (lock) {
            try {
                System.out.println("Thread is waiting...");
                lock.wait(); // Thread waits for notification
            } catch (InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
            System.out.println("Thread is notified and continues execution...");
        }
    }

    public void notifyThread() {
        synchronized (lock) {
            System.out.println("Notifying thread...");
            lock.notify(); // Notify the waiting thread
        }
    }
}

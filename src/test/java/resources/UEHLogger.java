package resources;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UEHLogger implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getLogger("UEHLogger");
        logger.log(Level.SEVERE, "Потом терминирован с исключением: " + t.getName(), e);
    }
}

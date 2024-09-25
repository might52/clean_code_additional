import org.junit.jupiter.api.Test;
import org.might.lambda.functional.implementation.ThreadLocalDateFormat;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 1/4/2024
 * Time: 2:53 PM
 */
public class ThreadLocalDateFormatTest {

    @Test
    public void test() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String formatted = ThreadLocalDateFormat.FORMATTER.get().format(cal.getTime());
        assertEquals("01-Jan-1970", formatted);
    }

}
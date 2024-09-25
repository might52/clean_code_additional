
package org.might.lambda.functional.implementation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Date: 1/4/2024
 * Time: 2:53 PM
 */
public class ThreadLocalDateFormat {
    public static final ThreadLocal<DateFormat> FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MMM-yyyy"));
}
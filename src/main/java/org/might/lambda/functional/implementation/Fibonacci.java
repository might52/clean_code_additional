package org.might.lambda.functional.implementation;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 1/11/2024
 * Time: 2:35 PM
 */
public class Fibonacci {

    private final Map<Integer,Long> cache;

    public Fibonacci() {
        cache = new HashMap<>();
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public long fibonacci(int x) {
        return cache.computeIfAbsent(x, n -> fibonacci(n-1) + fibonacci(n-2));
    }

}

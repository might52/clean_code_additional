package org.might.lambda.functional.implementation;

import java.util.stream.Stream;

/**
 * Date: 1/5/2024
 * Time: 12:09 PM
 */
public class Functions {
    public int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (summary, value) -> summary + value);
    };
}

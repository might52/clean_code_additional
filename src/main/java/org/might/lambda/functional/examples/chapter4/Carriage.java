package org.might.lambda.functional.examples.chapter4;

// BEGIN body
public interface Carriage {

    public default String rock() {
        return "... from side to side";
    }

}
// END body

package org.might.lambda.functional.examples.chapter4;

// BEGIN body
public class OverridingParent extends ParentImpl {

    @Override
    public void welcome() {
        message("Class Parent: Hi!");
    }

}
// END body

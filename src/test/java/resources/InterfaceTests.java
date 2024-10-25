package resources;

public interface InterfaceTests {

    String fields = "nothing";

    default void test() {
        System.out.println("nothing to do");
        System.out.println(fields);
    }

}

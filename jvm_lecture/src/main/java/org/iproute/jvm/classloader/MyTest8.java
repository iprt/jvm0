package org.iproute.jvm.classloader;

/**
 * @author tech@intellij.io
 */
public class MyTest8 {
    public static void main(String[] args) {
        System.out.println(FinalTest.x);
    }
}

class FinalTest {
    public static final int x = 3;
//    public static int x = 3;
//    public static final int x = new Random().nextInt();

    static {
        System.out.println("FinalTest static block");
    }
}

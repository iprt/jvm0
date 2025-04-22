package org.iproute.jvm.classloader;

/**
 * @author tech@intellij.io
 */
public class MyTest11 {
    public static void main(String[] args) {
        System.out.println(Child3.a);
        Parent3.doSth();
    }
}


class Parent3 {
    static int a = 3;

    static {
        System.out.println("Parent3 static block");
    }

    static void doSth() {
        System.out.println("Parent3 do something");
    }
}

class Child3 extends Parent3 {

    static {
        System.out.println("Child3 static block");
    }
}
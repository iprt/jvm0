package org.iproute.jvm.classloader;

/**
 * @author tech@intellij.io
 */
public class MyTest10 {
    public static void main(String[] args) {
        Parent2 parent2;

        System.out.println("-----------");

        parent2 = new Parent2();

        System.out.println("-----------");

        System.out.println(Parent2.a);

        System.out.println("-----------");

        System.out.println(Child2.b);
    }

    static {
        System.out.println("MyTest10 static block");
    }
}

class Parent2 {
    static int a = 3;

    static {
        System.out.println("MyParent2 static block");
    }
}

class Child2 extends Parent2 {
    static int b = 4;

    static {
        System.out.println("MyChild2 static block");
    }
}


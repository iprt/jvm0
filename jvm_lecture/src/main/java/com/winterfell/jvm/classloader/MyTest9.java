package com.winterfell.jvm.classloader;

/**
 * @author winterfell
 */
public class MyTest9 {

    public static void main(String[] args) {
        System.out.println(Child.b);
    }

    static {
        System.out.println("MyTest9 static block");
    }
}

class Parent {
    static int a = 3;

    static {
        System.out.println("Parent static block");
    }
}

class Child extends Parent {
    static int b = 4;

    static {
        System.out.println("Child static block");
    }
}

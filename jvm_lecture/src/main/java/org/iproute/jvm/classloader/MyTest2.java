package org.iproute.jvm.classloader;

/**
 * 在编译阶段，str这个常量就会存入到调用这个常量的方法所在的类的常量池中
 *
 * @author winterfell
 **/
public class MyTest2 {
    public static void main(String[] args) {
        System.out.println(MyParent2.str);
    }
}

class MyParent2 {
    public static final String str = "hello world";

    static {
        System.out.println("MyParent2 static block");
    }
}

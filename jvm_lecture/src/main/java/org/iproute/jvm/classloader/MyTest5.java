package org.iproute.jvm.classloader;

/**
 * 当一个接口在初始的时候并不要求其父接口都完成了初始化
 * 只有在真正使用到父接口的时候（如引用接口所定义的常量时）才会初始化
 *
 * @author tech@intellij.io
 **/
public class MyTest5 {
    public static void main(String[] args) {
        System.out.println(MyChild5.b);
    }
}

interface MyParent5 {
    public static final int a = 5;
//    public static final int a = new Random().nextInt();
}

interface MyChild5 extends MyParent5 {
    public static final int b = 6;
//    public static final int b = new Random().nextInt();
}
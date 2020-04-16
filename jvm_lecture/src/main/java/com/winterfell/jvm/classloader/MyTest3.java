package com.winterfell.jvm.classloader;

import java.util.UUID;

/**
 * 当一个常量的值在编译期间就不可以确定，那么其值就不会放到调用类的常量池中
 * 这时程序运行，会导致主动使用这个常量所在的类，显然会导致这个类的初始化
 *
 * @author winterfell
 **/
public class MyTest3 {
    public static void main(String[] args) {
        System.out.println(MyParent3.str);
    }
}

class MyParent3 {
    public static final String str = UUID.randomUUID().toString();

    static {
        System.out.println("MyParent3 static code");
    }
}

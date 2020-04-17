package com.winterfell.jvm.classloader;

/**
 * 当前类加载器 (Current ClassLoader)
 *
 * 每个类都会
 *
 * @author winterfell
 **/
public class MyTest24 {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());
    }
}

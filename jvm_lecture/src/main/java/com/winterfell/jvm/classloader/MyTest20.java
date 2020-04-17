package com.winterfell.jvm.classloader;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author winterfell
 **/
public class MyTest20 {

    public static void main(String[] args) throws Exception {

        MyTest16 loader1 = new MyTest16("loader1");
        MyTest16 loader2 = new MyTest16("loader2");

        Class<?> clazz1 = loader1.loadClass("com.winterfell.jvm.classloader.MyPerson");
        Class<?> clazz2 = loader2.loadClass("com.winterfell.jvm.classloader.MyPerson");

        System.out.println(clazz1 == clazz2);

        Object object1 = clazz1.newInstance();
        Object object2 = clazz2.newInstance();

        Method method = clazz1.getMethod("setMyPerson", Object.class);
        method.invoke(object1, object2);

    }

}

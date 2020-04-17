package com.winterfell.jvm.classloader;

import java.lang.reflect.Method;

/**
 * 测试的时候删除 IDE 编译的 MyPerson.class
 *
 * @author winterfell
 **/
public class MyTest21 {

    public static void main(String[] args) throws Exception {

        //  测试的时候删除 IDE 编译的 MyPerson.class !!!!!!!!!!!!!!!!!!!!

        MyTest16 loader1 = new MyTest16("loader1");
        MyTest16 loader2 = new MyTest16("loader2");
        loader1.setPath("D:/tmp/classes/");
        loader2.setPath("D:/tmp/classes/");

        // 删除了ide自己编译的class文件 会由我们自己定义的类加载器来加载类
        // 所以 两个 Class对象 clazz1 和 clazz2 的类加载器是不同的
        Class<?> clazz1 = loader1.loadClass("com.winterfell.jvm.classloader.MyPerson");
        Class<?> clazz2 = loader2.loadClass("com.winterfell.jvm.classloader.MyPerson");

        // clazz1 和 clazz2 的类加载器是不同的
        // 类加载器的命名空间不同
        // 所以这两个类是不同的 这种情况下会返回false
        System.out.println(clazz1 == clazz2);

        Object object1 = clazz1.newInstance();
        Object object2 = clazz2.newInstance();

        Method method = clazz1.getMethod("setMyPerson", Object.class);
        method.invoke(object1, object2);

    }

}

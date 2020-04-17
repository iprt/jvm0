package com.winterfell.jvm.classloader;

/**
 * 类加载器在复杂情况下的运行分析
 *
 * @author winterfell
 **/
public class MyTest17 {

    public static void main(String[] args) throws Exception {
        MyTest16 loader1 = new MyTest16("loader1");

        Class<?> sampleClazz = loader1.loadClass("com.winterfell.jvm.classloader.MySample");
        System.out.println("class :" + sampleClazz.hashCode());

        // 如果注释掉下面的行，并不会实例化MySample对象，即MySample构造方法不会被使用
        // 因此不会实例化MyCat对象
//        Object object = sampleClazz.newInstance();
    }

}

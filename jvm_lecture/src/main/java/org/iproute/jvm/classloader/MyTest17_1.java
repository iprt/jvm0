package org.iproute.jvm.classloader;

/**
 * 关于命名空间的重要说明
 * <p>
 * 子加载器加载的类可以访问父加载器加载的类
 * 父加载器加载的类不能访问子加载器加载的类
 *
 * @author tech@intellij.io
 **/
public class MyTest17_1 {

    public static void main(String[] args) throws Exception {
        MyTest16 loader1 = new MyTest16("loader1");
        loader1.setPath("D:/tmp/classes/");

        Class<?> sampleClazz = loader1.loadClass("org.iproute.jvm.classloader.MySample");
        System.out.println("class :" + sampleClazz.hashCode());

        Object object = sampleClazz.newInstance();
    }
}

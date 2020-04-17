package com.winterfell.jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author winterfell
 */
public class MyTest16 extends ClassLoader {
    /**
     * 类加载器的名称
     */
    private String classLoaderName;

    /**
     * 加载文件的后缀
     */
    private final String fileExtension = ".class";

    /**
     * 定义加载类的路径
     */
    private String path;

    public MyTest16(String classLoaderName) {
        // Creates a new class loader using the the method getSystemClassLoader() as the parent class loader.
        // 以系统类加载器作为parent
        super();
        this.classLoaderName = classLoaderName;
    }

    public MyTest16(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    /**
     * 关键
     *
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        byte[] data = this.loadClassData(className);
        return super.defineClass(className, data, 0, data.length);
    }

    /**
     * 参考 ClassLoader 的doc 文档
     *
     * @param className
     * @return
     */
    private byte[] loadClassData(String className) {
        InputStream in = null;
        byte[] data = null;
        ByteArrayOutputStream bos = null;

        className = className.replaceAll("\\.", "/");

        try {

            in = new FileInputStream(new File(path + className + this.fileExtension));
            bos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = in.read())) {
                bos.write(ch);
            }
            data = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "[" + this.classLoaderName + "]";
    }

    /**
     * 自定义类加载器的使用
     *
     * @throws Exception
     */
    public static void test01() throws Exception {
        MyTest16 loader1 = new MyTest16("loader1");
        loader1.setPath("D:/tmp/classes/");

        Class<?> clazz1 = loader1.loadClass("com.winterfell.jvm.classloader.MyTest1");
        Object object1 = clazz1.newInstance();
        System.out.println("class :" + clazz1.hashCode());
        System.out.println(object1);
        System.out.println(object1.getClass().getClassLoader());
    }


    /**
     * 类加载器命名空间的理解
     *
     * @throws Exception
     */
    public static void test02() throws Exception {

        MyTest16 loader1 = new MyTest16("loader1");
        loader1.setPath("D:/tmp/classes/");

        Class<?> clazz1 = loader1.loadClass("com.winterfell.jvm.classloader.MyTest1");
        Object object1 = clazz1.newInstance();
        System.out.println("class :" + clazz1.hashCode());
        System.out.println(object1);
        System.out.println(object1.getClass().getClassLoader());


        System.out.println("---------------------");

        MyTest16 loader2 = new MyTest16("loader2");
        loader2.setPath("D:/tmp/classes/");

        Class<?> clazz2 = loader2.loadClass("com.winterfell.jvm.classloader.MyTest1");
        Object object2 = clazz2.newInstance();
        System.out.println("class :" + clazz2.hashCode());
        System.out.println(object2);
        System.out.println(object2.getClass().getClassLoader());


    }

    /**
     * 类加载器命名空间的理解
     *
     * @throws Exception
     */
    public static void test03() throws Exception {


        MyTest16 loader1 = new MyTest16("loader1");
        loader1.setPath("D:/tmp/classes/");

        Class<?> clazz1 = loader1.loadClass("com.winterfell.jvm.classloader.MyTest1");
        Object object1 = clazz1.newInstance();
        System.out.println("class :" + clazz1.hashCode());
        System.out.println(object1);
        System.out.println(object1.getClass().getClassLoader());


        System.out.println("---------------------");

        MyTest16 loader2 = new MyTest16(loader1, "loader2");
        loader2.setPath("D:/tmp/classes/");

        Class<?> clazz2 = loader2.loadClass("com.winterfell.jvm.classloader.MyTest1");
        Object object2 = clazz2.newInstance();
        System.out.println("class :" + clazz2.hashCode());
        System.out.println(object2);
        System.out.println(object2.getClass().getClassLoader());


        System.out.println("---------------------");

        MyTest16 loader3 = new MyTest16(loader2, "loader2");
        loader3.setPath("D:/tmp/classes/");

        Class<?> clazz3 = loader2.loadClass("com.winterfell.jvm.classloader.MyTest1");
        Object object3 = clazz3.newInstance();
        System.out.println("class :" + clazz3.hashCode());
        System.out.println(object3);
        System.out.println(object3.getClass().getClassLoader());


    }

    /**
     * 类的卸载
     */
    public static void test04() throws Exception {

        MyTest16 loader1 = new MyTest16("loader1");
        loader1.setPath("D:/tmp/classes/");

        Class<?> clazz1 = loader1.loadClass("com.winterfell.jvm.classloader.MyTest1");
        Object object1 = clazz1.newInstance();
        System.out.println("class :" + clazz1.hashCode());
        System.out.println(object1);
        System.out.println(object1.getClass().getClassLoader());

        // 类的卸载
        System.out.println("---------------------");

        loader1 = new MyTest16("loader2");
        loader1.setPath("D:/tmp/classes/");
        clazz1 = loader1.loadClass("com.winterfell.jvm.classloader.MyTest1");
        System.out.println("class :" + clazz1.hashCode());
    }

    public static void main(String[] args) throws Exception {

        test04();

        // test02();

        // test03();

        // test04();
    }
}

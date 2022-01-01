package org.iproute.jvm.classloader;

/**
 * 调用ClassLoader类的loadClass方法加载一个类，并不是对类的主动使用，不会导致类的初始化
 *
 * @author winterfell
 */
public class MyTest12 {

    public static void main(String[] args) throws ClassNotFoundException {

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        Class<?> clazz = classLoader.loadClass("org.iproute.jvm.classloader.CL");

        System.out.println(clazz);

        System.out.println("---------------");

        Class.forName("org.iproute.jvm.classloader.CL");
    }

}

class CL {
    static {
        System.out.println("Class CL");
    }
}

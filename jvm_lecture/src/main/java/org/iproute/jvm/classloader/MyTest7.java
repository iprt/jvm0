package org.iproute.jvm.classloader;

/**
 * @author tech@intellij.io
 */
public class MyTest7 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz1 = Class.forName("java.lang.String");
        System.out.println(clazz1.getClassLoader());

        Class<?> clazz2 = Class.forName("org.iproute.jvm.classloader.C");
        System.out.println(clazz2.getClassLoader());
    }
}

class C {

}

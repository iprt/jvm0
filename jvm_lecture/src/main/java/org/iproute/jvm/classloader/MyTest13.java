package org.iproute.jvm.classloader;

/**
 * @author winterfell
 */
public class MyTest13 {
    public static void main(String[] args) {
        // 获取系统类加载器
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        System.out.println(classLoader);

        while (null != classLoader){
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
    }
}

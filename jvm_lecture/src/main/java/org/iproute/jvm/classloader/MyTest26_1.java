package org.iproute.jvm.classloader;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author winterfell
 */
public class MyTest26_1 {

    public static void main(String[] args) {

        // 加上这行，当前的线程上下文类加载器就变成了 ExtClassLoader
        Thread.currentThread().setContextClassLoader(MyTest26_1.class.getClassLoader().getParent());

        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);

        Iterator<Driver> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Driver driver = iterator.next();
            System.out.println("driver:" + driver + ",classLoader:" + driver.getClass().getClassLoader());
        }

        System.out.println("当前线程的上下文类加载器为：" + Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader的类加载器为：" + ServiceLoader.class.getClassLoader());

    }
}

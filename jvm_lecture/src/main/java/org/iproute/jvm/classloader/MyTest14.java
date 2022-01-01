package org.iproute.jvm.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author winterfell
 */
public class MyTest14 {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        System.out.println(classLoader);

        String resourceName = "org/iproute/jvm/classloader/MyTest14.class";

        Enumeration<URL> resources = classLoader.getResources(resourceName);

        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            System.out.println(url);
        }
    }
}

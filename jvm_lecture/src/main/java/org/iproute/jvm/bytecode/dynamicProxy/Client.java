package org.iproute.jvm.bytecode.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author winterfell
 **/
public class Client {

    public static void main(String[] args) {

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        RealSubject rs = new RealSubject();
        InvocationHandler ds = new DynamicSubject(rs);

        Class<?> clazz = rs.getClass();

        // Proxy line 639
        // sun.misc.ProxyGenerator.saveGeneratedFiles
        Subject subject = (Subject) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), ds);

        subject.request();

        System.out.println(subject.getClass());

        System.out.println(subject.getClass().getSuperclass());
    }

}

package org.iproute.jvm.bytecode.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author tech@intellij.io
 **/
public class DynamicSubject implements InvocationHandler {

    private Object sub;

    public DynamicSubject(Object sub) {
        this.sub = sub;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("before calling: " + method);
        Object result = method.invoke(this.sub, objects);
        System.out.println("after calling " + method);
        return result;
    }
}

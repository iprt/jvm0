package org.iproute.jvm.bytecode.dynamicProxy;

/**
 * @author winterfell
 **/
public class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("from RealSubject");
    }
}

package org.iproute.jvm.classloader;

/**
 * @author winterfell
 **/
public class MyCat {

    public MyCat() {
        System.out.println("MyCat is loaded by " + this.getClass().getClassLoader());
    }
}

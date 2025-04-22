package org.iproute.jvm.classloader;

/**
 * @author tech@intellij.io
 **/
public class MyCat {

    public MyCat() {
        System.out.println("MyCat is loaded by " + this.getClass().getClassLoader());
    }
}

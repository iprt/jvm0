package org.iproute.jvm.classloader;

import com.sun.crypto.provider.AESKeyGenerator;

/**
 * @author tech@intellij.io
 **/
public class MyTest19 {
    public static void main(String[] args) {
        AESKeyGenerator aesKeyGenerator = new AESKeyGenerator();
        System.out.println(aesKeyGenerator.getClass().getClassLoader());
        System.out.println(MyTest19.class.getClassLoader());
    }
}

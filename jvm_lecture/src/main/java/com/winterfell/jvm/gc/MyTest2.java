package com.winterfell.jvm.gc;

/**
 * @author winterfell
 **/
public class MyTest2 {

    public static void main(String[] args) {
        // 1M大小
        int size = 1024 * 1024;

        byte[] myAlloc = new byte[size];

    }

}
/*
java -XX:+PrintCommandLineFlags -version


 */

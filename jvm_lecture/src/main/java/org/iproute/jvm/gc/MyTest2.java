package org.iproute.jvm.gc;

/**
 * @author winterfell
 **/
public class MyTest2 {
    public static void main(String[] args) {
        // 1M大小
        int size = 1024 * 1024;
        byte[] myAlloc = new byte[5 * size];
    }
}
/*
java -XX:+PrintCommandLineFlags -version

-verbose:gc
-Xms20m
-Xmx20m
-Xmn10m
-XX:+PrintGCDetails
-XX:SurvivorRatio=8
-XX:PretenureSizeThreshold=4194304
-XX:+UseSerialGC


// 创建对象大小的阈值 PretenureSizeTheshold以字节为单位
// 如果创建的对象的大小超过了阈值，则不会在新生代分配
-XX:PretenureSizeTheshold=11111

 */

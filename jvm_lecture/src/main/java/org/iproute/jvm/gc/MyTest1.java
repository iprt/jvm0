package org.iproute.jvm.gc;

/**
 * @author tech@intellij.io
 **/
public class MyTest1 {

    public static void main(String[] args) {
        // 1M大小
        int size = 1024 * 1024;

        byte[] myAlloc1 = new byte[2 * size];
        byte[] myAlloc2 = new byte[2 * size];
        byte[] myAlloc3 = new byte[3 * size];

        System.out.println("hello world");

    }
}
/*
// 输出详细的垃圾回收的日志
-verbose:gc

// 打印垃圾回收详细信息
-XX:+PrintGCDetails

// 堆的初始大小
-Xms20M

// 堆的最大值
-Xmx20M

// 新生代的容量是10M
-Xmn10M

// Eden空间和Survivor空间占据的大小比例 8:1
// Eden:From:To = 8:1:1
// 换言之：新生代大小为10M ---> Eden:8M From:1M To:1M
-XX:SurvivorRatio=8

 */
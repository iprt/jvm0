package com.winterfell.jvm.gc;

/**
 * @author winterfell
 **/
public class MyTest4 {

    public static void main(String[] args) throws InterruptedException {
        // byte_1 和 byte_2 会在 from survivor 和 to survivor 中来回拷贝
        // 随着每一次的GC，年龄不断增加
        byte[] byte_1 = new byte[512 * 1024];
        byte[] byte_2 = new byte[512 * 1024];

        myGC();
        Thread.sleep(1000);

        System.out.println("111111111111111111");

        myGC();
        Thread.sleep(1000);

        System.out.println("222222222222222222");

        myGC();
        Thread.sleep(1000);

        System.out.println("333333333333333333");

        myGC();
        Thread.sleep(1000);

        System.out.println("44444444444444444");

        byte[] byte_3 = new byte[1024 * 1024];
        byte[] byte_4 = new byte[1024 * 1024];
        byte[] byte_5 = new byte[1024 * 1024];

        myGC();
        Thread.sleep(1000);

        System.out.println("55555555555555555");

        myGC();
        Thread.sleep(1000);

        System.out.println("66666666666666666");
    }

    private static void myGC() {
        // 方法执行完会被回收
        for (int i = 0; i < 40; i++) {
            byte[] byteArray = new byte[1024 * 1024];
        }
    }
}
/*
启动参数

-verbose:gc
-Xmx200M
-Xmn50M
-XX:TargetSurvivorRatio=60
-XX:+PrintTenuringDistribution
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+UseConcMarkSweepGC
-XX:+UseParNewGC
-XX:MaxTenuringThreshold=3



-XX:TargetSurvivorRatio=60
如果对象在Survivor空间占据的大小超过60%的时候，重新计算对象晋升的阈值

-XX:+PrintTenuringDistribution
打印出对象的年龄

-XX:PrintGCDateStamps
打印出GC执行的时间戳

-XX:+UseConcMarkSweepGC
老年代使用CMS

-XX:+UserParNewGC
新生代使用Parallel GC




 */
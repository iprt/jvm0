package com.winterfell.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟机栈 Stack Frame 栈帧 (方法入栈出栈)
 * 程序计数器（Program Counter）:字节码执行的时候下一条在什么地方
 * 本地方法栈：主要用于执行本地方法
 * 堆（heap）: Java操作对象都是基于引用操作的，JVM管理的最大一块内存空间
 * 方法区（Method Area）：存储元信息  永久代（Permanent Generation） 从JDK 1.8 开始，已经彻底废弃了永久代，使用元空间（meta space）
 * 运行时常量池（编译期确定的）：方法区的一部分内容。
 * 直接内存：Direct Memory
 *
 * @author winterfell
 **/
public class MyTest1 {

    /*
    生成堆转储文件
    -Xms5m -Xmx5m -XX:+HeapDumpOnOutOfMemoryError
     */

    public static void main(String[] args) {
        List<MyTest1> list = new ArrayList<>();
        for (; ; ) {
            list.add(new MyTest1());

            /*System.gc();*/
        }
    }
}
/*
引用（栈） 对象（堆）  元数据（方法区）
 */
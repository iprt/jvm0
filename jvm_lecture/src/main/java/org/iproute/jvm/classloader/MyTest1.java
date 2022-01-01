package org.iproute.jvm.classloader;

/**
 * 对于静态字段来说，只有直接定义了该字段的类才会被初始化
 * 当一个子类初始的时候必须要求其父类初始化完成，一直初始化到Object
 * -XX:+TraceClassLoading 用于追踪类的加载信息并打印出来
 *
 * @author winterfell
 **/
public class MyTest1 {
    public static void main(String[] args) {
        System.out.println(MyChild1.str);
        //  System.out.println(MyChild1.str);
    }
}

class MyParent1 {
    public static String str = "hello world";

    static {
        System.out.println("MyParent1 static block");
    }
}

class MyChild1 extends MyParent1 {
    public static String str2 = "welcome";

    static {
        System.out.println("MyChild1 static block");
    }
}

/*
一个虚拟机参数
-XX:+TraceClassLoading 用于追踪类的加载信息并打印出来


JVM参数理解

-XX:+<option>

-XX:-<option>

加号 + ：开启option选项
减号 - ：关闭option选项


-XX:option=value
   给option选项赋值成value

一共这三种

 */
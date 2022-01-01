package org.iproute.jvm.classloader;

/**
 * @author winterfell
 **/
public class MyTest6 {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println(Singleton.counter1);
        System.out.println(Singleton.counter2);
        // 需要深刻的理解一下准备和初始化
        // 初始化是有顺序的
    }
}

class Singleton {
    public static int counter1;

    private static Singleton singleton = new Singleton();
    private Singleton() {
        counter1++;
        counter2++; // 准备阶段的重要意义
    }

    public static int counter2 = 0;
    public static Singleton getInstance() {
        return singleton;
    }

}

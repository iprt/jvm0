package com.winterfell.jvm.bytecode;

/**
 * 方法的静态分派
 *
 * Grandpa g1 = new Father();
 * g1的静态类型是Grandpa g1的实际类型（真正指向的类型）是Father
 *
 * 我们可以得出这样一个结论：变量的静态类型是不会发生变化，变量的实际类型则是可以发生变化的（多态的一种体现），实际类型是在运行期方可确定
 * @author winterfell
 **/
public class MyTest5 {

    // 对于JVM来说 方法的重载是一种静态的行为，编译器就可以完全确定的

    public void test(Grandpa grandpa) {
        System.out.println("grandpa");
    }

    public void test(Father father) {
        System.out.println("father");
    }

    public void test(Son son) {
        System.out.println("son");
    }

    public static void main(String[] args) {

        Grandpa g1 = new Father();
        Grandpa g2 = new Son();

        MyTest5 myTest5 = new MyTest5();

        myTest5.test(g1);
        myTest5.test(g2);
    }
}

class Grandpa {

}

class Father extends Grandpa {

}

class Son extends Father {

}

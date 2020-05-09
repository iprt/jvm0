package com.winterfell.jvm.bytecode;

/**
 * @author winterfell
 **/
public class MyTest4 {

    public static void test(){
        System.out.println("test invoked");
    }

    public static void main(String[] args) {
        test();
    }
}


/*
- `invokeinterface`: 调用接口方法，实际上是在运行期间决定的，决定了到底调用实现该接口的哪个对象的特定方法
- `invokestatic`: 调用静态方法
- `invokespecial`: 调用自己的私有方法，构造方法(<init>)以及父类的方法
- `invokevirtual`: 调用虚方法，运行期动态查找的过程
- `invokedynamic`: 动态调用方法
 */
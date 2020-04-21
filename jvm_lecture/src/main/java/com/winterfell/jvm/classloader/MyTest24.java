package com.winterfell.jvm.classloader;

/**
 * 当前类加载器 (Current ClassLoader)
 *
 * 每个类都会
 *
 * @author winterfell
 **/
public class MyTest24 {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());
    }
}
/*
- `Current ClassLoader` 每个类都会使用自己的类加载器（即加载自身的类加载器）来去加载其他类（指的是所依赖的类）
    - 如果ClassX 引用了 ClassX 那么ClassX的类加载器就会尝试着去加载ClassY
        - 前提是ClassY尚未被加载
            - 尚未被加载在同一命名空间
- 线程上下文类加载器是从JDK1.2引入的，类`Thread`中的`getContextClassLoader()`与`setContextClassLoader(ClassLoader cl)` 分别用来获取和设置上下文类加载器
    - 如果没有通过`setContextClassLoader(ClassLoader cl)`进行设置的话，线程将继承其父线程的上下文类加载器
- Java应用运行时的**初始线程的上下文加载器是系统类加载器（main）**。在线程中运行的代码可以通过该类加载器来加载类与资源
- 线程上下文类加载器的重要性：
    - 分析一个JDBC的场景
    - SPI(Service Provider Interface)
    - 父ClassLoader可以使用当前线程 `Thread.currentThread().gerContextLoader` 所指定的 `ClassLoader` 加载的类
        - 这就改变了 `父ClassLoader` 不能使用 `子ClassLoader` 或者其他没有父子关系的`ClassLoader`加载类的情况
            - 即**改变了双亲委托模型**
- 线程上下文类加载器，就是当前的线程的 `Current ClassLoader`
- 在双亲委托模型下，类加载是由下至上的，即下层的类加载器会委托上层进行加载。
    - 但是对于SPI来说。有些接口是Java核心库所提供的，而Java核心库是由启动类加载器来加载的，而这些接口的实现却来自于不同的jar包
    - Java的启动类加载器是不会加载其他来源的jar包，那么传统的双亲委托模型就无法满足SPI的要求。而通过给当前线程设置上下文类加载器，就可以由设置的上下文加载器来实现对于接口的实现的加载。
 */
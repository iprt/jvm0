package org.iproute.jvm.classloader;

/**
 * @author tech@intellij.io
 */
public class MyTest15 {
    public static void main(String[] args) {
        String[] strings = new String[1];
        System.out.println(strings.getClass().getClassLoader());

        // 根据类加载器doc的翻译
        // 数组的类加载器由数据的元素的类加载决定的

        System.out.println("--------------------");
        MyTest15[] myTest15s = new MyTest15[1];
        System.out.println(myTest15s.getClass().getClassLoader());

        System.out.println("----------------");
        int[] ints = new int[1];
        System.out.println(ints.getClass().getClassLoader());

    }
}
/*
类加载器是负责加载类的对象。
ClassLoader类是一个抽象类。
给定类的二进制名称，类加载器应尝试查找或生成构成该类定义的数据。
一种典型的策略是将名称转换为文件名，然后从文件系统中读取该名称的“类文件”。
每个Class对象都包含对定义它的ClassLoader的引用。

数组类的类对象不是由类加载器创建的，而是根据Java运行时的要求自动创建的。由Class.getClassLoader（）返回的数组类的类加载器与其元素类型的类加载器相同。

如果元素类型是原始类型，则数组类没有类加载器。

应用程序实现ClassLoader的子类，以扩展Java虚拟机动态加载类的方式。

安全管理人员通常可以使用类加载器来指示安全域。 ClassLoader类使用委托模型搜索类和资源。

每个ClassLoader实例都有一个关联的父类加载器。
当请求查找类或资源时，ClassLoader实例会将对类或资源的搜索委托给其父类加载器，然后再尝试查找类或资源本身。

虚拟机的内置类加载器（称为“引导类加载器”）本身没有父级，但可以用作ClassLoader实例的父级。
支持并发加载类的类加载器称为具有并行功能的类加载器，并且要求它们通过调用ClassLoader.registerAsParallelCapable方法在其类初始化时进行自身注册。

请注意，默认情况下，ClassLoader类注册为具有并行功能。
但是，如果它们的子类具有并行功能，则仍然需要注册自己。

在委派模型不是严格分层的环境中，类加载器需要具有并行功能，否则类加载会导致死锁，因为在类加载过程中保持了加载器锁（请参见loadClass方法）。

通常，Java虚拟机以平台相关的方式从本地文件系统加载类。

例如，在UNIX系统上，虚拟机从CLASSPATH环境变量定义的目录中加载类。

但是，某些类可能不是源自文件的。它们可能源自其他来源，例如网络，也可能由应用程序构造。

方法defineClass将字节数组转换为Class类的实例。

可以使用Class.newInstance创建此新定义的类的实例。
由类加载器创建的对象的方法和构造函数可以引用其他类。
为了确定所引用的类，Java虚拟机将调用最初创建该类的类加载器的loadClass方法。

 */
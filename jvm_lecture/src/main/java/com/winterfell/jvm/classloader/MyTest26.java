package com.winterfell.jvm.classloader;

/**
 * 线程上下文类加载器的一般使用模式（获取-使用-还原）
 *
 *  ClassLoader classLoader = Thread.currentThread().getContextContextClassLoader();
 *
 *  try{
 *      Thread.currentThread().setContextClassLoader(targetTccl);
 *      myMethod();
 *  } finally {
 *      Thread.currentThread().setContextClassLoader(classLoader);
 *  }
 *
 *  myMethod() 里面则调用了 Thread.currentThread().getContextContextClassLoader(); 获取当前线程的上下文类加载器做某些事情
 *
 *  如果一个类由类加载器A加载，这个类的依赖类也是由相同的类加载的（如果该依赖类之前没有被加载的话）
 *
 *  ContextClassLoader的作用就是为了破坏Java的类加载委托机制
 *
 *  当高层提供了同一的接口让低层去实现，同时又要在高层加载（或实例化），就必须要通过现成上下文类加载器来帮助高层的ClassLoader找到并加载类
 *
 * @author winterfell
 **/
public class MyTest26 {

    public static void main(String[] args) {


    }
}

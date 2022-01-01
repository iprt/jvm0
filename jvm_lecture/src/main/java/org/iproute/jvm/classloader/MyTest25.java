package org.iproute.jvm.classloader;

/**
 * 线程类加载器
 *
 * @author winterfell
 **/
public class MyTest25 implements Runnable {

    private Thread thread;

    public MyTest25() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        ClassLoader classLoader = this.thread.getContextClassLoader();

        this.thread.setContextClassLoader(classLoader);
        // 对于这个线程来说 就是使用的应用类加载器
        System.out.println("Class : " + classLoader.getClass());
        System.out.println("Parent: " + classLoader.getParent().getClass());
    }


    public static void main(String[] args) {
        new MyTest25();
    }

}

/*
为什么默认的线程的上下文类加载器使用的默认的类加载器

查看一下Launcher源码

    public Launcher() {
        Launcher.ExtClassLoader var1;
        try {
            var1 = Launcher.ExtClassLoader.getExtClassLoader();
        } catch (IOException var10) {
            throw new InternalError("Could not create extension class loader", var10);
        }

        try {
            this.loader = Launcher.AppClassLoader.getAppClassLoader(var1);
        } catch (IOException var9) {
            throw new InternalError("Could not create application class loader", var9);
        }

        Thread.currentThread().setContextClassLoader(this.loader);


 */
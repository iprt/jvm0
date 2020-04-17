package com.winterfell.jvm.classloader;

/**
 * @author winterfell
 **/
public class MySample {

    public MySample() {
        System.out.println("MySample is loaded by " + this.getClass().getClassLoader());

        // 关键的一行代码

        // 加载了 MySample的类加载器会尝试加载 MyCat对象
        new MyCat();
    }
}

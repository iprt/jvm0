package com.winterfell.test;

import com.winterfell.basic.Welcome;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author winterfell
 **/
public class ClientTest {

    public static void main(String[] args) {

        ServiceLoader<Welcome> serviceLoader = ServiceLoader.load(Welcome.class);

        for (Welcome welcome : serviceLoader) {
            System.out.println(welcome.sayHello("zhuzhenjie"));
        }

    }

}

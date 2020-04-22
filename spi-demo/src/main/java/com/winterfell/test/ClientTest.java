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

        Iterator<Welcome> iterator = serviceLoader.iterator();

        while (iterator.hasNext()){
            Welcome welcome = iterator.next();
            System.out.println(welcome.sayHello("zhuzhenjie"));
        }

    }

}

package com.winterfell.basicimpl;

import org.iproute.spi.basic.Welcome;

/**
 * @author tech@intellij.io
 **/
public class EnglishWelcome implements Welcome {
    @Override
    public String sayHello(String word) {
        return "Hello, " + word;
    }
}

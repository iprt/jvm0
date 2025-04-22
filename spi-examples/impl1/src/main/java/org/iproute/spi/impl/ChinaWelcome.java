package org.iproute.spi.impl;

import org.iproute.spi.basic.Welcome;

/**
 * @author tech@intellij.io
 **/
public class ChinaWelcome implements Welcome {
    @Override
    public String sayHello(String word) {
        return "你好，" + word;
    }
}

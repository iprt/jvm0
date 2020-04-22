package com.winterfell.basicimpl;

import com.winterfell.basic.Welcome;

/**
 * @author winterfell
 **/
public class ChinaWelcome implements Welcome {
    @Override
    public String sayHello(String word) {
        return "你好" + word;
    }
}

package com.winterfell.basicimpl;

import com.winterfell.basic.Welcome;

/**
 * @author winterfell
 **/
public class EnglishWelcome implements Welcome {
    @Override
    public String sayHello(String word) {
        return "hello " + word;
    }
}

package com.example.demo.spi;

public class Log4j implements Log{
    @Override
    public void debug() {
        System.out.println("---------Log4j------------");
    }
}

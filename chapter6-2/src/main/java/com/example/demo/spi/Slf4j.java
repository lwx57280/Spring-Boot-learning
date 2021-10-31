package com.example.demo.spi;

public class Slf4j implements Log{
    @Override
    public void debug() {
        System.out.println("-----------------Slf4j------------");
    }
}

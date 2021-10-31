package com.example.demo.spi;

public class Logback implements Log{
    @Override
    public void debug() {
        System.out.println("-------------Logback-----------------");
    }
}

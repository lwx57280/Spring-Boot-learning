package com.example.demo.test;


import com.example.demo.spi.Log;
import org.junit.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;

import java.util.List;

public class SpiTest {

    @Test
    public void test(){
        // 获取对应的实例名称
        // 通过spring类加载器  ClassUtils.getDefaultClassLoader()
        List<String> strings = SpringFactoriesLoader.loadFactoryNames(Log.class, ClassUtils.getDefaultClassLoader());
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testInstance(){
        // 获取接口对应的所有实现实例
        List<Log> logs = SpringFactoriesLoader.loadFactories(Log.class, ClassUtils.getDefaultClassLoader());

        for (Log log : logs) {
            System.out.println(log);
        }
    }



}

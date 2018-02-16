package com.example.demo.service;


import com.example.demo.domain.Person;

/**
 * 服务接口
 */
public interface DemoService {

    Person savePersonWithRollBack(Person person);

    Person savePersonWithoutRollBack(Person person);

    Person save(Person person);

    void remove(Long id);

    Person findOne(Person person);
}

package com.example.demo.web;


import com.example.demo.dao.PersonRepository;
import com.example.demo.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {
    // 1、Spring Data JPA已自动为你注册bean,所以可自动注入
    @Autowired
    PersonRepository personRepository;

    /**
     *
     * 保存save支持批量保存：<S extents T> Iterable<S> save(Iterable<S> entities
     * 删除支持使用id删除对象，批量删除以及删除全部
     * void delete(ID id)
     * void delete(T entity)
     * void delete(Iterable<? extents T> entities);
     * void deleteAll();
     *
     * @param name
     * @param address
     * @param age
     * @return
     */
    @RequestMapping("/save")
    public Person save(String name, String address, Integer age){
        Person p = personRepository.save(new Person(null,name,age,address));
        return p;
    }

    /**
     * 测试findByAddress
     * @param address
     * @return
     */
    @RequestMapping("/q1")
    public List<Person> q1(String address){
        List<Person> people = personRepository.findByAddress(address);
        return people;
    }


    /**
     *  测试findByNameAndAddress
     * @param name
     * @param address
     * @return
     */
    @RequestMapping("/q2")
    public Person q2(String name,String address){
        Person people = personRepository.findByNameAndAddress(name, address);
        return people;
    }

    /**
     * 测试withNameAndAddressQuery
     * @param name
     * @param address
     * @return
     */
    @RequestMapping("/q3")
    public Person q3(String name ,String address){
        Person person = personRepository.withNameAndAddressQuery(name, address);
        return person;
    }

    /**
     * 测试withNameAndAddressNamedQuery
     * @param name
     * @param address
     * @return
     */
    @RequestMapping("/q4")
    public Person q4(String name,String address){
        Person p = personRepository.withNameAndAddressNamedQuery(name, address);
        return p;
    }

    // 访问:http://localhost:9090/sort

    /**
     * 测试排序
     * @return
     */
    @RequestMapping("/sort")
    public List<Person> sort(){
        List<Person> personList = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return personList;
    }

    /**
     * 测试分页
     * @return
     */
    @RequestMapping("/page")
    public Page<Person> page(){
        Page<Person> personPage = personRepository.findAll(new PageRequest(1, 2));
        return personPage;
    }

    @RequestMapping("/auto")
    public Page<Person> auto(Person person){
        Page<Person> personPage = personRepository.findByAuto(person, new PageRequest(0, 10));
        return personPage;
    }
}

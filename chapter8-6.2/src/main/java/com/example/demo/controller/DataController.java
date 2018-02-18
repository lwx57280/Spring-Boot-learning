package com.example.demo.controller;

import com.example.demo.dao.PersonDao;
import com.example.demo.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    PersonDao personDao;

    /**
     * 1、演示设置字符串及对象
     */
    @RequestMapping("/set")
    public void set(){
        Person person = new Person("1","wyf",32);
        personDao.save(person);
        personDao.stringRedisTemplateDao();
    }

    /**
     * 2、演示获得字符
     * @return
     */
    @RequestMapping("/getStr")
    public String getStr(){
        return personDao.getString();
    }

    /**
     * 3、演示获得对象
     * @return
     */
    @RequestMapping("/getPerson")
    public Person getPerson(){
        return personDao.getPerson();
    }
}

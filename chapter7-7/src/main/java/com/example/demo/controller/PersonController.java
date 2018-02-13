package com.example.demo.controller;


import com.example.demo.bean.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @RequestMapping(value = "/search",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person search(String personName){
        return new Person(personName,32,"hefei");
    }
}

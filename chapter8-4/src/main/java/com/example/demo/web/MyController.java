package com.example.demo.web;

import com.example.demo.domain.Person;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    DemoService demoService;

    /**
     * 测试回滚情况
     * @param person
     * @return
     */
    @RequestMapping("/rollback")
    public Person rollback(Person person){
        return demoService.savePersonWithRollBack(person);
    }

    /**
     * 测试不回滚情况
     * @param person
     * @return
     */
    @RequestMapping("/norollback")
    public Person noRollback(Person person){
        return demoService.savePersonWithoutRollBack(person);
    }
}

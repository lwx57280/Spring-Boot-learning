package com.example.demo.domain;

import javax.validation.constraints.Size;

public class Person {

    /**
     * 1、此处使用JSR-303注解来校验数据
     */
    @Size(max = 4,min = 2)
    private String name;

    private Integer age;

    private String nation;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

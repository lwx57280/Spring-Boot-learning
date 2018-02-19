package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 角色
 */
@Entity
public class SysRole implements Serializable{

    private static final long serialVersionUID = -2944404024591108470L;

    @Id
    @GeneratedValue
    private Long id;

    /**1、name为角色名称。*/
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

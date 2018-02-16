package com.example.demo.dao;

import com.example.demo.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 实体类Repository
 * 如果要对映射的名称进行修改的话，需要
 * 在实体类Repository上使用@RepositoryRestResource注解的path属性进行修改
 */
public interface PersonRepository extends JpaRepository<Person,Long> {


}

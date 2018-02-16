package com.example.demo.dao;


import com.example.demo.domain.Person;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * 如果要对映射的名称进行修改的话，需要
 * 在实体类Repository上使用@RepositoryRestResource注解的path属性进行修改
 *
 * @RepositoryRestResource(path = "people")此处接口若不加注解就以实体对象复数形式访问
 * 如：http://localhost:8089/persons
 */
@RepositoryRestResource(path = "people")
public interface PersonRepository extends JpaRepository<Person,Long> {


    @RestResource(path="nameStartsWith",rel="nameStartsWith")
    Person findByNameStartsWith(@Param("name") String name);
}

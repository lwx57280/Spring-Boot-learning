package com.example.demo.dao;

import com.example.demo.domain.Person;
import com.example.demo.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 如果要对映射的名称进行修改的话，需要
 * 在实体类Repository上使用@RepositoryRestResource注解的path属性进行修改
 *
 *  只需让实体类Repostiory继承我们自定义的Repository接口，即可使用我们在自定义Repostiroy中实现的功能。
 */
public interface PersonRepository extends CustomRepository<Person,Long> {

    // 1、接受一个name参数，返回值列表
    List<Person> findByAddress(String name);
    // 2、使用方法名查询,接受namae和address 返回值单个对象
    Person findByNameAndAddress(String name, String address);
    // 3、使用@Query 查询,参数按照铭绑定
    @Query("select p from Person p where p.name=:name and p.address=:address")
    Person withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);
    // 4、使用@NamedQuery查询,注意实体类中做的@NamedQuery
    Person withNameAndAddressNamedQuery(String name, String address);

    //URL:http://localhost:9090/people/?sort=age,desc

}

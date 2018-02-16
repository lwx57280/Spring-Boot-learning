package com.example.demo.service.service.impl;


import com.example.demo.dao.PersonRepository;
import com.example.demo.domain.Person;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoServiceImpl implements DemoService {

    /**
     * 1、可以直接注入我们的RersonRepository的Baen
     */
    @Autowired
    PersonRepository personRepository;

    /**
     * 2、使用@Transactional注解的rollbackFor属性，指定特定异常时，数据回滚。
     * @param person
     * @return
     */

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public Person savePersonWithRollBack(Person person) {
        Person p = personRepository.save(person);
        if(person.getName().equals("杨英")){
            //3、硬编码手动触发异常。
            throw new IllegalArgumentException("杨英已存在,数据将回滚!");
        }
        return p;
    }

    /**
     * 4、使用@Transactional注解的noRollbackFor属性，指定特定异常时，数据不回滚。
     * @param person
     * @return
     */

    @Override
    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    public Person savePersonWithoutRollBack(Person person) {
        Person p = personRepository.save(person);
        if(person.getName().equals("杨英")){
            throw new IllegalArgumentException("杨英虽已存在,数据将不会回滚!");
        }
        return p;
    }


    /**
     *   1、@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为people，数据的key是person的id
     * @param person
     * @return
     */
    @Override
    @CachePut(value = "people",key = "#person.id")
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为id，key为:"+p.getId()+"数据做了缓存");
        return p;
    }

    /**
     * 2、@CacheEvit从缓存people中删除key为id的数据。
     * @param id
     */
    @Override
    @CacheEvict(value = "people")
    public void remove(Long id) {
        System.out.println("删除了id,key为:"+id+"的数据缓存");
        personRepository.delete(id);
    }

    /**
     * * 3、@Cacheable缓存key为person的id数据到缓存people中。
     * @param person
     * @return
     */
    @Override
    @Cacheable(value = "people",key = "#person.id")
    public Person findOne(Person person) {
        Person p = personRepository.findOne(person.getId());
        System.out.println("为id，key为:"+p.getId()+"数据做了缓存");
        return p;
    }
}

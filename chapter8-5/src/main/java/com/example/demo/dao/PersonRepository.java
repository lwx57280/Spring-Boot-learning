package com.example.demo.dao;

import com.example.demo.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 实体类Repository
 */
public interface PersonRepository extends JpaRepository<Person,Long> {


}

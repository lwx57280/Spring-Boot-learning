package com.example.demo.dao;


import com.example.demo.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    SysUser findByUsername(String userName);

}

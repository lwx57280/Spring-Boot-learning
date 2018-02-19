package com.example.demo.security;

import com.example.demo.dao.SysUserRepository;
import com.example.demo.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 1、自定义需实现UserDetailsService接口。
 */
public class CustomUserService implements UserDetailsService {

    @Autowired
    SysUserRepository userRepository;

    /**
     * 2、重写loadUserByUsername方法获得用户。
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(userName);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在!");
        }
//        3、当前的用户实现了UserDetails接口，可直接返回给Spring Security使用。
        System.out.println("userName:"+userName);
        System.out.println("userName:"+user.getUsername()+";password:"+user.getPassword());
        return user;
    }
}

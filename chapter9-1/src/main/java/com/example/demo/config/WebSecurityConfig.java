package com.example.demo.config;

import com.example.demo.security.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 1、拓展Spring Security配置需继承WebSecurityConfigurerAdapter。
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 2、注册customUserService的Bean。
     * @return
     */
    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //3、添加我们自定义的user detail service认证。
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()   //4、所有请求需要认证即登录后才能访问。
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
               // .defaultSuccessUrl("/home")     // 3、登录成功后转向/home路径
                .permitAll()                    //5、定制登录行为，登录页面可任意访问。
                .and()
                .logout().permitAll();          //6、定制注销行为，注销请求可任意访问。
    }


    /**
     * 5、/resources/static/目录下的静态资源，Spring Security不拦截
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**","/static/**");
        web.ignoring().antMatchers("/bootstrap/**","/js/**");
    }

}

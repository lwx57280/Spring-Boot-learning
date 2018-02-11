package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1、设置Spring Security对/和/“login”路径不拦截
        http.authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")                // 2、设置Spring Security的登录页面访问的路径为“/login”
                .defaultSuccessUrl("/chat")         // 3、登录成功后转向/chat路径
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /**
     * 4、在内存中分别配置两个用户wyf和wisely，密码和用户名一致,角色USER
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("wyf").password("wyf").roles("USER")
                .and()
                .withUser("wisely").password("wisely").roles("USER");
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
        web.ignoring().antMatchers("/js-action/**");
    }
}

package com.example.demo;

import com.example.demo.support.support.CustomRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 在配置类上配置@EnableJpaRepositories,并指定repositoryFactoryBeanClass，让子女定义的
 * Repository实现起效。
 *
 * 如果不需要自定义Repository实现，则在Spring Data JPA里无须添加@EnableJpaRepositories注解，
 * 因为@SpringBootApplication包含的@EnableAutoConfiguraion注解已经开启了对Spring Data JPA的支持。
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

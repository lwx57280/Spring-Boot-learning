# Spring-Boot-learning
* Spring Boot实战

什么是Spring Boot
=================
Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使<br>
用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。用我的话来理解，就是spring boot其实不是什么新的框架，<br>
它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，spring boot整合了所有的框架（不知道这样比喻是否合适）。

	
Spring Boot特性理解
-------------------
	* 为基于Spring的开发提供更快的入门体验。
	
	* 开箱即用，没有代码生成，也无需XML配置，同时也可以修改默认值来满足特定的需求。
	
	* 提供了一些大型项目中常见的非功能特性，如嵌入式服务器、安全、指标，健康检测、外部配置等。
	
	* Spring Boot并不是对Spring功能上的增强，而是提供一种快速使用Spring的方式。
	
Spring Boot优缺点：
------------------
	# 优点：
	* (1)、快速构建项目;
	
	* (2)、对主流开发框架的无配置集成；
	
	* (3)、项目可独立运行，无须外部依赖Servlet容器。
	
	* (4)、提供运行时的应用监控；
	
	* (5)、极大地提高了开发、部署效率；
	
	* (6)、与云计算的天然集成。
	# 缺点：
	* (1)、书籍文档较少且不够深入；
	
	* (2)、如果不认同Spring框架，这也是它的缺点，但建议一定要使用Spring框架。

* 项目结构介绍:
    
![项目目录结构](https://github.com/lwx57280/Spring-Boot-learning/blob/master/img-folder/SpringBoot.jpg) 
* 另外，spingboot建议的目录结果如下：
* com
* +- example
* +- myproject
* +- Application.java
>*   |
    +- domain <br>
     |  +- Customer.java <br>
     |  +- CustomerRepository.java <br>
     |  <br>
      +- service <br>
     |  +- CustomerService.java <br>
     | <br>
        +- controller <br>
     |  +- CustomerController.java <br>
     | <br>
   
  1、Application.java 建议放到跟目录下面,主要用于做一些框架配置。
  
  2、domain目录主要用于实体（Entity）与数据访问层（Repository）。
  
  3、service 层主要是业务类代码。
  
  4、controller 负责页面访问控制。
  
 采用默认配置可以省去很多配置，当然也可以根据自己的喜欢来进行更改最后，启动Application main方法，至此一个java项目搭建好了！
* Spring Boot核心

基本配置
=======

## 入口类和@SpringBootApplication
SpringBoot通常有一个名为*Application的入口类,入口类有一个面方法，这个main方法<br>
其实就是一个标准的Java应用入口方法。在main方法中使用SpringApplication.run(Chapter52Application.class, args), <br>
启动Spring Boot应用项目。

* @SpringBootApplication是Spring Boot的核心注解，它是一个组合注解，源码如下：

@Target({ElementType.TYPE}) <br>
@Retention(RetentionPolicy.RUNTIME) <br>
@Documented <br>
@Inherited <br>
@SpringBootConfiguration <br>
@EnableAutoConfiguration <br>
@ComponentScan(excludeFilters = {@Filter(type = FilterType.CUSTOM,classes = {TypeExcludeFilter.class}), <br>
@Filter(type = FilterType.CUSTOM,classes = {AutoConfigurationExcludeFilter.class})}) <br>
public @interface SpringBootApplication { <br>
    @AliasFor(annotation = EnableAutoConfiguration.class, attribute = "exclude")  <br>
	
    Class<?>[] exclude() default {}; <br>
  
 * @SpringBootApplication注解主要组合了@Configuration、@EnableAutoConfiguration、@ComponentScan；若不适用<br>
 @SpringBootApplication注解，则可以在入口类上直接使用@Configuration、@EnableAutoConfiguration、@ComponentScan。
 
 * 其中，@EnableAutoConfiguration让Spring Boot根据类路径中的jar包依赖当前项目进行自动配置。<br>
 
 例如,添加了spring-boot-starter-web依赖，会自动添加Tomcat和Spring MVC的依赖，Spring Boot会对Tomcat和Spring MVC<br>
 进行自动配置。
 
 
* Spring Boot配置文件
    Spring Boot使用一个全局的配置文件application.properties或application.yml,放置在src/mian/resources目录或者类路径<br>
    的/config下。
    
    Spring Boot不仅支持常规的properties配置文件，还支持yaml语言的配置文件。yaml是以数据为中心的语言，在配置数据的时候具有<br>
    面向对象的特征。<br>
    Spring Boot全局配置文件的作用是对一些默认的配置值进行修改。

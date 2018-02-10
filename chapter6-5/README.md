* Spring Boot运行原理

在application.properties中设置属性：
----------------------------------
    debug=true <br>
 查看当前项目中已启用和未启用的自动配置的报告。
 
* 运行原理
@EnableAutoConfiguration注解源码:

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({EnableAutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
    String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

    Class<?>[] exclude() default {};

    String[] excludeName() default {};
}

* 关键功能是@Import注解导入的配置功能，EnableAutoConfigurationImportSelector使用SpringFactoriesLoader.loadFactoryNames<br>
方法来扫描具有META-INF/spring.factories文件的jar包。

如图：

* 核心注解

    @ConditionalOnBean：当容器里有指定的Bean的条件下。<br>
    
    @ConditionalOnClass:当类路径下有指定的类的条件下。<br>
    
    @ConditionalOnExpression:基于SpEL表达式作为判断条件。<br>
    
    @ConditionalOnJava：基于JVM版本作为判断条件。<br>
    
    @ConditionalOnJndi:在JNDI存在的条件下查找指定的位置。<br>
    
    @ConditionalOnMissingBean:当容器里没有指定Bena的情况下。<br>
    
    @ConditionalOnMissingClass:当类路径下没有指定的类的条件下。<br>
    
    @ConditionalOnNotWebApplication:当前项目不是Web项目的条件下。<br>
    
    @ConditionalOnProperty:指定的属性是否有指定的值。<br>
    
    @ConditionalOnResource:类路径是否有指定的值。<br>
    
    @ConditionalOnSingleCandidate:当指定Bean在容器中只有一个，或者虽然有多个但是指定首选的Bean。<br>
    
    @ConditionalOnWebApplication:当前项目是Web项目的条件下。<br>
    
    这些注解都是组合了@Conditional元注解，只是使用了不同的条件（Condition）。
    
    
* 注册配置

若想自动配置生效,需要注册自动配置类。
---------------------------------
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.demo.config.HelloServiceAutoConfiguration

若有多个自动配置，则用“，”隔开，此处“\”是为了换行后仍然能读到属性。
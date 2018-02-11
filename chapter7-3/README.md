* Web相关配置

Spring Boot提供的自动配置
------------------------

1、自动配置的ViewResolver
------------------------
    (1)、ContentNegotiatingViewResolver <br>
    这是Spring MVC提供的一个特殊的ViewResolver，ContentNegotiatingViewResolver不是自己处理View,而是代理给不同的<br>
    ViewResolver来处理不同的View,所以它有最高的优先级。
    
    (2)、BeanNameViewResolver
    在控制器（@Controller）中的一个方法的返回值的字符串（视图名）会根据BeanNameViewResolver去查找Bean的名称为返回字符<br>
    的View来渲染视图。
    
    （3）、InternalResolverViewResolver
    这是一个极为常用的ViewResolver,主要通过设置前缀、后缀，以及控制器中方法来返回视图名的字符串，以得到实际页面。
    
2、自动配置的静态资源
--------------------
    (1)、类路径文件
    把类路径下的/static、/public、/resources和/META-INF/resources文件夹下的静态文件直接映射为/**,可以通过<br>
    http://localhost:8080/**访问。
    
   （2）webjar
    何谓webjar，webjar就是将我们常用的脚本框架封装在jar包中的jar包，更多关于webjar的内容请访问http://www.webjars.org网站。
    
    把webjar的/META-INF/resources文件夹下的静态文件直接映射为/webjar/**,可以通过http://localhost:8080/webjar/**来访问。
    
3、自动配置的Formater和Converter
------------------------------
    关于自动配置Formater和Converter,我们可以看一下WebMvcAutoConfiguration类中的定义。
    只要我们定义了Converter、GenericConverter和Formater接口的实现类的Bean,这些Bean就会自动注册到Spring MVC中。
  
4、自动配置的HttpMessageConverters
----------------------------------
    在WebMvcAutoConfiguration中，注册了messsageConverter,代码如下：
     @Autowired
     private HttpMessageConverters messageConverters;
     
     public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
         converters.addAll(this.messageConverters.getConverters());
     }

5、静态首页的支持
---------------
    把静态index.html文件放置在如下目录：
    ● classpath:/META-INF/resources/index.html。
    
    ● classpath:/resources/index.html。
    
    ● classpath:/static/index.html。  
 
    ● classpath:/public/index.html。
    
    当我们访问应用根目录http://localhost:8080时，会直接映射。
    
* 7.3 接管Spring Boot的Web配置
    如果Spring Boot提供的Spring MVC默认配置不符合你的需求，则可以通过一个配置类（注解有@Configuration的类）加上<br>
    @EnableWebMvc注解来实现自己控制的MVC配置。
       
    通常情况下，Spring Boot的自动配置是符合我们大多数需求的。在你既需要保留Spring Boot提供的便利，又需要增加自己的额外<br>
    的配置的时候，可以定义一个配置类并继承WebMvcConfigurerAdapter,无须使用@EnableWebMvc注解。
    
    @Configuration  <br>
    @EnableWebMvc   //@EnableWebMvc注解，开启对SpringMVC的配置支持<br>
    @EnableScheduling <br>
    @ComponentScan("com.springmvc4") <br>
    public class MyMvcConfig extends WebMvcConfigurerAdapter { <br>
        
       /**
        * 添加转向upload页面的ViewControllers
        * @param registry
        */
       @Override
       public void addViewControllers(ViewControllerRegistry registry) {
           registry.addViewController("/index").setViewName("/index");
           // 添加更多、、、、
       }
    这里重写的addViewControllers方法，并不会覆盖WebMvcAutoConfiguraion中的AddViewControllers（在此方法中、Spring Boot<br>
    将"/"映射至index.html），这也就意味着我们自己的配置和Spring Boot的自动配置同时有效，这也是推荐添加自己的MVC配置方式。
    
    
* 注册Servlet、Filter、Listener

    当使用嵌入式的Servlet容器（Tomcat、Jetty等）时，我们通过将Servlet、Filter和Listener声明为Spring Bean而达到注册的效果<br>
    或者注册ServletRegistrationBean、FilterRegistrationBean和ServletListenerRegistrantionBean的Bean。
    
    访问方式：http://localhost:8089/hello
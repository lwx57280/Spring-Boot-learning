* 数据缓存Cache

8.1 Spring缓存支持
-----------------

    Spring定义了org.springframework.cache.CacheManager和org.springframework.cache.Cache接口用来统一不同的缓存技术。<br>
    其中，CahceManager是Spring提供的各种缓存技术抽象接口，Cache接口包含缓存的各种操作(增加、删除、获得缓存、我们一般不会直<br>
    接和此接口打交道)。
    
8.2 声明式缓存注解
------------------

    @Cahceable、@CachePut、@CacheEvit属性都有value属性,指定的是要使用的缓存名称；key属性指定的是数据在缓存中存储的键。
    
8.3开启声明式缓存支持
--------------------
    开启声明式缓存支持十分简单，只需在配置类上使用@EnableCaching注解即可，例如：
        @Configuration
        @EnableCaching
        public class Application {
        
        	
        }
   
Spring Boot的支持
----------------
    在Spring中使用缓存技术的关键是配置CacheManager，而Spring Boot为我们自动配置了多个CacheManager的实现。
    
    Spring Boot的CacheManager的自动配置放置在org.springframework.boot.autoconfigure.cache包中，如图：
    
![Spring boot缓存](https://github.com/lwx57280/Spring-Boot-learning/blob/master/chapter8-5/img-folder/Cache.jpg)
    在Spring Boot环境下，使用缓存技术只需在项目中导入相关的缓存技术依赖包，并在配置类使用@EnableCaching开启缓存支持即可。
    
    
* 切换缓存技术

    切换缓存技术除了导入相关依赖包或者配置外，使用方式和实战例子保持一致。EhCache和Guava作为缓存技术的方式，其余缓存技术<br>
    也是类似的方式。
    
    1、EhCache
    
    当使用EhCache作为缓存技术的时候，我们只需在pom.xml中添加EhCache的依赖即可。
    
        <!--EhCache缓存依赖-->
        <dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
        </dependency>
     EhCache所需的配置文件ehcache.xml只需放在类路径下，Spring Boot会自动扫描，例如：
     
        <?xml version="1.0" encoding="UTF-8"?>
        <ehcache>
            <cache name="people" maxElementsInMemory="1000"></cache>
        </ehcache>
        
       Spring Boot会给我们自动配置EnCacheManager的Bean。
      
    2、Guava
        当需要使用Guava作为缓存技术的时候，同样只需在pom.xml中增加Guava的依赖即可：
        
            <!--添加google guava依赖,它包含大量Java常用的工具类-->
            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>18.0</version>
            </dependency>
            
        Spring Boot会给我们自动配置GuavaCacheManager这个Bean。
        
        

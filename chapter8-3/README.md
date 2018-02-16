* Spring Data REST

    （1）什么是Spring Data REST
        
        Spring Data JPA是基于Spring Data的repository之上，可以将repository自动输出为REST资源。目前Spring Data REST 
        支持Spring Data JPA、Spring Data MongoDB、Spring Data Neo4j、Spring Data GemFire以及Spring Data Cassandra的 
        repository自动转换成REST服务。
        
    （2）Spring MVC中配置使用Spring Data REST
        
        是定义RepositoryRestMvcConfiguration（org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration）
        配置类中已经配置好了，我们可以通过继承此类或者直接在自己的配置类上@import此配置类。
    
* Spring Boot的支持
    
    Spring Boot对Spring Data REST的自动配置放置在Rest中，如图：
    
    
    
    
   5、REST的服务测试
    (1)列表
        
        
    (2)获取单一对象
    
    (3)查询
       在自定义实体类Repository中定义了findByNameStartsWith方法,若想此方法也暴露为REST资源，需要做如下修改:
       
       public interface PersonRepository extends JpaRepository<Person,Long> {
       
       
           @RestResource(path="nameStartsWith",rel="nameStartsWith")
           Person findByNameStartsWith(@Param("name") String name);
       }
   
   (4)、分页
   在postman中使用GET访问http://localhost:8089/persons/?page=1&size=2，page=1
   即第二页，size=2即每页数量为2
   
   
    
   (5)排序
   
   在postman中使用GET访问http://localhost:8089/persons/?sort=age,desc，即按照age属性排序
   
   
   (6)保存
   向http://localhost:8089/persons发起POST请求，将要保存的数据放置在请求体中,数据类型设置为JSON
   
   
   (7)更新
   更新的id为的数据用PUT方式访问http://localhost:8089/persons/7，并修改提交的数据
   
   (8)删除
   
   使用DELETE方式访问http://localhost:8089/persons/7
   
   
   6、定制
   ------
   (1)定制根路径
   访问的REST的资源路径是在根目录下的，即http://localhost:8089/persons,如果需要定制根路径的话，只需在Spring Boot<br>
   的application.properties下增加如下定义即可：
   
    spring.data.rest.base-path=/api
   
   此时REST资源的路径变成了http://localhost:8089/api/persons
   
   (2)定制节点路径
   
   节点路径为http://localhost:8089/persons,这是Spring Data REST的默认规则，就是实体类之后加“s”来形成路径。person的复数<br>
   是people而不是persons，在类似的情况下要对映射的名称进行修改的话，需要在实体类Repository上使用@RepositoryRestResource注<br>
   解的path属性进行修改,代码如下:
   
    @RepositoryRestResource(path = "people")
    public interface PersonRepository extends JpaRepository<Person,Long> {
   
   
       @RestResource(path="nameStartsWith",rel="nameStartsWith")
       Person findByNameStartsWith(@Param("name") String name);
    }
   此时访问REST服务的地址变为：http://localhost:8089/api/people
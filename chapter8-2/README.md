* Spring Boot的数据访问

使用以下设置连接数据库：

hostname: localhost
port: 1521
sid: xe
username: system
password: oracle
SYS＆SYSTEM的密码：


1、什么是Spring Data JPA 
-------------------
    介绍Spring Data JPA的时候，首先认识下Hibernate。Hibernate是数据访问解决技术绝对霸主，使用O/R映射（Object-Relational<br>
     Mapping）技术实现数据访问，O/R映射即将领域模型类和数据库的表进行映射，通过程序操作对象而实现表数据库操作的能力，让数据<br>
    访问操作无须关注数据库相关的技术。
    
2、定义数据访问层
----------------
    使用Spring Data JPA建立数据访问层十分简单，只需要定义一个继承JpaRepository的接口即可，定义如下：
    
    public interface PersonRepository extends JpaRepository<Person,Long> {
            //定义数据访问操作的方法
    } 
    
    继承JpaRepository接口意味着我们默认已经有了下面的数据访问操作方法：
    
    @NoRepositoryBean
    public interface JpaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>, <br>
            QueryByExampleExecutor<T> {
        List<T> findAll();
    
        List<T> findAll(Sort var1);
    
        List<T> findAll(Iterable<ID> var1);
    
        <S extends T> List<S> save(Iterable<S> var1);
    
        void flush();
    
        <S extends T> S saveAndFlush(S var1);
    
        void deleteInBatch(Iterable<T> var1);
    
        void deleteAllInBatch();
    
        T getOne(ID var1);
        
        //、、、、
    }

3、配置使用Spring Data JPA
------------------------
    在Spring环境中，使用Spring Data JPA可通过@EnableJpaRepositories注解来开启Spring Data JPA的支持，<br>
    @EnableJpaRepositories接收的value参数用来扫描数据访问层所在包下的数据访问的接口定义。
    
    @Configuration
    @EnableJpaRepositories("com.wisely.repos")
    public class JpaConfiguration{
        @Bean
        public EntityManageFactory entityManageFactory(){
            //、、、、、
        }
        
        //还需要配置DataSource、PlatformTransactionManager等相关必须的
    }
    
    
* hibernate.ddl-auto属性配置

    1、提供了根据实体类自动维护数据库表结构的功能，可通过spring.jpa.hibernate.ddl-auto来配置，有下列可选项：
    
    ● create:启动时删除上一次生成的表，并根据实体类生成表，表中数据会被清空。
    
    ● create-drop：启动时根据实体类生成表，sessionFactory关闭时表会被清空。
    
    ● update:启动时会根据实体类生成表，当实体类属性变动的时候，表结构也会更新，在初期开发阶段使用此项。
    
    ● validate:启动时验证实体类和数据表是否一致，在我们数据结构稳定时采用此选项。
    
    ● none:不采取任何措施。
    
    
    2、spring.jpa.show-sql 用来设置hibernate操作的时候在控制台显示其真是的sql语句。
    
    3、让控制器输出的json字符串格式更美观。
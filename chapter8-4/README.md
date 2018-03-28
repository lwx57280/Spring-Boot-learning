* 声明式事务

8.4.1 Spring的事务机制
--------------------
    所有的数据库访问技术都有事务处理机制，这些技术提供了API用来开启事务、提交事务来完成数据操作，或者在发送错误的时候回滚数据。
    
    而Spring的事务机制是用统一的机制来处理不同数据访问技术的事务处理。Spring的事务机制提供了一个PlatforomTransactionManager接口，不同
    的数据访问技术的事务使用不同的接口实现。
    
   
    事务：是用户定义的一个操作序列。这些操作，要么都做，要么都不做。是一个不可分割的工作单位。通过事务，数据库可以把逻辑相关的一组操作绑定在一起，以便数据库服务器保持数据的完整性。事务通常是以BEGIN TRANSACTION开始，以COMMIT或者ROLL BACK结束。
    
    事务的特性： 
    1.原子性 
    事务是数据库逻辑的工作单元，事务包括的所有操作，要么都做，要么都不做。 
    2.一致性 
    事务执行的结果是使数据库从一个一致性状态变成另一个一致性状态。一致性与原子性是密切相关的。 
    3.隔离性 
    一个事务的执行不能被其他事务干扰。 
    4.持久性 
    一个事务一旦提交，它对数据库中数据的改变应该是永久性的。
    
    事务的隔离级别： 
    数据库事务的隔离级别有四种：从低到高依次为： 
    Read uncommit（读，未提交） 
    Read commit（读，提交） 
    Repeatable read（重复读） 
    Serializable（序列化） 
    四个级别逐个解决了脏读，不可重复读，幻读这积累问题。
    
    
Spring Data JPA的事务支持
----------------------
    Spring Data JPA对所有的默认方法都开启了事务支持，且查询类事务默认启用readOnly=true属性。
    
Spring Boot的事务支持
-------------------
    1、自动配置的事务管理器
    在使用JDBC作为数据访问技术的时候，Spring Boot为我们定义了PlatformTransactionManager的实现DataSourceTransactionManager<br>
    的Bean；配置见org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration类中的定义:
    
        @Bean
        @ConditionalOnMissingBean({PlatformTransactionManager.class})
        public DataSourceTransactionManager transactionManager(DataSourceProperties properties) {
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource);
            if (this.transactionManagerCustomizers != null) {
                this.transactionManagerCustomizers.customize(transactionManager);
            }

            return transactionManager;
        }
        
    2、自动开启注解事务的支持
    Spring Boot专门用于配置事务的类为：org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,
    此配置类依赖于JpaBaseConfiguration和DataSourceTransactionManagerAutoConfiguration。
    
    而在DataSourceTransactionManagerAutoConfiguration配置里还开启了声名式事务的支持，代码如下：
    
        @Configuration
        @ConditionalOnSingleCandidate(DataSource.class)
        static class DataSourceTransactionManagerConfiguration {
            private final DataSource dataSource;
            private final TransactionManagerCustomizers transactionManagerCustomizers;
    
            DataSourceTransactionManagerConfiguration(DataSource dataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
                this.dataSource = dataSource;
                this.transactionManagerCustomizers = (TransactionManagerCustomizers)transactionManagerCustomizers.getIfAvailable();
            }
    
            @Bean
            @ConditionalOnMissingBean({PlatformTransactionManager.class})
            public DataSourceTransactionManager transactionManager(DataSourceProperties properties) {
                DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource);
                if (this.transactionManagerCustomizers != null) {
                    this.transactionManagerCustomizers.customize(transactionManager);
                }
    
                return transactionManager;
            }
        }
        
        
 数据库事务隔离级别
 -----------------
        
       数据库事务的隔离级别有4个，由低到高依次为Read uncommitted、Read committed、Repeatable read、Serializable，这四个级别可以逐个解决脏读、不可重复读、幻读这几类问题。
       
 ![Transaction](https://github.com/lwx57280/Spring-Boot-learning/blob/master/chapter8-4/img-folder/Transaction.jpg)
       
       注意：我们讨论隔离级别的场景，主要是在多个事务并发的情况下，因此，接下来的讲解都围绕事务并发。
       
Read uncommitted 读未提交
-------------------------
       公司发工资了，领导把5000元打到singo的账号上，但是该事务并未提交，而singo正好去查看账户，发现工资已经到账，是5000元整，非常高兴。可是不幸的是，领导发现发给singo的工资金额不对，是2000元，于是迅速回滚了事务，修改金额后，将事务提交，最后singo实际的工资只有2000元，singo空欢喜一场。
       
 ![事务场景](https://github.com/lwx57280/Spring-Boot-learning/blob/master/chapter8-4/img-folder/MyCatch.jpg)
    
    出现上述情况，即我们所说的脏读，两个并发的事务，“事务A：领导给singo发工资”、“事务B：singo查询工资账户”，事务B读取了事务A尚未提交的数据。
    
    当隔离级别设置为Read uncommitted时，就可能出现脏读，如何避免脏读，请看下一个隔离级别。
    
Read committed 读提交
---------------------
    singo拿着工资卡去消费，系统读取到卡里确实有2000元，而此时她的老婆也正好在网上转账，把singo工资卡的2000元转到另一账户，并在singo之前提交了事务，当singo扣款时，系统检查到singo的工资卡已经没有钱，扣款失败，singo十分纳闷，明明卡里有钱，为何......
    
    出现上述情况，即我们所说的不可重复读，两个并发的事务，“事务A：singo消费”、“事务B：singo的老婆网上转账”，事务A事先读取了数据，事务B紧接了更新了数据，并提交了事务，而事务A再次读取该数据时，数据已经发生了改变。
    
    当隔离级别设置为Read committed时，避免了脏读，但是可能会造成不可重复读。
    
    大多数数据库的默认级别就是Read committed，比如Sql Server , Oracle。如何解决不可重复读这一问题，请看下一个隔离级别。
    
Repeatable read 重复读
----------------------
    当隔离级别设置为Repeatable read时，可以避免不可重复读。当singo拿着工资卡去消费时，一旦系统开始读取工资卡信息（即事务开始），singo的老婆就不可能对该记录进行修改，也就是singo的老婆不能在此时转账。
    
    虽然Repeatable read避免了不可重复读，但还有可能出现幻读。
    
    singo的老婆工作在银行部门，她时常通过银行内部系统查看singo的信用卡消费记录。有一天，她正在查询到singo当月信用卡的总消费金额（select sum(amount) from transaction where month = 本月）为80元，而singo此时正好在外面胡吃海塞后在收银台买单，消费1000元，即新增了一条1000元的消费记录（insert transaction ... ），并提交了事务，随后singo的老婆将singo当月信用卡消费的明细打印到A4纸上，却发现消费总额为1080元，singo的老婆很诧异，以为出现了幻觉，幻读就这样产生了。
    
    注：Mysql的默认隔离级别就是Repeatable read。
   
   
Serializable 序列化
-------------------
    Serializable是最高的事务隔离级别，同时代价也花费最高，性能很低，一般很少使用，在该级别下，事务顺序执行，不仅可以避免脏读、不可重复读，还避免了幻像读。
    

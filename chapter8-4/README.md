* 声明式事务

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
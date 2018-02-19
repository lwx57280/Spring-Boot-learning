* 批处理Spring Batch

1、什么是Spring Batch
    Spring Batch是用来处理大量数据操作的一个框架，主要用来读取大量数据，然后进行一定处理输出成指定的形式。
    
2、Spring Batch主要组成
    Spring Batch主要由以下几部分组成。
    
        JobRepository       用来注册Job容器
        
        JobLauncher         用来启动Job的接口
        
        Job                 我们哟啊实际执行的任务，包含一个或多个Step
        
        Step                Step步骤包含ItemReader、ItemProcessor和ItemWriter
        
        ItemReader          用来读取数据的接口
        
        ItemProcessor       用来处理数据的接口
        
        ItemWriter          用来输出数据的接口
   
   以上Spring Batch的主要组成部分只需注册成Spring的Bean即可。若想开启批处理的支持还需在配置类上使用@EnableBatchProcessing。
   
    @Configuration
    @EnableBatchProcessing
    public class BatchConfig{
        @Bean
        public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
            JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
            jobRepositoryFactoryBean.setDataSource(dataSource);
            jobRepositoryFactoryBean.setTransactionManager(transactionManager);
            jobRepositoryFactoryBean.setDatabaseType(DatabaseType.ORACLE.name());
            return jobRepositoryFactoryBean.getObject();
        }
        
        @Bean
        public SimpleJobLauncher jobLauncher(DataSource dataSource,PlatformTransactionManager transactionManager) throws Exception {
            SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
            simpleJobLauncher.setJobRepository(jobRepository(dataSource,transactionManager));
            return simpleJobLauncher;
        }
        
        @Bean
        public Job importJob(JobBuilderFactory jobs, Step s1){
            return jobs.get("importJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(s1)   //1、为Job指定step
                    .end()
                    .listener(csvJobListener()) //2、绑定监听器csvJobListener。
                    .build();
        }
        
        @Bean
        public Step step1(StepBuilderFactory stepBuilderFactory,ItemReader<Person> reader,ItemWriter<Person> writer,ItemProcessor<Person,Person> processor){
            return stepBuilderFactory.get("step1")
                    .<Person,Person>chunk(65000)    //1、批处理每次提交65000条数据。
                    .reader(reader)                             //2、给step绑定reader。
                    .processor(processor)                       //3、给step绑定processor。
                    .writer(writer)                             //4、给step绑定writer。
                    .build();
        }

        @Bean
        public ItemReader<Person> reader()throws Exception{
            // 新建ItemReader接口的实现类返回
            return reader;
        }
        
        @Bean
        public ItemProcessor<Person,Person> processor(){
            // 新建ItemProcessor接口的实现类返回
            return processor;
        }
        
        @Bean
        public ItemWriter<Person> writer(DataSource dataSource){
            // 新建ItemWriter接口的实现类返回
            return writer;
        }
    }
    
3、Job监听
    若需要监听我们的Job执行情况，则定义一个类实现JobExecutionListener，并在定义Job的Bean上绑定该监听器.
    
    监听器定义如下：
    
    public class MyJobListener implements JobExecutionListener{
    
        long startTime;
        
        long endTime;
        
        @Override
        public void beforeJob(JobExecution jobExecution) {
            startTime = System.currentTimeMillis();
            System.out.println("任务处理开始!");
        }
    
        @Override
        public void afterJob(JobExecution jobExecution) {
            endTime = System.currentTimeMillis();
            System.out.println("任务处理结束!");
            System.out.println("耗时:" + (endTime - startTime) + "ms");
        }
    }
    
  注册并绑定监听器到Bean
    
    @Bean
    public Job importJob(JobBuilderFactory jobs, Step s1){
        return jobs.get("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)   //1、为Job指定step
                .end()
                .listener(csvJobListener()) //2、绑定监听器csvJobListener。
                .build();
    }
    
    @Bean
    public CsvJobListener csvJobListener() {
        return new MyJobListener();
    }
    
   手动触发任务
   
   数据导入效果如图：
   ![手动触发](https://github.com/lwx57280/Spring-Boot-learning/blob/master/chapter9-2/img-folder/batch.jpg)

* Spring Boot常规属性配置(基于properties)
在Spring Boot里，只需在application.properties定义属性，直接使用@Value注入即可。

* 类型安全的配置(基于properties)
Spring Boot还提供了基于类型安全的配置方式，通过@ConfigurationProperties将properties属性和一个Bean及其属性关联，从而实现类<br>
型安全的配置。
* 日志配置
Spring Boot默认情况下使用Logback作为日志框架。
    配置日志级别：<br>
    logging.file=D:/mylog/log.log <br>
    配置日志文件，格式为loggin.level.包名=级别
    logging.level.org.springframework.web=DEBUG
    
* Profile配置
> profile是Spring用来针对不同环境不同的配置提供支持的，全局Profile配置使用application-{profile}.properties(如<br>
application.prod.properties)。

>通过在application.properties中设置spring.profiles。active=prod来指定活动的Profile。
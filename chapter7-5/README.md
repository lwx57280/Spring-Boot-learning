* Spring Boot Favicon配置

2、关闭Favicon
----------
    我们可以在application.properties中设置关闭Favicon，默认为开启。<br>
    
    spring.mvc.favicon.enabled=false <br>

3、设置自己的Favicon
------------------
    若需要设置自己的Favicon，则只需将自己的favicon.ico（文件名不能变动）文件放置在类路径根目录、类路径META-INF/resources/<br>
    下、类路径resources/下、类路径static/下或类路径public/下。这里将favicon.ico放置在src/main/resources/static下。
    
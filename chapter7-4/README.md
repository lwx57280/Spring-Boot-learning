* Tomcat配置

配置文件配置Tomcat
----------------
    只需在application.properties配置属性做配置即可。通用的Servlet容易配置都以“server”作为前缀，而Tomcat特有配置都以<br>
    "server.tomcat"作为前缀。
    
    # 配置Servlet容器
    server.port=8089
    # server.session.timeout= #用户会话session过期时间，以秒为单位
    # 配置访问路径，默认为/
    server.context-path=/helloboot
    # 配置Tomcat
    server.tomcat.uri-encoding=UTF-8
   
* 代码配置Tomcat
    如果需要通过代码的方式配置servlet容器，则可以注册一个实现EmbeddedServletContainerCustomizer接口的Bean；若想<br>
    直接配置Tomcat、Jetty、Undertow，则可以直接定义TomcatEmbeddedServletContainerFactory、<br>
    JettyEmbeddedServletContainerFactory、UndertowEmbeddedServletContainerFactory。
    
* 替换内嵌Tomcat容器
    
    Spring Boot默认使用Tomcat作为内嵌Servlet容器,如果要使用Jetty或者Undertow为Servlet容器，只需修改spring-boot-starter-web<br>
    的依赖即可。

* SSL配置
    
    SSL配置也是在实际应用中经常遇到的场景。
    
    SSL(Secure Sockets Layer,安全套接层)是为网络通信提供安全及数据完整性的一种安全协议，SSL在网络传输层对网络连接<br>
    进行加密。SSL协议位于TCP/IP协议与各种应用层协议之间，为数据通信提供安全支持。SSL协议可分为两层：SSL记录协议(SSL <br>
    Record Protocol）, 它建立在可靠的传输协议（如TCP）之上，为高层协议提供数据封装、压缩、加密等基本功能的支持。SSL握手协<br>
    议<SSL Handshake Protocol)，它建立在SSL记录协议之上，用于在实际数据传输开始前，通信双方进行身份认证、协商加密算法、交<br>
    换加密密钥等。
           
    而基于B/S的Web应用中，是通过HTTPS来实现SSL的。HTTPS是以安全为目标的HTTP通道，简单讲就是HTTP的安全版，即在HTTP下加入SSL<br>
    层,HTTPS的安全基础是SSL。    
    
    生成证书
    -------
    在控制台输入如下命令，然后按照提示操作，
    
    keytool -genkey -alias tomcat <br>
    
    
   Spring Boot配置SSL
   -----------------    
    在Spring Boot中配置SSL非常简单，同样在application.properties下配置：<br>
    
    server.port=8443
    server.ssl.key-store=chapter7-4/.keystore
    server.ssl.key-store-password=123456
    server.ssl.key-store-type=JKS
    server.ssl.key-alias=tomcat <br>
    
   说明： <br>
    1：指定监听端口 <br>
    2：ssl存储的文件名 <br>
    3：证书密码 <br>
    4：证书类型 <br>
    5：证书别名 <br>
    
   使用代码配置http自动跳转https
   ---------------------------
   
   
* 异步消息
    异步消息主要目的是为了系统与系统之间的通信。所谓异步消息即小消息发送者无须等待消息接收者的处理及返回，甚至无须关心消息是否<br>
    发送成功。
    
    在异步消息中有两个很重要的概念，即消息代理（message broker）和目的地（destination）。当消息发送者发送消息后，消息将由消息<br>
    代理接管，消息代理保证消息传递到指定的目的地。
    
    异步消息主要有两种形式的目的地：队列（queue）和主题(topic)。队列用于点对点式（point-to-point）的消息通信；主题用于发布/订阅<br>
    (publish/subscribe)的消息通信。
    
    1、点对点式
    当消息发送者发送消息，消息代理获得消息后将消息放进一个队列（queue）里，当有消息接收者来接收消息的时候，消息将从队列里取出来<br>
    传递给接收者，这时候队列里就没有了这条消息。
    
    点对点式确保的是每一条消息只有唯一的发送者和接收者，但这并不能说明只有一个接收者可以从队列里接收消息。因为队列里有多个消息<br>
    ,点对点式只保证每一条消息只有唯一的发送者和接收者。
   
   2、发布/订阅
   
    和点对点式不同，发布/订阅式是消息发送者发送消息到主题(topic)，而多个消息接收者监听这个主题。此时的消息发送者和接收者分别叫<br>
    做发布者和订阅者。
    
9.3.企业级消息代理
-----------------

            JMS(Java Message Service)即Java消息服务，是基于JVM消息代理的规范。而ActiveMQ、HornetQ是一个JMS消息代理的实现。
        
            AMQP(Advanced Message Queuing Protocol)也是一个消息代理的规范，但它不仅兼容JMS,还支持跨语言和平台。AMQP的主要实现有RabbitMQ。
        
            Spring为JMS、AMQP的提供了@JMSListener、@RabbitListener注解在方法上监听消息代理发布的消息。我们需要分别通过@EnableJMS、
            @EnableRabbit开启支持。
Spring Boot的支持
----------------
    Spring Boot对JMS的自动配置支持位于org.springframework.boot.autoconfigure.jms下，支持JMS的实现有ActiveMQ、HornetQ、
    Artemis(由HornetQ捐赠给ActiveMQ的代码库形成的ActiveMQ的子项目)。以ActiveMQ为例，Spring Boot为我们定义了ActiveMQConnectionFactory
    的Bean作为连接，并通过“spring.activemq”为前缀的属性来配置ActiveMQ的连接属性，包含：
       
            spring.activemq.broker-url=tcp://localhost:61616  #消息代理的路径
            spring.activemq.user=
            spring.activemq.password=
            spring.activemq.in-memory=true
            spring.activemq.pooled=false
        
     Spring Boot 在JMSAutoConfiguration还为我们配置好了JmsTemplate,且为我们开启了注解式消息监听的支持，即自动开启@EnableJms。
     
     Spring Boot对AMQP的自动配置支持位于org.springframework.boot.autoconfigure.amqp下，它为我们配置了连接的ConnectionFactory
     和RabbitTemplate,且为我们开启了注解式消息监听，即自动开启@EnableRabbit。RabbitMQ的配置可通过“spring.rabbitmq”来配置RabbitMQ
     ,主要包含：
     
        spring.rabbitmq.host=localhost  #rabbitmq服务器地址，默认为localhost
        spring.rabbitmq.port=5672       #rabbitmq端口，默认为5672
        spring.rabbitmq.usernmae=admin
        spring.rabbitmq.password=secret
     
     
    运行
    控制台显示Receiver已接收到消息
    
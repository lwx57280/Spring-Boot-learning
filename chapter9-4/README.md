* 企业系统集成Spring

9.1 Spring Integration入门
-------------------------
    Spring Integration提供了基于Spring的EIP(Enterprise Integration Patterms,企业集成模式)的实现。Spring Integration主要解决的问题是不同系统之间交互的问题，通过异步
    消息驱动来达到系统交互时系统之间的松耦合。基于无XML配置的原则使用Java配置、注解以及Spring Integration Java DSL来使用Spring Integration。
    
    Spring Integration主要由Message、Channel和 Message EndPoint组成。
    
9.2 Message
-----------
    Message是用来在不同部分之间传递的数据。Message由两部分组成：消息体(payload)与消息头（header）。消息体可以是任何数据 类型（如XML、JSON、Java对象）;消息头表示的
    元数据就是解释消息体的内容。
    
        public interface Message<T>{
            T getPayload();
            
            MessageHeaders getHeaders();
        }
        
9.3 Channel
-----------
    在消息系统中，消息发送者发送消息到通道（Channel）,消息收受者从通道（Channel）接收消息。
    
        1、顶级接口
        （1）MessageChannel
        MessageChannel是Spring Integration消息通道的顶级接口：
       
            public interface MessageChannel{
                public static final long INDEFINITE_TIMEOUT=-1;
                
                boolean send(Messag<T> message);
                
                boolean send(Message<T> message,long timeout);
                
            }
        
        使用send方法发送消息时，返回值为true,则表示消息发送成功。MessageChannel有两大子接口，分别为PollableChannel（可轮询）和SubscribaleChannel(可订阅)。所有的消息通
        道类都是实现这两个接口。
        
        （2）PollableChannel
        PollableChannel具备轮询获得消息的能力，定义如下：
        
            public interface PollableChannel extends MessageChannel{
                Message<?> receive();
                
                Message<?> receive(long timeout);
            }
        
        (3)SubscribaleChannel
        
        SubscribaleChannel发送消息给了订阅MessageHanlder的订阅者：
       
            public interface SubscribaleChannel extends MessageChannel{
                boolean subscribe(MessageHandler handler);
                
                boolean unsubscribe(MessageHandler handler);
            }
        
       2、常用消息通道
       (1)PublishSubscribeChannel
       
       PublishSubscribeChannel允许广播消息给所有订阅者，配置方式如下：
       
           @Bean
           public PublishSubscribeChannel publishSubscribeChannel(){
                PublishSubscribeChannel channel = new PublishSubscribeChannel();
                return channel;
           } 
       
       其中，当前消息通道的id为publishSubscribeChannel;
       
       (2)QueueChannel
       
       QueueChannel允许消息接收者轮询获得消息，用一个队列（queue）接收消息，队列的容量大小可配置，配置方式如下：
       
           @Bean
           public QueueChannel queueChannel(){
                QueueChannel channel = new QueueChannel(10);
                return channel;
           }
       
       其中QueueChannel构造参数10即为队列的容量。
       
      （3）PriorityChannel
      
      PriorityChannel可按照优先级将数据存储到对，它依据于消息的消息头priority属性，配置方式如下：
      
          @Bean
          public PriorityChannel priorityChannel(){
                PriorityChannel channel = new PriorityChannel(10);
                return channel;
          }
      
      (4)RendezvousChannel
      
      RendezvousChannel确保每一个接收者都接收到消息后再发送消息，配置方式如下：
      
          @Bean
          public RendezvousChannel rendezvousChannel(){
                RendezvousChannel channel = new RendezvousChannel()
                return channel;
          }
      
      (5)DirectChannel
      
      DirectChannel是Spring Integraion默认的消息通道，它允许将消息发送给为一个订阅者，然后阻碍发送直到消息被接收，配置方式如下：
      
          @Bean
          public DirectChannel directChannel(){
                DirectChannel channel = new DirectChannel();
                return channel;
          }
      
      (6)ExecutorChannel
      
      ExecutorChannel可绑定一个多线程的task executor,配置方式如下：
      
          @Bean
          public ExecutorChannel executorChannel(){
                ExecutorChannel channel = new ExecutorChannel(executor());
                return channel;
          }
      
          @Bean
          public Executor executor(){
                ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
                taskExecutor.setCorePoolSize(5);
                taskExecutor.setMaxPoolSize(10);
                taskExecutor.setQueueCapacity(25);
                taskExecutor.initialize();
                return taskExecutor;
          }
          
        3、通道拦截器
        
        Spring Integraion给消息通道提供了通道拦截器（ChannelInterceptor）,用来连接发送和接收消息的操作。
        
       ChannelInterceptor接口定义如下，我们只需要实现这个接口即可：
       
           public interface ChannelInterceptor {
       
               Message<?> preSend(Message<?> message, MessageChannel channel);
       
               void postSend(Message<?> message, MessageChannel channel, boolean sent);
       
               void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex);
       
               boolean preReceive(MessageChannel channel);
       
               Message<?> postReceive(Message<?> message, MessageChannel channel);
       
               void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex);
       
           }
       
       通过如下代码给所有的channel增加拦截器
       
       channel.addInterceptor(someInterceptor);
       
9.4 Message EndPoint
--------------------
       
       消息端点(Message EndPoint)是真正处理消息的(Message)组件，它还可以控制通道的路由。我们可用的消息端点包含如下：
       
       (1) Channel Adapter
       
       通道适配器(Channel Adapter)是一种连接外部系统或传输协议的端点(EndPoint)，可以分为入站(inbound)和出站(outbound)。 
       通道适配器是单向的，入站通道适配器只支持接收消息，出站通道适配器只支持输出消息。
       
       Spring Integration内置了如下的适配器：
       
       RabbitMQ、Feed、File、FTP/SFTP、Gemfire、HTTP、TCP/UDP、JDBC、JPA、JMS、Mail、MongoDB、Redis、RMI
       Twitter、XMPP、WebServices(SOAP、REST)、WebSocket等。
       
       (2) Gateway
       
       消息网关(Gateway)类似于Adapter，但是提供了双向的请求/返回集成方式，也分为入站(inbound)和出站(outbound)。 
       Spring Integration 对响应的Adapter都提供了Gateway。
       
       (3) Service Activator
       
       Service Activator 可调用Spring的Bean来处理消息，并将处理后的结果输出到指定的消息通道。
       
       (4) Router
       
       路由(Router) 可根据消息体内容(Payload Type Router)、消息头的值(Header Value Router) 以及定义好的接收表(Recipient List Router) 作为条件，来决定消息传递到的通道。
       
       (5) Filter
       
       过滤器(Filter) 类似于路由(Router)，不同的是过滤器不决定消息路由到哪里，而是决定消息是否可以传递给消息通道。
       
       (6) Splitter
       
       拆分器(Splitter)将消息拆分为几个部分单独处理，拆分器处理的返回值是一个集合或者数组。
       
       (7) Aggregator
       
       聚合器(Aggregator)与拆分器相反，它接收一个java.util.List作为参数，将多个消息合并为一个消息。
       
       (8) Enricher
       
       当我们从外部获得消息后，需要增加额外的消息到已有的消息中，这时就需要使用消息增强器(Enricher)。消息增强器主要有消息体 
       增强器(Payload Enricher)和消息头增强器(Header Enricher)两种。
       
       (9) Transformer
       
       转换器(Transformer)是对获得的消息进行一定的转换处理(如数据格式转换).
       
       (10) Bridge
       
       使用连接桥(Bridge)可以简单的将两个消息通道连接起来。
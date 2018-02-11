* WebSocket

1、什么是WebSocket
-----------------
    
    WebSocket为浏览器和服务器提供了双工异步通信的功能，即浏览器可以向服务器段发送消息，服务端也可以向浏览器发送消息。<br>
    WebSocket需浏览器支持，如IE 10+、Chrome 13+、Firefox 6+，这对我们现在的浏览器来说都不是问题。
    
    WebSocket是通过一个socket来实现双工异步通信能力的。但是直接使用WebSocket（或者SockJS:WebSocket协议的模拟，增加了当浏<br>
    览器不支持WebSocket的时候的兼容支持）协议开发程序显得特别繁琐，我们会使用它的子协议STOMP,它是一个更高级别的协议，STOMP<br>
    协议使用一个基于帧（frame）的格式来定义消息，与HTTP的request和response类似（具有类似与@RequestMapping的<br>
    @MessageMapping）。
    
2、Spring Boot提供自动配置
-----------------------
    
    Spring Boot为WebSocket提供的stater pom是spring-boot-starter-websocket。
 
* 广播式
    
    广播式即服务端有消息时，会将消息发送给所有连接了当前endpoint的浏览器。
    
   
   
    从上述截图可以观察得出，连接服务器的格式为:
    
    CONNECT↵accept-version:1.1,1.0↵<br>
    
    heart-beat:10000,10000↵<br>
    
    连接成功的返回为：<br>
    
    CONNECTED↵version:1.1↵heart-beat:0,0 <br>
    
    订阅目标（destination）/topic/getResponse <br>
    
    SUBSCRIBE↵id:sub-0↵destination:/topic/getResponse <br>
    
    向目标（destination）/welcome发送消息的格式为：<br>
    
    SEND↵destination:/welcome↵content-length:16↵↵{"name":"hello"}<br>
    
    从目标（destination）/topic/getResponse接收的格式为：<br>
    
    MESSAGE↵<br>
    destination:/topic/getResponse↵content-type:application/json;charset=UTF-8↵<br>
    subscription:sub-0↵message-id:dxhau255-20↵content-length:36↵<br>
    ↵{"responseMessage":"Welcome,hello!"}
 
* 点对点式

    广播式有自己的应用场景，但是广播式不能解决我们一个常见的场景，即消息由谁发送、又由谁接收的问题。
    
* 基于Bootstrap和AngularJS的现代Web应用

    1、单页面应用<br>
    ---------------
    单页面应用（single-page application,简称SPA）指的是一种类型原生客户端软件的更流畅的用户体验的页面 。在单页面应用中，<br>
    所有的资源（HTML、JavaScript、CSS）都是按需动态加载到页面上的，且不需要服务端控制页面的转向。
    
    2、响应式设计
    ------------
    响应式设计(Responsive web design,简称RWD)指的是不同的设备（电脑、平板、手机）访问相同的页面的时候，得到不同的页面视图<br>
    ,而得到的视图是适应当前屏幕的。当然就算在电脑上，我们通过拖动浏览器窗口的大小，也能得到合适的视图。
    
    3、数据向导
    ----------
    数据向导是对于页面导向而言的，页面上的数据获得是通过消费后台的REST服务来实现的，而不是通过服务器渲染的的动态页面（如JSP）<br>
    来实现的，一般数据交换使用的格式是JSON。
    
    
* Bootstrap
    
   1、什么是Bootstrap
   -----------------
   Bootstrap官方定义：Bootstrap是开发响应式和移动式优先的Web应用的最流行的HTML、CSS、JavaScript框架。<br>
   
   Bootstrap实现了只使用一套代码就可以在不同的设备显示你想要的视图的功能。Bootstrap还为我们提供了大量美观的HTML元素前端<br>
   组件和JQuery插件。
   
   2、下载并引入Bootstrap
   ---------------------
   
   下载地址：http://www.bootcss.com/
   
   
   
   3、CSS支持
   ---------
   Bootstrap的CSS样式为基础的HTML元素提供了美观的样式，此外还提供了一个高级的网格系统用来做页面布局。
   
   
   4、页面组件支持
   --------------
   Bootstrap为我们提供了大量的页面组件，包括字体图标、下拉框、导航条、进度条、缩略图等，更多请阅读,<br>
   https://v2.bootcss.com/components.html
   
   5、JavaScript支持
   ---------------
    Bootstrap为我们提供了大量的JavaScript插件，包含模式对话框、标签页、提示、警告等。
    
* AngularJS
    
    1、什么是AngularJS
    ------------------
    AngularJS官方定义：AngularJS是HTML开发本应该的样子，它是用来设计开发Web应用的。<br>
    
    AngularJS使用声明式模板+数据绑定（类似与JSP、Thymeleaf）、MVW(Model -View -Whatever)、MVVM(Model -View -Controller<br>
    )、依赖注入和测试，但是这一切的实现却只借助纯客户端的JavaScript。
    
    HTML一般是用来声明静态页面的，但是通常情况下我们希望页面是基于数据动态生成的，这也是我们很多服务端模板引擎出现的原因<br>
    ;而AngularJS可以只通过前端技术就实现动态的页面。
    
    2、下载并引入AngularJS
    --------------------
    
    3、模块、控制器和数据绑定
    ----------------------
    我们对MVC的概念已经烂熟于心了，但是平时的MVC都是服务端的MVC,这里用AngularJS实现了纯页面端的MVC，即实现了视图模板、<br>
    数据模型、代码控制的分离。
    
    AngularJS为了分离代码达到复用的效果，提供了一个module(模块)。定义一个模块需使用下面的代码。
    
    无依赖模块
    --------
    angular.module('firstModule',[ ]);
    
    有依赖模块
    ---------
    
    angular.module('firstModule',['moduleA','moduleB'])；
    
    V就是页面元素，M就是我们的ng-model,那C呢？我们可以通过下面的代码来定义控制器，页面使用ng-controller来和其关联。
    
    4、Scope和Event
    ---------------
    （1）Scope
    ----------
    Scope是AngularJS的内置对象，用$Scope来获得。在Scope中定义的数据是数据模型，可以通过{{模型名}}在视图上获得。Scope<br>
    主要是在编码中需要对数据模型进行处理的时候使用，Scope的作用范围与在页面声明的范围一致（如在controller内使用，Scope<br>
    的作用范围是页面声明ng-controller标签元素的作用范围）。
    
    定义：<br>
    $scope.greeting='Hello' <br>
    
    获取：<br>
    
    {{greeting}} <br>
    
    (2)Event
    --------
    因为Scope的作用范围不同，所以不同的Scope之间若要交互的话需要通过事件（Event）来完成。
     1)、冒泡事件（Emit）冒泡事件负责从Scope向上发送事件，示例如下：<br>
       
       子Scope发送 :          
        $scope.$emit('EVENT_NAME_EMIT','message'); <br>
        
       父Scope接收:
       $scope.$on('EVENT_NAME_EMIT',function(event,data)){
        .....
       }
     2)、广播事件（Broadcast）。广播事件负责从父Scope向下发送事件，示例如下：<br>
        父Scope发送：
        $scope.$broadcast('EVENT_NAME_EMIT','message');
        
        子scope接收：
        $scope.$on('EVENT_NAME_BROAD',function(event,data)){
           .....
         }
     
    5、多视图和路由
     --------------
     多视图和路由是AngularJS实现单页面应用的技术关键，AngularJS内置了一个$routeProvider对象来负责页面加载和页面路由转向。<br>
     
     
     
   6、依赖注入
   ------------
     依赖注入是 AngularJS的一大酷炫功能。可以实现对代码的解耦，在代码里可以注入AngularJS的对象或者我们自定义的对象。
     
     如下是在控制器中注入$scope，注意使用依赖注入的代码格式：
     
     var app = angular.module('myApp', []);
     app.controller('myCtrl', function($scope) {
         $scope.name = "John Doe";
     });
     
   7、Service和Factor
   -----------------
     AngularJS为我们内置了一些服务，如$location、$timeout、$rootScope。很多时候我们需要自己定制一些服务，Angular为我们<br>
     提供了Service和Factory。
     
     Service和Factor的区别是：使用Service的话，AngularJS会使用new来初始化对象、而使用Factory会直接获得对象。
   
        
     (1)Service
     定义：
     angular.module('firstModule').service('helloService',function()){
        this.sayHello=function(name){
            alert('Hello'+name);
        }
     }
      
     注入调用：
     
     angular.module('firstModule').controller("diController",['$scope','helloService',function($scope,
        helloService){
            helloService.sayHello('wyf');
     }]);
        
    (2)Factory
      angular.module('firstModule').service('helloFactory',function(){
        return{
            sayHello:function(){
                alert('Hello'+name);
            }
        }
      });
   8、http操作
   ----------
      AngularJS内置了$http对象用来进行Ajax的操作：
      
      $http.get(url)
      
      $http.post(url,data)
      
      $http.put(url,data)
      
      $http.delete(url)
      
      $http.head(url)
      
   9、自定义命令
   ------------
      AngularJS内置了大量的指令（directive）,如ng-repeat、ng-show、ng-model等。
      
      比方说，有一个日期的js/jQuery插件，使用AngularJS封装后，在页面上调用此插件可以通过指令来实现<br>
      例如：
      
      元素指令：<date-picker></date-picker>
      
      属性指令：<input type="text" date-picker/>
      
      样式指令：<input type="text" class="date-picker"/>
      
      注释指令：<!--directive:date-picker-->
   
   定义指令：
   
    angular.module('myApp',[]).directive('helloWorld',function(){
        return{
            restrict:'AE,   //支持使用属性、元素
            replace:true,
            template:'<h3>Hello,World!</h3>'
        };
    });
   
   调用指令，元素标签
   
     <hello-world/>    
   
     <hello:world/>
   或者属性方式：
   
    <div hello-world/>
  
   
* 安全控制Spring Security

9.1 Spring Security快速入门
--------------------------

 1、什么是Spring Security
    
    Spring Security是专门针对基于Spring的项目安全框架，充分利用了依赖注入和AOP来实现安全功能。
    
    在早期的Spring Security版本，使用Spring Security需要使用大量的XML配置，而本实例将全部基于java配置来实现Spring Security的功能。
    
    安全框架有两个重要的概念,即认证（Authentication）和授权（Authorization）。认证即确认用户可以访问当前系统：授权即确定用户当前系
    统下所拥有的功能权限。
    
    
2、Spring Security的配置。<br>
   (1)DelegatingFilterProxy
   Spring Security为我们提供了一个多个过滤器来实现所有安全的功能，我们只需要注册一个特殊的DelegatingFilterProxy过滤器到
   WebApplicationInitializer即可。
   
   而在实际使用中，我们只需让自己的Initializer类继承WebApplicationInitializer抽象类即可。AbstractSecurityWebApplicationInitializer<br>
   实现了WebApplicationInitializer接口，并通过onStartup方法调用：
   
   insertSpringSecurityFilterChain(servletContext);
   
   它为我们注册了DelegatingFilterProxy。insertSpringSecurityFilterChain源码如下：
   
    private void insertSpringSecurityFilterChain(ServletContext servletContext){
        String filterName =DEFAULT_FILTER_NAME;
        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy(
            filterName);
        String contextAttribute = getWebApplicationContextAttribute();
        if(contextAttribute !=null){
            springSecurityFilterChain.setContextAttribute(contextAttribute);
        }
        registerFilter(servletContext,true,filterName,springSecurityFilterChain);
       
    }  
    
    所以我们只需用一下嗲吗即可开启Spring Security的过滤器支持：
        public class AppInitializer extents AbstractSecurityWebApplicationInitializer{
        
        }
  (2)配置
  Spring Security的配置和Spring MVC的配置类似，只需在一个配置类上注解@EnableWebSecurity，并让这个类继承WebSecurityConfigurerAdapter<br>
  即可。我们可以通过重写configure方法类配置相关的安全配置：
  
  代码如下：
       
       @Configuration
       @EnableWebSecurity 
       public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
           @Override
           protected void configure(HttpSecurity http) throws Exception {
              super.configure(http);
           }
        
           @Override
           protected void configure(AuthenticationManagerBuilder auth) throws Exception {
              super.configure(auth);      
           }   
           
           @Override
           public void configure(WebSecurity web) throws Exception {
              super.configure(web);
           }
       }
  
3、用户认证
    认证需要我们有一套用户数据的来源，而授权则是对于某个用户有相应的角色权限。在Spring Security里我们通过重写。
    
        protected void configure(AuthenticationManagerBuilder auth)

    方法来实现定制.
    (1)内存中的用户
    
    使用AuthenticationManagerBuilder的inMemoryAuthentication方法即可添加在内存中的用户，并可给用户指定角色权限。
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("wyf").password("wyf").roles("ROLE_ADMIN")
            .and()
            .withUser("wisely").password("wisely").roles("ROLE_USER");

    }
   (2)JDBC中的用户
   JDBC中的用户直接指定dataSource即可.
   
    @Autowired
    DataSource dataSource;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {****
        auth.jdbcAuthentication().dataSource(dataSource);
        ///****
    }
    不过这看上去很奇怪，其实这里的Spring Security是默认了你的数据库结构的。通过jdbcAuthentication的源码，我们可以看出在
    jdbcDaoImpl中定义了默认的用户及角色授权获取的SQL语句：
    
    public static final String DEF_USERS_BY_USERNAME_QUERY="select username,password,enabled"
            +"from users " + "where username=?";
    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY="select username,authority"
            +"from authorities " + "where username=?";
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {****
        auth.jdbcAuthentication().dataSource(dataSource);
            .userByUsernameQuery("select username,password,true "
                +"from myusers where username=?")
            .authoritesByUsernameQuery("select username,role"
                +"from roles where username=?");
        
    }
            
    (3)通用的用户
    上面两种用户和权限的获取方式只限于内存或者JDBC，我们的数据访问方式可以是多种各样的，可以是非关系数据库，也可以是我们常用的
    JPA等。
    
    这时我们需要自定义实现CustomUserService接口。上面的内存中用户及JDBC用户就是UserDetailsService的实现，定义如下：
    
    public class CustomUserService implements UserDetailsService {
    
        @Autowired
        SysUserRepository userRepository;
        
        @Override
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
             SysUser user = userRepository.findByUsername(userName);
             List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
             authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
             return new User(user.getUsername(),user.getPassword(),authorities);
        }
    }  
    说明：SysUser是系统的用户领域对象类，User来自于org.springframework.security.core.userdetails.User。
    
    除此之外，我们需要注册这个CustomUserService，代码如下：
        @Bean
        UserDetailsService customUserService(){
            return new CustomUserService();
        }
        
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            //3、添加我们自定义的user detail service认证。
            auth.userDetailsService(customUserService());
        }
4、请求授权
    Spring security是通过重写
    protected void configure(HttSecurity http)
    
    方法来实现请求拦截的。
    
    Spring security使用一下匹配器来匹配请求路径：
        ● antMatchers:使用Ant风格的路径匹配。
        ● regexMatchers:使用正则表达式匹配路径。
        
    anyRequest：匹配所有请求路径。
    
    如图：
    
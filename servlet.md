## 为什么使用 Servlet & JSP


### 什么是 Http 协议？

HTTP 是 TCP/IP 的上层协议。TCP/IP 协议是网络的基础。

1. 链路层，也称为数据链路层或网络接口层。设备驱动程序和网卡。
2. 网络层，也成为互联网层。IP。
3. 传输层，两台主机上应用程序提供端到端的通信。TCP/IP 协议族中，传输协议：TCP 和 UDP。
4. 应用层。 

http(ip(tcp))






### HTTP 请求

方法


1. POST 
2. GET
3. DELETE
4. HEAD
5. TRACE
6. PUT
7. OPTIONS
8. CONNECT


POST 与 GET

GET 包含，请求行，请求首部；不安全，参数直接放在请求行，在地址栏显示，而且有大小限制，；可以建立书签；
POST 包含，请求行，请求首部，请求体（消息体，或负载）；安全，参数看不到，没有限制；不可以建立书签；


GET/PUT/HEAD 都是幂等的（重复做相同的事，不会修改服务器上的东西）。

1. Http Get 不会修改服务器上的内容。他是幂等的，执行多次，不会产生任何不好的副作用。但是若在 doGet() 方法中，用请求参数修改数据，也就成了非幂等的情况了。记住：http get　和　servlet 中的 doGet 方法是有区别的。
2. Post 方法不是幂等的。
post 提交的数据可能用于不可逆转的事物。（就要做防止表单重复提交了）


### HTTP 响应

首部

```
➜  ~   curl -i -X GET  http://10.42.0.1:32772/k2data/courses

HTTP/1.1 200 OK // web 服务器实用的协议版本，响应的 HTTP 状态码， 状态码的响应文本
Server: Apache-Coyote/1.1
Content-Type: application/json //内容类型,它的值称为 MIME 类型。其值与 Http 请求中 Accept 所列的值有关 
Transfer-Encoding: chunked
Date: Tue, 19 Apr 2016 17:05:34 GMT
```

体,具体响应的具体内容

```
{
    "courses": [
        {
            "name": "java", 
            "time": {
                "start": "2016-04-10", 
                "end": "2016-04-22"
            }, 
            "estimatedTime": 20, 
            "facilitator": "shaonian", 
            "link": {
                "rel": "self", 
                "href": "http://10.42.0.1:32772/k2data/courses/1"
            }
        }
    ], 
    "info": {
        "status": "success", 
        "code": "OK", 
        "message": "查询课程列表成功"
    }
}

```

### 其他

端口

表示服务器硬件上运行的一个特定软件的逻辑连接，一个抽象的逻辑数。服务器上有 65535 个端口(0~65535)，0~1023 的 TCP 端口已经保留。

HTTP WEB 服务器

擅长提供静态的 Web 页面。它自己并不会去「动态内容」和「在服务器上保存数据」，而是让「Web 辅助应用」去处理。即：`CGI`，Java 中的实现为 `Servlet`。





## Web 应用体系结构

### 什么是容器?

客户端发送一个 `servlet` 请求 -> web 服务器 -> 交给部署该 `servlet` 的容器(`tocmat`)-> 容器为该请求创建或分配一个线程 -> 调用 `servlet service()`-> `doGet() 或 doPost()` ->「填入」响应对象->线程结束，容器把响应对象转换为 HTTP 响应发回给客户，然后删除请求和响应对象。

WEB 容器

独立的 WEB 容器通常配置 HTTP WEB 服务器（如 Apache）协作。不过 Tomcat 容器本身就是一个基本的 HTTP 服务器，只是功能没有 Apache 健壮。通常组合 Apache 作为 HTTP Web 服务器，Tomcat 作为 Web 容器。


### 配置信息

#### Maven 依赖

```
  <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jsp-api</artifactId>
      <version>2.0</version>
    </dependency>
```

#### web.xml、JSTL、EL 描述


**Servlet 3.1 deployment descriptor**

Java EE 7 XML schema, namespace is http://xmlns.jcp.org/xml/ns/javaee/

```
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
		 http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
</web-app>
```

**Servlet 3.0 deployment descriptor**

Java EE 6 XML schema, namespace is http://java.sun.com/xml/ns/javaee

```
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
	      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
	      http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	      version="3.0">
</web-app>
```

**Servlet 2.5 deployment descriptor**

Java EE 5 XML schema, namespace is http://java.sun.com/xml/ns/javaee

```
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
	      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
	      http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	      version="2.5">
</web-app>
```

JSTL1.2

`<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>`


**Servlet 2.4 deployment descriptor**

J2EE 1.4 XML schema, namespace is http://java.sun.com/xml/ns/j2ee

```
<web-app xmlns="http://java.sun.com/xml/ns/j2ee"
	      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	      xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee 
	      http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
	      version="2.4">

  <display-name>Servlet 2.4 Web Application</display-name>
</web-app>
```
JSTL1.1和JSP2.0

`<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>`



**Servlet 2.3 deployment descriptor**

J2EE 1.3 DTDs schema. This web.xml file is too old, highly recommend you to upgrade it.


```
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Servlet 2.3 Web Application</display-name>
</web-app>
```
JSTL1.0(不支持EL）和JSP1.2

`<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>`

### 如何找到 Servlet ？

使用部署描述文件将 URL 映射到 Servlet 

```
    <!--servlet 元素在 servlet-mapping 元素的前面-->
    <!--否则报:java.lang.IllegalStateException: No such servlet: DemoServlet-->
    
    <servlet>
        <servlet-name>DemoServlet</servlet-name>
        <servlet-class>cn.gx.servlet.DemoServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>DemoServlet</servlet-name>
        <url-pattern>/demo</url-pattern>
    </servlet-mapping>
```

从 Servlet 3.0开始（JavaEE 6.0 支持），可以使用注解的方式

`@WebServlet(name = "HelloServlet",value ="/HelloWorld")`


## Servlet

### Servlet 生命周期的作用和事件序列：

#### 1. servlet 类加载。

#### 2. servlet 实例化，无参构造函数。

此时只是一个普通的 Java 对象。并且只有一个是实例。

若容器使用了集群，每个 JVM 都会有特定 Servlet 的一个实例。不过对于每个 JVM 来说，只有一个servlet 实例。

#### 3. 调用 init 方法。

在 Servlet 的一生中只调用一次，而且必须在容器调用 service() 方法之前完成。此时才是一个具有处理 http 功能的 servlet。 

**ServletConfig**

* 每个 servlet 都有一个 ServletConfig 对象。
* 用于向 servlet 传递部署时的信息，而你不想把这个信息硬编码写到 servlet 中，servlet 上下文初始化参数。

如， SpringMVC 的配置文件配置。
```
 <!-- Processes application requests -->
  <servlet>
    <servlet-name>appServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
    </init-param>

    <init-param>
      <param-name>throwExceptionIfNoHandlerFound</param-name>
      <param-value>true</param-value>
    </init-param>
  </servlet>

  <servlet-mapping>
    <servlet-name>appServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
```

* 用户访问 ServletContext。 
* 参数在部署描述文件中配置。

容器，读取一次 web.xml 文件中的配置信息，包括 servlet 的初始化参数`<init-param>`,并为每一个 Servlet 创建一个新的 ServletConfig 实例，
并且提供了 key/value 的初始化参数的引用。

即：`容器.init(ServletConfig(<init-params>))`

**ServletContext**

* 每个 web 应用有一个 ServletContext 
* 用于访问 web 应用参数（也再部署描述文件中配置），上下文初始化参数。

如, Spring 容器的配置文件配置。
```
<!-- The definition of the Root Spring Container shared by all Servlets and Filters -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/spring/root-context.xml</param-value>
  </context-param>
```

* 放置属性，应用其他部分可以访问这些消息。
* 用于得到服务器信息，包括容器名，容器版本。

#### 4. 调用 service 方法。

每个请求都在一个单独的线程中运行。确定 HTTP 方法，并在调用对应的 servlet 实现方法，若没有实现，405 错误(Method Not Allowed)。

#### 5. 调用 destory 方法。

只能调用一次。


### 转发 & 重定向

#### 重定向

客户端浏览器完成，用户可以看到这个连接，

```
response.sendRedirect("hello");
```


#### 转发

在服务器端，客户端连接不变。

```
request.getRequestDispatcher("result.jsp").forward(request,response);
```


#### 相对 URL

有两种类型：前面有斜线("/") 和没有斜线



没有斜线，相对于原先请求的 URL 建立完整的 URL。

```
http://localhost:8080/myapp/user/save.do

response.sendRedirect("user/list.do");

// 相对于：http://localhost:8080/myapp/user/ 建立完整URL
http://localhost:8080/myapp/user/user/list.do
```

有斜线，相对于 WEB 应用本身简历完整的 URL。

```
http://localhost:8080/myapp/user/save.do

response.sendRedirect("/user/list.do");

// 相对于：http://localhost:8080/ ,即 WEB 应用本身，建立完整URL
http://localhost:8080/myapp/user/user/list.do
```


## WEB 应用，属性和监听者
### 监听者
ServletConfig 初始化参数只能让 servlet 使用，ServletContext 初始化参数可以让整个 Web 应用使用，但是它们的初始化参数只能是 String，即
`servletContext.getInitParameter()` 返回值是 String 类型。
如果希望应用初始化参数是一个数据库 DataSource 呢？虽然可以把 DataSource 的查找名放在一个上下文初始化参数里，但是之后谁将 String 参数
转换成 DataSource 对象呢？不能把代码放到 Servlet 中，你真想保证总是能让某个特定的 Servlet 最先运行么？

一个对象，它监听一个上下文初始化事件，这样就能得到上下文初始化参数，并在应用为客户提供服务之前运行一些代码。即，初始化应用。 

需要一个独立的对象满足如下功能：

1. 上下文初始化时（应用正在实现部署）时得到通知。
	* 从 ServletContext 得到上下文初始化参数。
	* 使用初始化参数查找名简历一个数据库连接。
	* 把数据库连接存储为一个属性，使得 web 应用的各个部分都能访问。
2. 上下文撤销时(应用取消部署或结束)得到通知。
	* 关闭数据库连接。
	
这个对象就是 `ServletContextListener`

如，Spring 上下文监听器。
```
  <!-- Creates the Spring Container shared by all Servlets and Filters -->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  
  public class ContextLoaderListener extends ContextLoader implements ServletContextListener {
    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

    public void contextInitialized(ServletContextEvent event) {
        this.initWebApplicationContext(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        this.closeWebApplicationContext(event.getServletContext());
        ContextCleanupListener.cleanupAttributes(event.getServletContext());
    }
}
```

容器->web.xml->sc=new ServletContext()->new key/value 配置参数->sc 引用 key/value 参数->new MyServletContextListener()->
contextInitialized(ServletContextEvent(sc)) 


### 属性

属性不是参数。

谁可以看到这个属性？属性可以存活多久？即，属性的作用域是什么？

三个作用域：ServletContext（上下文）,HtppServletRequest（请求）,HttpSession（会话）


   
3. 容器：Servlet 没有 main 方法，它们接受于另一个 Java 应用，这个 Java 应用称为容器。
4. 容器提供：
	1. 通信支持：轻松的让 Servlet 与 Web 服务器对话。无需自己建立 ServletSocket,监听端口，创建流。
	2. 生命周期管理
	3. 多线程支持：容器自动的为每个 Servlet 请求创建一个新的 Java 线程。
	4. 声明方式实现安全
	5. JSP 支持。

5. 关键API：
	* javax.servlet.http.HttpServlet extends javax.servlet.GenericServlet implements javax.servlet.Servlet(interface)
	* javax.servlet.http.HttpServletRequest extends javax.servlet.ServletRequest
	*  javax.servlet.http.HttpServletResponse extends javax.servlet.ServletResponse


* HttpServletRequest
	* 请求输入流:request.getInputStream();
		* 获得请求体，Get没有，Post有。若请求体包含二进制内容，就可以使用 getReader 或者 getInputStream

* HttpServletResponse
	* 响应输出流：response.setContentType(),getOutputStream(),getWriter()
		* 使用这个流写出数据，返回给客户（json/文件下载）。
			* 例子
			```
				InputStream is=ctx.getResourceAsStream("/book.txt");//“/” 表示Web应用根目录
                //普通的io操作
            ```
            * 文件下载为什么不用一个链接（就像其他静态资源一样）？
            	* 1. 可以这么做。
            	* 2. 可是如果在下载的时候，想要加入一些其他的逻辑（保存下载该文件的用户信息，或该用户是否可以下载）
            	* 3. 如果以系统是这样的，用户输入参数，使用参数动态的生成一个声音，发送回去。原先是没有“声音”文件的。你要建立一个这样的声音，然后把他放在响应中返回。
          * 输出，字符 Or  字节：

6. Servlet 重定向让浏览器完成工作。
	* response.sendRedirect("http://www.baidu.com");
		* 路径是相对的两种类型：有斜线（“/”）和没有斜线，原路径（http://localhost:8080/app/cool/bar.do）
			*没有斜线，相对于原来请求URL sendRedirect("foo/stuff.html")->http://localhost:8080/app/cool/foo/stuff.html
			*有斜线，相对于Web应用本身建立完整URL sendRedirect("/foo/stuff.html")->http://localhost:8080/app/foo/stuff.html
    * 客户端浏览器 url 变了
	* 不能再响应之后调用
7. 请求分配是在服务器端
	* servlet 决定将请求应该交给 Web 应用的另一个部分（在这里，就是一个JSP），result.jsp 接管响应，jsp->html 展示给客户。 request.getRequestDispatcher("result.jsp").forward(request,response);

##Web 容器模型
* 一个真正的 Servlet 拥有 ServletConfig,ServletContext(都通过 get 方法得到):
1. ServletConfig
	* Servlet 初始化参数
		1. 容器创建 Servlet 会读取 web.xml，读取它的 <init-param>（针对<servlet>）
		2. 容器为这个 Servlet 创建一个新的ServletConfig 实例，含有 servlet 的初始化参数的引用。
		3. 容器创建 Servlet 的一个新实例，容器调用 servlet 的 init() 方法。传入 ServletConfig 的引用。
		4. 便可以得到该 Servlet 的一些启动参数。

2. ServletContext
	* 上下文初始化参数
		`<context-param>`:getServletContext().getInitParameter("key");
	* 部署 Web 应用时，容器会建立一个 ServletContext ，这个上下文对 Web 应用中的每个 Servlet 和 Jsp(也是一个 Servlet)都可以用。
	
* 每个 Servlet 都有一个 ServletConfig ，每个 Web(准确说是每个 JVM) 应用有一个 ServletContext。

3. ServletContextListener :监听上下文，这样就能得到上下文初始化参数，并在应用为客户提供服务之前运行一些代码，比如一个数据库连接。

4. 三个作用域：上下文、请求、会话
	* 上下文作用域不是线程安全的。
		* 如果 多个Servlet(线程) 访问修改了上下文中的属性，如何保证线程安全？
		
			解决：
			1. 同步服务方法，没用
			2. 对 上下文加锁
			
				```
				// 同样对 ServletContext 同步的其他代码无法同事访问上下文对象
				  synchronized (getServletContext()){//得到上下文本身的锁。
            		getServletContext().setAttribute("admin","baijuan");
            
        		  }					
				```
				
				
	 * 会话作用域不是线程安全的。
		* 客户打开新的浏览器，容器把来自第二个窗口的请求看做是来自同一个会话。
			
			解决：对 HttpSession 同步。
	
	 * 只有请求属性和局部变量（也包括方法参数）是线程安全的。
	 	* 实例变量不是线程安全的。
	 	
	 		Servlet 是单例的，多个客户对一个 Servlet 做出请求，意味着有多个线程在运行该 Servlet 代码。而且所有线程都能访问 Servlet 的实例变量。因此，实例变量不是线程安全的。
	 		一个写的好的 Servlet 是没有任何实例变量的。要多个线程共享该怎办？
	 			1. 把变量声明为服务方法中的局部变量。
	 			2. 在最合适的所用域中使用一个属性。
	 	
	* 请求属性和请求分派（转发）
		* `request.getRequestDispatcher("result.jsp").forward(request,response);`
		* `getServletContext().getRequestDispatcher("/result.jsp").forward(request,response);`
			* "/" 表示 Web 根目录。
			* 如果转发前，向客户端响应了数据，会发生异常。
		RequestDispatcher
		
## 会话

对客户的第一个连接，容器会产生一个唯一的会话 ID，并通过响应把它返回给客户。客户再以后的每一个请求中发回这个会话的 ID 。
1. 最简单最常用的方式是通过 cookie 交换这个会话ID信息。
	* Server Response->`Set-Cookie:JSESSIONID=333333`->Client
	* Client Request->`Cookie:JSESSIONID=3445653434`->Server
	
2. (客户禁用了 cookie，JSESSIONID 不能放在一个 cookie 中) URL 重写

* 会话迁移（包括属性，并不是复制）

向 Servlet A 的请求 A 可能再一个 VM 中完成，而指向 Servlet A 的请求B可能再另一个不同的 VM 中完成。所以问题是：ServletContext、ServletConfig 和 HttpSession 对象会有什么变化？
答： 只有 HttpSession 对象（及其属性）会从一个 VM 移到另一个 VM。每个 VM 有一个 ServletContext ，每个 VM 上的每个 Servlet 有一个 ServletConfig.但是对于每个 Web 应用的一个给定的会话 ID，只有一个 HttpSession 对象，而不论应用分布在多少个 VM 上，相同的会话不会同时出现在两个 VM 中，除了 HttpSession 外，所有对象都会在第二个服务器中复制。
	* 关键：其他部分会在每个 节点/VM 上复制，但是会话对象不会复制。而只是移动。  
	* VM1->迁移到->VM2,意味着会话在 VM1 上**钝化**，并在 VM2 上**激活**。
## JSP 

jsp->java->class(转换和编译只有一次)

三种不同类型的 JSP 元素：

1. Scriptlet（普通 Java 代码）:`<% %>`
	* JSP 声明：`<%! %>`,用于声明所生成 Servlet 类的成员。这说明变量和方法都可以声明。标记中的所有内容都会增加到类中，而置于服务方法之外。这意味着完全可以生命静态变量和方法。
2. 指令：`<%@ %>`
	1. page
		* 导包
		* import:会自动加到生成的 Servlet 类中。默认加：java.lang、javax.servlet、javax.servlet.http 和 javax.servlet.jsp
		* isErrorPage:定义当前页面是否是另一个 jsp 页面的错误页面。默认值`false`,此时不能使用 `exception` 对象。只有设置为`true` 时，才可以使用。
	2. include：`<%@ include file="/"%>`
		* 定义再转换时增加到当前页面的文本和代码，在转换时，直接插入包含的源代码。最终一个 class。
		* `<jsp:include>`：运行时调用另一个 Jsp 响应。多个 class。
	3. taglib 
3. 表达式：`<%= %>`
	* 表达式后无分号，`<%=person.getAge()>`->`out.print(person.getAge());`
	
9大内置对象

|API|隐式对象|
|-|-|
|JspWriter|out|
|HttpServletRequest|request|
|HttpServletResponse|response|
|HttpSession|session|
|ServletContext|application|
|ServletConfig|config|
|Throwable|exception|
|PageContext|pageContext|
|Object|page|


EL 表达式

1. `${firstThing.secondThing}`
	* firstTing
		* EL 隐式对象：映射对象(Map)：pageScope、requestScope、sessionScope、applicationScope\param、paramValues\header、headerValues\cookie\initParam；不是映射：pageContext（bean）
		* 或属性（页面作用域、请求作用域、会话作用域、应用作用域中的属性）。
		
2. 操作符访问性质和映射值
	* 使用 `.`，`${firstThing.secondThing}` name 要么是 Map(有键)，要么是一个 bean(有属性)。
	* 使用 `[]` `${firstThing["secondThing"]}` 与上等价
		* 其次，firstThing 还可以是 List 或是数组,此时 secondThing 是 List或数组中的索引。
		

Servlet 映射

* 三种 `<url-pattern>` 元素
	1. 完全匹配：/hello/shaonian.do(必须以`/`开头)
	2. 目录匹配：/hello/*（必须以`/`开头，以`/*`结尾） 
	3. 扩展名匹配：*.do(必须以`*`开头，不能以`/`开头)
* 匹配规则：
	1. 按上面顺序进行匹配。先查找完全匹配；不成功，则目录匹配；不成功，则扩展名匹配。
	2. 若一个请求与多个目录匹配。选择最长匹配。
* Servlet 加载顺序：
	1. `<load-on-startup></load-on-startup>`:大于0，容器启动就加载。然后按照1，2，3这样的顺序加载，若有相同的序号，则按照再 DD 中声明这些 Servlet 的顺序来加载。








1. Http 请求包括请求 URL(客户想要访问的资源)、Http 方法(GET、Post等)，以及（可选）表单参数数据（也称为“查询串”）
2. Http 响应包括一个状体码，内容类型，以及响应的实际内容。
3. GET 请求会把表单数据追加到 URL 的最后（参数放在请求行上）。有大小限制
4. POST 请求将表单数据包括在请求的体中。有一个体（消息体/负载）。
	* 区别：
		1. 发送参数大小
		2. 安全（Get　参数直接在地址栏上显示）
		3. 书签（Get　可以保存为书签，Post 请求不能）
		4. 重要：get 简单的获取。post 发送数据，进行处理，区别与 get 一样获取数据外，就是使用 post 体的数据来修改服务器上的东西。
	* 区分：
		* Get: 超链接
		* Post：form 表单中明确的说`method='POST'`,默认是 GET.

* GET/PUT/HEAD 都是幂等的（重复做相同的事，不会修改服务器上的东西）。
	* Http Get 不会修改服务器上的内容。他是幂等的，执行多次，不会产生任何不好的副作用。但是若在 doGet() 方法中，用请求参数修改数据，也就成了非幂等的情况了。记住：http get　和　servlet 中的 doGet 方法是有区别的。
* Post 方法不是幂等的。
	* post 提交的数据可能用于不可逆转的事物。（就要做防止表单重复提交了）


5. Web 服务器擅长提供静态 HTML　页面，若动态，就需要某种辅助应用与服务器协作。通常为 CGI(Perl),公共网关接口（Common Gateway Interface）。
6. URL(Uniform Resource Locator).Web 上的每个资源都有自己唯一的地址，都采用这种 URL 格式。协议：服务器名：端口/特定资源名，如果对应一个 Get 请求，那么它哈可能包含一个可选的查询串。

#Web 应用体系结构
1. Http 方法对应的 HttpServlet 方法
2. Servlet 生命周期的作用和事件序列：
	1. servlet 类加载。
	2. servlet 实例化。
	3. 调用 init 方法。
	4. 调用 service 方法。
	5. 调用 destory 方法。

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




JSP三大指令
  一个jsp页面中，可以有0~N个指令的定义！

1. page --> 最复杂：<%@page language="java" info="xxx"...%>
  * pageEncoding和contentType：
    > pageEncoding：它指定当前jsp页面的编码，只要不说谎，就不会有乱码！在服务器要把jsp编译成.java时需要使用pageEncoding!
    > contentType：它表示添加一个响应头：Content-Type！等同与response.setContentType("text/html;charset=utf-8");
    > 如果两个属性只提供一个，那么另一个的默认值为设置那一个。
    > 如果两个属性都没有设置，那么默认为iso
  * import：导包！可以出现多次
  * errorPage和isErrorPage
    > errorPage：当前页面如果抛出异常，那么要转发到哪一个页面，由errorPage来指定
    > isErrorPage：它指定当前页面是否为处理错误的页面！当该属性为true时，这个页面会设置状态码为500！而且这个页面可以使用9大内置对象中的exception!
  *   <error-page>
  	<error-code>404</error-code>
  	<location>/error/errorPage.jsp</location>
      </error-page>
      <error-page>
        <error-code>500</error-code>
        <location>/error/errorPage.jsp</location>
      </error-page>
      <error-page>
        <exception-type>java.lang.RuntimeException</exception-type>
        <location>/index.jsp</location>
      </error-page>
   * autoFlush和buffer
     > autoFlush：指定jsp的输出流缓冲区满时，是否自动刷新！默认为true，如果为false，那么在缓冲区满时抛出异常！
     > buffer：指定缓冲区大小，默认为8kb，通常不需要修改！
   * isELIgnored：是否忽略el表达式，默认值为false，不忽略，即支持！
   * 基本没有：
     > language：指定当前jsp编译后的语言类型，默认值为java。
     > info：信息！
     > isThreadSafe：当前的jsp是否支持并发访问！
     > session：当前页面是否支持session，如果为false，那么当前页面就没有session这个内置对象！
     > extends：让jsp生成的servlet去继承该属性指定的类！

2. include --> 静态包含
  * 与RequestDispatcher的include()方法的功能相似！
  * <%@include%> 它是在jsp编译成java文件时完成的！他们共同生成一个java(就是一个servlet)文件，然后再生成一个class！
  * RequestDispatcher的include()是一个方法，包含和被包含的是两个servlet，即两个.class！他们只是把响应的内容在运行时合并了！
  * 作用：把页面分解了，使用包含的方式组合在一起，这样一个页面中不变的部分，就是一个独立jsp，而我们只需要处理变化的页面。
3. taglib --> 导入标签库
  * 两个属性：
    > prefix：指定标签库在本页面中的前缀！由我们自己来起名称！
    > uri: 指定标签库的位置！
    > <%@taglib prefix="s" uri="/struts-tags"%> 前缀的用法<s:text>

=========================================

九个内置对象

* out --> jsp的输出流，用来向客户端响应
* page --> 当前jsp对象！　它的引用类型是Object，即真身中有如下代码：Object page = this;
* config --> 它对应真身中的ServletConfig对象！
* pageContext --> 一个顶9个！
* request --> HttpServletEequest
* response --> HttpServletResponse
* exception --> Throwable
* session --> HttpSession
* application --> ServletContext

1. pageContext
  * 一个顶9个！
  * Servlet中有三大域，而JSP中有四大域，它就是最后一个域对象！
    > ServletContext：整个应用程序
    > session：整个会话(一个会话中只有一个用户)
    > request：一个请求链！
    > pageContext：一个jsp页面！这个域是在当前jsp页面和当前jsp页面中使用的标签之间共享数据！
      > 域对象
      > 代理其他域：pageContext.setAttribute("xxx", "XXX", PageContext.SESSION_SCOPE);
      > 全域查找：pageContext.findAttribute("xxx");从小到大，依赖查找！
      > 获取其他8个内置对象：

=========================================

JSP动作标签
　　这些jsp的动作标签，与html提供的标签有本质的区别。
　　* 动作标签是由tomcat(服务器)来解释执行！它与java代码一样，都是在服务器端执行的！
　　* html由浏览器来执行！
    * <jsp:forward>：转发！它与RequestDispatcher的forward方法是一样的，一个是在Servlet中使用，一个是在jsp中使用！
    * <jsp:include>：包含：它与RequestDispatcher的include方法是一样的，一个是在Servlet中使用，一个是在jsp中使用！
      > <%@include>和<jsp:include>有什么不同！
    * <jsp:param>：它用来作为forward和include的子标签！用来给转发或包含的页面传递参数！

=========================================

JavaBean

javaBean的规范：
  1. 必须要有一个默认构造器
  2. 提供get/set方法，如果只有get方法，那么这个属性是只读属性！
  3. 属性：有get/set方法的成员，还可以没有成员，只有get/set方法。属性名称由get/set方法来决定！而不是成员名称！
  4. 方法名称满足一定的规范，那么它就是属性！boolean类型的属性，它的读方法可以是is开头，也可以是get开头！


内省：
  内省类 --> Bean信息 --> 属性描述符 --> 属性的get/set对应的Method！ --- > 可以反射了！

-----------------------

commons-beanutils，它是依赖内省完成！
  * 导包：
    > commons-beanutils.jar
    > commons-logging.jar

BeanUtils.getProperty(Object bean, String propertyName)
BeanUtils.setProperty(Object bean, String propertyName, String propertyValue)
BeanUtils.populate(Map map, Object bean)

CommontUtils.toBean(Map map, Class class)

-----------------------

jsp中与javaBean相关的标签！

* <jsp:useBean> --> 创建或查询bean
  * <jsp:useBean id="user1" class="cn.itcast.domain.User" scope="session"/> 在session域中查找名为user1的bean，如果不存在，创建之
  * <jsp:useBean id="user1" class="cn.itcast.domain.User" scope="session"/>
* <jsp:setProperty>
  * <jsp:setProperty property="username" name="user1" value="admin"/> 设置名为user1的这个javabean的username属性值为admin
* <jsp:getProperty>
  * <jsp:getProperty property="username" name="user1"/> 获取名为user1的javabean的名为username属性值

=========================================

EL表达式

1. EL是JSP内置的表达式语言！
  * jsp2.0开始，不让再使用java脚本，而是使用el表达式和动态标签来替代java脚本！
  * EL替代的是<%= ... %>，也就是说，EL只能做输出！

2. EL表达式来读取四大域
  * ${xxx}，全域查找名为xxx的属性，如果不存在，输出空字符串，而不是null。
  * ${pageScope.xxx}、${requestScope.xxx}、${sessionScope.xxx}、${applicationScope.xxx}，指定域获取属性！

3. javaBean导航
  <%
	Address address = new Address();
	address.setCity("北京");
	address.setStreet("西三旗");
	
	Employee emp = new Employee();
	emp.setName("李小四");
	emp.setSalary(123456);
	emp.setAddress(address);
	
	request.setAttribute("emp", emp);
  %>

<h3>使用el获取request域的emp</h3>
${requestScope.emp.address.street }<!-- request.getAttribute("emp").getAddress().getStreet() --><br/>


4. EL可以输出的东西都在11个内置对象中！11个内置对象，其中10个是Map！pageContext不是map，它就是PageContext类型，1个项9个。
  * 我们已经学习了四个
  * param：对应参数，它是一个Map，其中key参数名，value是参数值，适用于单值的参数。
  * paramValues：对应参数，它是一个Map，其中key参数名，value是多个参数值，适用于多值的参数。
  * header：对应请求头，它是一个Map，其中key表示头名称，value是单个头值，适用于单值请求头
  * headerValues：对应请求头，它是一个Map，其中key表示头名称，value是多个头值，适用于多值请求头
  * initParam：获取<context-param>内的参数！
    <context-param>
  	<param-name>xxx</param-name>
  	<param-value>XXX</param-value>
  </context-param>
  <context-param>
  	<param-name>yyy</param-name>
  	<param-value>YYY</param-value>
  </context-param>

    ${initParam.xxx}

  * cookie：Map<String,Cookie>类型，其中key是cookie的name，value是cookie对象。 ${cookie.username.value}
  * pageContext：它是PageContext类型！${pageContext.request.contextPath}

=========================================

EL函数库（由JSTL提供的）
  * 导入标签库：<%@ tablib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	String toUpperCase(String input)：把参数转换成大写
	String toLowerCase(String input)：把参数转换成小写
	int indexOf(String input, String substring)：从大串，输出小串的位置！
	boolean contains(String input, String substring)：查看大串中是否包含小串
	boolean containsIgnoreCase(String input, String substring)：忽略大小写的，是否包含
	boolean startsWith(String input, String substring)：是否以小串为前缀
	boolean endsWith(String input, String substring)：是否以小串为后缀
	String substring(String input, int beginIndex, int endIndex)：截取子串
	String substringAfter(String input, String substring)：获取大串中，小串所在位置后面的字符串
	substringBefore(String input, String substring)：获取大串中，小串所在位置前面的字符串
	String escapeXml(String input)：把input中“<”、">"、"&"、"'"、"""，进行转义
	String trim(String input)：去除前后空格
	String replace(String input, String substringBefore, String substringAfter)：替换
	String[] split(String input, String delimiters)：分割字符串，得到字符串数组
	int length(Object obj)：可以获取字符串、数组、各种集合的长度！
	String join(String array[], String separator)：联合字符串数组！

=========================================

自定义函数库
 * 写一个java类，类中可以定义0~N个方法，但必须是static，而且有返回值的！
 * 在WEB-INF目录下创建一个tld文件
   <function>
    <name>fun</name>
    <function-class>cn.itcast.fn.MyFunction</function-class>
    <function-signature>java.lang.String fun()</function-signature>
  </function>

 * 在jsp页面中导入标签库
   <%@ taglib prefix="it" uri="/WEB-INF/tlds/itcast.tld" %>
 * 在jsp页面中使用自定义的函数：${it:fun() }







































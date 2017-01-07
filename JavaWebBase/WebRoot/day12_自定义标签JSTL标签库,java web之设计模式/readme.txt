JSP指令

JSP指令分类
  JSP有三大指令：
  * page指令
  * include指令
  * taglib指令

  在JSP中没有任何指令是必须的！！！
  但基本上每个JSP都是使用page指令！  

==========================

page指令

page指令是最为常用的指令！

1. page指令的常用属性：
* import：等同与import语句
  --> <%@ page import="java.util.*" %>
  --> <%@ page import="java.util.*, java.net.*" %>
  --> 在一个JSP页面中可以给出多个page指令，而且import是可以重复出现的
   <%@ page import="java.util.*" %>
   <%@ page import="java.next.*" %>

* pageEncoding：指定当前页面的编码
  --> 如果pageEncoding没有指定，那么默认为contentType的值；
  --> 如果pageEncoding和contentType都没有指定，那么默认值为iso-8859-1
  
* contentType：等同与调用response.setContentType("text/html;charset=xxx");
  --> 如果没有指定contentType属性，那么默认为pageEncoding的值；
  --> 如果contentType和pageEncoding都没有指定，那么默认值为iso-8859-1

* errorPage：如果当前页面出现异常，那么跳转到errorPage指定的jsp页面。例如：<%@ page errorPage="a.jsp" %>
* isErrorPage：上面示例中指定a.jsp为错误页面，但在a.jsp中不能使用内置对象exception，保有a.jsp中使用<%@page isErrorPage="true"%>时，才能在a.jsp中使用错误页面。
* autoFlush：当autoFlush为true时，表示out流缓冲区满时会自动刷新。默认为true
* buffer：指定out流的缓冲区大小，默认为8KB
* isELIgnored：当前JSP页面是否忽略EL表达式，默认为false，表示不忽略，即支持EL表达式

2. page指令不常用的属性：
* language：当前JSP编译后的语言！默认为java，当前也只能选择java
* info：当前JSP的说明信息
* isThreadSafe：当前JSP是否执行只能单线程访问，默认为false，表示支持并发访问
* session：当前页面是否可以使用session，默认为false，表示支持session的使用。
* extends：指定JSP真身的父类！

3. web.xml中对jsp的配置

	<jsp-config>
		<jsp-property-group>
			<url-pattern>*.jsp</url-pattern> <!--表示对所有jsp进行配置-->
			<el-ignored>true</el-ignored> <!--忽略EL表达式-->
			<page-encoding>UTF-8</page-encoding> <!--编码为utf-8-->
			<scripting-invalid>true</scripting-invalid> <!--禁用java脚本-->
		</jsp-property-group>
	</jsp-config>

==========================

include指令
语法：<%@include file="页面"%>
include指令的作用是包含指定的页面！在jsp被编译成java文件之前会把两个jsp文件合并，然后再编译成一个java文件。

注意：
<%@include file="<%=myfile%>" %>
这是不能通过编译的，因为myfile是一个变量，它的值只有在java编译成class后执行时才能确定。而include指令需要在jsp编译java时就要确定包含的是哪个页面，所以...

==========================

taglib指令
taglib指令是用来在当前jsp页面中导入第三方的标签库
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

prefix：指定标签前缀，这个东西可以随意起名
uri：指定第三方标签库的uri（唯一标识）
当然，需要先把第三方标签库所需jar包放到类路径中。

==========================

九大内置对象

　　无需在jsp中声明即可使用的9个对象
* out（JspWriter）：等同与response.getWriter()，用来向客户端发送文本数据；
* config（ServletConfig）：对应“真身”中的ServletConfig；
* page（当前JSP的真身类型）：当前JSP页面的“this”，即当前对象，引用为Object类型；
* pageContext（PageContext）：页面上下文对象，它是最后一个没讲的域对象；
* exception（Throwable）：只有在错误页面中可以使用这个对象；
* request（HttpServletRequest）：即HttpServletRequest类的对象；
* response（HttpServletResponse）：即HttpServletResponse类的对象；
* application（ServletContext）：即ServletContext类的对象；
* session（HttpSession）：即HttpSession类的对象，不是每个JSP页面中都可以使用，如果在某个JSP页面中设置<%@page session=”false”%>，说明这个页面不能使用session。


pageContext对象

pageContext对象是PageContext类型
 * 域对象：只在当前jsp页面中有效的域，通常用来让jsp与当前jsp中标签之间共享数据
 * 获取其他8个内置对象：可以获取其他8个内置对象
 * 代理其他域对象：可以用pageContext来操作其他3个域。

==========================

JSP动作标签

JSP动作标签是用来替代一部分java脚本，使非java开发人员也可以向jsp中添加动态信息

----------------------

<jsp:include>
例如：<jsp:include page="xxx"/>，用来包含指定的页面。
例如在a.jsp中存在如下内容：<jsp:include page="b.jsp"/>
a.jsp和b.jsp分别编码成Servlet，然后在执行Servlet时才会执行包含过程。这也是include指令与include标签的区别。

注意：<jsp:include page="<%=myfile%>">，这是可以的！因为include指令是在执行时才完成的包含，在执行时已经可以确定myfile这个变量的值。

该标签内部使用的是RequestDispatcher#include()方法完成的包含

----------------------

<jsp:forward>
例如：<jsp:forward page="xxx"/>，用来转发到指定页面
例如在a.jsp中存在如下内容：<jsp:fowrad page="b.jsp"/>
a.jsp中的内容不会显示在浏览器上，而只是显示b.jsp的内容。而且在<jsp:forwad>标签下面的内容不会被执行。

----------------------

<jsp:param>
该标签是<jsp:include>和<jsp:forward>的子标签，用来向其他页面传递参数。
<jsp:include page="/b.jsp">
	<jsp:param value="zhangSan" name="username"/> 
</jsp:include>
在b.jsp中可以使用request.getParameter("username")来获取参数值。


==========================

JavaBean

满足JavaBean规范的类就是javaBean

* 必须有public的无参的构造器
* 如果成员变量提供了getter/setter方法，那么称之为javabean属性。

JavaBean主要是用来通过反射操作的类！

因为需要通过Class的newInstance()方法来创建类的实例，所以要求类必须提供public的无参构造器
因为需要通过反射来操作属性，所以需要提供getter/setter方法。

----------------------

内省

内省依赖反射，内省比反射简化一点点，用来操作JavaBean

把Map中的数据封装到指定类型的JavaBean中。
以有对象：Map、Class<User>

1. 通过Class对象获取BeanInfo
BeanInfo info = Introspector.getBeanInfo(User.class);

2. 通过BeanInfo获取所有属性描述符对象
PropertyDescriptor[] pds = info.getPropertyDescriptors();

3. PropertyDescriptor：
* String name getName()：获取当前属性名称
* Method getReadMethod()：获取get方法反射对象
* Method getWriteMethod()：获取set方法反射对象

----------------------

commons-beanutils

beanutils比内省要简单很多，而且还要强大很多，它底层依赖内省。

1. jar包
commons-beanutils.jar、commons-logging.jar


2. 通过反射设置Javabean
Class<User> clazz = User.class;
Object user = clazz.newInstance();
		
BeanUtils.setProperty(user, "username", "admin"); 
BeanUtils.setProperty(user, "password", "admin123"); 

3. 获取属性值

String username = BeanUtils.getProperty(user, "username");

4. 把Map数据封装到JavaBean对象中
Map<String,String> map = new HashMap<String,String>();
map.put("username", "admin");
map.put("password", "admin123");
		
User user = new User();
BeanUtils.populate(user, map); 

要求：map的key名称必须与User类的属性名称相同。不要无法赋值！

-----------------

JSP中与JavaBean相关的标签

* <jsp:useBean>
* <jsp:setProperty>
* <jsp:getProperty>


<jsp:useBean id="user1" class="cn.itcast.domain.User" />
查看page域中是否存在user1这个域属性，如果存在，那么直接获取。
如果不存在，那么创建之！

等同与：
User user1 = pageContext.getAttribute("user1");
if(user1 == null) {
  user1 = new User();//创建user1对象
  pageContext.setAttribute("user1", user1);//保存到page域
}


-----------------

上面是操作page域，可以通过scope属性来指定操作的域

<jsp:useBean id="user1" class="cn.itcast.domain.User" scope="page"/>
<jsp:useBean id="user2" class="cn.itcast.domain.User" scope="request"/>
<jsp:useBean id="user3" class="cn.itcast.domain.User" scope="session"/>
<jsp:useBean id="user4" class="cn.itcast.domain.User" scope="applicatioin"/>


------------------

<jsp:setProperty>
设置属性值

<jsp:setProperty property="username" name="user1" value="admin"/>
* name：指定名为user1的JavaBean
* property：指定要设置的属性名称
* value：指定要设置的属性值

等同与

User user1 = (User)pageContext.getAttribute("user1");
user1.setUsername("admin");

------------------

<jsp:getProperty>
获取属性值

<jsp:getProperty property="username" name="user1"/>
输出user1这个javaBean的username属性值

等同与

User user1 = (User)pageContext.getAttribute("user1");
out.print(user1.getUsername());

==========================
==========================
==========================

EL表达式

EL：Expression Language，它是可以在JSP页面中直接使用的语言！
JSP页面也可以忽略EL：<@page isELIgnored="true"%>
EL用来代替<%=...%>

--------------------

格式：${...}，例如：${1 + 2}，会在页面上输出3

--------------------

运算符：+ - * / % == != < > <= >= && ! || empty
${empty ""} --> 输出true，判断集合、数据、字符串长度是否为0

${null}，不会输出！如果是null不输出！

--------------------

EL内置对象

EL一共11个内置对象

EL操作四大域的内置对象：它们是Map类型
pageScope
requestScope
sessionScope
applicationScope

${pageScope.user}：输出pageContext.getAttribute("user")
${requestScope.user}：输出request.getAttribute("user");
${sessionScope.user}：输出session.getAttribute("user");
${applicationScope.user}：输出application.getAttribute("user");

${user}
依次在pageScope、requestScope、sessionScope、applicationScope中查找user
如果查找到，那么立刻停止查找。

-----------

对JavaBean的操作
对List的操作
对Map的操作

-----------

操作JavaBean
<%
User user = new User();
user.setUsername("zhangSan");
user.setPassword("123");
pageContext.setAttribute("user", user);
%>
${pageScope.user.username}
${pageScope.user.password}

-----------

操作List

<%
User user = new User();
user.setUsername("zhangSan");
user.setPassword("123");
List list = new ArrayList();
list.add(user);
pageContext.setAttribute("list", list);
%>

${pageScope.list[0].username}
${pageScope.list[0].password}

-----------

操作Map

<%
User user = new User();
user.setUsername("zhangSan");
user.setPassword("123");
Map map = new HashMap();
map.put("u1", user);

pageContext.setAttribute("map", map);
%>

${pageScope.map['u1'].username}
${pageScope.map['u1'].password}

${pageScope.map.u1.username}
${pageScope.map.u1.password}

------------------

EL操作参数内置对象：Map类型

param：Map<String,String>
paramValues：Map<String,String[]>

${param.username}：request.getParameter("username")
${paramValues.hobby}：request.getParameterValues("hobby");

------------------

EL操作请求头内置对象：Map类型

header：Map<String,String>
headerValues：Map<String,String[]>

${header.UserAgent}
${headerValues.UserAgener[0]}

------------------

应用初始化参数内置对象：Map类型
initParam：Map<String,String>

用来获取web.xml文件中的<context-param>参数，例如：

web.xml
<context-param>
  <param-name>p1</param-name>
  <param-value>v1</param-value>
</context-param>

${initParam.p1}

------------------

与Cookie相关的内置对象：Map类型
cookie：Map<String,Cookie>，其中key是Cookie的名称，而值是Cookie对象

${cookie.jsessionid.value}：获取sessionid

------------------

pageContext内置对象：PageContext类型

${pageContext.request}，等同pageContext.getRequest()
${pageContext.session}，等同pageContext.getSession()

${pageContext.request.contextpath}，获取项目名
${pageContext.session.id}，获取sessionId

------------------

EL中最重要的就是操作四大域！


==========================
==========================
==========================

EL函数库

EL函数库，当前就是一些函数了。

使用EL函数库需要在JSP页面中导入标签库：
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/function"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
…
String[] strs = {"a", "b","c"};
List list = new ArrayList();
list.add("a");
pageContext.setAttribute("arr", strs);
pageContext.setAttribute("list", list);
%>
${fn:length(arr) }<br/><!--3-->
${fn:length(list) }<br/><!--1-->
${fn:toLowerCase("Hello") }<br/> <!-- hello -->
${fn:toUpperCase("Hello") }<br/> <!-- HELLO -->
${fn:contains("abc", "a")}<br/><!-- true -->
${fn:containsIgnoreCase("abc", "Ab")}<br/><!-- true -->
${fn:contains(arr, "a")}<br/><!-- true -->
${fn:containsIgnoreCase(list, "A")}<br/><!-- true -->
${fn:endsWith("Hello.java", ".java")}<br/><!-- true -->
${fn:startsWith("Hello.java", "Hell")}<br/><!-- true -->
${fn:indexOf("Hello-World", "-")}<br/><!-- 5 -->
${fn:join(arr, ";")}<br/><!-- a;b;c -->
${fn:replace("Hello-World", "-", "+")}<br/><!-- Hello+World -->
${fn:join(fn:split("a;b;c;", ";"), "-")}<br/><!-- a-b-c -->

${fn:substring("0123456789", 6, 9)}<br/><!-- 678 -->
${fn:substring("0123456789", 5, -1)}<br/><!-- 56789 -->
${fn:substringAfter("Hello-World", "-")}<br/><!-- World -->
${fn:substringBefore("Hello-World", "-")}<br/><!-- Hello -->
${fn:trim("     a b c     ")}<br/><!-- a b c -->
${fn:escapeXml("<html></html>")}<br/> <!-- <html></html> -->


====================

自定义函数库

1. 编写类
类中只能有public static的方法，并且一定要有返回值

public class ItcastFuncations {
	public static String test() {
		return "传智播客自定义EL函数库测试";
	}
}

2. 部署文件
/WEB-INF/itcast.tld
<?xml version="1.0" encoding="UTF-8" ?>

<taglib xmlns="http://java.sun.com/xml/ns/j2ee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd"
  version="2.0">
    
  <tlib-version>1.0</tlib-version>
  <short-name>itcast</short-name>
  <uri>http://www.itcast.cn/jsp/functions</uri>

  <function>
    <name>test</name>
    <function-class>cn.itcast.el.funcations.ItcastFuncations</function-class>
    <function-signature>String test()</function-signature>
  </function>
</taglib>

3. 在JSP中使用自定义

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="itcast" uri="/WEB-INF/itcast.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body> 
  	<h1>${itcast:test() }</h1>
  </body>
</html>











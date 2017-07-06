<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'uploadFile.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <h1 >上传文件${pageContext.request.contextPath}</h1>
   <!-- 如果有这个属性enctype="multipart/form-data"。则 hidden属性将会失效。因为getParameter()将获取为null -->
   	<form action="<c:url value = '/servlet/UploadMyFileServlet'/>" method="post" enctype="multipart/form-data">
   		文件：<input type ="file" name = "myfile"/>
   		<input type = "submit" value = "上传"/>
   	</form>
   
  </body>
</html>
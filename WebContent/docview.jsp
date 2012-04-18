<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="index.IndexDAO"%>
<%@page import="index.Doc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String queryStr = request.getParameter("doc_id");
	queryStr = new String(queryStr.getBytes("ISO-8859-1"),"UTF-8");
	Doc doc = IndexDAO.getDoc(Integer.parseInt(queryStr));
	out.println(doc.getContent());
%>

</body>
</html>
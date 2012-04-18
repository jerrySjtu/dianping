<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="search.Search"%>
<%@page import="java.util.Iterator"%>
<%@page import="index.IndexDAO"%>
<%@page import="index.Doc"%>
<%@page import="search.Render"%>
<%@page import="search.Query"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
 	//get query
	String queryStr = request.getParameter("query");
	queryStr = new String(queryStr.getBytes("ISO-8859-1"),"UTF-8");
%>
<form action="search.jsp" method="get">
<%
	out.println("<input type=\"text\" name=\"query\" value=" + queryStr + " /> ");
%>
  <input type="submit" value="搜索" />
</form>
<% 
 	//get documents
 	Query query = new Query(queryStr);
 	Search s = new Search(query);
 	List<Integer> docList = s.search();
 	Iterator<Integer> iterator = docList.iterator();
 	while(iterator.hasNext()) {
 		int docID = iterator.next();
 		Doc doc = IndexDAO.getDoc(docID);
 		String content = Render.render(query, doc);
 		System.out.println(content);
 		out.println("<a href=\"docview.jsp?doc_id=" + docID + "\">" + content + "</br>");
 	}
%>
</body>
</html>
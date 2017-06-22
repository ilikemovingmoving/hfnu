<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="service.StudentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%@include file="../all.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<link rel="stylesheet" type="text/css" href="/zhpcxt/css/base.css"/>
<link rel="stylesheet" type="text/css"
      href="/zhpcxt/css/admin-css.css"/>
<link rel="stylesheet" type="text/css"
      href="/zhpcxt/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
      href="/zhpcxt/css/themes/icon.css">
      
<%
	StudentService studentService=new StudentService();
	Map<String, Object> map=new HashMap<String, Object>(); 
	map.put("studentId", request.getParameter("student_id"));
	List list=studentService.querySituation(map);
	if(list!=null && list.size()>0){
		Map item=(Map)(list.get(0));
		//out.println("");
		out.println("学业成绩：&nbsp;" + item.get("learning_status"));
		out.println("<br/><br/>奖励：&nbsp;" + item.get("reward"));
		out.println("<br/><br/>违纪：&nbsp;" + item.get("breach"));
		out.println("<br/><br/>惩罚：&nbsp;" + item.get("punish"));
		out.println("<br/><br/>备注：&nbsp;" + item.get("remark"));
		out.println("<br/><br/><a style='color:blue;text-decoration:underline;' href='../counsellor/stuScore.jsp?student_id="+request.getParameter("student_id")+"&class_id="+request.getParameter("class_id")+"'>学习成绩</a>");
		
	}else{
		out.println("未找到数据");
	}

%>


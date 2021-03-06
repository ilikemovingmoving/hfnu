<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ArrayList adminlogin = (ArrayList)session.getAttribute("adminlogin");
boolean closed = false;
ArrayList student = null;
if(adminlogin != null && adminlogin.size() != 0){
	closed = true;
	student = (ArrayList)array.getStudent((String)adminlogin.get(0)).get(0); 
}
String message = (String)request.getAttribute("message");
%>
<%@include file="../all.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/admin-css.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/icon.css">
<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path %>/js/admin.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('tbody tr:even').css({'background':'#ffffff'});
	$('tbody tr:odd').css({'background':'#eeeeff'});
	var message = <%=message %>;
	if(message != null && message != ""){
		alert(message);
	}
	login('<%=path %>',<%=closed %>);
	$("#click_logout").click(function(){
		logout('<%=path %>');
	});
	$("#editpass").click(function(){
		editpass('<%=path %>');
	});
	$("#admin_add").click(function(){
		adminAdd('<%=path %>');
	});
})
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no" resizable="false">
<div id="north" region="north" split="false" border="false">
  <jsp:include page="/admin/head.jsp"></jsp:include>
</div>
<div region="west" split="false" border="false" title="导航菜单" style="width:180px;" id="west">
  <div id="left-menus" border="false" fit="true">
    <jsp:include page="/admin/left.jsp"></jsp:include>
  </div>
</div>
<div id="mainPanle" region="center" border="true" style="background:#f7f7f7; padding:5px;">
  <table width="100%">
    <thead>
      <tr>
        <td colspan="11" align="center" style="padding:5px;">学生用户管理</td>
      </tr>
      <tr class="thead">
        <td align="center">学号</td>
        <!-- <td align="center">考生号</td> -->
        <td align="center">姓名</td>
        <td align="center">性别</td>
        <td align="center">班级编号</td>
        <!-- <td align="center">学生证号</td>
        <td align="center">层次</td> -->
        <td align="center">身份证</td>
        <td align="center">院系</td>
        <td align="center">专业</td>
        <td align="center">通讯地址</td>
      </tr>
    </thead>
    <tbody class="tbody">
    <tr>
    <%
    //if(student != null && student.size() != 0){
    	for(Object o:student){
    		out.print("<td align='center'>"+(o==null?"":o.toString())+"</td>");
    	}
    %>
    </tr>
    <%-- <tr>
	<td align="center"><%=student.get(1) %></td>
	<td align="center"><%=student.get(2) %></td>
	<td align="center"><%=student.get(3) %></td>
	<td align="center"><%=student.get(4) %></td>
	<td align="center"><%=student.get(5) %></td>
	<td align="center"><%=student.get(6) %></td>
	<td align="center"><%=student.get(7) %></td>
	<td align="center"><%=student.get(8) %></td>
	<td align="center"><%=student.get(9) %></td>
	<td align="center"><%=student.get(10) %></td>
    </tr>
      <%} %> --%>
    </tbody>
  </table>
</div>
</body>
</html>

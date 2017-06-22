<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ArrayList adminlogin = (ArrayList)session.getAttribute("adminlogin");
boolean closed = false;

ArrayList admin = null;
if(adminlogin != null && adminlogin.size() != 0){
	closed = true;
	admin = array.getAllAdmin(adminlogin.get(0).toString());
}
String message = (String)request.getAttribute("message");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
<link rel="shortcut icon" href="<%=path %>/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/admin-css.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/icon.css">
<style type="text/css">
body{
margin: 0;
padding: 0;}
.gradient{
    width:300px;
    height:150px;
    filter:alpha(opacity=100 finishopacity=50 style=1 startx=0,starty=0,finishx=0,finishy=150) progid:DXImageTransform.Microsoft.gradient(startcolorstr=red,endcolorstr=blue,gradientType=0);	
    -ms-filter:alpha(opacity=100 finishopacity=50 style=1 startx=0,starty=0,finishx=0,finishy=150) progid:DXImageTransform.Microsoft.gradient(startcolorstr=red,endcolorstr=blue,gradientType=0);	
	background:red; /* 一些不支持背景渐变的浏览器 */  
    background:-moz-linear-gradient(top, red, rgba(0, 0, 255, 0.5));  
    background:-webkit-gradient(linear, 0 0, 0 bottom, from(#9999ff), to(#ffffff));  
}
</style>
<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path %>/js/admin.js"  charset="UTF-8"></script>
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
	
	$("#doLogin").click(function(){
		/* var user_id=$("input[name='user_id']").val();
		var pwd=$("input[name='pwd']").val();
		var status=$("input[name='status']").val(); */
		if($("#loginForm").form('validate')){
			$.post(
				 "<%=path %>/AdminLoginServlet",
				 //{"user_id":user_id,"pwd":pwd,"status":status},
				 $("#loginForm").serialize(),
				 function(data){
					 if (data == "1") {
							$.messager.alert('系统消息', '用户名、密码或身份错误', 'error');
					}else if(data == "3"){//辅导员
						location.href = "<%=path %>/counsellor/classSelect.jsp";
					}else{//书记
						location.href = "<%=path %>/admin/allClassInfo.jsp";
					}
				 },
				 "json"
			);
			
		}
	});
	
});


</script>
</head>
<body >
<div id="container" style="width:100%;height: 100%">

<div id="header" style="">
	<h2 style="margin-bottom:0;">学生档案管理系统</h2>
</div>
<!-- 图片块 -->
<div id="left" style="height:420px;width:820px;float:left;padding-top:50px;padding-left: 150px;" >
	<img alt=""  src="<%=path %>/images/gongxinglou.jpg" style="height:400px;width:800px; ">
</div>
<!-- 登录块 -->
<div id="content" class="gradient" style="height:320px;width:280px;float:left;margin-top:80px;padding-left: 20px;padding-top:50px;">
	<br><br>
	<span style="color: blue;">用户登录</span>
	<br><br>
	<form name="loginForm" id="loginForm">
	用户名：<input class="easyui-validatebox" type="text" name="user_id" required="true" validType="pattern" customize="Customize" />
	<br><br>
	&nbsp;&nbsp;密码：<input class="easyui-validatebox" type="password" name="pwd" required="true" validType="pattern" customize="Customize" />
	<br><br>
	&nbsp;&nbsp;身份：<input type=radio name="status" value="辅导员" checked>辅导员
	      	<input type=radio name="status" value="书记">书记
	<br><br>
	<input style="margin-left: 150px;" type="button" value="登录" id="doLogin">
	</form>
</div>
	<div id="footer" style="clear:both;height:12px;text-align:center;vertical-align: center;padding-top: 12px;" >
</div>

</div>
</body>
</html>

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
<%@include file="../all.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/admin-css.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/icon.css">
<style type="text/css">
*{margin:0px; padding: 0px;}
			.div00{background: #fff; width: 100%; height:66px; padding:20px 0px; margin-bottom: 10px;} 
			.div00 img{display: block; float: left; margin-right: 20px;}
			.div00 span{display: block; line-height: 66px; float: left; font-size: 18px; color: #2a4f95;}
			
			.form00{width: 360px; height:300px; background: rgba(200,200,200,0.1); float: right; 
			 margin-top: 70px; border:1px solid #2a4f95;}
			form{width: 250px; height: 180px; margin:60px auto;}
			form img{display: block; float: left;}
			form input[type="text"]{width: 190px; height: 38px; margin-bottom: 20px; display: block; float: left;}
			form input[type='password']{width: 190px; height: 38px; margin-bottom: 20px; display: block; float: left;}
			form input[type='button']{width: 60px; height: 40px;border: 1px solid #000; border-radius: 5px; background: #00a2c8;
			float: right; margin-right: 7px;}
.title {
  font-size: 24px;
  color: #4882b2;
}

.myerror{
	color: red;
	margin-top: 10px;
	margin-left: 50px;
	font-size: 16px;
}
</style>
<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path %>/js/admin.js"  charset="UTF-8"></script>
<script type="text/javascript">
function hasText(a) {
	if (typeof (a) == "undefined") {
		return false;
	}
	if (a == null) {
		return false;
	}
	if (a == "null") {
		return false;
	}
	if (typeof (a) == "string") {
		a = a.replace(/(^\s*)|(\s*$)/g, "");
	}
	if (a === "") {
		return false;
	}
	return true;
}
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
		
		var user_id=$("input[name='user_id']").val();
		var pwd=$("input[name='pwd']").val();
		var status=$("input[name='status']").val();
			
		if(!hasText(user_id)&&!hasText(pwd)){
			$(".myerror").html("用户名、密码不能为空");
			$("input[name='user_id']").focus();
			return false;
		}
		if(!hasText(user_id)){
			$(".myerror").html("用户名不能为空");
			$("input[name='user_id']").focus();
			return false;
		}
		if(!hasText(pwd)){
			$(".myerror").html("密码不能为空");
			$("input[name='pwd']").focus();
			return false;
		}
		
		$.post(
			 "<%=path %>/AdminLoginServlet",
			 $("#loginForm").serialize(),
			 function(data){
				 if (data == "1") {
					 $(".myerror").html("系统消息:用户名、密码或身份错误");
				}else if(data == "2"){
					location.href = "<%=path %>/admin/allClassInfo.jsp";
				}else if(data == "3"){
					location.href = "<%=path %>/counsellor/classSelect.jsp";
				}else if(data == "4"){
					location.href = "<%=path %>/admin/gerenxinxi.jsp";
				}
			 },
			 "json"
		);
				
    });
});
		

</script>
</head>
<body >
	<div class="div00">
		<div style="width: 650px; margin: 0 auto;">
				<img src="<%=path %>/images/logo.png" /><span>计算机学院学生信息管理系统</span>
		</div>
	</div>
	<div style="width: 1200px; height: auto; margin: 0 auto;">
	<div style="float: left;">
		<img src="<%=path %>/images/gongxinglou.jpg" width="800px"/>
	</div>
	<div class="form00">
		<div class="title" style="margin-left: 50px;margin-top: 30px;">用户登录</div>
		<div class="myerror">&nbsp;</div>
		<form  method="post" style="margin-top: 8px;" id="loginForm" name="loginForm">
			<p><img src="<%=path %>/images/icon1.png">
				<input name="user_id" id="user_id" type="text" placeholder="输入用户名" style="padding-left: 10px;height: 42px;"/></p>
			<p><img src="<%=path %>/images/icon2.png">
				<input name="pwd" id="pwd" type="password" placeholder="输入密码" style="padding-left: 10px;height: 42px;"/></p>
			<p>
	      	<input type=radio name="status" style="" style="margin-left: 30px;" value="书记" />书记
			<input type=radio name="status" value="辅导员"  checked />辅导员
	      	<!-- <input type=radio name="status" style="" value="学生" />学生 --></p>
			<input type="button" style="color: white;" id="doLogin" value="登录">
		</form>
	</div>
</div>
</body>
</html>

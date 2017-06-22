<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="array" scope="page" class="bean.AllBean" />
<% 
	String path=request.getContextPath();
%>
<%@include file="../all.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/admin-css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/themes/icon.css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/admin.js"></script>
</head>
<body>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
	resizable="false">
	<div id="north" region="north" split="false" border="false">
		<jsp:include page="/admin/head.jsp"></jsp:include>
	</div>
	<div region="west" split="false" border="false" title="导航菜单"
		style="width:180px;" id="west">
		<div id="left-menus" border="false" fit="true">
			<jsp:include page="/admin/left.jsp"></jsp:include>
		</div>
	</div>
	<div id="londing" style="display: block;margin-top: 10%" align="center">数据加载ing</div>
	<div id="mainPanle" region="center" border="true"
		style="background:#f7f7f7; padding:5px;display: none">
		<table width="100%">
			<thead>
				<tr>
					<td colspan="11" align="center" style="padding:5px;"><h3>学业预警</h3></td>
				</tr>
				<tr>
					<td>
						<form method="post" name="termSelect">
							学年: <select name="schoolYear" class="schoolYear" id="schoolYear"
								onchange="getSchoolYear(this.value);">
								<option>
									<script language="javascript">
                                		document.termSelect.schoolYear.value = "<%=request.getParameter("schoolYear")%>";
                            		</script>
								</option>
								</select> 学期: <select name="term" id="term"
								onchange="getTerm(this.value);">
								</select> 班级: <select name="classno" if="classno"
								onchange="getClass(this.value);">
								</select> 不及格门数: <select name="noQuality"
								onchange="getQuality(this.value);">
								<option>
									<script language="javascript">
		                                document.termSelect.noQuality.value = "<%=request.getParameter("noQuality")%>";
		                            </script>
								</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
							</select>
						</form>
					</td>
				</tr>
		</table>
		<table width="100%">
			<tr class="thead">
				<td align="center">学号</td>
				<td align="center">姓名</td>
				<td align="center">性别</td>
				<td align="center">班级</td>
				<td align="center">学院</td>
				<td align="center">专业</td>
				<td align="center">未通过课程数</td>
			</tr>
			<tbody class="tbody classInfo">
	</tbody>
		</table>
	</div>
</body>
</html>
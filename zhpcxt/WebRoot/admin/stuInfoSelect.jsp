<%@page import="util.MyStringUtils"%>
<%@page import="util.Page"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    boolean closed = false;
    List student = null;
    String keys = request.getParameter("keys")==null?"":(new String(request.getParameter("keys").getBytes("iso-8859-1"), "UTF-8")).trim();
    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
        //student = array.getStudentOfKeys(keys);
    }else{
    	response.sendRedirect("/zhpcxt/admin/index.jsp");
    	return;
    }
    String message = (String) request.getAttribute("message");
%>
<%@include file="../all.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/css/admin-css.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/css/themes/icon.css">
    <script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path%>/js/admin.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('tbody tr:even').css({'background': '#ffffff'});
            $('tbody tr:odd').css({'background': '#eeeeff'});
            var message = <%=message%>;
            if (message != null && message != "") {
                alert(message);
            }
            login('<%=path%>', <%=closed%>);
            $("#click_logout").click(function () {
                logout('<%=path%>');
            });
            $("#editpass").click(function () {
                editpass('<%=path%>');
            });
            $("#admin_add").click(function () {
                adminAdd('<%=path%>');
            });
            $("#course_add").click(function () {
                courseAdd('<%=path%>');
            });
        });
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
      resizable="false">
      <%@include file="../common.jsp" %>
<%-- <div id="north" region="north" split="false" border="false">
    <jsp:include page="/admin/head.jsp"></jsp:include>
</div>
<div region="west" split="false" border="false" title="导航菜单"
     style="width:180px;" id="west">
    <div id="left-menus" border="false" fit="true">
        <jsp:include page="/admin/left.jsp"></jsp:include>
    </div>
</div> --%>
<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;">
    <table width="100%">
        <thead>
        <tr>
            <td colspan="11" align="center" style="padding:5px;"><h3>学生信息查询</h3></td>
        </tr>
        <tr>
	        <td colspan="2"><a href="/zhpcxt/secretary/address.jsp" style="text-decoration:underline;">查看学生地理分布</a></td>
	        
	        <td colspan="10" align="right">
	            <form method="post" name="searchForm" action="/zhpcxt/admin/stuInfoSelect.jsp">
	             <input type="hidden" name="pageIndex">
	 			 <input type="hidden" name="pageSize">
	             <input type="search" name="keys" id="selectKeys" placeholder="请输入学号或者姓名"/>
	             <input type="submit" value="查询" />
	            </form>
	        </td>
        </tr>
        <tr class="thead classInfo">
            <td align="center">学号</td>
            <td align="center">姓名</td>
            <td align="center">性别</td>
            <td align="center" width="150px;">班级</td>
            <td align="center">地址</td>
            <!-- <td align="center">专业</td> -->
            <td align="center">身份证号</td>
            <td align="center">银行卡号</td>
            <td align="center">家长姓名</td>
            <td align="center">家长联系方式</td>
            <td align="center">本人联系方式</td>
            <td align="center">宿舍号</td>
            <td align="center">操作</td>
        </tr>
        </thead>
        <tbody class="tbody classInfo">
        <%
           	Integer pageIndex=Integer.parseInt(request.getParameter("pageIndex")==null||"".equals(request.getParameter("pageIndex"))?"1":request.getParameter("pageIndex").toString());
           	Integer pageSize=Integer.parseInt(request.getParameter("pageSize")==null||"".equals(request.getParameter("pageSize"))?"15":request.getParameter("pageSize").toString());
            Map resMap=array.queryAllStudent(keys, pageIndex, pageSize);
            student = (List)resMap.get("list");		
            Page pages = new Page();	pages.setTotalCount(Integer.parseInt(resMap.get("count")+""));
            pages.setPageIndex(pageIndex); 	pages.setPageSize(pageSize);
            request.setAttribute("page", pages);
           	if (student != null && student.size() != 0) {
                for(Object o:student){
                	Map map=(Map)o;
                	out.println("<tr>");
        			//out.print("<td align='center'>"+(++i)+"></td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("student_id"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("name"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("sex"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("class_name"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("locate"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("IDCard"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("bank_card"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("parent_name"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("parent_phone"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("phone"))+"</td>");
        			out.println("<td align='center'>"+MyStringUtils.null2Str(map.get("room_id"))+"</td>");
        			out.println("<td align=\"center\" style=\"cursor:pointer;\" onclick=\"showSituation('"+path+"',"+MyStringUtils.null2Str(map.get("student_id"))+","+MyStringUtils.null2Str(map.get("class_id"))+");\">查看</td>");
        		    out.println("</tr>");
                }
            }else{
            	out.print("<tr><td colspan='13' align='center'>未找到数据</td></tr>");
            }
        %>
        </tbody>
    </table>
        <%@include file="../page.jsp" %>
</div>
</body>
</html>

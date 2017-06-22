<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- <%@include file="../all.jsp" %> --%>
<style type="text/css">
.table-padding td {padding:5px;}
</style>
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/admin.js"></script>
<form id="from_course_add" method="post">
  <table class="table-padding">
    <tr>
      <td align="right">课程名：</td>
      <td><input  type="text" name="course_name" required="true"  customize="Customize" /></td>
    </tr>
    <tr>
      <td align="right">课程类型：</td>
      <td><input  type="text" name="level" required="true"  customize="Customize" /></td>
    </tr>
    <tr>
      <td align="right">学分：</td>
      <td><input  type="text" name="grades" required="true"  customize="Customize" /></td>
    </tr>
  </table>
</form>

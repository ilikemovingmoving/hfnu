<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="../all.jsp" %>
<style type="text/css">
.table-padding td {padding:5px;}
</style>
<form id="form_systemuser_add" method="post">
  <table class="table-padding">
  <tr>
      <td align="right">职工号：</td>
      <td><input class="easyui-numberbox" type="text" name="user_id" required="true" validType="pattern" customize="Customize" /></td>
    </tr>
    <tr>
      <td align="right">用户名：</td>
      <td><input class="easyui-validatebox" type="text" name="name" required="true" customize="Customize" /></td>
    </tr>
    <tr>
      <td align="right">密码：</td>
      <td><input class="easyui-validatebox" type="password" name="pwd" required="true" validType="pattern" id="systemusers_pwd" customize="Customize" /></td>
    </tr>
    <tr>
      <td align="right">重复密码：</td>
      <td><input class="easyui-validatebox" type="password" name="repeatpwd" required="true" validType="repeat'#systemusers_pwd'" customize="Customize" /></td>
    </tr>
  </table>
</form>

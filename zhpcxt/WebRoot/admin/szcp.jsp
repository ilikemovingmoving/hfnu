<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ArrayList adminlogin = (ArrayList)session.getAttribute("adminlogin");
boolean closed = false;
ArrayList myscore = null;
ArrayList rating = null;
if(adminlogin != null && adminlogin.size() != 0){
	closed = true;
	//myscore = array.getSzcp(adminlogin.get(1).toString());
	rating = array.querySzcp(adminlogin.get(0).toString());

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
        <tr class="thead">
            <td align="center">序号</td>
            <td align="center">姓名</td>
            <td align="center">学年</td>
            <td align="center" colspan="4">道德素养</td>
            <td align="center" colspan="4">学习能力</td>
            <td align="center" colspan="4">体质健康</td>
            <td align="center" colspan="4">素质拓展</td>
            <td align="center" colspan="3">直接加减分</td>
            <td align="center">测评总分</td>
        </tr>
        <tr class="thead">
            <td align="center"></td>
            <td align="center"></td>
            <td align="center"></td>
            <td align="center">基本分</td>
            <td align="center">加分</td>
            <td align="center">减分</td>
            <td align="center">积分</td>
            <td align="center">基本分</td>
            <td align="center">加分</td>
            <td align="center">减分</td>
            <td align="center">积分</td>
            <td align="center">基本分</td>
            <td align="center">加分</td>
            <td align="center">减分</td>
            <td align="center">积分</td>
            <td align="center">基本分</td>
            <td align="center">加分</td>
            <td align="center">减分</td>
            <td align="center">积分</td>
            <td align="center">加分</td>
            <td align="center">减分</td>
            <td align="center">积分</td>
        </tr>
        </thead>
        <tbody class="tbody">
        <%
            if (rating != null && rating.size() != 0) {
                for (int i = 0; i < rating.size(); i++) {
                    ArrayList alRow = (ArrayList) rating.get(i);
                    
        %>
        <tr>
            <td align="center"><%=i + 1 %>
            </td>
            <%for(int j=1;j < alRow.size();j++){ %>
            <td align="center"><%=alRow.get(j)==null?"0":alRow.get(j) %>
            </td>
            <%} %>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
   <form action="<%=path%>/Shuchu" method="post"> 
<!--    <input  type=text value=123/> -->
   </form>
</div>
</body>
</html>

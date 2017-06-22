<%@page import="util.MyStringUtils"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    boolean closed = false;
    List student = null;
    String class_id = (String) session.getAttribute("class_id");
    session.setAttribute("class_id", class_id);
    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
        student = array.queryClassStudent(class_id);

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
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/admin-css.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/icon.css">
    <script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path %>/js/admin.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('tbody tr:even').css({'background': '#ffffff'});
            $('tbody tr:odd').css({'background': '#eeeeff'});
            var message = <%=message %>;
            if (message != null && message != "") {
                alert(message);
                location.href="query.action";
            }
            login('<%=path %>', <%=closed %>);
            $("#click_logout").click(function () {
                logout('<%=path %>');
            });
            $("#editpass").click(function () {
                editpass('<%=path %>');
            });
            $("#admin_add").click(function () {
                adminAdd('<%=path %>');
            });
        });

        /* function display_alert()
         {
         var lujing=document.form.fileUpload.value;
         alert("路径="+lujing)
         } */
         $(function(){
	         $("#allselected").click(function() { if ($(this).attr("checked") == true) { // 全选
	        	 $("input[name='studentId']").each(function() { $(this).attr("checked", true); }); } else { // 取消全选
	        		 $("input[name='studentId']").each(function() { $(this).attr("checked", false); }); } }); 
        	 
         var studentIdList='';
  		 $("#del").click(function(){  
  			 //alert();
  			 $("input[name='studentId']:checkbox:checked").each(function(){ 
  				 studentIdList+=$(this).val()+",";  
  		 	});
  			 if(studentIdList==''){
  				 alert("请选择");
  				 return;
  			 }
  			 
  			$.post(
 					 "student_del.action",
 					 {"studentIdList":studentIdList.substring(0,studentIdList.length-1)},
 					 function(data){
 						 alert(data.retMsg);
 						 if(data.retCode==1){
 							 location.href="query.action";
 						 }
 					 },"json");
  			 
         });

  		 });
    </script>
</head>
<body class="easyui-layout"  >
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

        </tr>
        <tr>
            <td><p>上传班级学生信息:</p></td>
            <td>
                <form action="student_upload.action" method="post" enctype="multipart/form-data" name="uploadForm" >
                    <input type="file" name="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                    <input type=submit value="上传文件"/>
                </form>
            </td>
            <td>
                <button onclick="window.open('<%=path%>/demo-xls/demo2.xls')">下载模板</button>
            </td>
           <!--  <td colspan="14">&nbsp;</td> -->
        </tr>
        <%
            //}
        %></thead>
        </table>
        <table width="100%">
        <thead>
        <tr>
            <td colspan="18" align="center" style="padding:5px;"><h3>班级学生信息</h3></td>
        </tr>
        <tr>
            <td colspan="18" align="left" style="padding:5px;"><input type="button" id="del" value="删除"></td>
        </tr>
        <tr class="thead">
            <td align="center">序号<input type="checkbox" value="全选" id="allselected"></td>
            <td align="center">学号</td>
            <td align="center">姓名</td>
            <td align="center">性别</td>
            <td align="center">家庭地址</td>
            <!-- <td align="center">专业</td> -->
            <td align="center">身份证号</td>
            <td align="center">银行卡号</td>
            <td align="center">家长姓名</td>
            <td align="center">家长联系方式</td>
            <td align="center">本人联系方式</td>
            <td align="center">宿舍号</td>
            <!-- <td align="center">学业成绩</td>
            <td align="center">奖励</td>
            <td align="center">违纪</td>
            <td align="center">惩罚</td>
            <td align="center">备注</td> -->
            <td align="center">操作</td>

        </tr>
        </thead>
        <tbody class="tbody">
        <%
        	if(student!=null && student.size()>0){
        		int i=0;
        		for(Object o:student){
        			Map map=(Map)o;
        			out.print("<tr>");

        			out.print("<td align='center'>"+(++i)+"<input type='checkbox' name='studentId' value='"+MyStringUtils.null2Str(map.get("student_id"))+"'></td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("student_id"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("name"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("sex"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("locate"))+"</td>");
        			/* out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("pro"))+"</td>"); */
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("IDCard"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("bank_card"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("parent_name"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("parent_phone"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("phone"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("room_id"))+"</td>");
        			/* out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("learning_status"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("reward"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("breach"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("punish"))+"</td>");
        			out.print("<td align='center'>"+MyStringUtils.null2Str(map.get("remark"))+"</td>");
        			out.print("<td align='center'></td>");*/
       			 out.println("<td align=\"center\" style=\"cursor:pointer;\" onclick=\"showSituation('"+path+"',"+MyStringUtils.null2Str(map.get("student_id"))+","+MyStringUtils.null2Str(map.get("class_id"))+");\">查看</td>");
        			out.print("</tr>");
        		;
        		}
        	}
        	
        %>
        </tbody>
    </table>

</div>
</body>
</html>

<%@page import="util.Page"%>
<%@page import="org.jfree.chart.servlet.ServletUtilities"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%-- <%@page errorPage="/error.jsp" %> --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    boolean closed = false;
    ArrayList student = null;
    int quality = 0;
    ArrayList classSchoolYear = null;
    ArrayList getAllclassId = null;
    //long index = Long.parseLong(request.getParameter("index")==null||"".equals(request.getParameter("index"))?"1":request.getParameter("index"));
    String grade = request.getParameter("grade");
    String classPro = request.getParameter("pro");
    String cls = request.getParameter("class");
    String schoolYear1 = request.getParameter("schoolYear");
    String term1 = request.getParameter("term");
    String noQuality =  request.getParameter("noQuality");
    String classno =  request.getParameter("classno");
    
    grade=(grade == null || grade == "")?null:(new String(grade.getBytes("iso-8859-1"), "UTF-8")).trim();
    classPro=(classPro == null || classPro == "")?null:(new String(classPro.getBytes("iso-8859-1"), "UTF-8")).trim();
    request.setAttribute("pro", classPro);
    cls=(cls == null || cls == "")?null:(new String(cls.getBytes("iso-8859-1"), "UTF-8")).trim();
    request.setAttribute("cls", cls);
    term1=(term1 == null || term1 == "")?null:(new String(term1.getBytes("iso-8859-1"), "UTF-8")).trim();
    noQuality=(noQuality == null || noQuality == "")?"4":(new String(noQuality.getBytes("iso-8859-1"), "UTF-8")).trim();
    classno=(classno == null || classno == "")?null:(new String(classno.getBytes("iso-8859-1"), "UTF-8")).trim();
    schoolYear1=(schoolYear1 == null || schoolYear1 == "")?null:(new String(schoolYear1.getBytes("iso-8859-1"), "UTF-8")).trim();
    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
        student = array.getStudent();
        if (student != null && student.size() != 0) {
        	getAllclassId=array.getAllclassId();
        }
    }else{
    	response.sendRedirect("index.jsp");
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
    <script type="text/javascript" src="<%=path%>/js/all.js"></script>
   <%--  <script type="text/javascript" src="<%=path%>/js/stuWarning.js"></script> --%>
    <script type="text/javascript">
        $(document).ready(function () {
        	//alert("${param.classno}");
        	var schoolYear=$("select[name='schoolYear']").val();
        	//schoolYear=schoolYear==null?"":schoolYear;
        	var term=$("select[name='term']").val();
        	//term=term==null?"":term;
        	var classno=$("select[name='classno']").val();
        	//classno=classno==null?"":classno;
        	//$("#view").attr("href","pieChart.jsp?schoolYear="+schoolYear+"&term="+term+"&classno="+classno);
        	
        	$("#view").click(function(){
        		if(schoolYear==null || schoolYear==''){
        			alert("请选择学年以后查看");
        			return;
        		}
        		location.href="pieChart.jsp?schoolYear="+schoolYear+"&term="+term+"&classno="+classno;
        	});
        	
            $("#londing").css('display', 'none');
            $("#mainPanle").css('display', '');
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
            
            $("#export").click(function(){
            	location.href="export_stuWarning.action?grade="+$("#grade2").val()+
            			"&pro="+$("#pro2").val()+"&cls="+$("#class2").val();
            	/* $.post(
            			"export_stuWarning.action",
            			{"grade":$("#grade2").val(),"pro":$("#pro2").val(),"cls":$("#class2").val()},
            			function(data){
            				
            			}); */
            });
        });
        
        function submitForm() {
            document.searchForm.submit();
        }
        <%--function showTheCourse (student_id,class_id){--%>
        <%--showTheCourseJS('<%=path%>',student_id,class_id);--%>
        <%--}--%>
        
        
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
      resizable="false">
<!-- 页面上，左部分 -->
<%@include file="../common.jsp" %>

<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;display: none">
     <!-- <div style="height: 480px;"> -->
    <table width="100%">
        <thead>
        <tr>
            <td colspan="11" align="center" style="padding:5px;"><h3>学业预警</h3></td>
        </tr>
        <tr>
            <td>
                <form method="post" name="searchForm" action="/zhpcxt/admin/stuWarning.jsp">
                <input type="hidden" name="index" value="${param.index }">
                <input type="hidden" name="pageIndex">
				<input type="hidden" name="pageSize">
				<select id="classGrade" name="grade" onchange="getGrade(this.value);">
                        <option value="" selected="selected" >年级</option>
                    </select>
				<select id="classPro" name="pro" onchange="getPro(this.value);">
                        <option value="" selected="selected">专业</option>
                    </select>
				<select id="class" name="class" onchange="getClass(this.value);">
                        <option value="" selected="selected">班级</option>
                    </select>
                    <input type="hidden" id="grade2" value="${param.grade }">
	<input type="hidden" id="pro2" value="${requestScope.pro }">
	<input type="hidden" id="class2" value="${requestScope.cls }">
	<input type="button" value="导出学业预警名单" id="export">
                 </form>
            </td>
        </tr>
    </table>
    <table width="100%">
        <tr class="thead">
            <td align="center">序号</td>
            <td align="center">学号</td>
            <td align="center">姓名</td>
            <td align="center">性别</td>
            <td align="center">班级</td>
            <!-- <td align="center">学院</td>
            <td align="center">专业</td> -->
            <td align="center">未通过课程数</td>
        </tr>
        <tbody class="tbody classInfo">
        <%
        //int sum[]={};
        
	        if (student != null && student.size() != 0) {
	        	//sum=new int[student.size()];
	        	 Map param=new HashMap();
               	param.put("tableName",classno);
               	param.put("grade",grade);
               	param.put("pro",classPro);
               	param.put("class",cls);
               	param.put("noQuality",noQuality);
               	//param.put("schoolYear",schoolYear1);
               	//param.put("term",term1);
               	//param.put("noQuality", noQuality);
                 List scores=array.queryStuWarning2(param);
                 Page pageModel=new Page();
                 Integer pageIndex=Integer.parseInt(request.getParameter("pageIndex")==null?"1":("".equals(request.getParameter("pageIndex"))?"1":request.getParameter("pageIndex")));
                 Integer pageSize=Integer.parseInt(request.getParameter("pageSize")==null?"15":("".equals(request.getParameter("pageSize"))?"15":request.getParameter("pageSize")));
                 pageModel.setTotalCount(scores.size());pageModel.setPageIndex(pageIndex);pageModel.setPageSize(pageSize);
                 request.setAttribute("page", pageModel);
                 if((scores.size()-(pageIndex-1)*pageSize)>pageSize){
	                 scores=scores.subList((pageIndex-1)*pageSize,pageIndex*pageSize);
                 }else{
                	 scores=scores.subList((pageIndex-1)*pageSize,scores.size());
                 }
               long index=(pageIndex-1)*pageSize + 1;
               if(scores.size()>0){
                 for(Object o:scores){
                	 Map map=(Map)o;
                	 String studentId=map.get("student_id")==null?"":map.get("student_id").toString();
                	 String name=map.get("name")==null?"":map.get("name").toString();
                	 String sex=map.get("sex")==null?"":map.get("sex").toString();
                	 String classId=map.get("class_id")==null?"":map.get("class_id").toString();
                	 String className=map.get("class_name")==null?"":map.get("class_name").toString();
                	 String dept=map.get("dept")==null?"":map.get("dept").toString();
                	 String pro=map.get("pro")==null?"":map.get("pro").toString();
                	 String sum_60=map.get("sum_60")==null?"0":map.get("sum_60").toString();
                	 //if(Long.parseLong(sum_60) >= Long.parseLong(noQuality)){
	                	 out.println("<tr>");
	                	 out.println("<td align=\"center\">"+(index++)+" </td>");
	                	 out.println("<td align=\"center\">"+studentId+" </td>");
	                	 out.println("<td align=\"center\">"+name+" </td>");
	                	 out.println("<td align=\"center\">"+sex+" </td>");
	                	 out.println("<td align=\"center\">"+className+" </td>");
	                	 /* out.println("<td align=\"center\">"+dept+" </td>");
	                	 out.println("<td align=\"center\">"+pro+" </td>"); */
	                	 out.println("<td align=\"center\" style=\"cursor:pointer\" onclick=\"showTheCourseJS('"+path+"',"+studentId+","+classId+");\">"+sum_60+" </td>");
	                	 out.println("</tr>");
                	 //}
                 }
               }else{
                 	out.println("<td colspan='8' align=\"center\"> 未找到数据 </td>");
               }
               
               //out.close();
	        }
        %>
        </tbody>
    </table>
     <%@include file="../page.jsp" %>
    
</div>
</body>
</html>

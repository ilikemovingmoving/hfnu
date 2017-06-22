<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@include file="../all.jsp" %>
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
    $(function(){
    	//$("#courseAdd").click(courseAdd());
    });
	</script>
    
      <input type="checkbox" style="margin-left:20px;" name="allDel" onclick="delAll();">取消
      <input type="button" name="downloadBtn" id="downloadBtn" onclick="download();" value="下载">
      <a href="javascript:;" style="margin-left: 100px;padding-right: 0px;" onclick="courseAdd('<%=path%>');">添加课程</a><br/><br/><br/>
      
      <form action="/zhpcxt/servlet/counsellor/ExportExcel" method="post" name="downloadForm" >
      <%
		List list=array.queryCourse(new HashMap());
		//System.out.println(JSONObject.fromObject(list));
		for(Object o:list){
			if(o!=null){
				Map map=(Map)o;
				String courseName=map.get("course_name")==null?"":map.get("course_name").toString();
				//Integer id=map.get("id")==null?:Integer.parseInt(map.get("id").toString());
				String id=map.get("id")==null?"":map.get("id").toString();
				out.println("<input style='margin-left:20px;' type='checkbox' name='course' value='"+id+"' >"+courseName+"<br/>");
			}
		}
      %>
      </form>
      
	<script type="text/javascript">
	
	function download(){
		document.downloadForm.submit();
	}
	
	$(function(){
	
		
	});
	function delAll(){
		$("input:checkbox[name='course']").each(function() {
		    $(this).attr('checked', false);
		});
	}
	
	
	
	
	</script>

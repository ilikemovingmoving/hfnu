<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="util.DataFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    ArrayList adminlogin = (ArrayList) session
            .getAttribute("adminlogin");
    boolean closed = false;
    String schoolYear1 = request.getParameter("schoolYear");
    String term1 = request.getParameter("term");
    String classno =  request.getParameter("classno");
    term1=(term1 == null || term1 == "")?null:(new String(term1.getBytes("iso-8859-1"), "UTF-8")).trim();
    classno=(classno == null || classno == "")?null:(new String(classno.getBytes("iso-8859-1"), "UTF-8")).trim();
    schoolYear1=(schoolYear1 == null || schoolYear1 == "")?null:(new String(schoolYear1.getBytes("iso-8859-1"), "UTF-8")).trim();

    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
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
        });

    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
      resizable="false">
<%@include file="../common.jsp" %>

<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;">
     <%
     int[] sum={};
     Map param=new HashMap();
    	param.put("schoolYear",schoolYear1);
    	param.put("term",term1);
    	param.put("tableName",classno);
      List scores=array.queryStuWarning2(param);
      String title="";
      if(schoolYear1 !=null && !"".equals(schoolYear1)){
    	  title += schoolYear1+"学年";
    	  if(term1!=null && !"".equals(term1)){
    		  //System.out.println(term1);
    		  title += "第"+term1 + "学期";
    	  }
    	  title+="计算机学院";
    	  if(classno!=null && !"".equals(classno)){
    		  Map param2=new HashMap();
    		  param2.put("classno",classno);
    		  Map res=array.queryClass(param2);
    		  title += res.get("className") + "";
    	  }
      }
      title+="学业成绩分析（未通过）";
      sum=new int[scores.size()];
      int i=0;
      for(Object o:scores){
    	 Map map=(Map)o;
    	 String sum_60=map.get("sum_60")==null?"":map.get("sum_60").toString();
    	 sum[i++]=Integer.parseInt(sum_60);
     }
     %>
    <div  class="imgChart"  align="center">
        <img src="/zhpcxt/servlet/PieChartServlet?sumStr=<%=StringUtils.join(DataFormat.toIntegerArray(sum), ",")%>&title=<%=title%>"/></div>
       </div>
</div>
</body>
</html>

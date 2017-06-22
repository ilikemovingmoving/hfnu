<%@page import="util.MyStringUtils"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    boolean closed = false;
    ArrayList allClass = null;
    ArrayList getBjofPro = null;
    ArrayList getBjofPro1 = null;
    ArrayList getBjofGrade = null;
    ArrayList getBjofGrade1 = null;
    String classMgr = null;
    String classPro = (String) request.getParameter("classPro");
    String classGrade = (String) request.getParameter("classGrade");
    classPro = classPro==null?null:(new String(classPro.getBytes("iso-8859-1"), "UTF-8")).trim();
    classGrade = classGrade==null?null:(new String(classGrade.getBytes("iso-8859-1"), "UTF-8")).trim();
    Map param=new HashMap();
    param.put("classPro",classPro==null?"":classPro);
    param.put("classGrade",classGrade==null?"":classGrade);
    Map res = array.queryClass(param);
    
    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
        getBjofPro1 = array.getBjofPro();
        getBjofGrade1 = array.getBjofGrade();
        //allClass = array.getAllBj();
        /* if (classPro != null) {
            getBjofPro = array.getBjofPro(classPro);
        }
        if (classGrade != null) {
            getBjofGrade = array.getBjofGrade(classGrade);
        } */
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
            $("#class_add").click(function () {
                classAdd('<%=path %>');
            });
            $("#course_add").click(function () {
                courseAdd('<%=path %>');
            });
        })

        function getPro(pro) {
            <%-- document.classSelect.action = "<%=basePath%>admin/allClassInfo.jsp?pro="
            + pro; --%>
            document.classSelect.submit();

        }
        function getGrade(grade) {
            document.classSelect.action = "<%=basePath%>admin/allClassInfo.jsp?grade="
            + grade;
            document.classSelect.submit();

        }
        
        function getGrade2(){
        	
        	$.post(
        	"/zhpcxt/AllClassInfoServlet",
        	{},
        	function(data){
        		var arr=data.gradeList;
       			$("#classGrade").html("");
       			$("#classGrade").append("<option selected='selected' value=''>年级</option>");
        		$.each(arr,function(i,a){
        			$("#classGrade").append("<option value='"+a+"'>"+a+"</option>");
        		});
        	},
        	"json"
        	);
        }
        
        $(function(){
        	//加载班级
        	getGrade2();
        });
    </script>
</head>
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
<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;">
    <form method="post" name="classSelect" action="allClassInfo.jsp">
        <table width="100%">
            <thead>
            <tr>
                <td colspan="11" align="center" style="padding:5px;"><h3>计算机学院班级</h3></td>
            </tr>
            <tr>
                <td colspan="4" style="padding:5px;"><a href="javascript:void(0)" id="class_add">新建班级</a></td>
            </tr>
            <%--<tr>--%>
            <%--<td colspan="4" style="padding:5px;"><a href="javascript:void(0)" id="course_add">新建课程</a></td>--%>
            <%--</tr>--%>
            <tr class="thead">
                <td align="center">编号</td>
                <td align="center">
                    <select id="classGrade" name="classGrade" onchange="getGrade(this.value);">
                        <option selected="selected" value="">年级</option>
                    </select>
                </td>
                <td align="center">院系</td>
                <td align="center">
                    <select name="classPro" onchange="getPro(this.value);">
                        <option selected="selected" value="" style="text-align:center">专业</option>
                        <%
                            for (int j = 0; j < getBjofPro1.size(); j++) {
                                ArrayList getClassPro = (ArrayList) getBjofPro1.get(j);
                        %>
                        <option value=<%=getClassPro.get(0)%>><%=getClassPro.get(0)%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </td>
                <td align="center">班级</td>
                <td align="center">辅导员</td>
                <td align="center">操作</td>
            </tr>
            </thead>

            <tbody class="tbody">
            <%
            	if(res!=null){
            		List list=(List)res.get("list");
            		if(list!=null && list.size()>0){
            			int i=0;
            			for(Object o:list){
            				Map item=(Map)o;
            				out.println("<tr>");
            				out.println("<td align='center'>"+(++i)+"</td>");
            				out.println("<td align='center'>"+StringUtils.trimToEmpty(item.get("grade")+"")+"</td>");
            				out.println("<td align='center'>"+StringUtils.trimToEmpty(item.get("dept")+"")+"</td>");
            				out.println("<td align='center'>"+StringUtils.trimToEmpty(item.get("pro")+"")+"</td>");
            				out.println("<td align='center'>"+StringUtils.trimToEmpty(item.get("class")+"")+"</td>");
            				out.println("<td align='center'>"+MyStringUtils.null2Str(item.get("mgr")) +"</td>");
            				out.println("<td align='center'>"
            				+"<a style='text-decoration:underline;' href='/zhpcxt/admin/classStuInfo.jsp?class_id="
            				+StringUtils.trimToEmpty(item.get("class_id")+"")+"'>班级学生</a>&nbsp;"
            				+"<a style='text-decoration:underline;' href='/zhpcxt/secretary/ratingSco.jsp?class_id="
            				+StringUtils.trimToEmpty(item.get("class_id")+"")+"'>测评分数</a>&nbsp;"
            				+"<a style='text-decoration:underline;' href='/zhpcxt/admin/classAllQualified.jsp?class_id="
            				+StringUtils.trimToEmpty(item.get("class_id")+"")+"'>及格率</a></td>");
            				out.println("</tr>");
            			}
            		}
            	}
            
            %>
            </tbody>

        </table>
    </form>
</div>
</body>
</html>

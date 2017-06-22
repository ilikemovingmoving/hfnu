<%@page import="util.MyStringUtils"%>
<%@page import="javax.swing.text.StyledEditorKit.ForegroundAction"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%-- <%@page errorPage="/error.jsp" %> --%>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    if(adminlogin==null || adminlogin.size()==0){
    	response.sendRedirect("../admin/index.jsp");	
    	return;
    }
    boolean closed = false;
    ArrayList course = null;
    ArrayList classSchoolYear = null;
    ArrayList classTerm = null;
    ArrayList getScoreOfSchYear1 = null;
    ArrayList getScoreOfTerm1 = null;
    String class_id = (String)session.getAttribute("class_id");//==null?"":(String)session.getAttribute("class_id");
    String schoolYear1 = request.getParameter("schoolYear");
    String term1 = request.getParameter("term");
    List list=null;
    List list2=null;
    //List list3=null;
    Map param=null;
    if(schoolYear1!=null){
	    param=new HashMap(); param.put("tableName",class_id );param.put("schoolYear",schoolYear1);param.put("term",term1);
	    list=array.queryScore(param);//主要为课名作表头
	    list2=array.queryScore2(param);//为表数据前面一部分
	    //list3=array.queryScore3(param);//为各科分数
    }
    schoolYear1=(schoolYear1 == null || schoolYear1 == "")?null:(new String(schoolYear1.getBytes("iso-8859-1"), "UTF-8")).trim();
    term1=(term1 == null || term1 == "")?null:(new String(term1.getBytes("iso-8859-1"), "UTF-8")).trim();

    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
        classSchoolYear = array.getSchYear(class_id);
        classTerm = array.getTerm(class_id);
        if (schoolYear1 != null) {
            getScoreOfSchYear1 = array.getStudentId(class_id, schoolYear1);
        }
        if (term1 != null) {
            getScoreOfTerm1 = array.getStudentId(class_id, schoolYear1, term1);
        }
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
        $(window).load(function () {
        	
            $("#londing").css('display', 'none');
            $("#mainPanle").css('display', '');
            $('tbody tr:even').css({'background': '#ffffff'});
            $('tbody tr:odd').css({'background': '#eeeeff'});
            
            if("${not empty message }"=="true"){
            	alert("${message}");
            }
            /* if (message != null && message != "") {
                alert(${message});
            } */
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
        })
        function getSchoolYear(schoolYear) {
            var cSchoolYear = schoolYear;
            document.termSelect.action = "<%=basePath%>counsellor/classScore.jsp?schoolYear="
            + cSchoolYear;
            document.termSelect.submit();

        }
        function getTerm(term) {
            var cTerm = term;
            document.termSelect.action = "<%=basePath%>counsellor/classScore.jsp?term="
            + cTerm;
            document.termSelect.submit();

        }
        function queryform() {
        	var term=$("#term").val();
        	var schoolYear=$("#schoolYear").val();
        	if(schoolYear==null || ""==schoolYear){
        		alert("请选择学年、学期");
        		return;
        	}
            //document.termSelect.action = "<%-- <%=basePath%> --%>//counsellor/classScore.jsp?schoolYear=" 
            	//	+ schoolYear + "&term=" + term;
            document.termSelect.submit();

        }
        
        function getSchoolYear2(){
        	$.post(
            		"/zhpcxt/ClassScoreServlet",
            		{},
            		function(data){
            			var arr=data.list;
               			$("#schoolYear").html("");
               			//$("#schoolYear").val("${param.schoolYear}");
               			document.termSelect.schoolYear.value = "<%=request.getParameter("schoolYear")%>";
               			$("#schoolYear").append("<option value=''></option>");
                		$.each(arr,function(i,a){
                			$("#schoolYear").append("<option value='"+a+"' >"+a+"</option>");
                		});
            		},
            		"json"
            	);
        }
        
        $(function(){
        	//getSchoolYear2();
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
<div id="londing" style="display: block;margin-top: 10%" align="center">数据加载ing</div>
<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;display: none;">
    <table>
        <thead>
        <tr>
            <td><p>上传成绩:</p></td>
            <td>
                <%-- <form action="<%=path%>/Upload" method="post" > --%>
                <form action="<%=path%>/counsellor/UploadScoreServlet" method="post" enctype="multipart/form-data" name="uploadScoreForm">
                    <select name="schoolYear">
                        <option>-请选择学年-</option>
                        <option value="2012-2013">2012-2013</option>
                        <option value="2013-2014">2013-2014</option>
                        <option value="2014-2015">2014-2015</option>
                        <option value="2015-2016">2015-2016</option>
                        <option value="2016-2017">2016-2017</option>
                        <option value="2017-2018">2017-2018</option>
                        <option value="2018-2019">2018-2019</option>
                        <option value="2019-2020">2019-2020</option>
                    </select>
                    <select name="term">
                        <option>-请选择学期-</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                    <input type="file" name="fileUpload2"/>
                    <input type=submit value="上传文件"/>
                </form>
            </td>
            <td>
                <%-- <button style="margin-left: 300px;" onclick="window.open('<%=path%>/demo-xls/class-score-demo.xls')">下载成绩模板</button>
 --%>            <button style="margin-left:300px;" onclick="showAllCourse('<%=path%>');">下载模板</button>
 </td>
        </tr>

        </thead>
    </table>

    <table>
        <thead>
        <tr>
            <td><p>学年:</p></td>
            <td>
                <form action="<%=path%>/counsellor/classScore.jsp" method="post" name="termSelect">
                    <select id="schoolYear" name="schoolYear" class="schoolYear" onchange="">
                        <option>
                            <script language="javascript">
                                document.termSelect.schoolYear.value = "<%=request.getParameter("schoolYear")%>";
                            </script>
                        </option>
                        <%
                            for (int j = 0; j < classSchoolYear.size(); j++) {
                                ArrayList getSchoolYear = (ArrayList) classSchoolYear.get(j);
                        %>
                        <option><%=getSchoolYear.get(0)%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    学期:
                    <select id="term" name="term" onchange="">
                        <option>
                            <script language="javascript">
                                document.termSelect.term.value = "<%=request.getParameter("term")%>";
                            </script>
                        </option>
                        <%
                            //if (schoolYear1 != null) {
                                for (int j = 0; j < classTerm.size(); j++) {
                                    ArrayList getTerm = (ArrayList) classTerm.get(j);
                        %>
                        <option value=<%=getTerm.get(0)%>><%=getTerm.get(0)%>
                        </option>
                        <%
                                }
                           // }
                        %>
                    </select>
                    <input type="button" value="查询" onclick="queryform();">
                </form>
            </td>
        </tr>

        </thead>
    </table>
    <form method="post">
        <table width="100%">
            <thead>
            <tr>
                <td colspan="11" align="center" style="padding:5px;"><h3>班级学生成绩</h3></td>
            </tr>
            <tr class="thead">
                <td align="center">序号</td>
                <td align="center">学号</td>
                <td align="center" width="100px">姓名</td>
                <td align="center">获得学分</td>
                <td align="center" width="100px">学年
                </td>
                <td align="center">学期
                </td>
                <%
				if(list!=null && list.size()>0){
                    for(Object o:list){ 
                    	Map map=(Map)o;
                %>
                <td align="center"><%=map.get("distinct_course") %><%-- <%=courseRealName.get(0)%> --%>
                </td>
                <%
                    }
				}
                %>
            </tr>
            </thead>

            <tbody class="tbody">
            <% 
            int i=1;
            if(list2!=null && list2.size()>0){
            	for(Object o:list2){
            		Map map=(Map)o;
            %>
            	<tr>
            		<td align="center"><%=i++ %></td>
            		<td align="center"><%=map.get("class_student_id") %></td>
            		<td align="center"><%=MyStringUtils.null2Str(map.get("name")) %></td>
            		<td align="center"><%=map.get("school_grades") %></td>
            		<td align="center"><%=map.get("school_year") %></td>
            		<td align="center"><%=map.get("term") %></td>
            	<%
            	param.put("studentId", map.get("class_student_id"));
            	List list3=array.queryScore3(param);
            	if(list3!=null && list3.size()>0){ 
            		for(Object o3:list3){
            			Map map3=(Map)o3;
            	%>
            		<td align="center"><%=map3.get("score") %></td>
            	<%} }
            	%>
            	</tr>
           <% }} %>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>

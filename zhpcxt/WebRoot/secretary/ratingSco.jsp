<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@page errorPage="/error.jsp" %> --%>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    boolean closed = false;
    ArrayList rating = null;
    List ratingSchoolYearList = null;
    String class_id = request.getParameter("class_id");//(String) session.getAttribute("class_id");//辅导员用
    Map param=new HashMap();
    param.put("classno",class_id);
    Map res = array.queryClass(param);
    String className=res.get("className")+"";
    /* if (class_id == null) {//书记用
        class_id = request.getParameter("class_id");
    } */
    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
        String schoolYear=request.getParameter("schoolYear");
        if(schoolYear!=null && !"".equals(schoolYear)){
	        rating = array.getEvaluating2(class_id,schoolYear);
        }
        ratingSchoolYearList = array.getSchYearOfEvaluating(class_id);//evaluating
        
        
    }else{
    	response.sendRedirect("/zhpcxt/admin/index.jsp");
    	return;
    }
    //String message = (String) request.getAttribute("message");
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
            
            if ("${not empty message}"=="true") {
                alert("${message}");
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
        })

        function queryEvaluating(schoolYear){
        	location.href="<%=path%>/secretary/ratingSco.jsp?schoolYear="
        			+schoolYear+"&class_id="+<%=class_id%>;
        }
        
        /* function display_alert()
         {
         var lujing=document.form.fileUpload.value;
         alert("路径="+lujing)
         } */
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no" resizable="false">
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
            <%
            //            if (student == null || student.size() == 0) {
        %>
        <%-- <tr>
            <td>上传班级学生综合测评信息:</td>
            <td>
                <form action="<%=path%>/counsellor/UploadRatingServlet"  enctype="multipart/form-data"  method="post">
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
                    <input type="file" name="rating"/>
                    <input type=submit value="上传文件"/>
                </form>
            </td>
            <td>
                <button onclick="window.open('<%=path%>/demo-xls/evaluating.xls')">下载成绩模板</button>
            </td>
        </tr> --%>
        <tr>
            <td colspan="19" align="center" style="padding:5px;"><h3><%=className %>学生素质测评成绩</h3></td>
        </tr>
    </table>
		<table>
			<thead>
				<tr>
					<td><p>学年:</p></td>
					<td>
						<form action="<%=path%>/counsellor/ratingScore.jsp" method="post"
							name="selectForm">
							<select id="schoolYear" name="schoolYear" class="schoolYear"
								onchange="queryEvaluating(this.value);">
								<option>
									<script language="javascript">
                      document.termSelect.schoolYear.value = "<%=request.getParameter("schoolYear")%>";
									</script>
								</option>
								<%
								if(ratingSchoolYearList !=null && ratingSchoolYearList.size()>0){
									for (int j = 0; j < ratingSchoolYearList.size(); j++) {
										ArrayList getSchoolYear = (ArrayList) ratingSchoolYearList.get(j);
								%>
								<option><%=getSchoolYear.get(0)%>
								</option>
								<%
									}}
								%>
							</select>
							
							
							<%-- <input type="hidden" name="class_id" value="<%=class_id%>"> --%>
						</form>
					</td>
				</tr>
			</thead>
		</table>
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
            <td align="center"></td>
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

</div>
</body>
</html>

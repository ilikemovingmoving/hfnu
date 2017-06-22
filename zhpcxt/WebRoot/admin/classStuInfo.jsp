<%@page import="util.MyStringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<jsp:useBean id="array" scope="page" class="bean.AllBean"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    boolean closed = false;
    ArrayList student = null;
    String class_id = (String) request.getParameter("class_id");
    if (class_id == null) {
        class_id = (String) session.getAttribute("class_id");
    }
    session.setAttribute("class_id", class_id);
    ArrayList course = null;
    ArrayList classSchoolYear = null;
    ArrayList classTerm = null;
    ArrayList getScoreOfSchYear1 = null;
    ArrayList getScoreOfTerm1 = null;
    String schoolYear1 = (String) request.getParameter("schoolYear");
    String term1 = (String) request.getParameter("term");
    schoolYear1=(schoolYear1 == null || schoolYear1 == "")?null:(new String(schoolYear1.getBytes("iso-8859-1"), "UTF-8")).trim();
    term1=(term1 == null || term1 == "")?null:(new String(term1.getBytes("iso-8859-1"), "UTF-8")).trim();

    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
        student = array.getClassStudent2(class_id);
        classSchoolYear = array.getSchYear(class_id);
        classTerm = array.getTerm(class_id);
        if (schoolYear1 != null) {
            getScoreOfSchYear1 = array.getStudentId(class_id, schoolYear1);
        }
        if (term1 != null) {
            getScoreOfTerm1 = array.getStudentId(class_id, schoolYear1, term1);
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
    <script type="text/javascript">
        $(document).ready(function () {
            $("#londing").css('display', 'none');
            $("#mainPanle").css('display', '');
            <%
            if(schoolYear1 != null){
            %>
            $('#classScore')[0].style.color = 'blue';
            $('#classInfo')[0].style.color = '';
            $('.classInfo')[0].style.display = 'none';
            $('.classScore')[0].style.display = '';
            $('.classInfo1')[0].style.display = 'none';
            $('.classScore1')[0].style.display = '';
            <%
            } else {
            %>
            $('#classInfo')[0].style.color = 'blue';
            $('.classInfo')[0].style.display = '';
            $('.classInfo1')[0].style.display = '';
            <%
            }
            %>
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
            $("#change_cmgr").click(function () {
                changeCmgr('<%=path%>');
            });
            $("#classInfo").click(function () {
                $('#classInfo')[0].style.color = 'blue';
                $('#classScore')[0].style.color = '';
                $('.classScore')[0].style.display = 'none';
                $('.classInfo')[0].style.display = '';
                $('.classScore1')[0].style.display = 'none';
                $('.classInfo1')[0].style.display = '';
            });
            $("#classScore").click(function () {
                $('#classScore')[0].style.color = 'blue';
                $('#classInfo')[0].style.color = '';
                $('.classInfo')[0].style.display = 'none';
                $('.classScore')[0].style.display = '';
                $('.classInfo1')[0].style.display = 'none';
                $('.classScore1')[0].style.display = '';
            });
        });
        function getSchoolYear(schoolYear) {
            var cSchoolYear = schoolYear;
            document.classScore.action = "<%=basePath%>admin/classStuInfo.jsp?schoolYear="
            + cSchoolYear;
            document.classScore.submit();


        }
        function getTerm(term) {
            var cTerm = term;
            document.classScore.action = "<%=basePath%>admin/classStuInfo.jsp?term="
            + cTerm;
            document.classScore.submit();
            $('.classScore')[0].style.display = 'none';
            $('.classInfo')[0].style.display = '';
            $('.classScore1')[0].style.display = 'none';
            $('.classInfo1')[0].style.display = '';
        }

        /* function display_alert()
         {
         var lujing=document.form.fileUpload.value;
         alert("路径="+lujing)
         } */
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
      resizable="false">
      
<%@include file="../common.jsp" %>

<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;display: none;">
    <form method="post" name="classScore">
        <table width="100%">
            <thead>
            <tr>
                <td colspan="11" align="center" style="padding:5px;">
                <%String className="";
                if (student != null && student.size() != 0) {className=(((ArrayList)student.get(0)).get(8))!=null?(String)((ArrayList)student.get(0)).get(8):"";}%>
                <h3><a href="javascript:void(0)" id="classInfo" style="color: blue;"><%=className %>班级学生信息</a></h3>
            </tr>
            <tr>
                <td colspan="4" style="padding:5px;"><a href="javascript:void(0)" id="change_cmgr">更换辅导员</a></td>
            </tr>
            <tr class="thead classInfo" style="display: none">
                <td align="center">序号</td>
                <td align="center">学号</td>
                <td align="center">姓名</td>
                <td align="center">性别</td>
                <!-- <td align="center">专业</td>
                <td align="center">班级</td> -->
                <td align="center">身份证号</td>
                <!-- <td align="center">学院</td> -->
                <td align="center">地址</td>
                <td align="center">操作</td>
            </tr>
            </thead>
            <tbody class="tbody classInfo1" style="display: none">
            <%
                if (student != null && student.size() != 0) {
                    for (int i = 0; i < student.size(); i++) {
                        ArrayList alRow = (ArrayList) student.get(i);
            %>
            <tr>

                <td align="center"><%=i+1 %>
                </td>
                <td align="center"><%=alRow.get(0)%>
                </td>
                <td align="center"><a
                        href="<%=basePath%>counsellor/stuScore.jsp?student_id=<%=alRow.get(0)%>&class_id=<%=alRow.get(3)%>"><%=alRow.get(1)%>
                </a></td>
                <td align="center"><%=alRow.get(2)%>
                </td>
                <%-- <td align="center"><%=alRow.get(6)%>
                </td> 
                <td align="center"><%=alRow.get(3)%>
                </td>--%>
                <td align="center"><%=alRow.get(4)%>
                </td>
                <%-- <td align="center"><%=alRow.get(5)%>
                </td> --%>
                <td align="center"><%=MyStringUtils.null2Str(alRow.get(7))%>
                </td>
                <td align="center"><a
                        href="<%=basePath%>counsellor/stuScore.jsp?student_id=<%=alRow.get(0)%>&class_id=<%=alRow.get(3)%>">查询学业成绩
                </a>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>

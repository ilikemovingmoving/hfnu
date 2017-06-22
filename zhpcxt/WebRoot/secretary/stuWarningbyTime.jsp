<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
    boolean closed = false;
    
   /*  String pro=request.getParameter("pro")==null?"":new String(request.getParameter("pro").getBytes("ISO-8859-1"),"utf-8");
    request.setAttribute("pro", pro); */
    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
    }else{
    	response.sendRedirect("/zhpcxt/admin/index.jsp");
    	return;
    }

    String message = (String) request.getAttribute("message");
%>
<%@include file="../all.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/admin-css.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/themes/icon.css">
<script type="text/javascript" src="/zhpcxt/js/jquery.js"></script>
    <script type="text/javascript" src="/zhpcxt/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/zhpcxt/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/zhpcxt/js/admin.js"></script>
    
<script src="/zhpcxt/highchart/code/highcharts.js"></script>
<script src="/zhpcxt/highchart/code/modules/exporting.js"></script>
	<script type="text/javascript">
        $(document).ready(function () {
            $('tbody tr:even').css({'background': '#ffffff'});
            $('tbody tr:odd').css({'background': '#eeeeff'});
            var message = <%=message %>;
            if (message != null && message != "") {

                alert(message);
            }
            $("#click_logout").click(function () {
                logout('<%=path %>');
            });
        });

    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
      resizable="false">
      
<%@include file="../common.jsp" %>

<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;">

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>



		<script type="text/javascript">

//Highcharts.chart('container', {
	var options={
    chart: {
    	renderTo:'container',
        type: 'column'
    },
    title: {
        text: '历年班级学业预警统计'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: [
            /* 'Jan',
            'Feb',
            'Mar',
            'Apr',
            'May',
            'Jun',
            'Jul',
            'Aug',
            'Sep',
            'Oct',
            'Nov',
            'Dec' */
        ],
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: '学业预警学生占班级比例 ( % )'
        }
    },
    credits:{
		enabled:false
	},
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: [/* {
        name: 'Tokyo',
        data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]

    }, {
        name: 'New York',
        data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

    }, {
        name: 'London',
        data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]

    }, {
        name: 'Berlin',
        data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

    } */]
};
	//);
	
	$.getJSON("student_stuWarningbyTime.action",
			{},
			function(d){
				options.xAxis.categories=eval(d.grade);
				options.series=eval(d.stuWarning);
				var chart=$("#container").highcharts(options);
			}
			);
		</script>
		</div>
	</body>
</html>

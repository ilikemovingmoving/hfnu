<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://"
         + request.getServerName() + ":" + request.getServerPort()
         + path + "/";
 
 String pro=request.getParameter("pro")==null?"":new String(request.getParameter("pro").getBytes("ISO-8859-1"),"UTF-8");
 request.setAttribute("pro", pro);
 
 ArrayList adminlogin = (ArrayList) session.getAttribute("adminlogin");
 if (adminlogin != null && adminlogin.size() != 0) {
     //student = array.getStudentOfKeys(keys);
 }else{
 	response.sendRedirect("index.jsp");
 	return;
 }
 %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/css/admin-css.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/css/themes/icon.css">
<script type="text/javascript" src="/zhpcxt/js/jquery.js"></script>
<script type="text/javascript" src="/zhpcxt/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/zhpcxt/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/zhpcxt/js/admin.js"></script>
<script src="/zhpcxt/highchart/code/highcharts.js"></script>
<script src="/zhpcxt/highchart/code/modules/data.js"></script>
<script src="/zhpcxt/highchart/code/modules/drilldown.js"></script>
<script src="/zhpcxt/js/all.js"></script>

<script type="text/javascript">
$(document).ready(function () {
	$("#click_logout").click(function () {
        logout("${pageContext.request.contextPath}");
    });
	
    $("#londing").css('display', 'none');
    $("#mainPanle").css('display', '');
    $('tbody tr:even').css({'background': '#ffffff'});
    $('tbody tr:odd').css({'background': '#eeeeff'});
});

function submitForm(){
	document.searchForm.submit();
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
      resizable="false">
<!-- 页面上，左部分 -->
<%@include file="../common.jsp" %>


<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;display: none">
     
<form action="address.jsp" method="post" name="searchForm">
	<select id="classGrade" onchange="getGrade(this.value)" name="grade"></select>
	<select id="classPro" onchange="getPro(this.value)" name="pro"></select>
	<select id="class" onchange="getClass(this.value)" name="cls"></select>
	<input type="hidden" id="grade2" value="${param.grade }">
	<input type="hidden" id="pro2" value="${requestScope.pro }">
	<input type="hidden" id="class2" value="${param.cls }">
</form>

<div id="container" style="min-width: 310px; max-width: 600px; height: 400px; margin: 0 auto">
</div>




<script type="text/javascript">
var chart=Highcharts.chart('container',
//var options=
		{
    chart: {
    	//renderTo: 'container', 
        type: 'pie'
    },
    title: {
        text: ("${not empty param.grade}"=="true"?"${param.grade}级":"")
        +("${not empty param.pro}"=="true"?"${requestScope.pro}":"")
        +("${not empty param.cls}"=="true"?"${param.cls}班":"")+"学生住址地理分布"
    },
    subtitle: {
        text: "${param.province}${param.city}${param.distrinct}"
    },
    plotOptions: {
    	pie : {
    		
    		events:{  
    			  
                click: function(e) {  
                	//alert("${empty param.province and empty param.city and empty param.distrinct}");
                	if("${empty param.province and empty param.city and empty param.distrinct}"!="false"){
						window.location="address.jsp?province="+e.point.name;
                	}else if("${not empty param.province and empty param.city and empty param.distrinct}"!="false"){
                		window.location="address.jsp?city="+e.point.name;
                	}else if("${not empty param.city and empty param.distrinct}"!="false"){
                		window.location="address.jsp?distrinct="+e.point.name;
                	}
//window.open('address?&id='+e.point.id); //单击每个饼图打开一个页面，同时传递参数，单击了哪个饼图，题                                                                //目id是多少。  

                }  

            }
    	},
        series: {
        	allowPointSelect: true,  
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '{point.name}: {point.y:.1f}%',
                /* formatter: function() {  
              	  
                    return '<b>'+ point.name +'</b>: '+ point.num +' 人'; 

                }  */
            }
        }
    },
	credits:{
		enabled:false
	},
    tooltip: {
        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        /* ,formatter: function() {
            return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                         Highcharts.numberFormat(this.y, 0, ',') +' 个)';
         } */
    },
    series: [{
        name: '',
        colorByPoint: true,
        data: [{}]
    }],
    drilldown: {
        series: [{}]
    } 
}
);

var province="${param.province}";
//alert(province);
var city="${param.city}";
var distrinct="${param.distrinct}";
var grade="${param.grade}";
var pro="${requestScope.pro}";
var cls="${param.cls}";
$.getJSON(
		"student_address.action",//?province="+province+"&city="+city+"&distrinct="+distrinct,
		{"province":province,"city":city,"distrinct":distrinct,"grade":grade,"pro":pro,"cls":cls},
		function(d){
			
			chart.series[0].setData(eval(d.list));
		});

		</script>
		</div>
	</body>
</html>

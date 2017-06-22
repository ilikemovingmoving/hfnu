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
    
    String pro=request.getParameter("pro")==null?"":new String(request.getParameter("pro").getBytes("ISO-8859-1"),"utf-8");
    request.setAttribute("pro", pro);
    if (adminlogin != null && adminlogin.size() != 0) {
        closed = true;
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
    
<script src="/zhpcxt/highchart/code/highcharts.js"></script>
<script src="/zhpcxt/highchart/code/modules/exporting.js"></script>
<script src="/zhpcxt/js/all.js"></script>
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
            
            if("${param.grade}"!="" || "${requestScope.pro}"!="" || "${param.cls}"!=""){
            	if($("#score1").css('display')=='none'){
            		$("#score2").css('display','none');
                	$("#score1").css('display','block');
            	}
            }
            
            //$("#score1").css('display','');
            $("#view1").click(function(){
            	if($("#score1").css('display')=='none'){
            		$("#score2").css('display','none');
                	$("#score1").css('display','block');
            	}else{
            		$("#score1").css('display','none');
            	}
            });
            $("#view2").click(function(){
            	if($("#score2").css('display')=='none'){
            		/* $("#grade2").val("");
            		$("#pro2").val("");
            		$("#cls2").val(""); */
            		$("#score1").css('display','none');
                	$("#score2").css('display','block');
            	}else{
            		$("#score2").css('display','none');
            	}
            });
            
        });
        

    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no"
      resizable="false">
      
<%@include file="../common.jsp" %>

<div id="mainPanle" region="center" border="true"
     style="background:#f7f7f7; padding:5px;">
     
     <ol style="font:30px;">
     <li><a href="javascript:;" id="view1">查看不及格率</a></li>
     <li><a href="javascript:;" id="view2">查看学业预警</a></li>
     </ol>


 <div id="score1" style="display: none;margin: 20px;" >    
 <form action="scoreAnalyze.jsp" method="post" name="searchForm">
	<select id="classGrade" onchange="getGrade(this.value)" name="grade"></select>
	<select id="classPro" onchange="getPro(this.value)" name="pro"></select>
	<select id="class" onchange="getClass(this.value)" name="cls"></select>
	<input type="hidden" id="grade2" value="${param.grade }">
	<input type="hidden" id="pro2" value="${requestScope.pro }">
	<input type="hidden" id="class2" value="${param.cls }">
</form>

<div id="container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 20px 20px;"></div>

<script type="text/javascript">

function submitForm(){
	document.searchForm.submit();
}

//var chart=Highcharts.chart('container', {
	var options={
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie',
        renderTo:'container'
    },
    title: {
        text: ("${not empty param.grade}"=="true"?"${param.grade}级":"")
        +("${not empty param.pro}"=="true"?"<%=pro%>专业":"")
        +("${not empty param.cls}"=="true"?"${param.cls}班":"")+"学生统计（课程未通过数量）"
    },
    credits:{
		enabled:false
	},
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'/* ,
        formatter: function() {
            return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                         Highcharts.numberFormat(this.y, 0, ',') +' 个)';
         } */
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: /* '<b>{point.name}</b>: {point.percentage:.1f} %('+
                Highcharts.numberFormat('${point.y}', 0, ',') +' 个)', */
                '<b>{point.name}</b>: {point.percentage:.1f} %',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }/* ,
                distance: -30  */
            }
        }
    },
    series: [{
         name: 'Brands',
        colorByPoint: true,
        data: [{
        }]
    }]
	};
//});
var grade="${param.grade}";//"${empty param.grade}"=="true"?"2016":"${param.grade}";
$.getJSON("student_scoreAnalyze.action",
		{"grade":grade,"pro":"<%=pro%>","cls":"${param.cls}"},
		function(d){
			options.series[0].data=eval(d.list);
			$("#container").highcharts(options);
});



</script>
</div>
<div id="score2" style="display:none;">
<div id="container2" style="min-width: 310px; height: 400px; margin: 0 auto"></div>



		<script type="text/javascript">

//Highcharts.chart('container', {
	var options2={
    chart: {
    	renderTo:'container2',
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
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}:</td>' +
            '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0/* ,
            dataLabels: {
                enabled: true,  //显示数量提示
                color: '#000000',
                formatter : function() {
                    a = this.y/data.total*100;    //a为当前柱状图y轴值除以总数然后乘以100
                    return a.toFixed(2) + "%"+"<br/>"+this.y+"个";  //返回百分比和个数
                }
            } */
        }
    },
    series: []
};
	//);
	
	$.getJSON("student_stuWarningbyTime.action",
			{},
			function(d){
				options2.xAxis.categories=eval(d.grade);
				options2.series=eval(d.stuWarning);
				var chart2=$("#container2").highcharts(options2);
			}
			);
		</script>
		</div>
</div>
</body>
</html>

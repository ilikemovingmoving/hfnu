<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function getPro(pro) {
    /*document.classSelect.action = "/zhpcxt/admin/allClassInfo.jsp?pro="
    + pro;*/
    //document.classSelect.submit();
	submitForm();

}
function getGrade(grade) {
	/*document.classSelect.action = "/zhpcxt/admin/allClassInfo.jsp?grade="
    + grade;*/
	//document.classSelect.submit();
	submitForm();
	
}
function getClass(cls) {
    /*document.classSelect.action = "/zhpcxt/admin/allClassInfo.jsp?grade="
    + grade;*/
    //document.classSelect.submit();
	submitForm();

}

function getAll(){
	
	$.post(
	"/zhpcxt/AllClassInfoServlet",
	{},
	function(data){
		var arr=data.gradeList;
		$("#classGrade").html("");
		$("#classGrade").append("<option value='' selected='selected'>年级</option>");
		$.each(arr,function(i,a){
			$("#classGrade").append("<option value='"+a+"'>"+a+"</option>");
		});
	},
	"json"
	);
	
	$.post(
			"class_queryDistinctPro.action",
			{},
			function(data){
				var arr=data.list;
				$("#classPro").html("");
				$("#classPro").append("<option value='' >专业</option>");
				alert("${param.pro}");
				$.each(arr,function(i,a){
					
					$("#classPro").append("<option value='"+a+"'>"+a+"</option>");
				});
			},
	"json");
	
	$.post(
	"class_queryDistinctClass.action",
	{},
	function(data){
		var arr=data.list;
		$("#class").html("");
		$("#class").append("<option value='' >班级</option>");
		$.each(arr,function(i,a){
			/*var cls="${param.cls}";//alert(cls);
			if(cls==a){
				$("#class").append("<option value='"+a+"' selected='selected'>"+a+"</option>");
				alert();
			}else{*/
				$("#class").append("<option value='"+a+"'>"+a+"</option>");
				
			//}
		});
	},
	"json");
}

$(function(){
	
	getAll();
	//$("#classGrade").val("${param.grade}");
});
</script>
</head>
<body>

</body>
</html>
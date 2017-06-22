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
			if($("#grade2").val()==a){
				
				$("#classGrade").append("<option value='"+a+"' selected='selected'>"+a+"</option>");
			}else{
				$("#classGrade").append("<option value='"+a+"'>"+a+"</option>");
				
			}
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
				//alert($("#pro2").val());
				$.each(arr,function(i,a){
					if($("#pro2").val()==a){
						
						$("#classPro").append("<option value='"+a+"' selected='selected'>"+a+"</option>");
					}else{
						$("#classPro").append("<option value='"+a+"'>"+a+"</option>");
						
					}
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
			/*var cls="${param.cls}";//alert(cls);*/
			//alert($("#class2").val());
			if($("#class2").val()==a){
				$("#class").append("<option value='"+a+"' selected='selected'>"+a+"</option>");
				//alert();
			}else{
				$("#class").append("<option value='"+a+"'>"+a+"</option>");
				
			}
		});
	},
	"json");
}

$(function(){
	
	getAll();
	//$("#classGrade").val("${param.grade}");
});
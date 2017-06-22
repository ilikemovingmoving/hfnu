<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	function jumpGO(p,s){
		var totalPage=document.getElementById("totalPage").value;
		var reg=/^[1-9]\d*$/;
		if(!reg.test(p)){
			jump(1,s);
		}else if(p-totalPage>0){
			jump(totalPage,s);
		}else{
			jump(p,s);
		}
	}
	
	function jump(p,s){
		var searchForm=document.searchForm;
		with(searchForm){
			pageIndex.value=p;
			pageSize.value=s;
			submit();
		}
	}
</script>
<input type="hidden" id="totalPage" value="${page.totalPage }">
<table style="width:100%;">
	<tr>
		<td style="text-align:left;" >
			第<span style="color:red;">${page.pageIndex }</span>/${page.totalPage }页&nbsp;
			共${page.totalCount }条
		</td>
		<td style="text-align:right;" >
			<c:choose>
				<c:when test="${page.pageIndex==1 }">
					首页
					上一页
				</c:when>
				<c:otherwise>
					<a href="javascript:jump(1,${page.pageSize });">首页</a>
					<a href="javascript:jump(${page.pageIndex-1 },${page.pageSize });">上一页</a>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page.pageIndex==page.totalPage }">
					下一页
					末页
				</c:when>
				<c:otherwise>
					<a href="javascript:jump(${page.pageIndex+1 },${page.pageSize });">下一页</a>
					<a href="javascript:jump(${page.totalPage },${page.pageSize });">末页</a>
				</c:otherwise>
			</c:choose>
			转到<input style="width:20px;" id="goPage" value="${page.pageIndex }">
			<input type="button" value="GO" onclick="javascript:jumpGO(document.getElementById('goPage').value,${page.pageSize});">
			一页显示<select onchange="javascript:jump(1,this.value);">
				<option value="15" <c:if test="${page.pageSize==15 }">selected</c:if> >15</option>
				<option value="100" <c:if test="${page.pageSize==100 }">selected</c:if> >100</option>
			</select>
		</td>
	</tr>
</table>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script src="${contextPath}/resources/bower_components/tinymce/tinymce.min.js"></script>
<script>
$(function(){
})
</script>

<h2 class="my-5">
<i class="fas fas-blog"></i>
<c:if test="${blog.blogId==0}">
	 블로그 만들기
</c:if>
<c:if test="${blog.blogId!=0}">
	블로그 관리
</c:if>
</h2>

<div>
${result}
</div>
	
<form:form modelAttribute="blog" enctype="multipart/form-data">
	<form:hidden path="blogId"/>
	<input type="hidden" name="owner" value="${USER.userId}"/>
	<div class="form-group">
		<label for="title">제목</label> 
		<form:input path="title" class="form-control"/>
		<form:errors path="title" element="div" cssClass="error"/>
	</div>
	
	<div class="form-group">
		<label for="file">배경 이미지</label>
		<input type="file" name="file" id="file" 
			class="form-control-file" accept="image/*">
		
	</div>
	
	<div class="form-group">
		<label for="description">설명</label>
		<form:textarea path="description" class="form-control" rows="5"/>
	</div>
	
	<div class="text-center">
		<button type="submit" class="btn btn-primary ok">
			<i class="fas fa-check"></i> 완료</button>
	</div>
</form:form>

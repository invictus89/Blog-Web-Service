

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script src="${contextPath}/resources/bower_components/tinymce/tinymce.min.js"></script>
<script>
$(function(){
	tinymce.init({ 
		selector:'textarea',
		language : 'ko_KR',
		height: 500
	});
})
</script>

<h2 class="my-5">
	<i class="fas fa-edit"></i> 게시글 작성
</h2>

<form:form modelAttribute="board" enctype="multipart/form-data">
	<div class="form-group">
		<label for="writer">작성자</label>
		<input type="hidden" name="writer" value="${USER.userId}"/>
		<p class="form-control-static">${USER.userId}</p>
	</div>
	
	<div class="form-group">
		<label for="password">비밀번호</label> 
		<form:password path="password" class="form-control"/>
		<form:errors path="password" element="div" cssClass="error"/>
		
	</div>		
	<div class="form-group">
		<label for="title">제목</label> 
		<form:input path="title" class="form-control"/>
		<form:errors path="title" element="div" cssClass="error"/>
	</div>
	
	<div class="form-group">
		<label for="description">첨부 파일들</label>
		<input type="file" name="files" class="form-control-file"
				multiple="multiple">
	</div>
	
	
	<div class="form-group">
		<label for="content">내용</label>
		<form:textarea path="content" class="form-control" rows="10"/>
	</div>
	
	<div class="text-center">
		<button type="submit" class="btn btn-primary ok">
			<i class="fas fa-check"></i> 완료</button>
		<a  href="list?page=${param.page}" class="btn btn-primary back">
			<i class="fas fa-undo"></i> 목록</a>	
	</div>
		
</form:form>	


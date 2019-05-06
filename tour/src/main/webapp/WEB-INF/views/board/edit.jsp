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

	// 목록으로 가기 버튼
	$('.back').click(function(){
		location = '../view/${board.boardId}?page=${param.page}';
	});
	


 	// 전체 체크 하기
 	$('#check-all').change(function(e){
		$('.check-item').prop('checked', this.checked);
	});
	
 	// 개별 체크박스가 체크 해제되면 전체 체크박스도 해제 
	$('.check-item').change(function(e){
		if(!this.checked) {
			$('#check-all').prop('checked', false);	
		}
	});
	
});
</script>

<h2 class="my-5">
	<i class="fas fa-edit"></i> 게시글 수정
</h2>

<form:form modelAttribute="board"  enctype="multipart/form-data">
	<form:hidden path="boardId"/>
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

	<c:if test="${not empty board.fileList}">
	<div class="form-group">
		삭제할 파일 선택 : 
		<label class="mr-3">
			<input type="checkbox" id="check-all"> 전체 선택
		</label>
		<div>
		<c:forEach var="file" items="${board.fileList}">
			<label>
				<input type="checkbox" name="deleteFiles" value="${file.attachmentId}"
					<c:if test="${gallery.isDeleteImage(image.imageId)}">checked</c:if>
					class="check-item">
				<i class="fas fa-file"></i> ${file.originalName}	
			</label>
		</c:forEach>
		</div>
	</div>
	</c:if>	
	
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
		<button type="button" class="btn btn-primary back">
			<i class="fas fa-undo"></i> 취소</button>	
	</div>
		
</form:form>	

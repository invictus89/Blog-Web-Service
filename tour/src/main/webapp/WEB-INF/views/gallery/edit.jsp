<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<style>
.check-item {
	position:absolute;
	left:2px;
	top:2px;
	width:20px;
	height:20px
}
</style>

<script>

$(function(){
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
	<i class="fas fa-edit"></i> 갤러리 수정
</h2>

<form:form modelAttribute="gallery" enctype="multipart/form-data">
	<form:hidden path="galleryId"/>
	<div class="form-group">
		<label for="owner">소유자 </label>
		<input type="hidden" name="owner" value="${USER.userId}"/>
		<p class="form-control-static">
			<img src="${contextPath}/member/avata/${gallery.galleryId}"
				class="rounded-circle avata-sm" >
			${USER.userId}</p>
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
		<label for="description">내용</label>
		<form:textarea path="description" class="form-control" rows="5"/>
	</div>
	
	<div class="form-group">
		삭제할 파일 선택 : 
		<label class="mr-3">
			<input type="checkbox" id="check-all"> 전체 선택
		</label>
		
		<!--  사진 목록  -->
		<div style="overflow:hidden">		
		<c:forEach var="image" items="${gallery.list}">
			<div class="float-left mr-1" style="position:relative">
				<label>
					<input type="checkbox" name="deleteImages" value="${image.imageId}"
						class="check-item"
						<c:if test="${gallery.isDeleteImage(image.imageId)}">checked</c:if>
					>
					<img src="${contextPath}/gallery/thumb/${image.imageId}">
				</label>
			</div>
		</c:forEach>
		</div>
	</div>	
	
	<div class="form-group">
		<label for="description">이미지 파일들</label>
		<input type="file" name="files" class="form-control-file"
				multiple="multiple" accept="image/*">
	</div>
	
	
	
	<div class="text-center">
		<button type="submit" class="btn btn-primary ok">
			<i class="fas fa-check"></i> 완료</button>
		<a  href="../view/${gallery.galleryId}?page=${param.page}" class="btn btn-primary back">
			<i class="fas fa-undo"></i> 돌아가기</a>	
	</div>
		
</form:form>	
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<script src="${contextPath}/resources/bower_components/axios/dist/axios.min.js"></script>
<script>
$(function(){
 	$('#delete-panel').deletePanel({
		triger : '.delete',
		url : '../delete/${post.postId}',
		moveUrl : '../list?page=${param.page}'
	});
});
</script>

<h2 class="my-5">
	<i class="fas fa-file-alt"></i> ${post.title}
</h2>
<div style="overflow:hidden">
	<div class="float-left">
		조회수 : 	${post.readCnt}</div>
	<div class="float-right">
		수정일 :
		<fmt:formatDate value="${post.updateDate}"
			pattern="yyyy-MM-dd HH:mm:ss" />
	</div>
</div>
<div>
첨부파일 : 
<c:forEach var="file" items="${post.fileList}">
	<a href="../downlaod?attachmentId=${file.attachmentId}">
		<i class="fas fa-file"></i> ${file.originalName}
	</a>
</c:forEach>
</div>
<hr />
${post.content}
<hr />

<div id="delete-panel"></div>

<div class="text-center">
	<a href="../edit/${post.postId}?page=${param.page}"
		class="btn btn-primary ok text-white"> <i class="fas fa-edit"></i>
		수정
	</a>
	<button class="btn btn-danger delete">
		<i class="fas fa-trash"></i> 삭제
	</button>

	<a href="../list?page=${page}" class="btn btn-primary back">
		<i class="fas fa-undo"></i> 목록
	</a>
</div>





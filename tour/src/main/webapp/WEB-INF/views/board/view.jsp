<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<script src="${contextPath}/resources/bower_components/axios/dist/axios.min.js"></script>
<script src="${contextPath}/resources/js/reply.js"></script>

<script>
$(function(){
 	$('#delete-panel').deletePanel({
		triger : '.delete',
		url : '../delete/${board.boardId}',
		moveUrl : '../list?page=${param.page}'
	});

 	var opt = {
 	 		el : '#top-level-reply',
 	 		writer : 'hong2', // '${USER.userId}',
 	 		tableName : 'board',
 	 		groupId : ${board.boardId},
 	 		action : '${contextPath}/reply'
 	 	}; 	
 	TopLevelReply.init(opt);
 	
 	opt.el = '#reply-list';
 	ReplyList.init(opt);
 	
});
</script>

<h2 class="my-5">
	<i class="fas fa-file-alt"></i> ${board.title}
</h2>
<div style="overflow:hidden">
	<div class="float-left">작성자 : 
		<img src="${contextPath}/member/avata/${board.writer}"
				class="rounded-circle avata-sm mr-2" >
		${board.writer}, 조회수 :
		${board.readCnt}</div>
	<div class="float-right">
		수정일 :
		<fmt:formatDate value="${board.updateDate}"
			pattern="yyyy-MM-dd HH:mm:ss" />
	</div>
</div>
<div>
첨부파일 : 
<c:forEach var="file" items="${board.fileList}">
	<a href="../downlaod?attachmentId=${file.attachmentId}">
		<i class="fas fa-file"></i> ${file.originalName}
	</a>
</c:forEach>
</div>
<hr />
${board.content}
<hr />

<div id="top-level-reply"></div>

<div id="reply-list"></div>

<div id="delete-panel"></div>

<div class="text-center mt-2">
	<c:if test="${USER.userId == board.writer}">
		<a href="../edit/${board.boardId}?page=${param.page}"
			class="btn btn-primary ok text-white"> <i class="fas fa-edit"></i>
			수정
		</a>
		<button class="btn btn-danger delete">
			<i class="fas fa-trash"></i> 삭제
		</button>
	</c:if>
	<a href="../list?page=${page}" class="btn btn-primary back">
		<i class="fas fa-undo"></i> 목록
	</a>
</div>





<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags/util" prefix="iot"%>
<script src="${contextPath}/resources/bower_components/axios/dist/axios.min.js"></script>
<script>

$(function(){
 	$('#delete-panel').deletePanel({
		triger : '.delete',
		// url : 'delete/${member.userId}',	// <-- 올바른가?
		url : 'delete/{user-id}',
		multiple : '[name=check-item]:checked',
		moveUrl : 'list?page=${param.page}'
	});

 	// 전체 체크 하기
 	$('#check-all').change(function(e){
		$('[name=check-item]').prop('checked', this.checked);
	});
	
 	// 개별 체크박스가 체크 해제되면 전체 체크박스도 해제 
	$('[name=check-item]').change(function(e){
		if(!this.checked) {
			$('#check-all').prop('checked', false);	
		}
	});
	
	// 체크 항목 삭제 버튼 처리 
/* 	$('.check-delete').click(function(e){
		var items = $('[name=check-item]:checked');
		items.each(function(){
			var userId = $(this).val();	// user id 얻기
			// 해당 id 삭제 
		})
	}) */
});

</script>
<h2 class="my-5 text-primary">
	<i class="fas fa-users"></i> 회원 목록</h2>

<div>
	<div class="float-left">
		<label class="mr-3">
			<input type="checkbox" id="check-all"> 전체
		</label>
		<button class="btn btn-danger btn-sm check-delete delete"
				data-mode="multiple">
			<i class="fas fa-user-times"></i> 선택 삭제
		</button>
	</div>
	
	<div class="float-right">
		<a href="create?page=${pi.page}"><i class="fas fa-user-plus"></i> 추가</a> 
		(총 : ${pi.totalCount} 명)
	</div>
</div>



<table class="table table-striped table-hover">
	<tr>
		<th>No</th>
		<th>사용자 ID</th>
		<th>이름</th>
		<th>email</th>
		<th>전화번호</th>
		<th>등록일</th>
		<th></th>
	</tr>
	
	<c:forEach var="member" items="${pi.list}" varStatus="status">
		<tr>
			<td>
				<label>
					<input type="checkbox" name="check-item" 
						value="${member.userId}">
					${pi.totalCount-((pi.page-1)*10)- status.index}
				</label>
			</td>
			<td>
				<a href="view/${member.userId}?page=${pi.page}">
					${member.userId}
					<iot:newToday test="${member.regDate}"/>
				</a>
			</td>
			<td>${member.name}</td>
			<td>${member.email}</td>
			<td>${member.phone}</td>
			<td>
				<fmt:formatDate value="${member.regDate}" pattern="yyyy-MM-dd"/>
			</td>	
			<td class="text-right">
				<a href="edit/${member.userId}?page=${pi.page}" 
					class="btn btn-primary btn-sm mr-2" title="수정"><i class="fas fa-user-cog"></i></a>
				<button class="btn btn-danger btn-sm delete"
					data-user-id="${member.userId}"	
					title="삭제"><i class="fas fa-user-times"></i>
				</button>
			</td>
		</tr>
	</c:forEach>
</table>

<iot:pagination pageInfo="${pi}"/>

<div id="delete-panel"></div>

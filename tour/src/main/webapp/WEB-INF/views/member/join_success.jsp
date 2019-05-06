<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	$(function() {
		setTimeout("location.href='${contextPath}/login'", 1500);
	});
</script>
<div class="row">
	<div class="col-sm-6 mx-auto">
		<h2 class="my-5 text-primary">
			<i class="fas fa-user"></i> 회원 가입 완료
		</h2>
		<div class="my-1">사용자 ID : ${member.userId}</div>
		<div class="my-1">이름 : ${member.name}</div>
	</div>
</div>



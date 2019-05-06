<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
	<div  class="col-sm-5 mx-auto">
		<h2 class="my-5 text-primary"><i class="fas fa-sign-in-alt"></i> 로그인</h2>

		<c:if test="${not empty loginInfo.target}">
			<div class="alert alert-warning">
				<strong>${loginInfo.reason}</strong>
			</div>
		</c:if>

		<!-- 
		form에 modelAttribute가 설정되면 이 페이지를 호출하는 컨트롤단에서 반드시 같은 타입의 객체가 선언되어 있어야 한다.
		otherwise, 다음과 같은 error 발생함
		---------------------------------------------------------
		java.lang.IllegalStateException: 
		Neither BindingResult nor plain target object for bean name 'loginInfo' available as request attribute
		 --------------------------------------------------------
		 -->
		<form:form modelAttribute="loginInfo">
			<form:hidden path="target"/>
			<div class="form-group">
				<label for="userId">사용자 ID</label> 
				<form:input path="userId" class="form-control"/>
				<form:errors path="userId" element="div" cssClass="error"/>
			</div>
			
			<div class="form-group">
				<label for="password">비밀번호</label> 
				<form:password path="password" class="form-control"/>
				<form:errors path="password" element="div" cssClass="error"/>
			</div>		
			
			<div class="form-group">
				<form:errors path="" element="div" cssClass="error"/>
			</div>
			
			<div class="text-center">
				<button type="submit" class="btn btn-primary">
					<i class="fas fa-sign-in-alt"></i> 로그인</button>
			</div>
		</form:form>	
	</div>
</div>

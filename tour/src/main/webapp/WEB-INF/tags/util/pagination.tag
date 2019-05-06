<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="pageInfo" required="true" 
		type="edu.autocar.domain.PageInfo" %>

<ul class="pagination justify-content-center">
	<c:if test="${pageInfo.currentBlock > 1}">
		<li class="page-item" >
			<a class="page-link" href="?page=1">처음</a>
		</li>
		<li class="page-item" >
			<a class="page-link" 
				href="?page=${pageInfo.prevBlockPage}">◀</a>
		</li>		
	</c:if>

	<c:forEach var="ix" begin="1" end="${pageInfo.totalPage}" >
		<c:choose>
			<c:when test="${ix == pageInfo.page}">
				<li class="page-item active">
					<a class="page-link" href="#">${ix}</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
					<a class="page-link" href="?page=${ix}">${ix}</a></li>
			</c:otherwise>
		</c:choose>		
	</c:forEach>
	
	<c:if test="${pageInfo.currentBlock < pageInfo.totalBlock}">
		<li class="page-item" >							
			<a class="page-link"  
				href="?page=${pageInfo.nextBlockPage}">▶</a>
		</li>
		<li class="page-item" >
			<a class="page-link"  
				href="?page=${pageInfo.totalPage}">마지막</a>
		</li>
	</c:if>
	
</ul>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=rbeyz68rf5"></script>

네이버 지도 연습

<div id="map" style="width:100%;height:400px;"></div>

<script>
var multiCampus = new naver.maps.LatLng(37.501394, 127.039665); 

var mapOptions = {
    center: multiCampus,
    zoom: 13
};

var map = new naver.maps.Map('map', mapOptions);

var markerIcon = {
	url : '${contextPath}/resources/images/marker.png',
	size : new naver.maps.Size(32, 32),		// 아이콘 크기
	origin : new naver.maps.Point(0, 0),	// 이미지 시작 	
	anchor : new naver.maps.Point(16, 32)	// 가리키는 지점 	
};

var marker = new naver.maps.Marker({
	position : multiCampus,
	map : map,
	// icon : markerIcon
});
</script>
  
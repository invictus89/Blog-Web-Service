<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=rbeyz68rf5"></script>


<style>
.iw_inner {
	padding : 2px
}
</style>
<div id="map" style="width:100%;height:400px;"></div>

<script>
var cityhall = new naver.maps.LatLng(37.5666805, 126.9784147),
map = new naver.maps.Map('map', {
    center: cityhall.destinationPoint(0, 500),
    zoom: 10
}),
marker = new naver.maps.Marker({
    map: map,
    position: cityhall
});

var contentString = [
    '<div class="p-2">',
    '   <h3>서울특별시청</h3>',
    '   <p>서울특별시 중구 태평로1가 31 | 서울특별시 중구 세종대로 110 서울특별시청<br />',
    '       02-120 | 공공,사회기관 &gt; 특별,광역시청<br />',
    '       <a href="http://www.seoul.go.kr" target="_blank">www.seoul.go.kr/</a>',
    '   </p>',
    '</div>'
].join('');

var infowindow = new naver.maps.InfoWindow({
	content: contentString
});

naver.maps.Event.addListener(marker, "click", function(e) {
	if (infowindow.getMap()) {
	    infowindow.close();
	} else {
	    infowindow.open(map, marker);
	}
});

infowindow.open(map, marker);
</script>
  
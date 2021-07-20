//취소버튼 클릭시 메인페이지로 이동
function goIndex() {
	location.replace("/");
}

//지역인증 -> 지역받아오기
var options = {
    enableHighAccuracy : true,
    timeout : 5000,
    maximumAge : 0
};

function success(pos) {
	//위도,경도 받아오기
    var crd = pos.coords;
    lat = crd.latitude;
    lon = crd.longitude;

    getAddr(lat,lon);
    function getAddr(lat,lon){
        let geocoder = new kakao.maps.services.Geocoder();

        let coord = new kakao.maps.LatLng(lat, lon);
        let callback = function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                //지역명 받아오기
                var locate = result[0].address.region_1depth_name;
                console.log(locate);
                $('input#location').val(locate);
            }
        };

        geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
    }
    
};

function error(err) {
    console.warn('ERROR(' + err.code + '): ' + err.message);
};

$("button#findLocation").click(function() {
    console.log("되닝?");
    navigator.geolocation.getCurrentPosition(success, error, options);

})
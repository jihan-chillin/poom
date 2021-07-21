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
    navigator.geolocation.getCurrentPosition(success, error, options);

})

//이메일인증 -> 인증키 메일로 보내고 키값 데이터 받아오기
var code = "";

$("button#emailSend").click(function() {// 메일 입력 유효성 검사
	var mail = $("input#email").val(); //사용자의 이메일 입력값.
	
	if (mail == "") {
		alert("메일 주소가 입력되지 않았습니다.");
	} else {
		$.ajax({
			type : 'post',
			url : '/CheckMail',
			data : {
				mail:mail
				},
			dataType :'json',
			success : function(data) {
		        $('tr.email_number').show();
		        code = data.key;
				console.log(data.key);
			}
		});
	
		alert("인증번호가 전송되었습니다. 메일을 확인해주세요!") 
		isCertification=true; //추후 인증 여부를 알기위한 값
	}
});

//인증번호 비교
$(".email_number input").keyup(function(){
    
    var inputCode = $(".email_number input").val();        // 입력코드    
    var checkResult = $("#emailCheck");    // 비교 결과     
    
    if(inputCode == code){                            // 일치할 경우
        checkResult.html("O 일치");
        checkResult.attr("class", "correct");        
    } else {                                            // 일치하지 않을 경우
        checkResult.html("X 불일치");
        checkResult.attr("class", "incorrect");
    }    
    
});
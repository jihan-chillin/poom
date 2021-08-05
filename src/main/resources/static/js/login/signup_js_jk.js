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
                //console.log(locate);
                $('input[name=memberLoc]').val(locate);
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
	var mail = $("input[name=memberEmail]").val(); //사용자의 이메일 입력값.
	
	if (mail == "") {
		alert("메일 주소가 입력되지 않았습니다.");
	} else {
		$.ajax({
			type : 'post',
			url : getContextPath()+'/login/CheckMail',
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
	
		alert("인증번호가 전송되었습니다. 메일을 확인해주세요!");
		$("[name=checked_email]").val("y");
	}
});

//인증번호 비교
$(".email_number input").keyup(function(){
    
    var inputCode = $(".email_number input").val(); //입력코드    
    var checkResult = $("#emailCheck");// 비교 결과     
    
    if(inputCode == code){ //일치할 경우
        checkResult.html("O 일치");
        checkResult.attr("class", "correct");
        $("[name=checked_emailNumber]").val("y");
    } else { //일치하지 않을 경우
        checkResult.html("X 불일치");
        checkResult.attr("class", "incorrect");
    }    
    
});

//취소버튼 클릭시 메인페이지로 이동
function goIndex() {
	location.replace(getContextPath()+"/");
}

//회원가입 유효성 검사
$(function(){
    //id 중복확인 & 유효성검사
    $("#idCheck").on("click",function() {
        var id = $("[name=memberId]").val();
        if(id=="") {
            alert("아이디를 입력해주세요");
            $("[name=memberId]").focus();
            return;
        } 

        //아이디 영문소문자+숫자(8~15자리 입력) 정규식
        var idCheck = /^(?=.*[a-z])(?=.*[0-9]).{8,15}$/;
        var result = idCheck.exec(id);

        if(result !=null) {
            window.open(getContextPath()+"/login/duplCheck?type=id&check="+id,"","width=500px,height=300px,top=300px,left=200px");
        }else {
            alert("아이디는 8~15자의 영어소문자+숫자 조합으로 사용해야 합니다.");
            $("[name=memberId]").focus();
        }
    });

    //비밀번호 유효성검사
    $("[name=memberPw]").on("blur",function() {
    	var pw = $("[name=memberPw]").val();
        if(pw=="") {
            alert("비밀번호를 입력해주세요");
            return;
        }
    	
        //비밀번호 영문소문자+숫자(8~20자리 입력) 정규식
        var pwCheck = /^(?=.*[a-z])(?=.*[0-9]).{8,20}$/;
        var result = pwCheck.exec(pw);

        if(result == null) {
        	alert("비밀번호는 8~20자의 영어소문자+숫자 조합으로 사용해야 합니다.");
        }
    });

    //비밀번호 확인
    $("[name=pwc]").on("keyup",function() {
        var checkResult = $("#pwCheck");
        if($("[name=memberPw]").val() == $("[name=pwc]").val()) {
            checkResult.html("O 일치");
            checkResult.attr("class", "correct");
        }else {
            checkResult.html("X 불일치");
            checkResult.attr("class", "incorrect");
        }
    });
    
    //닉네임 중복확인 & 유효성검사
    $("#nicknameCheck").on("click",function() {
        var nickname = $("[name=memberNickname]").val();
        if(nickname=="") {
            alert("닉네임을 입력해주세요");
            $("[name=memberNickname]").focus();
            return;
        } 

        //닉네임 영문소문자 또는 한글(2~8자리 입력) 정규식
        var nicknameCheck = /^[가-힣|a-z]{2,8}$/;
        var result = nicknameCheck.exec(nickname);

        if(result !=null) {
            window.open(getContextPath()+"/login/duplCheck?type=nick&check="+nickname,"","width=500px,height=300px,top=300px,left=200px");
        }else {
            alert("닉네임은 2~8자의 영어소문자 또는 한글로 사용해야 합니다.");
            $("[name=memberNickname]").focus();
        }
    });
    
    //관심키워드박스 3개제한
    $("input[type='checkbox']").on("click",function(){
        let count = $("input:checked[type='checkbox']").length;

        if(count>3) {
            $(this).prop("checked",false);
            alert('3개까지만 선택할 수 있습니다.')
        }
    });
    
    
    //회원가입 버튼 눌렀을 때, 빈칸 있으면 다시 유효성 검사진행    
    $("button[type=submit]").on("click",function(){
        var id = $("[name=memberId]").val();
        var pw = $("[name=memberPw]").val();
        var name = $("[name=memberName]").val();
        var nickname = $("[name=memberNickname").val();
        var birth = $("[name=memberBirth]").val();
        var location = $("[name=memberLoc]").val();
        var email = $("[name=memberEmail]").val();
        
        var nameCheck = /[가-힣]{2,}/;
        var birthCheck= /^[0-9]*$/;
        var emailCheck = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+$/;
        
        if(id == ""){
            alert("아이디는 필수입력사항입니다.");
            $("[name=memberId]").focus();
            return false;
        }
        if($("[name=checked_id]").val() =="") {
        	alert("아이디 중복체크를 해주세요.");
            return false;
        }
        
        if(pw == ""){
            alert("비밀번호는 필수입력사항입니다.");
            $("[name=memberPw]").focus();
            return false;
        }
        if($("[name=memberPw]").val() != $("[name=pwc]").val()) {
        	alert("비밀번호가 일치하지 않습니다! 다시 설정해주세요.");
        	return false;
        }

        var nameCheck = nameCheck.exec(name);
        if(name == ""){
            alert("이름은 필수입력사항입니다.");
            $("[name=memberName]").focus();
            return false;
        }else if(nameCheck == null) { 
            alert("이름은 한글만 입력 가능합니다.");
            $("[name=memberName]").focus();
            return false;
        }

        if(nickname == ""){
            alert("닉네임은 필수입력사항입니다.");
            $("[name=memberNickname]").focus();
            return false;
        }
        if($("[name=checked_nick]").val() =="") {
        	alert("닉네임 중복체크를 해주세요.");
            return false;
        }

		if(birth == ""){
            alert("생년월일은 필수입력사항입니다.");
            $("[name=memberBirth]").focus();
            return false;
        }
        if(birth.length != 8) {
        	alert("생년월일 형식을 맞춰주세요! ex)20210811");
            $("[name=memberBirth]").focus();
            return false;
          }
        
        if(location == ""){
            alert("지역인증버튼을 눌러 지역을 인증하세요!");
            return false;
        }

        if(email == ""){
            alert("이메일은 필수입력사항입니다. 입력 후 인증하세요!");
            $("[name=memberEmail]").focus();
            return false;
        }
        if($("[name=checked_email]").val() =="") {
        	alert("이메일을 인증해주세요.");
            return false;
        }
        if($("[name=checked_emailNumber]").val() =="") {
        	alert("이메일 인증번호가 일치하지 않습니다.");
        	return false;
        }
        
        //빈칸 없을 때 제출.
        $("[name=signUp_form]").submit();
    
    })
})


function setDupl(dupl,type,checked) {
	if(type == 'id') {
		$("[name=memberId]").val(dupl);
		$("[name=checked_id]").val(checked);
		$("[name=memberId]").focus();
	}else {
		$("[name=memberNickname]").val(dupl);
		$("[name=checked_nick]").val(checked);
		$("[name=memberNickname]").focus();
	}
}



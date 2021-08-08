// 취소버튼 클릭시 메인화면으로
function goMain(){
    location.href=getContextPath()+'/login/main';
}

// 자기소개 글자수 체크
$('[name=intro]').on('keyup', function() {
	$('#intro_cnt').html("("+$(this).val().length+" / 30)");
 
	if($(this).val().length > 30) {
		$(this).val($(this).val().substring(0, 30));
		$('#intro_cnt').html("(30 / 30)");
	}
});  

// 미리보기 이미지 생성
$('input[name=input_file]').change(e=>{  
	
	if(e.target.files[0].type.includes("image")){ 
     	let reader=new FileReader();
        reader.onload=function(e){
	        const img=$("<img>").attr({
	        	"src":e.target.result,
	            "width":"200px",
	            "height":"200px"
	        });
        	$(".profile-img>img").attr("src",e.target.result);
     	}
     reader.readAsDataURL(e.target.files[0]);   //fileReader의 readAsDateURL메소드 이용
     }
});

// 삭제하기 클릭시 기본이미지로 변경
$('input[name=delete_file]').click(e=>{ 
    $(".profile-img>img").attr('src', getContextPath()+'/images/profile/poom_profile.jpg');
});

//버튼 클릭시 프로필정보 수정
$('button.btn_submit').click(function(){
	$("[name=updatePro_form]").submit();
});


// tab 클릭시 이동 ajax 
$('ul.findtab li').click(function(){
    var tab_id = $(this).attr('data-tab');
    
    $('ul.findtab li').removeClass('tab_on');
    $(this).addClass('tab_on');
    
    if(tab_id=='tab2') {
    	$.ajax({
	        url: getContextPath()+'/member/modiprivacy',
	        success:function(result){
	            $('#info-modi').html(result);
	        }
    	});
    }else {
    	$.ajax({
	        url: getContextPath()+'/member/modiprofile',
	        success:function(result){
	            $('#info-modi').html(result);
	        }
    	});
    }
});
  


//개인정보 js
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
	
		alert("인증번호가 전송되었습니다. 메일을 확인해주세요.");
		$("[name=checked_email]").val("y");
	}
});

//메일 인증번호 비교
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

//비밀번호 확인
$(".pwcheck input").on("keyup",function() {

    var checkResult = $("#pwCheck");
    if($("[name=pw]").val() == $(".pwcheck input").val()) {
        checkResult.html("O 일치");
        checkResult.attr("class", "correct");
    }else {
        checkResult.html("X 불일치");
        checkResult.attr("class", "incorrect");
    }
});

//버튼 클릭시 개인정보 수정
$('button.btn_submitpri').click(function(){

	//비밀번호 유효성검사
	var pw = $("[name=pw]").val();
    if(pw=="") {
        alert("비밀번호를 입력해주세요.");
        $("[name=pw]").focus();
        return false;
    }
	
    //비밀번호 영문소문자+숫자(8~20자리 입력) 정규식
    var pwCheck = /^(?=.*[a-z])(?=.*[0-9]).{8,20}$/;
    var result = pwCheck.exec(pw);

    if(pw != ""&&result == null) {
    	alert("비밀번호는 8~20자의 영어소문자+숫자 조합으로 사용해야 합니다.");
    	$("[name=pw]").focus();
    	return false;
    }
	
	//불일치 유효성검사
	if($("#pwCheck").hasClass("incorrect")==true) {
		alert('비밀번호가 일치하지 않습니다.');
		$("[name=pwk]").focus();
		return false;
	}
	if($("#emailCheck").hasClass("incorrect")==true) {
		alert('인증번호가 일치하지 않습니다.');
		$("[name=emk]").focus();
		return false;
	}
	
	//메일변경시 유효성검사
	var newmail = $("[name=memberEmail]").val();
	var oldmail = $("[name=oldmail]").val();
	if(oldmail==newmail) {
		$("[name=checked_emailNumber]").val("y");
	}
	if(newmail=="" || $("[name=checked_emailNumber]").val()=="") {
		alert('이메일 변경은 인증을 해야합니다.');
		$("[name=memberEmail]").focus();
		return false;
	}
	
	$("[name=updatePri_form]").submit();
});

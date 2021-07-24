$(document).ready(function(){
	
	//tab 클릭 -> ID/PW 찾기 전환
    $('ul.findtab li').click(function(){
        var tab_id = $(this).attr('data-tab');
		$('form').each(function() {
      		this.reset();
 		 });

        $('ul.findtab li').removeClass('tab_on');
        $('.findtab_con').removeClass('tab_on');

        $(this).addClass('tab_on');
        $("#"+tab_id).addClass('tab_on');
        
    });
	
});

//이메일인증 -> 인증키 메일로 보내고 키값 데이터 받아오기
var code = "";

$("button#emailSend").click(function() {// 메일 입력 유효성 검사
	var mail = $("input[name=memberEmail2]").val(); //사용자의 이메일 입력값.
	console.log(mail);
	
	if (mail == "") {
		alert("메일 주소가 입력되지 않았습니다.");
		$("input[name=memberEmail2]").focus();
		return false;
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

//유효성검사
$("button[type=submit]").on("click",function(){
    var email = $("[name=memberEmail2]").val();
    
    if($(this).hasClass('pwBtn')===true) {
	    if($("[name=checked_email]").val() =="") {
	    	alert("이메일을 인증해주세요.");
	        return false;
	    }
	    if($("[name=checked_emailNumber]").val() =="") {
	    	alert("이메일 인증번호가 일치하지 않습니다.");
	    	return false;
	    }
	    $("[name=pwFind_form]").submit();
    }
    
    $("[name=idFind_form]").submit();
    window.open("/duplCheck?type=id&check="+id,"","width=500px,height=300px,top=300px,left=200px");
})

//취소버튼 클릭시 메인페이지로 이동
function goIndex() {
	location.href="/";
}

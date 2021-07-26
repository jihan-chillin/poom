//메인페이지로 이동
function goIndex() {
	opener.goIndex();
	window.close();
}
function goSignUp() {
	opener.goSignUp();
	window.close();
}

//비밀번호 일치여부 보여주기
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
    
//비밀번호 업데이트 폼 보내기
function updatePw() {

	//비밀번호 유효성검사
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
    	return;
    }
    
    if($("[name=memberPw]").val() != $("[name=pwc]").val()) {
    	alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
    	return;
    }
	
    //빈칸 없을 때 제출.
    $("[name=updatePw_form]").submit();
}


//약관동의 스크립트
//모두 체크 or 해제
function allCheck(a) {
    if($(a).prop("checked")){
        $(".nomal").prop("checked",true);
        $("button.agree").prop("disabled",false).addClass('button_able');
    }else {
        $(".nomal").prop("checked",false); 
        $("button.agree").prop("disabled",true).removeClass('button_able'); 
    }
};

//아래 체크갯수에 따라 전체항목 체크여부 바꾸기
function oneCheck(a) {
    var allChkBox = $("#checkAll");
    var chkBoxName = $(a).attr("id");
    
    //전체체크박스 수(모두동의하기 체크박스 제외)
    checkBoxLength = $(".nomal").length;
    //체크된 체크박스 수
    checkedLength = $(".nomal:checked").length;
    
    if( checkBoxLength == checkedLength ) {
        //전체체크박스수 == 체크된 체크박스 수 같다면 모두체크
        allChkBox.prop("checked", true);
        $("button.agree").prop("disabled",false).addClass('button_able');
    }else if( checkedLength == 3 ) {
    console.log("오니?");
		allChkBox.prop("checked", false);
		if($("#check04").is(':checked')) {
        	$("button.agree").prop("disabled",true).removeClass('button_able');	       
		}else {
        	$("button.agree").prop("disabled",false).addClass('button_able');	       
		}
    }else if(checkedLength < 3) {
    	allChkBox.prop("checked", false);
        $("button.agree").prop("disabled",true).removeClass('button_able');
    }
};

//&& $("#check04:checked")
$(function(){
    //모두동의하기 체크박스 클릭시
    $("#checkAll").click(function(){
            allCheck(this);
    });
    $(".nomal").each(function(){
        $(this).click(function(){
            oneCheck(this);
        });
    });
});

//약관동의 후 확인 클릭시 회원가입창으로 이동
$("button.agree").click(function() {
	location.href=getContextPath()+"/login/signup";
});

//취소버튼 클릭시 메인페이지로 이동
function goIndex() {
	location.replace(getContextPath()+"/");
}

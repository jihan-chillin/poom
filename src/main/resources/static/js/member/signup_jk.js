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

function oneCheck(a) {

    var allChkBox = $("#checkAll");
    var chkBoxName = $(a).attr("class");

    if($(a).prop("checked")) {

        //전체체크박스 수(모두동의하기 체크박스 제외)
        checkBoxLength = $("[class="+ chkBoxName +"]").length;
        //체크된 체크박스 수
        checkedLength = $("[class="+ chkBoxName +"]:checked").length;

        if($("input:required").prop("checked")) {
            $("button.agree").addClass('button_able');
        }
        
        if( checkBoxLength == checkedLength ) {
            //전체체크박스수 == 체크된 체크박스 수 같다면 모두체크
            allChkBox.prop("checked", true);
            $("button.agree").addClass('button_able');
        }else {
            allChkBox.prop("checked", false);
        }
    }else {
        allChkBox.prop("checked", false);
        $("button.agree").removeClass('button_able');
    }
};

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



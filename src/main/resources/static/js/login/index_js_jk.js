$("button[type=submit]").on("click",function(){
    if($("#id").val()=="") {
        alert('아이디를 입력하세요!');
        $("#id").focus();
        return false;
    }
    if($("#pw").val()=="") {
        alert('비밀번호를 입력하세요!');
        $("#pw").focus();
        return false;
    }

    //빈칸 없을 때 제출.
    $("[name=login_form]").submit();
})

$(document).ready(function(){

    $('ul.findtab li').click(function(){
        var tab_id = $(this).attr('data-tab');
        console.log(tab_id);

        $('ul.findtab li').removeClass('tab_on');
        $('.findtab_con').removeClass('tab_on');

        $(this).addClass('tab_on');
        $("#"+tab_id).addClass('tab_on');
    });

    $('[name=email]').click(function(){
        $('tr.email_number').show();
    });

});

//취소버튼 클릭시 메인페이지로 이동
function goIndex() {
	location.href="/";
}

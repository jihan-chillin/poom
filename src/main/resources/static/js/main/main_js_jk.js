$(document).ready(function(){
 
    // 좌측메뉴바 show/hide 스크립트
    $('.menu_open').on('click', function(){
        $('.city_bar').show().animate({
            left:0
        }); 
    });
    $('.city_bar>button').on('click', function(){
        $('.city_bar').show().animate({
            left:-300
        }); 
    });

    // main피드 작성시 textarea 영역 변동 스크립트
    $('.feed_write>textarea').keyup(function(e) {
        $(this).css('height','auto');
        $(this).height(this.scrollHeight);
    });

});
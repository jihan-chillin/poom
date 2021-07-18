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

//프로필 부분 채팅아이콘 클릭시 채팅리스트로 이동
function moveChatList(){
    location.replace("/chat/list");
}

//프로필 부분 쪽지아이콘 클릭스 쪽지 페이지로 이동
function messageBox(){
    location.replace("/message");
}


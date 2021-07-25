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
    
    //로고 클릭시 메인화면으로 이동
    $('div.logo').click(function(){
    	location.href="/main";
    });


});


//프로필 부분 쪽지아이콘 클릭스 쪽지 페이지로 이동
function messageBox(){
    location.replace("/message?type=receive");
}

// 프로필 부분 edit버튼 클릭시 정보수정 페이지로 이동
function membermodi(){
    location.assign("/member/modiprofile");
}

function mywrite(){
    location.assign("/mywrite");
}

//로그아웃ui 클릭시 로그아웃+index로 이동
function logOut(){
    location.replace("/logOut");
}
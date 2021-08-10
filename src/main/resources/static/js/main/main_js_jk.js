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
    
    //로고 클릭시 메인화면으로 이동
    $('div.logo').click(function(){
    	location.assign(getContextPath()+"/login/main");
    });
    
});    

//프로필 부분 쪽지아이콘 클릭스 쪽지 페이지로 이동
function messageBox(){
    location.assign(getContextPath()+"/message");
}

// function searchList(){
// 	location.assign(getContextPath()+"search/list");
// }

// 프로필 부분 edit버튼 클릭시 정보수정 페이지로 이동 ajax
function mypage(){
	location.assign(getContextPath()+'/member/mypage');
}

//내가쓴글 페이지로 이동
function mywrite(){
    location.assign(getContextPath()+"/mywrite");
}

//로그아웃ui 클릭시 로그아웃+index로 이동
function logOut(){
    location.replace(getContextPath()+"/login/logOut");
}

function moveToBoard(cate){
	// console.log("넘어가는 주소값 : "+getContextPath()+"/board/view?cate="+cate);
	location.assign(getContextPath()+"/board/view?cate="+cate);
}

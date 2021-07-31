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
    	location.href=getContextPath()+"/login/main";
    });
});

// main피드 작성시 textarea 영역 변동 스크립트
function resize(obj) {
	obj.style.height = '1px';
    obj.style.height = obj.scrollHeight + 'px';
    obj.style.background = '#fdfdfd';
    obj.style.borderRadius = '10px';
}


//프로필 부분 쪽지아이콘 클릭스 쪽지 페이지로 이동
function messageBox(){
    location.assign(getContextPath()+"/message");
}

// 프로필 부분 edit버튼 클릭시 정보수정 페이지로 이동 ajax
function membermodi(){
	$('.feed').remove();
  	$('.profile').remove();
  	$('.rank').remove();

  $.ajax({
    url: getContextPath()+'/member/modiprofile',
    success:function(data){
        $('#content').html(data)
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}

function mywrite(){
    location.assign(getContextPath()+"/mywrite");
}

//로그아웃ui 클릭시 로그아웃+index로 이동
function logOut(){
    location.replace(getContextPath()+"/login/logOut");
}


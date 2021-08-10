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

//왼쪽 카테고리 누르면 해당 카테고리 페이지 이동 ajax
// function moveToBoard(cate){
// 	$('.feed_write').remove();
// 	$('.feed_new').remove();
//
// 	$('.feed').css({
// 		"background": "#f7f7f7","border-radius":"20px",
// 		"height" : "800px"
// 	});
// 	$.ajax({
// 		url:getContextPath()+"/board/boardList",
// 		data:{"cate":cate},
// 		success:function (result){
// 			$('.feed').html(result)
//
// 		},
// 		error:(e,m,i)=>{
// 			console.log(e);
// 			console.log(m);
// 			console.log(i);
// 		}
// 	})
// }

function moveToBoard(cate){
	// console.log("넘어가는 주소값 : "+getContextPath()+"/board/view?cate="+cate);
	location.assign(getContextPath()+"/board/view?cate="+cate);
}
	
	function pay_check(loc){
		var payLevel=$("div.city_bar").children("p").text();
		if($("div#nav>p").text()==loc || payLevel=='1'){
			location.assign(getContextPath()+"/board/all");
		}else{
			if(confirm("이용권이 필요한 메뉴입니다. 이용권을 구매하시겠습니까?")===true){
				location.assign(getContextPath()+"/pay");
			}
		}
	}

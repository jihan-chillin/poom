$(document).ready(function(){
 	feedNew();
 	
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
function mypage(){
	$('.feed').remove();
  	$('.profile').remove();
  	$('.rank').remove();

    $.ajax({
    	url: getContextPath()+'/member/mypage',
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

//내가쓴글 페이지로 이동
function mywrite(){
    location.assign(getContextPath()+"/mywrite");
}

//로그아웃ui 클릭시 로그아웃+index로 이동
function logOut(){
    location.replace(getContextPath()+"/login/logOut");
}

function feedWrite() {
	var title = $("[name=title]").val();
	var content = $("[name=content]").val();

		if(title == ""){
        alert("제목을 입력해주세요.");
        $("[name=title]").focus();
        return false;
    }
    if(content == ""){
        alert("내용을 입력해주세요.");
        $("[name=content]").focus();
        return false;
    }
		// 태그 입력창이 비어있는지 확인하는 메소드
		return checkTagInputEmpty();

		// 사용자가 입력한 태그 등록하는 메소드
		addTagEach(getConfirmTag());

    $("[name=feedWrite_form]").submit();
} 

//지역 on,off
var check = $("input[name=loc_check]");
check.click(function(){
	$("p.onoffBtn").toggle();
	feedNew();
});

//feed_new tab 클릭시 전환
$('ul.feedtab li').click(function(){

    $('ul.feedtab li').removeClass('feedtab_on');
    $(this).addClass('feedtab_on');
    feedNew();
    
});
    
function feedNew() {
	var check = $("input[name=loc_check]");
	var loc="";
	if($(check).prop("checked")) {
		loc = $("p.onoffBtn:eq(0)").html();
	}else {
		loc = $("p.onoffBtn:eq(1)").html();
	}
	
	var list="";
	if($("ul.feedtab>li").hasClass('feedtab_on')) {
		list = $("li.feedtab_on").attr('id');
	}
	var id=$("input[name=id]").val();
	
	console.log(loc);
	console.log(list);
	console.log(id);
	
	$.ajax({
		url: getContextPath()+"/board/feedNew",
		data:{
			"loc" : loc,
			"list" : list,
			"id" : id
		},
		success:data=>{
			$("#feed_content").html(data);
		}
	})
}
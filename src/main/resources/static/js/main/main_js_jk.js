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
		//return checkTagInputEmpty();

		// 사용자가 입력한 태그 등록하는 메소드
		//addTagEach(getConfirmTag());

    $("[name=feedWrite_form]").submit();
} 

//지역 on,off(결제권없으면 alert창 뜸)
var check = $("input[name=loc_check]");
var payLevel = $("input[name=payLevel]").val();
var loc = $("p.onoffBtn").html();
check.click(function(){
	if(payLevel!=0){
		if(check.is(':checked')==true) {
			$("span.round").addClass("slider");
			$("p.onoffBtn").html("전국");
			check.removeAttr('checked');
		}else {
			$("span.round").removeClass("slider");
			$("p.onoffBtn").html(loc);
			check.attr('checked');
		}
		feedNew();
	}else {
		alert('결제권을 구매하시면 전국게시글을 보실 수 있습니다.');
	}
});

//feed_new tab 클릭시 전환
$('ul.feedtab li').click(function(){

    $('ul.feedtab li').removeClass('feedtab_on');
    $(this).addClass('feedtab_on');
    feedNew();
    
});
    
function feedNew() {
	var loc=$("p.onoffBtn").html();
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

//왼쪽 카테고리 누르면 해당 카테고리 페이지 이동 ajax
function moveToBoard(cate){
	$('.feed_write').remove();
	$('.feed_new').remove();

	$('.feed').css({
		"background": "#f7f7f7","border-radius":"20px",
		"height" : "800px"
	});
	$.ajax({
		url:getContextPath()+"/board/boardList",
		data:{"cate":cate},
		success:function (result){
			$('.feed').html(result)

		},
		error:(e,m,i)=>{
			console.log(e);
			console.log(m);
			console.log(i);
		}
	})
}

//피드 글자수(200자) 체크 + 제한
$('[name=content]').on('keyup', function() {
	$('#intro_cnt').html("("+$(this).val().length+" / 200)");
 
	if($(this).val().length > 200) {
		$(this).val($(this).val().substring(0, 200));
		$('#intro_cnt').html("(200 / 200)");
	}
}); 
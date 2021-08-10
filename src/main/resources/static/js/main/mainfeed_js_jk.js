$(document).ready(function(){
 	feedNew();
});    

function feedWrite() {
	var title = $("[name=title]").val();
	var content = $("[name=content]").val();

    var tCheck = /^.{0,20}$/;
    var result = tCheck.exec(title); 
	if(title == ""){
        alert("제목을 입력해주세요.");
        $("[name=title]").focus();
        return false;
    }
    if(result == null) {
    	alert("제목은 30자 이내로 입력해주세요.");
    	$("[name=title]").focus();
    	return false;
    }
    if(content == ""){
        alert("내용을 입력해주세요.");
        $("[name=content]").focus();
        return false;
    }
    
    
	// 태그 입력창이 비어있는지 확인하는 메소드
	if( !checkTagInputEmpty()){
		return false;
	}

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
			$("label.loc_switch").css({"background":"darkgray"});
			check.removeAttr('checked');
		}else {
			$("span.round").removeClass("slider");
			$("p.onoffBtn").html(loc);
			$("label.loc_switch").css({"background":"#247094"});
			check.attr('checked');
		}
		
		feedNew();
	}else {
		var confirmResult = confirm('결제권을 구매하시면 전국게시글을 보실 수 있습니다. 확인을 누르면 결제창으로 이동합니다.');
		if(confirmResult==true) {
			location.assign(getContextPath()+"/pay");
		}
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
	
	$.ajax({
		url: getContextPath()+"/board/feedNew",
		data:{
			"loc" : loc,
			"list" : list,
			"id" : id,
		},
		success:data=>{
			$("#feed_content").html(data);
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

//마우스 호버시 툴팁박스 나오기
var toolTip = $('div.feed_write button:first-of-type');
toolTip.hover(function() {
	$(this).children('img').show();
}, function() {
	$(this).children('img').hide();
});

//무한스크롤
$(function(){
	let target =$('div.fList'); 
    let breakList = 5;
	let listCount = 2;
      
    let winTop;
    let onTop;
    
    function getList() {
    	let result;
    	
    	var loc=$("p.onoffBtn").html();
		var list="";
		if($("ul.feedtab>li").hasClass('feedtab_on')) {
			list = $("li.feedtab_on").attr('id');
		}
		var id=$("input[name=id]").val();
		
		$.ajax({
			url: getContextPath()+"/board/feedNew",
			async: false,
			data:{
				"loc" : loc,
				"list" : list,
				"id" : id,
				"cPage" : listCount
			},
			success: function(data) {
				result = data;
			}
		});
    	
    	if(listCount>breakList) {
    		result=null;
    	}else {
    		result;
    	}
    	listCount++;
    	return result;
    }
    
    $('div.feed').scroll(function() {
        winTop=$(this).scrollTop();
        onTop=$('div.feed').height() - $('div.feed_write').height() - $('div.fList').height();
        console.log(onTop);

        if(winTop>=onTop){ 
          let data =getList(); 

          if(data !== null){
            $('div#feed_content').append(data);
          }else{
            return false;
          }
        }
      });
      
      
});
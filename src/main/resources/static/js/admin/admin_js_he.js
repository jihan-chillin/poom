//관리자 메뉴바 토글
$("#blame_menu").parent().click((e)=>{
            $("#blame_menu").toggle(500);
});
//type에 따라 메뉴 이름 색상 넣기
/*$(function(){
	var adminTitle=$("#admin_page_title").html();
	console.log(adminTitle);
	if(adminTitle=="blame"){
		$("#blame_menu").parent().css("color","#F77B1E");
		$("#blame_menu").show();
		$("#blame_menu").children().first().css({"color":"#F77B1E","list-style-type": "disc"});
	}else if(adminTitle=="notice"){
		$("#admin_menu>ul>li:first").css("color","#F77B1E");
	}else if(adminTitle=="pay"){
		$("#admin_menu>ul>li:last").css("color","#F77B1E");
	}
});*/

//공지사항 글쓰기 이동
function fn_notice_write(){
	$.ajax({
		url:getContextPath()+"/admin/moveWrite",
	}).done(function (fragment){
		$("#admin_section").html(fragment);
	});
}

//공지사항 등록버튼
function fn_notice_submit(){
	$.ajax({
		url:getContextPath()+"/admin/noticeWrite",
		data: $("#notice_form").serialize(),
		type:"POST"
	}).done(function (fragment){
		alert('공지사항이 등록되었습니다.');
		$("#target").html(fragment);
	}).fail(function(){
			alert('공지사항 등록 오류!! 모든 항목을 입력해주세요.');
	})
}

//공지사항 취소버튼
function fn_cancle(){
	if(confirm('작성을 취소하시겠습니까?')){
		$.ajax({
			url:getContextPath()+"/admin/notice",	
		}).done(function(fragment){
			$("#target").html(fragment);
		})
	}
}

//공지사항 목록으로 돌아가ㅣㄱ
function moveToList(){
	$.ajax({
		url:getContextPath()+"/admin/notice"
	}).done(function (fragment){
		$("#target").html(fragment);
	})
}



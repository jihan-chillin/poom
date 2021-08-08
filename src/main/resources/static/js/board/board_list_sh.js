
// // 전체 글 보기 ajax
// function allBoardList(){
// 	$('.feed_write').remove();
// 	$('.feed_new').remove();
//
// 	$('.feed').css({
// 		"background": "#f7f7f7","border-radius":"20px",
// 		"height" : "800px"
// 	});
//
// 	$.ajax({
// 		url:getContextPath()+"/board/all",
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


//글쓰기 버튼 클릭시 글작성 페이지로 이동
function goWriteForm() {

	location.assign(getContextPath()+"/board/form");
}

// 게시판리스트에서 글작성 페이지로 이동 ajax
function moveTooWriteForm(){
	$('#board-list-catebar').remove();
	$('#board_list-table').remove();
	$('.feed').css({"background": "#f7f7f7","border-radius":"20px"});

	$.ajax({
		url:getContextPath()+'/board/form',
		success:function(data){
			$('.feed').html(data)
		},
		error:(e,m,i)=>{
			console.log(e);
			console.log(m);
			console.log(i);
		}
	});
}

//게시판리스트에서 view로 이동 ajax
function feedToView(no){
	$.ajax({
		url:getContextPath()+"/board/view",
		data : {
			"boardNo":no
		},
		success:data=>{
			$("#content").html(data);
		}
	})
}

//게시판에서 공지사항 들어가기
function fn_moveBoardNotice(){
	$.ajax({
		url:getContextPath()+"/board/boardNotice",
		data:{
			"no":1							//나중에 no값 받아서 처리하기!!!! 
		}
	}).done(function (fragment){
		$("#content").html(fragment);
	})
}

//3번째 공지부턴 숨기기
$(function(){
	$("[name=noticeList]>li:eq(1)").nextAll().hide();
	$("[name=noticeList]>li:last").show();

	$("[name=noticeList]>li:last").click(e=>{
		$("[name=noticeList]>li:eq(1)").nextAll().slideToggle();
		$("[name=noticeList]>li:last").show();
	})
})

//전체글ajax페이징처리
function fn_alllist_paging(i){
	$.ajax({
		url:getContextPath()+"/board/allAjax",
		data:{
			"cPage":i,
		}
	}).done(function(fragment){
		$("#board_box").html(fragment);
	});
};


//게시글 뷰에서 목록으로 연결하기
function move_list(cate) {
	//cate==all =>전체글에서 눌렀음
	if (cate == 'all') {
		$.ajax({
			url: getContextPath() + "/board/all",
			success: data => {
				$("div#content").html(data);
			}
		});
	}else if(cate=='feed'){
		location.assign(getContextPath()+"/login/main/");
	} else {
		$.ajax({
			url: getContextPath() + "/board/cateList",
			data: {
				"cate": cate
			},
			success: data => {
				$("div#content").html(data);
			}
		});
	}
}

//댓글 가져오는 메소드
function comment_list(){
	$.ajax({
		url:getContextPath()+"/comment/list",
		data:{
			boardNo:$("#comment_boardNo").text()
		},
		success:data=>{
			$("div#comment_container>ul").html(data);
		}
	});
}

//댓글 입력
function comment_write(){
	var commentContent=$("#commentContent").val();
	var boardNo=$("#comment_boardNo").text();
	if(commentContent==""){
		alert("댓글 내용을 입력하세요.");
	}else{
		$.ajax({
			url:getContextPath()+"/comment/write",
			method:"POST",
			data:{
				boardNo:boardNo,
				commentContent:commentContent
			},
			success:data=>{
				comment_list();
				// 알림 전송하는 함수 by 희웅
				sendNoti();
			}
		});
	}
}

//댓글 삭제기능
function delete_comment(target){
	//console.log("test");
	//console.log($(target));
	var commentNo=$(target).siblings("span.commentNo").text();
	$.ajax({
		url:getContextPath()+"/comment/delete",
		data:{
			boardNo:$("#comment_boardNo").text(),
			commentNo:commentNo
		},
		success:data=>{
			alert("댓글이 삭제되었습니다.");
			comment_list();
		}
	});
}

//수정화면 나오도록 하기
function change_modify(target){
	$(target).parent().parent().find("span#original_content");
	$(target).parent().parent().find("div.modify_container").css("display", "block");
	$(target).parent().parent().find("div.modify_container").find("input.modify_content").text();
	$(target).parent().parent().find("div.modify_container").find("input").text($("span.original_content").text());
}

//댓글 수정기능
function modify_comment(e){
	$.ajax({
		url:getContextPath()+"/comment/modify",
		data:{
			boardNo:$("#comment_boardNo").text(),
			commentNo:commentNo,
			commentContent:"테스트"
		},
		success:data=>{
			console.log("수정메소드");
		}
	});
}


$(function(){
	comment_list();

	$("span.b_comment_menu").click(e=>{
		if($(e.target).next("div").css("display")=="none") {
			$("span.b_comment_menu").next("div").css("display", "none");
			$(e.target).next("div").css("display", "block");
		}else{
			$(e.target).next("div").css("display", "none");
		}
	});
});

//게시판 view에서 삭제하기 구현
//진짜 삭제 아닌 del_status=>1로 변경 //restController
function fn_board_delete(no,cate){
	if(confirm("해당 게시글을 정말 삭제하시겠습니까?")){
		$.ajax({
			url:getContextPath()+"/board/boardDelete",
			data:{"no":no}
		}).done(function (fragment){
			alert('삭제가 완료되었습니다');
			move_list(cate);
		}).fail(function(fragment){
			alert('삭제 실패! 관리자에게 문의하세요');
		})
	}
}

function boardrealModi(boardNo){
	console.log("모디");
	location.assign(getContextPath()+"/board/modi?boardNo="+boardNo);
}
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
	boardNo=$("#comment_boardNo").text();
	
	console.log(boardNo);
	$.ajax({
		url:getContextPath()+"/comment/list",
		data:{
			boardNo:boardNo
		},
		success:data=>{
			$("div#comment_container>ul").html(data);
		}
	});
}

//댓글 입력
function comment_write(bNo){
	var commentContent=$("#commentContent").val();
	console.log(commentContent);
	if(commentContent==""){
		alert("댓글 내용을 입력하세요.");
	}else{
		$.ajax({
			url:getContextPath()+"/comment/write",
			method:"POST",
			data:{
				boardNo:bNo,
				commentContent:commentContent
			},
			success:data=>{
				comment_list();
				$("#commentContent").val("");
				// 알림 전송하는 함수 by 희웅
				sendNoti();
			}
		});
	}
}

//답글 입력
function recomment_write(target){
	var recommentContent=$(target).siblings("input").val();
	var boardNo=target.getAttribute("data-bNo");
	var boardCommentRef=target.getAttribute("data-cRef");
	console.log(recommentContent);
	if(commentContent==""){
		alert("댓글 내용을 입력하세요.");
	}else{
		$.ajax({
			url:getContextPath()+"/recomment/write",
			method:"POST",
			data:{
				boardNo:boardNo,
				commentContent:recommentContent,
				boardCommentRef:boardCommentRef,
				commentLevel:2
			},
			success:data=>{
				comment_list();
				$("#commentContent").val("");
				// 알림 전송하는 함수 by 희웅
				sendNoti();
			}
		});
	}
}

//댓글 삭제기능
function delete_comment(cNo, bNo){
	$.ajax({
		url:getContextPath()+"/comment/delete",
		data:{
			boardNo:bNo,
			commentNo:cNo
		},
		success:data=>{
			alert("댓글이 삭제되었습니다.");
			comment_list();
		}
	});
}

//수정화면 나오도록 하기
function change_modify(target){
	var li=$(target).parent().parent().parent();
	//원래내용 지우기
	li.find("span.original_content").css("display","none");
	//수정화면 보이게 하기
	li.find("span.modify_container").css("display", "inline-block");
	
	//아직 글내용 받아오게 안함-수정하기
	li.find("span.modify_container").find("input.modify_content").val(li.find("span.original_content").text());
}

//댓글 수정기능
function modify_comment(target){
	var commentContent=$(target).siblings("input").val();
	var commentNo=target.getAttribute('data-cNo');
	console.log(commentNo, commentContent);
	$.ajax({
		url:getContextPath()+"/comment/modify",
		data:{
			boardNo:$("#comment_boardNo").text(),
			commentNo:commentNo,
			//commentContent:$(target).siblings("input.modify_content").val()
			commentContent:commentContent
		},
		success:data=>{
			console.log("수정메소드");
			comment_list();
		}
	});
}

//댓글수정 취소하기
function modify_cancel(target){
	var li=$(target).parent().parent();
	//원래내용 보이게하기
	li.find("span.original_content").css("display","inline-block");
	//수정화면 안보이게 하기
	li.find("span.modify_container").css("display", "none");
}

//댓글 답글 작성화면 보이게 하는 함수
function view_recomment(target){
	if($(target).next("div").css("display")=="none") {
		$(target).next("div").css("display", "block");
	}else{
		$(target).next("div").css("display", "none");
	}
}

$(function(){
	comment_list();
	
	//댓글 메뉴 보이게하기
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
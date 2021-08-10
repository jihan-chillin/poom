//게시글 목록으로 이동
function move_list(){
	location.assign(getContextPath()+"/board/all");
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

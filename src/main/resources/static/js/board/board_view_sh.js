//게시글 뷰에서 목록으로 연결하기
function move_list(cate){
	//cate==all =>전체글에서 눌렀음
	if(cate=='all'){
		$.ajax({
			url:getContextPath()+"/board/all",
			success:data=>{
				$("div#content").html(data);
			}
		});
	}else{
		$.ajax({
			url:getContextPath()+"/board/cateList",
			data:{
				"cate":cate
			},
			success:data=>{
				$("div#content").html(data);
			}
		});
	}
}
const comment_write=()=>{
	
	$.ajax({
		url:getContextPath()+"/board/comment",
		success:data=>{
			$("div#comment_container").append("li").html(data);
		}
	});
}
$(".comment_menu>p").click(e=>{
	if($(e.target).siblings("ul").first().css("display")=="block"){
		$(e.target).siblings("ul").first().css("display", "none");
	}else{
		$(e.target).siblings("ul").first().css("display", "block");
	}
});

//게시판 view에서 삭제하기 구현 
//진짜 삭제 아닌 del_status=>1로 변경
function fn_board_delete(no){
	alert(no);
}
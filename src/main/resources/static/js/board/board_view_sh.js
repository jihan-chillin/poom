//게시글 뷰에서 목록으로 연결하기
function move_list(cate){
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

const move_list=()=>{
	$.ajax({
		url:"/board/all",
		success:data=>{
			$("div#content").html(data);
		}
	});
}
const comment_write=()=>{
	
	$.ajax({
		url:"/board/comment",
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
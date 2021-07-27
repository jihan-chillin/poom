// const writeBoard=()=>{
// 	location.assign("/board/form")
// 	// $.ajax({
// 	// 	url:"/board/form",
// 	// 	success:data=>{
// 	// 		$("div#content").html(data);
// 	// 	},
// 	// 	error:function(r,s,e){
// 	// 		console.log(r, s, e);
// 	// 	}
// 	// });
// }

// 전체 글 보기 ajax
function allBoardList(){
	$('.feed_write').remove();
	$('.feed_new').remove();

	$.ajax({
		url:"/board/all",
		success:function (result){
			$('.feed').html(result)
			$('.feed').attr('style', 'height:1000px')
		},
		error:(e,m,i)=>{
			console.log(e);
			console.log(m);
			console.log(i);
		}
	})
}

const movetoForm=()=>{
	location.assign("/board/form");
}

$("[name=boardDetail]").click((e)=>{

	$('#board-whole-content').remove();

	const boardNo = e.target.title;
	$.ajax({
		url:"/board/view",
		data:{
			"boardNo" : boardNo
		}
	}).done(function(result){
		$(".feed").html(result);
	});
});

// const viewBoard=()=>{
//
// 	$('#board-whole-content').remove();
//
// 	$.ajax({
// 		url:"/board/view?boardNo=",
// 		data:{
// 			"boardNo" : boardNo
// 		},
// 		success:function(data){
// 			$(".feed").html(data)
// 		}
// 	});
// }
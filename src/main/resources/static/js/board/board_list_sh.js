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

const movetoForm=()=>{
	location.assign("/board/form");
}

const viewBoard=()=>{
	$.ajax({
		url:"/board/view?boardNo=2",
		success:data=>{
			$("div#content").html(data);
		},
		error:function(r,s,e){
			console.log(r,s,e);
		}
	});
}
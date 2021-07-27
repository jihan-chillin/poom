
// 전체 글 보기 ajax
function allBoardList(){
	$('.feed_write').remove();
	$('.feed_new').remove();

	$('.feed').css({
		"background": "#f7f7f7","border-radius":"20px",
		"height" : "800px"
	});

	$.ajax({
		url:"/board/all",
		success:function (result){
			$('.feed').html(result)

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



//글쓰기 버튼 클릭시 글작성 페이지로 이동
function goWriteForm() {
	$('.feed_write').remove();
  	$('.feed_new').remove();
  	$('.feed').css({"background": "#f7f7f7","border-radius":"20px"});

  $.ajax({
    url:'/board/form',
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
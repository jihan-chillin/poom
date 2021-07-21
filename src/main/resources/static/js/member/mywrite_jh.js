// 1. 내가 쓴 댓글 영역 ajax
function mycomment(){
    $('#mywrite-content').remove();

    $.ajax({
        url:'/member/mycomment',
        success:function(result){
            $('#ajaxcontent').html(result)
        }
    })
}

//2. 내가 찜한 글 영역 ajax
function mylike(){
    $('#mywrite-content').remove();

    $.ajax({
        url:'/member/mylike',
        success:function(result){
            $('#ajaxcontent').html(result)
        }
    })
}

// 3. 다른 카테고리에서 내가 쓴 글 영역 넘어갈 때
function barmywrite(){
    location.assign("/member/mywrite");
}
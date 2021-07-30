function mywrite(){
    location.replace(getContextPath()+"/mywrite");
}

// 1. 내가 쓴 댓글 영역 ajax
function mycomment(){
    $('#mywrite-content').remove();
    $('#write-list').remove();

    $.ajax({
        url:getContextPath()+'/mycomment',
        success:function(result){
            $('.feed').html(result)
            $('.feed').attr('style', 'height:1000px')
        }
    })
}

//2. 내가 찜한 글 영역 ajax
function mylike(){
    $('#mywrite-content').remove();

    $.ajax({
        url:getContextPath()+'/mylike',
        success:function(result){
            $('.feed').html(result)
        }
    })
}

// 3. 다른 카테고리에서 내가 쓴 글 영역 넘어갈 때
function barmywrite(){
    location.assign(getContextPath()+"/mywrite");
}


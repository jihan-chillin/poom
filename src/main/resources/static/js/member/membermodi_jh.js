// 뒤로가기
function back(){
    history.back();
}

// 1. 미리보기 이미지 생성
$('#input-file').change(function(){
    setImageFromfile(this, '#img');
});

function setImageFromfile(input, expression){
    if(input.files && input.files[0]){
        var reader = new FileReader();
        reader.onload = function(e){
            $(expression).attr('src',e.target.result);
            $(expression).css({
                'width' : '100%',
                'height' : '100%',
                'border-radius' : '20px',
                'object-fit' : 'cover'
            })
        }
        reader.readAsDataURL(input.files[0]);
    }
}

// 2. 미리보기 이미지 삭제하기
$('delete-file').click(function(){
   var targetImg = $('#img');
   targetImg.remove();
})


// 3. 개인정보 수정 영역 ajax
function modiprivacy(){
    // 정보수정 카테고리 삭제
    $('#info-bar').remove();
    // 정보수정란 삭제
    $('#info-modi').remove();

    $.ajax({
        url:'/member/modiprivacy',
        success:function(result){
            $('#modi-wrapper').html(result)
        }
    })
}

// 4. 개인정보 영역에서 프로필 수정 영역으로 넘어갈때
function modiprofile(){
    location.assign("/member/modiprofile");
}




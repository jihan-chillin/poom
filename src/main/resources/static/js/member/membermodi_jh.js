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


// 3. 프로필부분 & 인기 키워드 삭제하고
// 멤버 정보 수정 페이지 -> ajax 적용 ( 프로필 수정,
// function membermodi(){
//     // 피드영역 삭제
//     $('.feed_write').remove();
//     $('.feed_new').remove();
//     // 핫키워드 삭제
//     $('.rank').remove();
//
//     // ajax 적용하기
//     $.ajax({
//         url:'/member/modiprofile',
//         success:function(data){
//             $('.feed').html(data)
//         },
//         error:(e,m,i)=>{
//             console.log(e);
//             console.log(m);
//             console.log(i);
//         }
//     });
//
// }
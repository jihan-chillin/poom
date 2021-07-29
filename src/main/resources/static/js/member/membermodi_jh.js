// 뒤로가기
function back(){
    history.back();
}

// 1. 미리보기 이미지 생성
$('#input-file').change(function(){
    setImageFromfile(this, '#img');
});

function setImageFromfile(input, expression){
    // 인풋태그에 파일이 있는 경우
    if(input.files && input.files[0]){

        // FileReader 인스턴스 생성
        var reader = new FileReader();

        // 이미지가 로드가 되면
        reader.onload = function(e){
            $(expression).attr('src',e.target.result);
            $(expression).css({
                'width' : '100%',
                'height' : '100%',
                'border-radius' : '20px',
                'object-fit' : 'cover'
            })
        }
        // reader가 이미지를 읽도록 하기
        reader.readAsDataURL(input.files[0]);
    }
}

// 2. 미리보기 이미지 삭제하기
$('#input-file').change(setImageFromfile);
$('.profile-txt').on('click', '#delete-file', function () {
    $(".profile-img").empty()
    $("#input-file").val("");
});

// 3. tab 클릭시 이동 ajax 
$('ul.findtab li').click(function(){
    var tab_id = $(this).attr('data-tab');
    
    $('ul.findtab li').removeClass('tab_on');
    $(this).addClass('tab_on');
    
    if(tab_id=='tab2') {
    	$.ajax({
	        url:'/member/modiprivacy',
	        success:function(result){
	            $('#info-modi').html(result);
	        }
    	});
    }else {
    	$.ajax({
	        url:'/member/modiprofile',
	        success:function(result){
	            $('#content').html(result);
	        }
    	});
    }
});

// 4. 개인정보 영역에서 프로필 수정 영역으로 넘어갈때
function modiprofile(){
    location.assign("/member/modiprofile");
}




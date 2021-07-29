var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';

// 뒤로가기
function back(){
    history.back();
}

// 미리보기 이미지 생성
$('input[name=input_file]').change(e=>{  
	
	if(e.target.files[0].type.includes("image")){ 
     	let reader=new FileReader();
        reader.onload=function(e){
	        const img=$("<img>").attr({
	        	"src":e.target.result,
	            "width":"200px",
	            "height":"200px"
	        });
        	$(".profile-img>img").attr("src",e.target.result);
     	}
     reader.readAsDataURL(e.target.files[0]);   //fileReader의 readAsDateURL메소드 이용
     }
});

// 2. 미리보기 이미지 삭제하기
$('input[name=delete_file]').click(e=>{ 
    $(".profile-img>img").attr('src','/images/profile/poom_profile.jpg');
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





// 취소버튼 클릭시 메인화면으로
function goMain(){
    location.href=getContextPath()+'/login/main';
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

// 삭제하기 클릭시 기본이미지로 변경
$('input[name=delete_file]').click(e=>{ 
    $(".profile-img>img").attr('src', getContextPath()+'/images/profile/poom_profile.jpg');
});

// 3. tab 클릭시 이동 ajax 
$('ul.findtab li').click(function(){
    var tab_id = $(this).attr('data-tab');
    
    $('ul.findtab li').removeClass('tab_on');
    $(this).addClass('tab_on');
    
    if(tab_id=='tab2') {
    	$.ajax({
	        url: getContextPath()+'/member/modiprivacy',
	        success:function(result){
	            $('#info-modi').html(result);
	        }
    	});
    }else {
    	$.ajax({
	        url: getContextPath()+'/member/modiprofile',
	        success:function(result){
	            $('#content').html(result);
	        }
    	});
    }
});

// 자기소개 글자수 체크
$('[name=intro]').on('keyup', function() {
	$('#intro_cnt').html("("+$(this).val().length+" / 30)");
 
	if($(this).val().length > 30) {
		$(this).val($(this).val().substring(0, 30));
		$('#intro_cnt').html("(30 / 30)");
	}
});    

$('button.btn_submit').click(function(){
	$("[name=updatePro_form]").submit();
});




//관리자 메뉴바 토글
$("#blame_menu").parent().click((e)=>{
            $("#blame_menu").toggle(500);
});
//type에 따라 메뉴 이름 색상 넣기
$(function(){
	var adminTitle=$("#admin_page_title").html();
	console.log(adminTitle);
	if(adminTitle=="blame"){
		$("#blame_menu").parent().css("color","#F77B1E");
		$("#blame_menu").show();
		$("#blame_menu").children().first().css({"color":"#F77B1E","list-style-type": "disc"});
	}else if(adminTitle=="notice"){
		$("#admin_menu>ul>li:first").css("color","#F77B1E");
	}else if(adminTitle=="pay"){
		$("#admin_menu>ul>li:last").css("color","#F77B1E");
	}
});
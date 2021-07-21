//관리자 메뉴바 토글
$("#blame_menu").parent().click((e)=>{
            $("#blame_menu").toggle(500);
});
//type에 따라 메뉴 이름 색상 넣기
/*$(function(){
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
});*/

//공지사항 글쓰기 이동
function fn_notice_write(){
	$.ajax({
		url:"/admin/moveWrite",
	}).done(function (fragment){
		$("#admin_section").html(fragment);
	});
}

//공지사항 글쓰기 체크박스
// $(".allCheck").click(e=>{
// 	alert('체크');
// })
//공지사항 등록버튼
function fn_notice_submit(){
	alert('공지사항이 등록되었습니다.');
	$.ajax({
		url:"/admin/noticeWrite",
		data: $("#notice_form").serialize(),
		type:"POST"
	}).done(function (fragment){
		$("#target").html(fragment);
	})
}



//공지사항 취소버튼
function fn_cancle(){
	if(confirm('작성을 취소하시겠습니까?')){
		$.ajax({
			url:"/admin/notice",	
		}).done(function(fragment){
			$("#target").html(fragment);
		})
	}
}






//ajax시도해보기
//Chart.js

/*$(document).ready(function(){
	function cash_chart(){
		new Chart(document.getElementById("line-chart"), {
		type: 'line',
		data: {
		    labels: [1,2,3,4,5,6,7],
		    datasets: [{ 
		        label: "7일권",
		        data: [2900,5800,8700,0,2900,2900,8700],
		        borderColor: "#4285F4",
		        fill: false
		    }, { 
		        data: [0,0,4900,14700,9800,9800,4900],
		        label: "14일권",
		        borderColor: "#DB4437",
		        fill: false
		    }, { 
		        data: [11800,5900,0,0,59000,29500,11800],
		        label: "30일권",
		        borderColor: "#F4B400",
		        fill: false
		    }
		    ]
		},
		options: {
		    title: {
		    display: true,
		    text: '이번주 결제 현황'
		    }
		}
		});
	}
	cash_chart();
}); */



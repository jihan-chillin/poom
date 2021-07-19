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

//ajax시도해보기
//Chart.js

$(document).ready(function(){
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
});

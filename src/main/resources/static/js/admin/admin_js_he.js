//관리자 메뉴바 토글
$("#blame_menu").parent().click((e)=>{
            $("#blame_menu").toggle(500);
});

//공지사항 글쓰기 이동
function fn_notice_write(){
	$.ajax({
		url:getContextPath()+"/admin/moveWrite",
	}).done(function (fragment){
		$("#admin_section").html(fragment);
	});
}

//공지사항 등록버튼
function fn_notice_submit(){
	$.ajax({
		url:getContextPath()+"/admin/noticeWrite",
		data: $("#notice_form").serialize(),
		type:"POST"
	}).done(function (fragment){
		alert('공지사항이 등록되었습니다.');
		$("#target").html(fragment);
	}).fail(function(){
			alert('공지사항 등록 오류!! 모든 항목을 입력해주세요.');
	})
}

//공지사항 취소버튼
function fn_cancle(){
	if(confirm('작성을 취소하시겠습니까?')){
		$.ajax({
			url:getContextPath()+"/admin/notice",	
		}).done(function(fragment){
			$("#target").html(fragment);
		})
	}
}

//공지사항 목록으로 돌아가기
function moveToList(){
	$.ajax({
		url:getContextPath()+"/admin/notice"
	}).done(function (fragment){
		$("#target").html(fragment);
	})
}

//차트 js
//날짜셋팅
var day1=[[${days[0]}]];
var day2=[[${days[1]}]];
var day3=[[${days[2]}]];
var day4=[[${days[3]}]];
var day5=[[${days[4]}]];
var day6=[[${days[5]}]];
var day7=[[${days[6]}]];

//chart.js
//차트 세팅하기
var ctx=document.getElementById("line-chart");
var config = {
  type: 'line',
  data:{
	    labels: [day7,day6,day5,day4,day3,day2,day1],
	    datasets: [{ 
		        label: "7일권",
		        data: [0,0,0,0,0,0,0],
		        borderColor: "#4285F4",
		        fill: false
		    }, { 
		        data: [0,0,0,0,0,0,0],
		        label: "14일권",
		        borderColor: "#DB4437",
		        fill: false
		    }, { 
		        data: [0,0,0,0,0,0,0],
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
};
		
//차트 그리기
var myChart=new Chart(ctx,config);

//데이터 변경
var dataset = config.data.datasets;
//dataset의 인덱스 번호로 지정해서 값 넣어주기
var dataArray=[];
dataArray=[[${sumList}]];
var data=dataset[0].data;
for(var i=0;i<dataArray.length;i++){
	if(dataArray[i].ITEM_TYPE=='7'){
		if(day7==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[0]=dataArray[i].S;
		}
		if(day6==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[1]=dataArray[i].S;
		}
		if(day5==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[2]=dataArray[i].S;
		}
		if(day4==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[3]=dataArray[i].S;
		}
		if(day3==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[4]=dataArray[i].S;
		}
		if(day2==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[5]=dataArray[i].S;
		}
		if(day1==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[6]=dataArray[i].S;
		}
	}
}
var data=dataset[1].data;
for(var i=0;i<dataArray.length;i++){
	if(dataArray[i].ITEM_TYPE=='14'){
		if(day7==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[0]=dataArray[i].S;
		}
		if(day6==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[1]=dataArray[i].S;
		}
		if(day5==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[2]=dataArray[i].S;
		}
		if(day4==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[3]=dataArray[i].S;
		}
		if(day3==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[4]=dataArray[i].S;
		}
		if(day2==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[5]=dataArray[i].S;
		}
		if(day1==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[6]=dataArray[i].S;
		}
	}
}
var data=dataset[2].data;
for(var i=0;i<dataArray.length;i++){
	if(dataArray[i].ITEM_TYPE=='30'){
		if(day7==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[0]=dataArray[i].S;
		}
		if(day6==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[1]=dataArray[i].S;
		}
		if(day5==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[2]=dataArray[i].S;
		}
		if(day4==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[3]=dataArray[i].S;
		}
		if(day3==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[4]=dataArray[i].S;
		}
		if(day2==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[5]=dataArray[i].S;
		}
		if(day1==new Date(dataArray[i].PAY_DATE).getMonth()+1+"월 "+new Date(dataArray[i].PAY_DATE).getDate()+"일"){
			data[6]=dataArray[i].S;
		}
	}
}
myChart.update();


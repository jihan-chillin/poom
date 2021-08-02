'use strict';

// 모임 날짜 가져오는 메소드
function getGatherDate(){
  let gatherDate = $('.gather-date').val();
  let date = gatherDate.split("-");
  return date;
}

// 오늘 날짜 가져오는 메소드
function getToday(){
  let today = new Date();
  let refDate=[today.getFullYear(),today.getMonth()+1,today.getDate()];
  return refDate;
}
// 오늘 날짜로 고정하는 함수
function dayFixedToday(){
  let gatherDate = $('.gather-date');
  let year = getToday()[0];
  let month = getToday()[1].toString();
  let date = getToday()[2].toString();

  // 달이나 일이 한자리 이면 앞에 0붙임
  if(month.length === 1){
    month = "0"+month;
  }

  if(date.length === 1){
    date = "0"+date;
  }

  alert("모임 날짜는 오늘 이후만 선택가능합니다.");
  gatherDate.val(year+"-"+month+"-"+date);
}

// 날짜 오늘 이후로 로직
$('.gather-date').change(e=>{

  if(getToday()[0] > getGatherDate()[0]){
   dayFixedToday();

  }else if(getToday()[0] <= getGatherDate()[0]){
    if(getToday()[1] > getGatherDate()[1]){
      dayFixedToday();
      return;

    }else if(getToday()[1] <= getGatherDate()[1]){
      if(getToday()[2] > getGatherDate()[2]){
        dayFixedToday();
        return;
      }
    }
  }
});
'use strict';

function searchRank(){
  $.ajax({
    url:getContextPath()+'/rank/searching',
    success:data=>{
     $('.rank>ul>*').remove();

      let val ='';
      for(let i=0; i<data.length; i++){
        val +='<li>'+(i+1)+'.'+data[i].BOARD_TITLE+'</li>'
      }

      $('.rank>ul').append(val);
    }
  });
}
searchRank();
// 1분 30초마다 랭킹 새로고침
setInterval(searchRank,90000);

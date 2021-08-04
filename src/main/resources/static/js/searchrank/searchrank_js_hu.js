'use strict';

// 랭킹 가져오는 함수
function searchRank(){
  $.ajax({
    url:getContextPath()+'/rank/searching',
    success:data=> {
      $('.rank>ul>*').remove();
      let val = '';
      for (let i = 0; i < data.length; i++) {
        val += '<li>' + (i + 1) + '.' + data[i].BOARD_TITLE + '</li>'
      }

      $('.rank>ul').append(val);
      $('.rank>ul>li').click(e=>{
        moveBoard(data[$(e.target).text().substring(0,1)-1].BOARD_NO);
      });
    }
  });
}

searchRank();
// 1분 30초마다 랭킹 새로고침
setInterval(searchRank,90000);

// 클릭시 게시글 상세화면으로 이동
function moveBoard(boardNo){
  $.ajax({
    url:getContextPath()+'/board/view',
    data:{
      "boardNo":boardNo
    },
    success:function(data){
      $('.feed').remove();
      $('.rank').remove();
      $('#content').html(data)
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}
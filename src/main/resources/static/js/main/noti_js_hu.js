'use strict';
$(function (){
  const notiModal= $('#modal');
  const doc = $('body');

// li.click(e=>{
//   if(modal.css("display")==='none'){
//     modal.css("display","");
//   }
// });

  doc.click(e=>{
    let ref =$(e.target)[0].classList;

    if(ref.contains('noti_icon')){
      notiModal.css("display","");

    }else if(ref.contains("noti-info-alarm")){
      return;
    }else if(ref.contains("noti-info-message")){
      return;
    }else if(ref.contains("noti-content-icon")){
      return;
    }else if(ref.contains("noti-detail")){
      return;
    }else if(ref.contains("noti-info-alarm")){
      return;
    }else if(ref.contains("modal-title")){
      return;
    } else if(ref.contains("modal-content-list")){
      return;
    }else if(ref.contains("noti-content-title")){
      return;
    }else if(ref.contains("noti-content-box")) {
      return;
    } else{
      notiModal.css("display","none");

    }
  });


});

// 알림창 데이터 트리거
$('.noti_icon').click(e=>{
  getNotificationData();
});

// 알림창 데이터 가져옴
function getNotificationData(){
  $.ajax({
    url:getContextPath()+'/noti/my/data',
    data:{
      "loginid":$('#loginMember_id').text()
    },
    success:data=>{
      /*
        notiData = 알림 데이터
        boardTitleFromBoardNo = 좋아요 게시글 제목
        getBoardTitleFromCommentNo = 댓글 달린 게시글 제목
        getMsgContentFromMsgNo = 쪽지 내용
       */
      $('.modal-content-list>*').remove();

      let val = '';

      for(let i =0; i<data.notiData.length; i++){
        val += '<div class="noti-content-box">';
          val += '<div class="noti-img">';

        // 댓글
        if(data.notiData[i].NOT_TYPE === '0'){
            val += '<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="Pencil"><path d="M4.333 16.048L16.57 3.81a2.56 2.56 0 0 1 3.62 3.619L7.951 19.667a2 2 0 0 1-1.022.547L3 21l.786-3.93a2 2 0 0 1 .547-1.022z"/><path d="M14.5 6.5l3 3"/></svg>';
            val += '</div>';

            val += '<div class="noti-detail">';
              val += '<span>댓글 알림</span>';
              val += '<span class="noti-content-title">';
                val += '\''+data.getBoardTitleFromCommentNo+'\'에 댓글이 달렸습니다.';



        }else if(data.notiData[i].NOT_TYPE === '1'){//쪽지
            val += '<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="Envelope"><path d="M2 6a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6z"/><path d="M2 8l7.501 6.001a4 4 0 0 0 4.998 0L22 8"/></svg>'
            val += '</div>';

            val += '<div class="noti-detail">';
              val += '<span>쪽지 알림</span>';
              val += '<span class="noti-content-title">';
                val += data.getMsgContentFromMsgNo.substring(0,21);

        }else{// 좋아요
            val += '<svg xmlns="http://www.w3.org/2000/svg" class="noti-content-icon" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="Heart"><path d="M7 3C4.239 3 2 5.216 2 7.95c0 2.207.875 7.445 9.488 12.74a.985.985 0 0 0 1.024 0C21.125 15.395 22 10.157 22 7.95 22 5.216 19.761 3 17 3s-5 3-5 3-2.239-3-5-3z"/></svg>';
            val += '</div>';

            val += '<div class="noti-detail">';
              val += '<span>좋아요 알림</span>';
              val += '<span class="noti-content-title">';
                val += '\''+data.boardTitleFromBoardNo+'\'에 좋아요가 눌렸습니다.';
        }

              val += '</span>';
            val += '</div>';
          val += '</div>';
      }

      $('.modal-content-list').append(val);

    }
  })
}

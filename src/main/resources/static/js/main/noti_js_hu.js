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

  // 알림창 최초 데이터 가져옴
  getNotificationData();

  // 쪽지함 클릭시
  $('.noti-info-message').click(e=>{
    $('.noti-info-message').attr('style','color:#000000; border-bottom: 3px solid #000000;');
    $('.noti-info-alarm').attr('style','color:#d3d3d3; border:none;');

    getMessageDataToNotify();
  });

  // 알림함 클릭시
  $('.noti-info-alarm').click(e=>{
    $('.noti-info-alarm').attr('style','color:#000000; border-bottom: 3px solid #000000;');
    $('.noti-info-message').attr('style','color:#d3d3d3;');

    getNotificationData();
  });

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
      let val2 ='<div class="alarm-count"><span>N</span></div></div>';

      if(data.boardTitleFromBoardNo.length === 0){
        val += '<div class="noti-content-box">';
        val += '<div class="noti-img">';
        val += '<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="CircleAlert"><circle cx="12" cy="12" r="10"/><path d="M12 7v6m0 3.5v.5"/></svg>';
        val += '</div>';

        val += '<div class="noti-detail"><a><span>알림이 없습니다.</span></a></div></div>';

        $('.modal-content-list').append(val);
        $('.noti_icon').attr("style","background: url("+getContextPath()+"/images/ui/alarm_normal.png) no-repeat center; background-size: contain;");
        return;
      }


      for(let i =0; i<data.notiData.length; i++){
        $('.noti_icon').attr("style","background: url("+getContextPath()+"/images/ui/alarm_receive.png) no-repeat center; background-size: contain;");
        val += '<div class="noti-content-box">';
          val += '<div class="noti-img">';

        // 댓글
        if(data.notiData[i].NOT_TYPE === '0'){
            val += '<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="Pencil"><path d="M4.333 16.048L16.57 3.81a2.56 2.56 0 0 1 3.62 3.619L7.951 19.667a2 2 0 0 1-1.022.547L3 21l.786-3.93a2 2 0 0 1 .547-1.022z"/><path d="M14.5 6.5l3 3"/></svg>';
            val += '</div>';

            val += '<div class="noti-detail">';
              val += '<span>댓글 알림</span>';
              val += '<span class="noti-content-title">';
                val += '\''+data.getBoardTitleFromCommentNo[i]+'\'에<br> 댓글이 달렸습니다.';



        }else if(data.notiData[i].NOT_TYPE === '1'){//쪽지
            val += '<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="Envelope"><path d="M2 6a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6z"/><path d="M2 8l7.501 6.001a4 4 0 0 0 4.998 0L22 8"/></svg>'
            val += '</div>';

            val += '<div class="noti-detail">';
              val += '<span>쪽지 알림</span>';
              val += '<span class="noti-content-title">';
                val += data.getMsgContentFromMsgNo[i].substring(0,21);

        }else{// 좋아요
            val += '<svg xmlns="http://www.w3.org/2000/svg" class="noti-content-icon" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="Heart"><path d="M7 3C4.239 3 2 5.216 2 7.95c0 2.207.875 7.445 9.488 12.74a.985.985 0 0 0 1.024 0C21.125 15.395 22 10.157 22 7.95 22 5.216 19.761 3 17 3s-5 3-5 3-2.239-3-5-3z"/></svg>';
            val += '</div>';

            val += '<div class="noti-detail">';
              val += '<span>좋아요 알림</span>';
              val += '<span class="noti-content-title">';
                val += '\''+data.boardTitleFromBoardNo[i]+'\'에<br>  좋아요가 눌렸습니다.';
        }

              val += '</span>';
            val += '</div>';
          val += '</div>';

      }

      $('.modal-content-list').append(val);
      $('.noti-info-alarm').append(val2);

      $('.noti_icon').attr("style","background: url("+getContextPath()+"/images/ui/alarm_receive.png) no-repeat center; background-size: contain;");
    }
  });
}

function getMessageDataToNotify(){
  $.ajax({
    url:getContextPath()+'/noti/my/data',
    data:{
      "loginid":$('#loginMember_id').text()
    },
    success:data=>{
      /*
        notiData = 알림 데이터
        getMsgContentFromMsgNo = 쪽지 내용
       */
      $('.modal-content-list>*').remove();

      let val = '';
      let val2 ='<div class="alarm-count"><span>N</span></div></div>';

      if(data.getMsgContentFromMsgNo.length === 0){
        val += '<div class="noti-content-box">';
        val += '<div class="noti-img">';
        val += '<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="CircleAlert"><circle cx="12" cy="12" r="10"/><path d="M12 7v6m0 3.5v.5"/></svg>';
        val += '</div>';

        val += '<div class="noti-detail"><a><span>쪽지가 없습니다.</span></a></div></div>';
        $('.modal-content-list').append(val);

        return;
      }

      for(let i =0; i<data.getMsgContentFromMsgNo.length; i++){
        val += '<div class="noti-content-box">';
        val += '<div class="noti-img">';

        // 댓글
        if(data.notiData[i].NOT_TYPE === '1'){//쪽지
          val += '<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" display="block" id="Envelope"><path d="M2 6a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6z"/><path d="M2 8l7.501 6.001a4 4 0 0 0 4.998 0L22 8"/></svg>'
          val += '</div>';

          val += '<div class="noti-detail">';
          val += '<span>쪽지 알림</span>';
          val += '<span class="noti-content-title">';
          val += data.getMsgContentFromMsgNo[i].substring(0,21);
          $('.noti-info-alarm').append(val2);

        }

        val += '</span>';
        val += '</div>';
        val += '</div>';
        val2 += '<div class="alarm-count"><span>N</span></div></div>';

      }

      $('.modal-content-list').append(val);
      $('.noti-info-message').append(val2);

    }
  })
}

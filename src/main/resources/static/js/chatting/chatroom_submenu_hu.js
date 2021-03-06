'use strict';
// 이미 관심목록 등록 됐는지 조회
function checkAlreadyInterestedChatroom(chatNo,memberId){
  $.ajax({
    url:getContextPath()+'/chat/room/check/inter',
    data:{
      "chatNo":chatNo,
      "memberId":memberId,
    },
    success:data=>{
      if(data === 1){
        return interestedChatroom(true,chatNo,memberId);
      }else{
        return interestedChatroom(false,chatNo,memberId);
      }
    }
  });

  // return blameChatroom(false);
}
function interestedChatroom(check,chatNo,memberId){
  if(check){
    alert("이미 관심목록에 있는 채팅방입니다.");

    if(!confirm("관심 채팅방에서 삭제하시겠습니까?")){
      return;
    }else{
      enrollInterestChatroom(chatNo,memberId);
    }

    return;
  }else{
    if(!confirm("관심채팅방에 추가하시겠습니까?")){
      return;
    }else{
      enrollInterestChatroom(chatNo,memberId);
    }
  }
}

// 관심 채팅방으로 등록
function enrollInterestChatroom(chatNo,memberId){
  $.ajax({
    url:getContextPath()+'/chat/room/like',
    data:{
      "chatNo":chatNo,
      "memberId":memberId
    },
    success:data=>{
      if(data === 1){
        alert("관심채팅방으로 등록되었습니다.");
      }else{
        alert("다시 시도해주세요");
      }
    }
  });
}
// 관심 채팅방에서 삭제
function deleteInterestChatroom(chatNo,memberId){
  $.ajax({
    url:getContextPath()+'/chat/room/unlike',
    data:{
      "chatNo":chatNo,
      "memberId":memberId
    },
    success:data=>{
      if(data === 1){
        alert("관심채팅방에서 삭제되었습니다.");
        location.replace("/21AM_POOM_final/login/main");

      }else{
        alert("다시 시도해주세요");
      }
    }
  });
}

// 이미 신고됐는지 테이블 조회
function checkAlreadyBlame(chatNo,memberId){
  $.ajax({
    url:getContextPath()+'/chat/room/check/blame',
    data:{
      "chatNo":chatNo,
      "memberId":memberId,
    },
    success:data=>{
      if(data === 1){
        alert("이미 신고한 채팅방입니다.");
        return;
      }else{
        blameChatroom(chatNo,memberId);
      }
    }
  });

  // return blameChatroom(false);
}

function blameChatroom(chatNo,memberId){
    if(!confirm("이 채팅방을 신고하시겠습니까?")){
      return;
    }else{
    	window.open(getContextPath()+"/blame/report?type=ch"+"&no="+chatNo+"&id="+memberId,"","width=495px,height=530px,top=260px,left=800px,status=no,toolbar=no,scrollbars=no");
  }

}
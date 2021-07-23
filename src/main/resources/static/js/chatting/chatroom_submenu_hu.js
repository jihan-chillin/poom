'use strict';
// 이미 관심목록 등록 됐는지 조회
function checkAlreadyInterestedChatroom(chatNo,memberId){
  $.ajax({
    url:'/chat/room/check"',
    data:{
      "chatNo":chatNo,
      "memberId":memberId,
      "ref":"inter"
    },
    success:data=>{
      if(data === 1){
        return interestedChatroom(true,chatNo,memberId);
      }else{
        return interestedChatroom(false);
      }
    }
  });

  return blameChatroom(false);
}
function interestedChatroom(check,chatNo,memberId){
  if(check){
    alert("이미 관심목록에 있는 채팅방입니다.");
    return;
  }else{
    if(!confirm("관심채팅방에 추가하시겠습니까?")){
      return;
    }else{
      $.ajax({
        url:'/chat/room/like',
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
  }
}

// 이미 신고됐는지 테이블 조회
function checkAlreadyBlame(chatNo,memberId){
  $.ajax({
    url:'/chat/room/check"',
    data:{
      "chatNo":chatNo,
      "memberId":memberId,
      "ref":"blame"
    },
    success:data=>{
      if(data === 1){
        return blameChatroom(true,chatNo,memberId);
      }else{
        return blameChatroom(false);
      }
    }
  });

  return blameChatroom(false);
}

function blameChatroom(check,chatNo,memberId){
  if(check){
    alert("이미 신고한 채팅방입니다.");
    return;
  }else{
    if(!confirm("이 채팅방을 신고하시겠습니까?")){
      return;
    }else{

      // 신고페이지 연결해줘야함.
      $.ajax({
        url:'',
        data:{
          "chatNo":chatNo,
          "memberId":memberId
        },
        success:data=>{
          if(data === 1){
            alert("신고가 완료되었습니다.");
          }else{
            alert("다시 시도해주세요");
          }
        }
      });

    }
  }

}
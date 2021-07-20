'use strict';

//프로필 부분 채팅아이콘 클릭시 채팅리스트로 이동
function moveMyChatList(){
  // location.replace("/chat/list");
  $('.feed_write').remove();
  $('.feed_new').remove();

  $.ajax({
    url:'/chat/mylist/page',
    success:function(data){
        $('.feed').html(data)
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });

  $.ajax({
    url:'/chat/mychat/list',
    success:data=>{
      $('.chatroom').children().remove();
      const chatNo = data.list.CHAT_NO;
      let val = '';

      if(chatNo !== null){
        // 채팅방 리스트가 있다면

        if(data.list.CATEGORY_NO ==='0'){
          val+= '<span class="chatroom-icon-study">스터디</span>'
        }else{
          val+= '<span class="chatroom-icon-gather">소모임</span>'
        }
        val+= '<span class="chatroom-title">';
        val+= '<span onclick="moveMyChatroom('+chatNo+')">'+data.list.CHAT_TITLE+'</span></span>';
        val+= '<span><img style="width:25px; height: 25px;"></span>';
        // 채팅방 참여인원수
        val+= '<span>'+data.countMember+'</span>';
        val+= '<span>/</span>';
        // 채팅방 제한 인원
        val+= '<span>'+data.list.CHAT_PERSON+'명</span>';

        // 채팅방 번호 전달
        // $('.chatroom-title>span').click(e=>{
        //   moveMyChatroom(data.list.CHAT_NO);
        //
        // });

      }else{
        // 참여중인 채팅방이 없으면.
        val += '<div></div>'
        val += '<div id="nochatroom">참여중인 채팅이 없습니다.</div>'
      }

      $('.chatroom').append(val);

    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}

// 내 채팅방리스트에서 채팅방 제목 클릭했을때 채팅방으로 이동
function moveMyChatroom(chatNo){
  // 채팅방리스트 -> 내 채팅방으로 이동
  // 데이터 보내고
  // 페이지 가져오기
  $.ajax({
    url:'/chat/chatroom/page',
    success:data=>{
      $('#chatlist-container').remove();
      $('.feed').html(data);
      $('.feed').attr('style','height:905px')
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
  const url ='/chat/mychat/member';

  // 참여인원 불러오기
  getMyChatroom(chatNo,url);

  // 채팅내용 불러오기
  getChatList(chatNo,url);

}
// 참여인원 불러오기
function getMyChatroom(chatNo,url){
  $.ajax({
    url:url,
    data:{
      "chatNo":
      chatNo
    },
    success:data=>{
      $('.entered-mem').remove();
      let val ='';

      for(let i =0; i < data.list.length; i++){
        val += '<div class="entered-mem"><span>';
        val += '<img width="30" height="30"></span>';
        val += '<span>'+data.list[i].MEMBER_ID+'</span></div>';
      }
      $('.entered-mem-list>div').html(val);

    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}

// 채팅방 내용 불러오기
function getChatList(chatNo,url){
  $.ajax({
    url:url,
    data:{
      "chatNo":
      chatNo
    },
    success:data=>{
      console.log(data);

      $('.msg-container>*').remove();
      let val = '';
      // 로그인 한 아이디와 일치하면
      // .my-profile .message-orange
      // 다르면
      // .others-profile .message-blue
      // 나눠야함.
      // 로그인이 안만들어져서 test 아이디로 함. -> 수정필요함.
      let loginId = 'test';

      for(let i=0; i<data.messageContent.length; i++){
      // 내가 쓴 메세지 일때
        if(loginId === data.messageContent[i].MEMBER_ID){
          val += '<div  class="my-profile">';
          val += '<img width="30px" height="30px">'+data.messageContent[i].MEMBER_ID+'</div>';
          val += '<div class="message-orange"><div class="message-content">';
          val += data.messageContent[i].MESSAGE_CONTENT+'</div></div>';
        }else{
          val += '<div class="others-profile">';
          val += '<img width="30px" height="30px">'+data.messageContent[i].MEMBER_ID+'</div>';
          val += '<div class="message-blue"><div class="message-content">';
          val += data.messageContent[i].MESSAGE_CONTENT+'</div></div>';
        }
      }

      $('.msg-container').append(val);
    }
  });
}

function moveChatList(){
  $('.feed_write').remove();
  $('.feed_new').remove();

  $.ajax({
    url:'/chat/list/page',
    success:function(data){
      $('.feed').html(data);
      $('.feed').attr('style','height:905px');
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });

  $.ajax({
    url:'/chat/list/data',
    success:data=>{
      $('.chatroom-list-container>*').remove();
      let val = '';
      console.log(data);

      for(let i=0; i<data.chatList.length; i++){

        val +='<div class="chatroom-info">';
        val +='<div class="chatroom-info-header"><div>';

        if(data.chatList[i].CATEGORY_NO ==='0'){
          val +='<span class="chatroom-icon">스터디</span></div>';
        }else{
          val +='<span class="chatroom-icon">소모임</span></div>';
        }

        // 모집중 // 모집마감 설정
        // chat_person하고 현재 채팅방에 있는 사람 수 비교해야함.
        if(data.chatList[i].CHAT_PERSON > data.chatRoomMemCount[i] ){
          val += '<div><span>모집중</span></div>';
        }else{
          val += '<div><span>모집마감</span></div>';
        }

        val += '<div><div>...</div></div></div>'
        val += '<div class="chatroom-content-container"><div class="chatroom-content-title">';
        // 제목
        val += data.chatList[i].CHAT_TITLE +'</div><div class="chatroom-content-info">';
        // 내용
        val += data.chatList[i].CHAT_CONTENT +'</div></div>';

        val += '<div class="chatroom-mem"><span> </span>';
        val += '<span><img style="width:25px; height: 25px;"></span>';
        val += '<span>'+data.chatRoomMemCount[i]+'명</span>';
        val += '<span>/</span>';
        val += '<span>'+data.chatList[i].CHAT_PERSON+'명</span></div></div></div>';
      }

      $('.chatroom-list-container').append(val);
    }
  });
}

function moveChatListDetail(){

}
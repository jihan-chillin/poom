'use strict';

//프로필 부분 채팅아이콘 클릭시 채팅리스트로 이동
function moveChatList(){
  // location.replace("/chat/list");
  $('.feed_write').remove();
  $('.feed_new').remove();

  $.ajax({
    url:'/chat/list',
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
        val+= '<span>1</span>';
        val+= '<span>/</span>';
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
    url:'/chat/chatroom',
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
      let val = '';
      console.log(data);
      // 로그인 한 아이디와 일치하면
      // .my-profile .message-orange
      // 다르면
      // .others-profile .message-blue
      // 나눠야함.
      // 로그인이 안만들어져서 그냥 함. -> 수정필요함.

    }
  });
}
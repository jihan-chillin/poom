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
      if(data.list[0].length === 0){
        return;
      }else{
        $('.chatroom').remove();

        for(let i = 0; i<data.list.length; i++){
          let val = '';

          const chatNo = data.list[0][i].CHAT_NO;
          // 로그인 아이디.
          const memberId = data.loginId;

          if(chatNo !== null){
            // 채팅방 리스트가 있다면
            val +='<li class="chatroom">';
            if(data.list[0][i].CATEGORY_NO ==='1'){
              val+= '<span class="chatroom-icon-study">스터디</span>'
            }else{
              val+= '<span class="chatroom-icon-gather">소모임</span>'
            }
            val+= '<span class="chatroom-title">';
            val+= '<span onclick="moveMyChatroom(\''+chatNo+'\',\''+memberId+'\')">'+data.list[0][i].CHAT_TITLE+'</span></span>';
            val+= '<span></span>';
            // 채팅방 참여인원수
            val+= '<span>'+data.countMember[i]+'</span>';
            val+= '<span>/</span>';
            // 채팅방 제한 인원
            val+= '<span>'+data.list[0][i].CHAT_PERSON+'명</span>';
            val+='</li>';

            $('#chatroom-list>ul').append(val);
            return;
          }
        }
      }
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}
function checkEnterChatroom(memberId,chatNo){
  $.ajax({
    url:'/chat/chatroom/check',
    data:{
      "chatNo":chatNo,
      "memberId":memberId
    },
    success: data=>{
      // 입장해 있다면
      if (data === 1){
        moveMyChatroom(chatNo,memberId);
      }else{
        if(confirm("채팅방에 입장하시겠습니까?")){

          if(enterChatroom(chatNo,memberId,'/chat/chatroom/enter') === 1){
            moveMyChatroom(chatNo,memberId);
          }else{
            alert("채팅방에 입장하지 못했습니다. 다시 시도해주세요");
            return;
          }

        }else{
          return;
        }
      }
    }

  });

  return false;
}

// 채팅방으로 이동
function moveMyChatroom(chatNo,memberId){
  // 참여한 채팅방으로 이동

  // 데이터 보내고
  // 페이지 가져오기
  $.ajax({
    url:'/chat/chatroom/page',
    data:{
      "chatNo":
      chatNo
    },
    success:data=>{
      $('#chatlist-container').remove();
      $('.feed').html(data);
      $('.feed').append(
        '<input type="hidden" class="chatNo" value="'+chatNo+'">'
      );
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
  getChatList(chatNo,url,memberId);

  return;
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
      const memberId = data.loginMember.MEMBER_ID;
      $('.entered-mem').remove();
      $('.chatroom-header>*').remove();
      let val ='';

      for(let i =0; i < data.list.length; i++){
        val += '<div class="entered-mem"><span>';
        val += '<img width="30" height="30"></span>';
        val += '<span>'+data.list[i].MEMBER_ID+'</span></div>';
      }
      $('.entered-mem-list>div').html(val);

      let val2 = '';
      if(data.chatData.CATEGORY_NO ==='1'){
        val2 += '<span class="chatroom-icon-study">스터디</span>';
      }else{
        val2 += '<span class="chatroom-icon-gather">소모임</span>';
      }
      val2 += '<div>'+data.chatData.CHAT_TITLE+'</div>';
        val2 += '<div>';
          val2 += '<span>...</span>';
          val2 += '<div class="chatroom-submenu">';
            val2 += '<div><span class="interested-chatroom" onclick="checkAlreadyInterestedChatroom('+data.chatData.CHAT_NO+',\''+memberId+'\')">관심 채팅방에 추가</span></div>';
            val2 += '<div><span class="blame-chatroom" onclick="checkAlreadyBlame('+data.chatData.CHAT_NO+',\''+memberId+'\')">신고하기</span></div>';
        val2 += '</div>';
      val2 += '</div>';

      $('.chatroom-header').append(val2);
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}

// 채팅방 내용 불러오기
function getChatList(chatNo,url,memberId){
  $.ajax({
    url:url,
    data:{
      "chatNo":
      chatNo
    },
    success:data=>{
      $('.msg-container>*').remove();
      let val = '';
      // 로그인 한 아이디와 일치하면
      // .my-profile .message-orange
      // 다르면
      // .others-profile .message-blue
      // 나눠야함.
      // 로그인이 안만들어져서 test 아이디로 함. -> 수정필요함.
      let loginId =  memberId;

      for(let i=0; i<data.messageContent.length; i++){
      // 내가 쓴 메세지 일때
        if(loginId === data.messageContent[i].MEMBER_ID){
          val += '<div class="my-profile">';
          // 아바타 만들기
          val +='<i class="chat-avatar">'+(data.messageContent[i].MEMBER_ID).substring(0,1)+'</i>';
          val += data.messageContent[i].MEMBER_ID+'</div>';

          val += '<div class="message-orange"><div class="message-content">';
          val += data.messageContent[i].MESSAGE_CONTENT+'</div></div>';
        }else{
          val += '<div class="others-profile">';
          // 아바타 만들기
          val +='<i class="chat-avatar">'+(data.messageContent[i].MEMBER_ID).substring(0,1)+'</i>';
          val += data.messageContent[i].MEMBER_ID+'</div>';
          val += '<div class="message-blue"><div class="message-content">';
          val += data.messageContent[i].MESSAGE_CONTENT+'</div></div>';
        }


      }
      $('.msg-container').append(val);

      // 색상 속성 추가.
      for(let i =0; i<data.messageContent.length; i++){
        $('.chat-avatar').attr("style",'background-color:'+getAvatarColor(data.messageContent[i].MEMBER_ID));
      }
    }
  });
}
// 채팅방 참여하기
function enterChatroom(chatNo,memberId,url){

  $.ajax({
    url:url,
    data:{
      "chatNo":chatNo,
      "memberId":memberId
    },
    success:data=>{
      if( data == 1){
        return 1;
      }else{
        return 0;
      }
    }
  });

  return 0;
}


function moveChatList(){
  $('.feed>*').remove();

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
      const memberId = data.loginMember.MEMBER_ID;

      $('.chatroom-list-container>*').remove();
      let val = '';
      for(let i=0; i<data.chatList.length; i++){

        val +='<div class="chatroom-info">';
        val +='<div class="chatroom-info-header"><div>';

        if(data.chatList[i].CATEGORY_NO ==='1'){
          val +='<span class="chatroom-icon-study">스터디</span></div>';
        }else{
          val +='<span class="chatroom-icon-gather">소모임</span></div>';
        }

        // 모집중
        // 모집마감 설정
        if(data.chatList[i].CHAT_PERSON > data.chatRoomMemCount[i] ){
          val += '<div><span>모집중</span></div>';
        }else{
          val += '<div><span>모집마감</span></div>';
        }

        val += '<div>';
          val += '<span>...</span>';
          val += '<div class="chatroom-submenu">';
              val += '<div><span class="interested-chatroom" onclick="checkAlreadyInterestedChatroom('+data.chatList[i].CHAT_NO+',\''+memberId+'\')">관심 채팅방에 추가</span></div>';
              val += '<div><span class="blame-chatroom" onclick="checkAlreadyBlame('+data.chatList[i].CHAT_NO+',\''+memberId+'\')">신고하기</span></div>';
          val += '</div>';
        val += '</div></div>';


        val += '<div class="chatroom-content-container"><div class="chatroom-content-title" onclick="moveChatListDetail('+data.chatList[i].CHAT_NO+');">';
        // 제목
        val += data.chatList[i].CHAT_TITLE +'</div><div class="chatroom-content-info">';
        // 내용
        val += data.chatList[i].CHAT_CONTENT +'</div></div>';

        val += '<div class="chatroom-mem"><div> </div>';
        val += '<div><i class="gg-user"></i></div>';
        val += '<div>'+data.chatRoomMemCount[i]+'명</div>';
        val += '<div>/</div>';
        val += '<div>'+data.chatList[i].CHAT_PERSON+'명</div></div></div></div>';
      }

      $('.chatroom-list-container').append(val);
    }
  });
}

// 세부 채팅방 세부화면으로
// 화면 가져오기용.
function moveChatListDetail(chatNo){
  $('.feed>*').remove();

  $.ajax({
    url:'/chat/list/detail',
    data:chatNo,
    success:data=>{
     $('.feed').html(data);
     $('.feed').attr('style','height:905px; width:100%;');
    }
  });
  chatListDetailData(chatNo);
}

function chatListDetailData(chatNo){

  $.ajax({
    url:'/chat/detail/data',
    data:{
      "chatNo":
      chatNo
    },
    success:data=>{
      const memberId = data.loginMember.MEMBER_ID;

      let val = '';
      val +='<link rel="stylesheet" type="text/css" href="/css/chatting/chatroom-list-detail.css">';
      // header
      val += '<div class="chatroom-header"><div>';

      if(data.chatData.CATEGORY_NO === '1'){
        val += '<span class="chatroom-icon-study"> 스터디</span></div>';
      }else{
        val += '<span class="chatroom-icon-gather"> 소모임</span></div>';
      }

      val += '<div>'+data.chatData.CHAT_TITLE+"/"+data.chatData.GROUP_DATE.substring(0,10)+'</div>';
      val += '<div>';
      val += '<span>...</span>';
      val += '<div class="chatroom-submenu">';
      val += '<div><span class="interested-chatroom" onclick="checkAlreadyInterestedChatroom('+data.chatData.CHAT_NO+',\''+memberId+'\')">관심 채팅방에 추가</span></div>';
      val += '<div><span class="blame-chatroom" onclick="checkAlreadyBlame('+data.chatData.CHAT_NO+',\''+memberId+'\')">신고하기</span></div>';
      val += '</div></div></div></div>';

      // nav
      val += '<div class="chatroom-nav"><div class="chatroom-status">';
      if(data.chatData.CHAT_PERSON > data.memCount){
        val +='모집중</div>';
      }else{
        val +='모집마감</div>';
      }

      val += '<div class="chatroom-maker">';
      val += '<span> 모임장 </span>';
      val += '<span>'+data.chatData.MEMBER_ID+'</span></div></div>';

      val += '<div class="chatroom-content"><div>';
      val += data.chatData.CHAT_CONTENT;
      val += '</div></div>';

      val += '<div class="chatroom-const">';
        val += '<div>';
          val += '<div><i class="gg-user"></i></div>';
          val += '<div>'+data.memCount+'명</div>';
          val += '<div> / </div>';
          val += '<div>'+data.chatData.CHAT_PERSON +'명</div>';
        val += '</div>';

      val += '<div><span><i class="gg-key"></i></span>';
      val += '<span>'+data.chatData.CHAT_CONDITION+'</span></div></div>';

      val += '<div class="enter-btn"><div>';
      val += '<form onsubmit="return checkEnterChatroom(\''+memberId+'\','+data.chatData.CHAT_NO+')">';
      val += '<input type="submit" value="참여하기"></form>';
      val += '</div>';

      $('.chatroom-header').remove();
      $('.chatroom-nav').remove();
      $('.chatroom-content').remove();
      $('.chatroom-const').remove();
      $('.enter-btn').remove();

      $('#chatroom-container').html(val);

     if($('.chatroom-status').text() ==='모집마감'){
       $('.enter-btn>div>form').remove();
       $('.enter-btn>div').html("모집이 마감되었습니다.")
     }

    }
  });
}

// 채팅방 만들기 페이지로 이동
// create-chatroom.css 수정해야함.
// radio 크기
// button 모양등
function createChatroom(){
  $.ajax({
    url:'/chat/room/page',
    success:data=>{
      $('.feed>*').remove();
      $('.feed').html(data);

      countMem();
    }
  });
  return false;
}
// 채팅방 인원수 증감
function countMem(){
  const plus = $('.increase-mem');
  const min = $('.decrease-mem');
  // const val = $('.memCount').text();
  let countMem =1;

  plus.click(e=>{
    countMem =countMem+1;
    $('.memCount').text(countMem);
  });

  min.click(e=>{
    countMem = countMem - 1;

    if(countMem<1){
      alert("참여인원은 최소한 1명이어야합니다.");
    }else {
      $('.memCount').text(countMem);
    }
  });

}

// 채팅방 데이터 가져오기

function chatroomData(){
  let category = '';

  if($('[name=select-cate]:checked').val() === 'stu'){
    category='1';
  }else{
    category='0';
  }

  const title = $('.chatroom-title').val();
  const content = $('.chatroom-content').val();
  const condition = $('.chatroom-condition').val();
  const memCount =$('.memCount').text();
  const gatherDate = $('.gather-date').val();
  const memberId = $('#memberId').val();

  return {category, title, content, condition, memCount, gatherDate,memberId};
}

// 채팅방 만드는 데이터 보내기
function sendChatroomData(data){
  $.ajax({
    url:'/chat/room/data',
    data:{
      "category":data.category,
      "title":data.title,
      "content":data.content,
      "condition":data.condition,
      "memCount":data.memCount,
      "date":data.gatherDate,
      "memberId":data.memberId,
    },
  });

  moveChatList();
  return true;
}
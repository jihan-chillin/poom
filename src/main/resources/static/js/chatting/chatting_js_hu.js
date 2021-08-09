'use strict';

//프로필 부분 채팅아이콘 클릭시 채팅리스트로 이동
function moveMyChatList(){
  // location.replace("/chat/list");
  $('.feed_write').remove();
  $('.feed_new').remove();

  $.ajax({
    url:getContextPath()+'/chat/mylist/page',
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
    url:getContextPath()+'/chat/mychat/list',
    success:data=>{
      if(data.list[0].length === 0){
        return;
      }else{
        $('.chatroom').remove();

        let val = '';

        for(let i = 0; i<data.list.length; i++){
          let chatNo = data.list[i][0].CHAT_NO;

          // 로그인 아이디.
          let memberId = data.loginId;


          // 채팅방 리스트가 있다면
          val +='<li class="chatroom">';

          if(data.list[i][0].CATEGORY_NO ==='1'){
            val+= '<span class="chatroom-icon-study">스터디</span>'
          }else{
            val+= '<span class="chatroom-icon-gather">소모임</span>'
          }

          val+= '<span class="chatroom-title">';
          val+= '<span onclick="moveMyChatroom(\''+chatNo+'\',\''+memberId+'\')">'+data.list[i][0].CHAT_TITLE+'</span></span>';
          val+= '<span></span>';
          // 채팅방 참여인원수
          val+= '<span>'+data.countMember[i]+'</span>';
          val+= '<span>/</span>';
          // 채팅방 제한 인원
          val+= '<span>'+data.list[i][0].CHAT_PERSON+'명</span>';
          val+='</li>';

        }

        $('#chatroom-list>ul').append(val);

        disconnection("notMove");

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
    url:getContextPath()+'/chat/chatroom/check',
    data:{
      "chatNo":chatNo,
      "memberId":memberId
    },
    success: data=>{
      // 입장해 있다면
      if (data === 1){
        moveMyChatroom(chatNo,memberId);
        return;
      }else{
        if(confirm("채팅방에 입장하시겠습니까?")){
          enterChatroom(chatNo,memberId);
          moveMyChatroom(chatNo,memberId);
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
    url:getContextPath()+'/chat/chatroom/page',
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

  // 참여인원 불러오기
  getMyChatroom(chatNo,'/chat/mychat/member');

  // 채팅내용 불러오기
  getChatList(chatNo,'/chat/mychat/chatlist',memberId);

  return;
}
// 참여인원 불러오기
function getMyChatroom(chatNo,url){
  $.ajax({
    url:getContextPath()+url,
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
    url:getContextPath()+url,
    data:{
      "chatNo":
      chatNo
    },
    success:data=>{
      $('.msg-container>*').remove();
      let val = '';

      for(let i=0; i<data.messageContent.length; i++){
      // 내가 쓴 메세지 일때
        if(memberId === data.messageContent[i].MEMBER_ID){
          val += '<div class="my-chat">';
          // val += data.messageContent[i].MEMBER_ID+'</div>';
          val += '<div></div>';
          val += '<div></div>';
          val += '<div></div>';
          val += '<div class="message-blue"><div class="message-content">';
          val += data.messageContent[i].MESSAGE_CONTENT+'</div>';
          // 아바타 만들기
          val += '</div></div>';
        }else{
          val += '<div class="other-chat">';
          val += '<div class="others-profile">';
          // 아바타 만들기
          val +='<i class="chat-avatar" style="background-color:'+getAvatarColor(data.messageContent[i].MEMBER_ID)+'">';
          val += (data.messageContent[i].MEMBER_ID).substring(0,1)+'</i></div>';
          // val += data.messageContent[i].MEMBER_ID+'</div>';
          val += '<div>'+data.messageContent[i].MEMBER_ID+'</div>';
          val += '<div></div>';
          val += '<div class="message-blue"><div class="message-content">';
          val += data.messageContent[i].MESSAGE_CONTENT+'</div>';
          val += '</div></div>';
        }


      }
      $('.msg-container').append(val);
      $('.msg-container').scrollTop($('.msg-container').height());
    }
  });
}
// 채팅방 참여하기
function enterChatroom(chatNo,memberId){

  $.ajax({
    url:getContextPath()+'/chat/chatroom/enter',
    data:{
      "chatNo":chatNo,
      "memberId":memberId
    },
  });

  return 0;
}
// 채팅방 나가기
function quitChatroom(chatNo,memberId){
  $.ajax({
    url:getContextPath()+'/chat/chatroom/quit',
    data:{
      "chatNo":chatNo,
      "memberId":memberId
    }
  });
}

// 페이징 처리위한 변수
var listCount =1;

function moveChatList(){

  if(listCount !== 1){
    listCount =1;
  }


  $.ajax({
    url:getContextPath()+'/chat/list/page',
    success:function(data){
      $('.feed').append(data);
      // $('.feed').attr('style','height:905px');
      $('.chatroom-list-container>*').remove();
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });

  getChatroomListData(listCount);
}

// 채팅방 리스트 가져오는 함수
function getChatroomListData(listCount){
  $.ajax({
    url:getContextPath()+'/chat/list/data',
    data:{
      "cPage":listCount
    },
    success:data=>{
      const memberId = data.loginId;

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

        if(data.checkInterested[i] == 1){
          val += '<div><span class="interested-chatroom" onclick="deleteInterestChatroom('+data.chatList[i].CHAT_NO+',\''+memberId+'\')">관심 채팅방에서 삭제</span></div>';
        }else{ // 관심채팅방에 없으면
          val += '<div><span class="interested-chatroom" onclick="checkAlreadyInterestedChatroom('+data.chatList[i].CHAT_NO+',\''+memberId+'\')">관심 채팅방에 추가</span></div>';
        }
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

  listCount++;
}

$(function(){
// 스크롤시 채팅방 리스트 불러옴
  let win = $('.feed');
  win.scroll(function() {
    let height = $('#room-container').height()-win.height()-$('.chatroom-list-container').height();
    if ( win.scrollTop() >= height) {
      listCount++;
      getChatroomListData(listCount);
    }
  });
});

// 채팅방 분류 타입이 변경되었을때.
function chatTypeChange(){
  let option = $('.select-chatroom-status option:selected').val();
  $('.chatroom-list-container>*').remove();

  if(option === 'allChatroom'){
    getChatroomListData(listCount);
  }else{

    $.ajax({
      url:getContextPath()+'/chat/list/data/sort',
      data:{
        "cPage":listCount,
        "ref":option
      },
      success:data=>{
        const memberId = data.loginId;

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

          // 관심채팅방에 추가된 채팅방이면
          if(data.checkInterested[i] == 1){
            val += '<div><span class="interested-chatroom" onclick="deleteInterestChatroom('+data.chatList[i].CHAT_NO+',\''+memberId+'\')">관심 채팅방에서 삭제</span></div>';
          }else{ // 관심채팅방에 없으면
            val += '<div><span class="interested-chatroom" onclick="checkAlreadyInterestedChatroom('+data.chatList[i].CHAT_NO+',\''+memberId+'\')">관심 채팅방에 추가</span></div>';
          }

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
}


// 세부 채팅방 세부화면으로
// 화면 가져오기용.
function moveChatListDetail(chatNo){
  $('.feed>*').remove();

  $.ajax({
    url:getContextPath()+'/chat/list/detail',
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
    url:getContextPath()+'/chat/detail/data',
    data:{
      "chatNo":
      chatNo
    },
    success:data=>{
      const memberId = data.loginId;

      let val = '';
      val +='<link rel="stylesheet" type="text/css" href="'+getContextPath()+'/css/chatting/chatroom-list-detail.css">';
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

      // 관심채팅방에 추가된 채팅방이면
      if(data.checkInterested == 1){
        val += '<div><span class="interested-chatroom" onclick="deleteInterestChatroom('+data.chatData.CHAT_NO+',\''+memberId+'\')">관심 채팅방에서 삭제</span></div>';
      }else{ // 관심채팅방에 없으면
        val += '<div><span class="interested-chatroom" onclick="checkAlreadyInterestedChatroom('+data.chatData.CHAT_NO+',\''+memberId+'\')">관심 채팅방에 추가</span></div>';
      }

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
    url:getContextPath()+'/chat/room/page',
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
    url:getContextPath()+'/chat/room/data',
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
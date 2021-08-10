'use strict';
// 참여중인 채팅방 클릭시
$('.joined-chatroom').click(e=>{
  $('.joined-chatroom').attr('style','color:#000000; border-bottom: 3px solid #000000;');
  $('.interested-chatroom').attr('style','color:#d3d3d3; border:none;');
  moveMyChatList();
});

// 관심 채팅방 클릭시
$('.interested-chatroom').click(e=>{
  $('.interested-chatroom').attr('style','color:#000000; border-bottom: 3px solid #000000;');
  $('.joined-chatroom').attr('style','color:#d3d3d3; border:none;');
  getInterestedChatList();
});

// 관심 채팅방 목록 불러오는 함수
function getInterestedChatList(){
  $.ajax({
    url:getContextPath()+'/chat/mychat/list/interest',
    success:data=>{
      $('.chatroom').remove();
      let val = '';

      if(data.list.length === 0){
        val += '<li class="chatroom"><div></div><div id="nochatroom">관심 채팅방이 없습니다.</div></li>';
      }else{

        for(let i = 1; i<data.list.length; i++){
          let chatNo = data.list[i][0].CHAT_NO;
          // 채팅방 리스트가 있다면
          val +='<li class="chatroom">';

          if(data.list[i][0].CATEGORY_NO ==='1'){
            val+= '<span class="chatroom-icon-study">스터디</span>'
          }else{
            val+= '<span class="chatroom-icon-gather">소모임</span>'
          }

          val+= '<span class="chatroom-title">';
          val+= '<span onclick="moveChatListDetail(\''+chatNo+'\')">'+data.list[i][0].CHAT_TITLE+'</span></span>';
          val+= '<span></span>';
          // 채팅방 참여인원수
          val+= '<span>'+data.countMember[i]+'</span>';
          val+= '<span>/</span>';
          // 채팅방 제한 인원
          val+= '<span>'+data.list[i][0].CHAT_PERSON+'명</span>';
          val+='</li>';

        }
      }

      $('#chatroom-list>ul').append(val);
      disconnection("notMove");
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}


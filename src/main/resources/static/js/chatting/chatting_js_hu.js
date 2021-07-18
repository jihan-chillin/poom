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

      let val = '';

      if(data.list.CATEGORY_NO ==='0'){
        val+= '<span class="chatroom-icon">스터디</span>'
      }else{
        val+= '<span class="chatroom-icon">소모임</span>'
      }
      val+= '<span class="chatroom-title">';
      val+= '<a href="/chatroom.do">'+data.list.CHAT_TITLE+'</a></span>';
      val+= '<span><img style="width:25px; height: 25px;"></span>';
      val+= '<span>1</span>';
      val+= '<span>/</span>';
      val+= '<span>'+data.list.CHAT_PERSON+'명</span>';

      $('.chatroom').append(val);
    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}
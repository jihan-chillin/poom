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


}
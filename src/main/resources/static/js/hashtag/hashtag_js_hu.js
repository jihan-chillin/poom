'use strict';
// 내 해시태그 페이지로 이동
function moveMyTagPage(){
  $('.feed_write').remove();
  $('.feed_new').remove();

  $.ajax({
    url:'/tag/my/page',
    success:function(data){
      $('.feed').html(data)
      $('.feed').attr('style','height:1000px');
    },

    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  });
}
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

  getMyTagData();
}

// 내 태그 가져오기
function getMyTagData(){
  $.ajax({
    url:'/tag/my/data',
    success:data=>{
      console.log(data);
      $('.my-tag-container>*').remove();
      let val ='';

      for(let i =0; i<data.length; i++){
        val += '<div class="my-tag">';
        val += '<span>'+data[i].TAG_NAME+'</span>';
        val += '<span class="delete-tag">x</span>';
        val += '</div>';
      }

      $('.my-tag-container').append(val);
    }
  });
}
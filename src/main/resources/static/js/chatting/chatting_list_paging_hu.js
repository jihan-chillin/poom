'use strict';
let listCount = 0;

$(function(){
  let target = $('#room-container');
  let winTop;
  let onTop;
  function getList(){
    $.ajax({
      url: '',
      data:{
        "cPage":listCount
      },
      success:data=>{
        let getData='';
        // $('.allProductContainer *').remove();
        console.log(data);
        $(data).each((i,v)=>{
          getData = '<div class="totalProductContainer">';
          getData += '<span><a href="<%=request.getContextPath()%>/shopping/shopping?productNo='+v.productNo+'">';
          getData += '<input type="hidden" value="'+v.productNo+'">';
          getData += '<img src="<%=request.getContextPath() %>/image/'+v.productImage+'" alt="" class="shop" style="width: 250px; height:250px;"></a></span>';
          getData += '<span>제품명 :'+v.productName+'</span><br>';
          getData += '<span>가격 :'+v.price+'</span>';
          getData += '</div>';
          // console.log(getData);
          $(".allProductContainer").append(getData);
        });
      }
    });
    listCount++;
  }
  function listCall(){
    winTop =$(window).scrollTop();
    onTop=$(document).height() - $(window).height() - $('.footer').height();
    console.log(winTop);
    console.log(onTop);
    if(winTop>=onTop){
      getList();
    }
  }
  $(window).scroll(function(){
    listCall();
  });
});
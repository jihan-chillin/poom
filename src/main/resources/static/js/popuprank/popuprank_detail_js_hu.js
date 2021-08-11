'use strict';

$(function(){
  $.ajax({
    url:'/21AM_POOM_final/popup/rank/data',
    success:data=>{
      $('.popup>*').remove();

      let val ='<div>인기왕</div>';
      val += '<div>';
      val += '<img src="/21AM_POOM_final/images/poprank/images/crown.png" width="50px" style="margin-left: -10px;"/>';
      val += '<div class="popup_profile" width="180px"></div>';
      val += '</div>';

      val +=' <div>';
      val +=' <span>순위</span>';
      val +=' <span>닉네임</span>';
      val +=' <span>게시물</span>';
      val +=' <span>추천수</span>';
      val +=' </div>';


      for(let i =0;i<3; i++){
        val +='<div>';
        val +='<span>'+(i+1)+'위</span>';
        val +='<span>'+data[i].MEMBER_NICKNAME+'</span>';
        val +='<span onclick="moveBoardDetail('+data[i].BOARD_NO+')">'+data[i].BOARD_TITLE+'</span>';
        val +='<span>'+data[i].LIKECOUNT+'</span>';
        val +='</div>';
      }

      $('.popup').append(val);
      $('.popup_profile').attr("style","background-image:url('http://rclass.iptime.org:9999/21AM_POOM_final/resources/profile/"+data[0].MEMBER_IMG+"')")
    }
  });
});

function moveBoardDetail(boardNo){
  // opener.parent.location='/21AM_POOM_final/board/view?boardNo='+boardNo;
  $.ajax({
    url:"/21AM_POOM_final/board/view",
    data : {
      "boardNo":boardNo
    },
    success:data=>{
      $(opener.document).find("#content>*").remove();
      $(opener.document).find("#content").append(data);
    }
  })
}

function setCookie(cookie_name, value, days) {
  let exdate = new Date();
  exdate.setDate(exdate.getDate() + days);
  // 설정 일수만큼 현재시간에 만료값으로 지정

  let cookie_value = escape(value) + ((days == null) ? '' : '; expires=' + exdate.toUTCString());
  window.parent.document.cookie = cookie_name + '=' + cookie_value;
  window.opener.document.cookie = cookie_name + '=' + cookie_value;

}

//닫기 버튼 클릭시
$(".popup_visible").children("a").click(e=>{

  if($('.check_dont_show').is(":checked")){
    setCookie("donotshowrank",'1',7);
  }

  self.close();
});

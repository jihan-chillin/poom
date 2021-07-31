'use strict';
/*
  태그 추가하기 누르면 태그 입력하게 하는 js 파일
  최대 개수 5개 제한.
  키 입력 시 지금 등록된 태그가 있는지 없는지 알려 줌
 */

$('.create_tag_input').click(e=>{
  createTagInput(e);
});

function createTagInput(e){

  if($(e.target).children().classList){
    $(e.target).children().remove();
  }else{
    //  태그입력 창 추가
    let val ='<div class="tag-input-container"><span><input type="text" class="field__input" placeholder="태그를 입력하세요"></span>';
    val += '<div class="tag-search-result-container">';
    val += '</div>';
    val += '</div>';

    $(e.target).after(val);

    $('.field__input').attr(
      "style","margin-left:10px; border: none;" +
      "    background-color: #f9f9f9;" +
      "    font-size: 20px;" +
      "    width: 170px;" +
      "    padding: 4px;"
    );

    // 태그 입력 할 때
    $('.field__input').keyup(e=>{
      let keyword =$(e.target).val().trim();
      //검색어 입력시 태그 검색
      checkKeyword(keyword);
    });
  }

  // 엔터 두번 방지
  $('.field__input').keypress(e=>{
    let keyword =$(e.target).val().trim();

    if(e.keyCode === 13 ){
      countTag(keyword);
    }
  });

}

// 태그 개수 제한하는 함수
// keyup 버그 때문에 4 초과로 해야
// 5개까지 등록이 됨.
function countTag(keyword){
  if($('.tag-confirm').length > 4){
    alert("태그는 최대 5개까지 등록 가능합니다.");
    $('.tag-input-container').remove();
    return;
  }else{
    confirmTag(keyword);
  }
}

function confirmTag(keyword){
  // 이미 추가한 태그면 추가못하게 함
  if($('.tag-confirm').text().split('#').indexOf(keyword) === -1){
    $('.create_tag_input').after('<div class="tag-confirm">#' + keyword + '</div>');
    $('.tag-input-container').remove();

    return;
  }
}

/*

 메인 page에서 글쓰기 누르면 실행되는 메소드
 필요값 => 입력된 태그
 결과 => tag 테이블에 사용자가 입력한 태그가 등록됨

 */

// 입력한 태그 가져오는 메소드
function getConfirmTag(){
  let tagText = $('.tag-confirm').text();

  // 등록된 태그가 없다면
  if( tagText === null){
    return;
  }else{
    return tagText.split("#");
  }

  return;
}

// 등록된 태그 각각 db에 등록하는 메소드
function addTagEach(getConfirmTag){
  // 0번 인덱스는 항상 빈값
  for(let i=1; i<getConfirmTag.length; i++){
    $.ajax({
      url:getContextPath()+'/tag/search',
      data:{
        "keyword": getConfirmTag[i]
      },
      success:data=>{
        // 메인피드의 게시물에서 요청
        if(data === null) {
          addTagFromMainPage(getConfirmTag[i]);
        }
      }
    });
  }
}

// 메인 페이지에서 태그 등록하는 메소드
function addTagFromMainPage(keyword){
  $.ajax({
    url:getContextPath()+'/tag/add',
    data:{
      "keyword":keyword
    }
  });
}

'use strict';
// 내 해시태그 페이지로 이동
function moveMyTagPage(){
  $('.feed_write').remove();
  $('.feed_new').remove();

  $.ajax({
    url:getContextPath()+'/tag/my/page',
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
    url:getContextPath()+'/tag/my/data',
    success:data=>{
      // console.log(data);
      $('.my-tag-container>*').remove();
      let val ='';

      if(data.length === 0 ){
        val +='<div style="font-size: 40px;"> 등록된 태그가 없습니다. 태그를 등록해주세요 </div>'
      }else{
        for(let i =0; i<data.length; i++){
          val += '<div class="my-tag">';
          val += '<span>'+data[i].TAG_NAME+'</span>';
          val += '<span class="delete-tag">x</span>';
          val += '</div>';
        }
      }

      $('.my-tag-container').append(val);

      // 태그 삭제
      $('.delete-tag').click(e=>{
        let tagName = $(e.target).prev().text();
        // console.log(tagName);
        if(!confirm(tagName+' 태그를 나의 해시태그에서 삭제하시겠습니까?')){
          return;
        }else{
          deleteTag(tagName);
        }
      });

      $('.field__input').keyup(e=> {
        let keyword =$(e.target).val().trim();

        if(checkKeyword(keyword)){
          $('.tag-search-result-container').attr('style','display:none');

          return;
        }
        // 엔터 누르면 실행
        if(e.keyCode === 13){
          insertMyTag(keyword);
          $('.field__input').val('');

          if(keyword.length === 0){
            return;
          }
        }
      });

      // 태그를 누르면 게시글로 이동
      $('.my-tag>span:first-child').click(e=>{

        const tagName = $(e.target).text().replace("x","");
        tagContainsBoardList(tagName);
      });
    }
  });
  $('.tag-search-result-container').attr('style','display:none');

}

function tagContainsBoardList(tagName){
  $('.feed_write').remove();
  $('.feed_new').remove();

  $('.feed').css({
    "background": "#f7f7f7","border-radius":"20px",
    "height" : "800px"
  });

  $.ajax({
    url:getContextPath()+"/tag/board",
    data:{
      "tagName":tagName
    },
    success:function (result){
      $('.feed').html(result)

    },
    error:(e,m,i)=>{
      console.log(e);
      console.log(m);
      console.log(i);
    }
  })
}

//검색어 입력시 태그 검색
function checkKeyword(keyword){
    if(keyword.length>=8){
      alert("태그는 최대 7자까지 가능합니다.");
      return true;

    }else if(keyword.length !== 0){
      searchTag(keyword);

    }else{
      $('.tag-search-result-container>*').remove();
      $('.tag-search-result-container').attr('style','display:none');
    }
}

// 키워드를 나의 태그에 추가
function insertMyTag(keyword){
  let text = $('.tag-search-result>span').text();

  if(text !== '검색결과가 없습니다.'){

    if(confirm(keyword+" 태그를 나의 해시태그에 추가하시겠습니까?")){
      $.ajax({
        url:getContextPath()+'/tag/add',
        data:{
          "keyword":keyword,
          "ref":"member"
        },
        success:data=>{
          $('.tag-search-result-container').attr('style','display:none');

          if(data === 0){
            alert("이미 등록된 태그입니다.");

          }else {
            alert("태그가 등록되었습니다.");
            getMyTagData();
          }
        }
      });
    }else{
      $('.tag-search-result-container').attr('style','display:none');
    }
  }
}

// 태그 삭제
function deleteTag(tagName){
  $.ajax({
    url:getContextPath()+'/tag/delete',
    data:{
      "tagName":tagName
    },
    success:data=>{
      alert('태그가 삭제되었습니다.');
      getMyTagData();
    }
  });
}

// 태그 검색 + 자동완성 영역
function searchTag(keyword){
  $.ajax({
    url:getContextPath()+'/tag/search',
    data:{
      "keyword":
      keyword
    },
    success:data=>{
      // 메인피드의 게시물에서 요청
      tagSearchResult(data);
    }
  });
}

// 마이 태그 자동완성 화면 화면 구성
function tagSearchResult(data){
  let val = '';
  $('.tag-search-result-container>*').remove();

  if(data.length !== 0){
    for(let i =0; i<data.length; i++){

      val += '<div class="tag-search-result">';
      val += '<span class="tag-result">'+data[i].TAG_NAME+'</span>';
      val += '</div>';
    }
  }else{
    val += '<div class="tag-search-result"><span>검색결과가 없습니다.</span></div>';
  }
  $('.tag-search-result-container').append(val);
  $('.tag-search-result-container').attr('style','display:block');

  // 검색어 클릭하면 인풋창에 입력
  $('.tag-result').click(e=>{
    selectTagAndInsertInput(e);
  });
}

// 마이 태그 검색 결과 태그 눌렀을 때
function selectTagAndInsertInput(e){
  let keyword= $(e.target).text();
  $('.field__input').val(keyword);
}

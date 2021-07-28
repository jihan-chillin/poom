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

      //검색어 입력시 태그 검색
      $('.field__input').keyup(e=>{
        let keyword =$(e.target).val().trim();

        if(keyword.length>=8){
          alert("태그는 최대 7자까지 가능합니다.");
          return;

        }else if(keyword.length !== 0){
          searchTag(keyword);

        }else{
          $('.tag-search-result-container>*').remove();
          $('.tag-search-result-container').attr('style','display:none');
        }

        if(e.keyCode === 13){
          if($('.tag-search-result>span').text() !== '검색결과가 없습니다.' ){
            if(!confirm(keyword+" 태그를 나의 해시태그에 추가하시겠습니까?")){
              return;
            }else{
              addTag(keyword);
            }
          }else{
            return;
          }
        }

      });
    }
  });
}
// 태그 등록
function addTag(keyword){
  $.ajax({
    url:'/tag/add',
    data:{
      "keyword":keyword
    },
    success:data=>{
      if(data === 0){
        alert("이미 등록된 태그입니다.");
        return;

      }else {
        alert("태그가 등록되었습니다.");
        getMyTagData();
        return;

      }
    }
  });
}

// 태그 삭제
function deleteTag(tagName){
  $.ajax({
    url:'/tag/delete',
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
    url:'/tag/search',
    data:{
      "keyword":
      keyword
    },
    success:data=>{
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
  });
}

function selectTagAndInsertInput(e){
  let keyword= $(e.target).text();
  $('.field__input').val(keyword);
}

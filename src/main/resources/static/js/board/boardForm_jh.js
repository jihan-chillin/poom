// 해시태그 버튼 호버하면 툴팁메시지 뜨게 하기



$('.create-tag').click(e=>{
    createTagInput(e);
});



// function createTagInput(e){
//
//     if($(e.target).children().classList){
//         $(e.target).children().remove();
//     }else{
//         //  태그입력 창 추가
//         let val ='<div class="tag-input-container"><span><input type="text" class="field__input" placeholder="태그를 입력하세요"></span>';
//         val += '<div class="tag-search-result-container">';
//         val += '</div>';
//         val += '</div>';
//
//         $(e.target).after(val);
//
//         $('.field__input').attr(
//             "style","margin-left:10px; border: none;" +
//             "    background-color: #f9f9f9;" +
//             "    font-size: 20px;" +
//             "    width: 170px;" +
//             "    padding: 4px;"
//         );
//
//         // 태그 입력 할 때
//         $('.field__input').keyup(e=>{
//             let keyword =$(e.target).val().trim();
//             //검색어 입력시 태그 검색
//             checkKeyword(keyword);
//         });
//     }
//
//     // 엔터 두번 방지
//     $('.field__input').keypress(e=>{
//         let keyword =$(e.target).val().trim();
//
//         if(e.keyCode === 13 ){
//             countTag(keyword);
//         }
//     });
//
// }
// 호버시 툴팁나오게
function appearTag(){
    $('.tooltip').show();
}

function disappearTag(){
    $('.tooltip').hide();
}



// 1. 해시태그 버튼 누르면 입력창 뜨게 하기
function InputTag(){

    // 1.1 해시태그 입력창을 한 번에 하나씩만 클릭할 수 있도록!
    if(oneInputBox()){
        // preventInputCreate
        return;
    }
    // 1.2 해시태그 개수 제한
    if(countLimitOfTag()){
        // preventConfirmTag()
        return;
    }

    // 1.3 해시태그 입력창 만들기
    let val2 ='<span id="tag-input-spn" name="tag-input-spn">'

    val2  += '<input type="text" class="tag_input" placeholder="태그를 입력하세요"></span>';
    // val2  += '<div class="tag-search-result-container">';
    // val2  += '</div>';

    $('.create-tag').parent().append(val2);


    $('.tag_input').attr(
        "style","margin-left: 5px;" +
        "    border: none;" +
        "    background-color: #f9f9f9;" +
        "    font-size: 15px;" +
        "    width: 135px;" +
        "    padding: 4px;" +
        "    border : 1px solid lightgray;" +
        "    border-radius : 10px;"
    )

    // 엔터 두번 방지 !
    // 해시태그 단독으로 만들어지게 하는 함수 불러오기
    $('.tag_input').keypress(e=>{
        let tagText = $(e.target).val().trim();
        console.log(tagText);

        if(e.keyCode === 13){
            createTagSpan(tagText);
        }
        if(e.keyCode === 32){
            createTagSpan(tagText);
        }
    });

}

// input 태그 한 개이상 생기는 거 방지
function oneInputBox(){
    if($('.tag_input').length >= 1){
        return true;
    }
}

// 태그 개수 제한
function countLimitOfTag(){
    if($('.confirm-tag').length>4){
        alert("태그는 최대 5개까지 입력가능합니다.");
        return true;
    }
}


// tag를 span태그로 가져오게 하는 방법
function createTagSpan(tagText){
    // 이미 추가한 태그일 경우 중복방지
    if($('.tag_input').text().split('#').indexOf(tagText) === -1 ){
        console.log("태그텍스트" + tagText);
        console.log($('.tag_input').text().split('#'));
        $('.tag_input').remove();

        let val3 = "<span class = 'confirm-tag' style='color: #287094; margin-left: 10px;'>#"+ tagText +"<span class='delete-tag' style='cursor:pointer'>x</span>";
        val3 += "</span>";
        $('.create-tag').parent().append(val3);


        // x 표시 구현
        $('.delete-tag').on("click", function(e){
            $(e.target).parent().remove();
        });

        return;
    }
}

// 입력한 태그 가져오기
function getTagSpan(){
    let tagContent = $('.confirm-tag').text();

    // 등록된 태그가 없다면
    if(  tagContent=== null){
        return;
    }else{
        return  tagContent.replace("x","").split("#");
    }

    return;
}


// 등록된 태그 각각 db에 등록하는 함수
function addTag(getTagSpan){
    for(let i=1; i<getTagSpan.length; i++){
        $.ajax({
            url:getContextPath()+'/board/dupleTagCheck',
            data:{
                "tagText" : getTagSpan[i]
            },
            success:data=>{
                if(data.length === 0 ){
                    console.log("DB에 하나하나 등록되나? : "+getTagSpan[i]);
                    addTagFromBoardForm(getTagSpan[i]);
                }
            }
        })
    }
}

function addTagFromBoardForm(tagText){
    $.ajax({
        url:getContextPath()+'/board/insert',
        data:{
            "tagText" : tagText,
        },
        success:data =>{
            console.log("테이블 추가");
    }
    });
}






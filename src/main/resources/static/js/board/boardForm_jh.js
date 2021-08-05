// 마우스 오버시 툴팁 말풍선 띄우기

function toolTip(){
    if($('.arrow-box').css("display") === "none"){
            $('.arrow-box').show();
        }

}

function leaveToolTip(){
    $('.arrow-box').css("display","none");
}

// hover했을 때 툴팁 뜨고, 커서 사라지면 툴팁 사라지기
$(document).ready(function (){
    $('.arrow-box').hover(function(){
        $(this).show();
    }, function (){
        $(this.css("display","none"));
    });
});


// 1. 해시태그 버튼 누르면 입력창 뜨게 하기
function InputTag(){

    if(preventTag()){
        return;
    }

    // 1.1 해시태그 입력창 만들기
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
    });

}

// 태그입력하는 거 최대 5개로 제한 하는 함수.
function preventTag(){
   if($("span[name=tag-input-spn]").length>4)
    {
        alert("태그는 최대 5개까지 입력가능합니다.")
        return true;
    }
}

// tag를 span태그로 가져오게 하는 방법
function createTagSpan(tagWord){
    // 이미 추가한 태그일 경우 중복방지
    if($('.tag_input').text().split('#').indexOf(tagWord) === -1 ){
        $('.tag_input').remove();

        let val3 = "<span class = 'confirm-tag' style='color: #287094; margin-left: 10px;'>#"+ tagWord +"<span class='delete-tag' style='cursor:pointer'>x</span>";
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

// 동록된 태그 각각 db에 등록하는 함수
function addTagToDb(getTagSpan){
    // 0번 인덱스는 항상 빈 값 (왜냐면 0번 인덱스는 # 이기 때문 )
    for(let i =1; i<getTagSpan.length ; i++){
        $.ajax({
            url:getContextPath()+'/tag/search',
            data  :{
                "tagWord" : getTagSpan[i]
            },
            success:data=>{
                if(data.length === 0 ){
                    addTagFromvboardForm(getTagSpan[i]);
                }
            }
        });
    }
}

function addTagFromvboardForm(tagWord){
    $.ajax({
        url: getContextPath()+'/board/addTag',
        data: {
            "tagWord" : tagWord,
        },
        success:data=>{
            console.log("테이블에 추가");
    }

    })
}



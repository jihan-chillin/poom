function searchList() {
    var uInput = $('#uInput').val();
    $.ajax({
        url:getContextPath()+"/search/list",
        data:{
        	"uInput":uInput,
        },
        type: 'POST',
    }).done(function (fragment){
        $(".feed").html(fragment);
    }).fail(function (fragment){
        alert('실ㅍㅐ');
    })
}

function moveToView(tRow){
    let cate = tRow.children('.cateNm').html();
    let no = tRow.children('.sNum').html();

    if(cate=='board') {
        $.ajax({
            url: getContextPath() + "/board/view",
            data: {
                "boardNo": no
            },
            success: data => {
                $("#content").html(data);
            }
        });
    }else if(cate=='notice'){
        $.ajax({
            url:getContextPath()+"/admin/noticeView",
            data:{"no":no}
        }).done(function (fragment){
            $("#target").html(fragment);
        });
    }

}


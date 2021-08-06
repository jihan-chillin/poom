function searchList(e) {
    var uInput = $('#uInput').val();
    $.ajax({
        url:getContextPath()+"/search/list",
        data:{"uInput":uInput},
        type: 'POST',
    }).done(function (fragment){
        $(".feed").html(fragment);
    }).fail(function (fragment){
        alert('실ㅍㅐ');
    })
}
function searchList() {
    var uInput = $('#uInput').val();
    $.ajax({
        url:getContextPath()+"/search/list",
        data:{"uInput":$("#searchFrm").serialize()},
        type: 'POST',
    }).done(function (fragment){
        $(".feed").html(fragment);
    }).fail(function (fragment){
        alert('실ㅍㅐ');
    })
}
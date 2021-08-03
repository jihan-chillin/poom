function searchList() {
    alert("searchList!!!!");
    var uInput = $('#uInput').val();
    $.ajax({
        url:getContextPath()+"/search/list",
        data:{"uInput":uInput},
        type: 'POST',
    }).done(function (fragment){
        $("#target").html(fragment);
    });
}
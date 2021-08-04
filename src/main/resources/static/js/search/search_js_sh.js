function searchList() {
    alert("searchList!!!!");
    var uInput = $('#uInput').val();
    $.ajax({
        url:getContextPath()+"/search/list",
        data:{"uInput":uInput},
        type: 'POST',
    }).done(function (fragment){
        alert('되니?');
        /*console.log(fragment);
        $(".feed").html(fragment);*/
    }).fail(function (fragment){
        alert('실퍂');
    })
}
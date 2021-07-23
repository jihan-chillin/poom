
window.onload = function() {

    $("#table1").show();
    $("#table2").hide();
    $("#table3").hide();
}
$(document).ready(function() {
    $("#btn_1").click(function() {

        location.replace("/message/received").show();
        $("#table2").hide();
        $("#table3").hide();
    })
    $("#btn_2").click(function() {

        $("#table1").hide();
        $("#table2").show();
        $("#table3").hide();
    })
    $("#btn_3").click(function() {

        $("#table1").hide();
        $("#table2").hide();
        $("#table3").show();
    })
})

function showPopup(type){
    if(type==1)
        window.open("/message/popup","message","width=400,height=300");
    else if(type==2)
        window.open("/message/receiver","receiver","width=400,height=300");
}




window.onload = function() {

    $("#table1").show();
    $("#table2").hide();
    $("#table3").hide();
}
$(document).ready(function() {
    $("#btn_1").click(function() {

        $("#table1").show();
        $("#table").hide();
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
    $("#btn_4").click(function() {

        $("#table1").hide();
        $("#table2").hide();
        $("#table3").hide();
    })
})



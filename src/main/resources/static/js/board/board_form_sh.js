const board_validate=function(){
	
}
$(document).ready(function(){
	$("div#board_form li:last-child>input").on("keyup", e=>{
		console.log($(e).val());
	});
});
const board_validate=function(){
	
}
$(document).ready(function(){
	$("div#board_form li:last-child>input").addEventListener("click", e=>{
		if($(e).target.val().lastIndexOf(" ")!=-1){
			console.log($(e).target.val());
		}
	});
});
// 이미지 미리보기 생성
$('#input-file').change(function(){
    setImageFromfile(this, '#img');
});

function setImageFromfile(input, expression){
    if(input.files && input.files[0]){
        var reader = new FileReader();
        reader.onload = function(e){
            $(expression).attr('src',e.target.result);
            $(expression).css({
                'width' : '100%',
                'height' : '100%',
                'border-radius' : '20px',
                'object-fit' : 'cover'
            })
        }
        reader.readAsDataURL(input.files[0]);
    }
}
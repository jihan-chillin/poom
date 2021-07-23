'use strict';
const modal= $('#modal');
const doc = $('body');

// li.click(e=>{
//   if(modal.css("display")==='none'){
//     modal.css("display","");
//   }
// });

doc.click(e=>{
  let ref =$(e.target)[0].classList;

  if(ref.contains('noti_icon')){
    modal.css("display","");

  }else if(ref.contains("noti-info-alarm")){
    return;
  }else if(ref.contains("noti-info-message")){
    return;
  }else if(ref.contains("noti-img")){
    return;
  }else if(ref.contains("noti-detail")){
    return;
  }else if(ref.contains("noti-info-alarm")){
    return;
  }else if(ref.contains("modal-title")){
    return;
  } else if(ref.contains("modal-content-list")){
    return;
  }else{
    modal.css("display","none");

  }
});
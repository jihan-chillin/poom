'use strict';
let notiStompClient=null;

function notiConnent(){
  let socket = new SockJS('/scno');
  notiStompClient = Stomp.over(socket);

  notiStompClient.connect({},onNotiConnected);
}

function onNotiConnected(){
  notiStompClient.subscribe('/receive/noti',onNotiReceived);
}

function sendNoti(loginId){
  notiStompClient.send("/send/notification/alarm",{},JSON.stringify(loginId));
}

function onNotiReceived(payload){
  console.log('페이로드 JSON :'+JSON.parse(payload.body));
  // console.log("됨");
  // getNotificationData();
}

$(function(){
  $('.noti_icon').click(e=>{
    let loginId = $('#loginMember_id').text();
    console.log(loginId);

    sendNoti(loginId);
  });
});

notiConnent();


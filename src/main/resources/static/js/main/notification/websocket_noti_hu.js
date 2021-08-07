'use strict';
let notiStompClient=null;

function notiConnent(){
  let contextPath = getContextPath()+'/scno';

  let socket = new SockJS(contextPath);
  notiStompClient = Stomp.over(socket);

  notiStompClient.connect({},onNotiConnected);
}

function onNotiConnected(){
  notiStompClient.subscribe('/receive/noti',onNotiReceived);
}

function sendNoti(){
  console.log("되니?>");
  notiStompClient.send("/send/notification/alarm",{},{});
}

function onNotiReceived(payload){
  console.log(JSON.parse(payload.body));

  getNotificationData();
}


notiConnent();


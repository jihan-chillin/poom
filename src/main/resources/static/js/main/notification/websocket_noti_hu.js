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
  notiStompClient.send("/send/notification/alarm",{},{});
}

function onNotiReceived(){
  getNotificationData();
}


notiConnent();


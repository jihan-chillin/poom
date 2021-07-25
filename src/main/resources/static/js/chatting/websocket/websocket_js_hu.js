'use strict';
// 로그인한 세션 아이디를 가져와서 넣어야함
// 우선 없어서 임시로 넣음
// let memberId=sessionStorage.getItem("memberId");
let memberId='test';
let stompClient=null;

let messageForm = $('#messageForm');
let sendMsgContent=$('.send-msg-content');

function connect(event){
  memberId = 'test';

  if(memberId){
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
  }

  event.preventDefault();
}

function onConnected(){
  // Controller @SendTo 와 연결
  // 채팅방 번호를 넘겨야함
  stompClient.subscribe('/topic/chatroom',onMessageReceived);

  stompClient.send("/chat.addUser",{},
    JSON.stringify({sender:memberId, type:'JOIN'})
  );

}
function onError(error){
  $('.msg-container').text('웹소켓에 연결하지 못했습니다. 새로고침 후 다시 시도해주세요');

}

function sendMessage(event) {
  let messageContent = sendMsgContent.val();
  let chatNo = $('.chatNo').val();
  let memberId=$('#chatmem_id').val();

  // console.log(messageContent);

  if(messageContent && stompClient) {
    let chatMessage = {
      memberId: memberId,
      messageContent: messageContent,
      chatNo:chatNo,
      type: 'CHAT'
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));

    sendMsgContent.val('');
  }

  return false;
}

function onMessageReceived(payload){
  let message = JSON.parse(payload.body);
  let messageElement = $('.msg-container');
  let chatNo = $('.chatNo').val();

  if(message.type ==='JOIN'){
    message.messageContent = message.memberId+'채팅방에 참여했습니다.';

  }else if(message.type ==='LEAVE'){
    message.messageContent = message.memberId+'채팅방을 나갔습니다.';

  }else{
    console.log("페이로드"+payload)
    // 채팅방 번호 받아와야함 -> 수정필요
    // messageElement.append(message.messageContent);
    getChatList(chatNo,'/chat/mychat/member',message.memberId);
  }
}

function disconnection(){
  if(stompClient !== null){
    stompClient.disconnect();
    // $('.chat-icon').click();
    location.replace('/');
  }
}

connect();
// messageForm.addEventListener('submit', sendMessage, true)

let submitAction = e=>{
  e.preventDefault();
  e.stopPropagation();
}

$('form').bind('submit',submitAction);
messageForm.submit(e=>{
  sendMessage(e);
});

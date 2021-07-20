'use strict';
let memberId=null;
let stompClient=null;


function webSocketConnect(event){
  // 로그인한 세션 아이디를 가져와서 넣어야함
  // 우선 없어서 임시로 넣음
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
    JSON.stringify({memberId:memberId, type:'JOIN'})
  );

}
function onError(error){
  $('.msg-container').text('웹소켓에 연결하지 못했습니다. 새로고침 후 다시 시도해주세요');

}

function sendMessage(event) {
  let messageContent = $('.send-msg-content').value.trim();

  if(messageContent && stompClient) {
    let chatMessage = {
      memberId: memberId,
      content: $('.send-msg-content').value,
      type: 'CHAT'
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));

    $('.send-msg-content').value = '';
  }
  event.preventDefault();
}

function onMessageReceived(payload){
  let message = JSON.parse(payload.body);
  let messageElement = $('.msg-container');

  if(message.type ==='JOIN'){
    message.content = message.memberId+'채팅방에 참여했습니다.';

  }else if(message.type ==='LEAVE'){
    message.content = message.memberId+'채팅방을 나갔습니다.';

  }else{

    console.log("페이로드"+payload)
  }

}

webSocketConnect();
$('.send-btn').click(sendMessage,true);
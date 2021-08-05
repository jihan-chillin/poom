'use strict';
var stompClient=null;
var memberId =$('#chatmem_id').val();

var colors = [
  '#2196F3', '#32c787', '#00BCD4', '#ff5652',
  '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

var messageForm = $('#messageForm');
var sendMsgContent=$('.send-msg-content');

function connect(event){

  if(memberId){
    let socket = new SockJS(getContextPath()+'/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
  }
}

function onConnected(){
  // Controller @SendTo 와 연결
  // 채팅방 번호를 넘겨야함
  let chatNo = $('.chatNo').val();

  stompClient.subscribe('/topic/chatroom/'+chatNo,onMessageReceived);

}
function onError(error){
  $('.msg-container').text('웹소켓에 연결하지 못했습니다. 새로고침 후 다시 시도해주세요');

}

function sendMessage(event) {
  let messageContent = sendMsgContent.val();
  let chatNo = $('.chatNo').val();


  if(messageContent && stompClient) {
    let chatMessage = {
      memberId: memberId,
      messageContent: messageContent,
      chatNo:chatNo,
      type: 'CHAT'
    };
    stompClient.send("/app/chat/"+chatNo, {}, JSON.stringify(chatMessage));
    sendMsgContent.val('');
  }

  return false;
}

function onMessageReceived(payload){
  let message = JSON.parse(payload.body);
  let chatNo = $('.chatNo').val();

  getChatList(chatNo,'/chat/mychat/chatlist',message.memberId);
}

function getAvatarColor(messageSender){
  // console.log(messageSender);

  let hash = 0;
  for (let i = 0; i < messageSender.length; i++) {
    hash = 31 * hash + messageSender.charCodeAt(i);
  }
  let index = Math.abs(hash % colors.length);
  return colors[index];
}

function disconnection(ref){
  if(stompClient !== null){
    stompClient.disconnect();
    stompClient = null;

    let chatNo = $('#chatroom_no').val();
    let memberId = $('#chatmem_id').val();

    if(chatNo !== undefined && memberId !== undefined){
      if(confirm("채팅방을 나가시겠습니까?")) {
        quitChatroom(chatNo, memberId);
      }
    }

    // 채팅방 나가기
    if(!ref) {
      location.replace('/21AM_POOM_final/login/main');
    }
  }
}

connect();

messageForm.submit(e=>{
  sendMessage(e);
});

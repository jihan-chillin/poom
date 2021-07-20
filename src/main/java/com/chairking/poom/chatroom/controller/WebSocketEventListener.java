package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.vo.ChatMessage;
import com.chairking.poom.chatroom.model.vo.ChatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {
    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    // 채팅방 연결시
    // 채팅방 참여자 리스트 가져와서 프론트로 주면?
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
        log.info("채팅방 연결됨");
    }

    // 채팅방 퇴장시
    // 채팅방 내용을 db에 저장?
    // 장점
    // 1. db 부하 줄일 수 있음.
    // 단점
    // 1. 저장을 안해놓으니 다른 사람이 들어왔을 때 이전 채팅을 볼 수 가 없음
    // -> 제일 큰 단점임.
    @EventListener
    public void handlerWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username= (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            ChatMessage chatMessage = new ChatMessage();
            // 떠난거 저장
            chatMessage.setType(ChatType.LEAVE);
            chatMessage.setMemberId(username);

            messageTemplate.convertAndSend("/topic/chatroom");
        }
    }
}

package com.chairking.poom.websocket.common;

import com.chairking.poom.chatroom.model.vo.ChatMessage;
import com.chairking.poom.chatroom.model.vo.ChatType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@Slf4j
public class WebSocketEventListener {
    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
//        log.info(" 웹소켓 연결됨");
//        log.info("simpSessionId :{}",event.getMessage().getHeaders().get("simpSessionId"));
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
//        log.info(" 웹소켓 끝");

    }
}

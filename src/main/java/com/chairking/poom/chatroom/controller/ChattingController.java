package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ChattingController {
    @Autowired
    private ChattingService service;

//    리스트 연결 테스트용. 수정예정
//    html 가져오기용 Controller
    @GetMapping("/chat/list")
    public String chattingList(){
        return "chatting/my-chattingList";
    }

    // 채팅방 가져오기
    @GetMapping("/chat/chatroom")
    public String chatroom(){
        return "chatting/chattingroom";
    }

    // 메세지 보냄
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chatroom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        log.info("컨트롤러에서 보내는 페이로드 : {}",chatMessage);
        // 메세지 디비로 저장하는 메소드
        // 구현 해야함
//        service.saveMessage(chatMessage);
        return chatMessage;
    }

    // 채팅방에 들어온 유저표시
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/chatroom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",chatMessage.getMemberId());
        return chatMessage;
    }

}

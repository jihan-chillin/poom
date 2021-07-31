package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class ChatroomController {

    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private ChattingService service;
    @Autowired
    public ChatroomController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    // 메세지 보냄
    @MessageMapping("/chat/{chatNo}")
    @SendTo("/topic/chatroom/{chatNo}")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        log.info("제발제발제발");
//        log.info("컨트롤러에서 보내는 페이로드 : {}",chatMessage);
        log.info("메세지 내용 : {}", chatMessage.getMessageContent());
        log.info("메세지 보낸 아이디 : {}", chatMessage.getMemberId());
        log.info("채팅방 번호 : {}", chatMessage.getChatNo());



        // 메세지 저장 서비스.
        service.saveMessage(chatMessage);
//        return chatMessage;
        simpMessagingTemplate.convertAndSend("/topic/chatroom/"+chatMessage.getChatNo(), chatMessage);
    }

}


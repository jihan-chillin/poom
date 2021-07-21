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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ChattingController {
    @Autowired
    private ChattingService service;
//    html 가져오기용 Controller

    // 내 채팅방 리스트
    @GetMapping("/chat/mylist/page")
    public String myChattingList(){
        return "chatting/my-chattingList";
    }

    // 채팅방 입장
    @GetMapping("/chat/chatroom/page")
    public String chatroom(){
        return "chatting/chattingroom";
    }

    // 메세지 보냄
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chatroom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        log.info("컨트롤러에서 보내는 페이로드 : {}",chatMessage);
        log.info("메세지 내용 : {}",chatMessage.getMessageContent());
        log.info("메세지 보낸 아이디 : {}",chatMessage.getMemberId());
        log.info("채팅방 번호 : {}",chatMessage.getChatNo());

       // 메세지 저장 서비스.
        service.saveMessage(chatMessage);
        return chatMessage;
    }

    // 채팅방에 들어온 유저표시
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/chatroom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",chatMessage.getMemberId());
        return chatMessage;
    }

    // 채팅방 리스트 가져오기
    @GetMapping("/chat/list/page")
    public String chattingList(){
        return "chatting/chatroom-list";
    }
    // 채팅방 상세
    @GetMapping("/chat/list/detail")
    public String detailChatroom(){
        return "chatting/chatroom-list-detail";
    }

    // 채팅방 만들기
    @GetMapping("/chat/room/page")
    public String createChatroom(){
        return "chatting/create-chatroom";
    }
}

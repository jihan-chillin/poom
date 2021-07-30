package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class ChattingController {
    @Autowired
    private ChattingService service;
//    html 가져오기용 Controller

//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
    // 내 채팅방 리스트
    @GetMapping("/chat/mylist/page")
    public String myChattingList(){
        return "chatting/my-chattingList";
    }

    // 채팅방 입장
    @GetMapping("/chat/chatroom/page")
    public ModelAndView chatroom(ModelAndView model,HttpServletRequest req){
        Map memberId = ((Map)req.getSession().getAttribute("loginMember"));
        model.addObject("memberId",memberId.get("MEMBER_ID"));
        model.setViewName("chatting/chattingroom");
        return model;
    }



    // 채팅방에 들어온 유저표시
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/chatroom")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
//        headerAccessor.getSessionAttributes().put("username",chatMessage.getMemberId());
//        return chatMessage;
//    }

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
    public ModelAndView createChatroom(ModelAndView model,HttpServletRequest req){
        model.addObject("memberId",((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
        model.setViewName("chatting/create-chatroom");
        return model;
    }

    // 채팅방에 사람이 찼으면 상태 자리 없음으로 자리 있으면 자리 있음으로 바꾸는 메소드
    public void chatTypeChange(String chatNo){
        service.chatTypeChange(chatNo);
    }
}

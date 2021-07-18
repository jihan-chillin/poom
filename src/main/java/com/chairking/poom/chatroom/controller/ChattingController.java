package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChattingController {
    @Autowired
    private ChattingService service;

//    리스트 연결 테스트용. 수정예정
    @GetMapping("/chat/list")
    public String chattingList(){
        return "chatting/chatroom-list";
    }
}

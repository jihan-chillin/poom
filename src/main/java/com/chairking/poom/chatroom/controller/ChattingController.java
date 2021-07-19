package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}

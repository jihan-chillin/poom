package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ChattingJsonController {
    // JSON 전송용 Controller
    @Autowired
    private ChattingService service;

    @GetMapping("/chat/mychat/list")
    public Map<String,Object>  getMyChatList(){
        Map<String,Object> list = new HashMap<>();
        list.put("list",service.getMyChatList());

        return list;
    }

    @GetMapping("/chat/mychat/member")
    public Map getMyChatroom(HttpServletRequest req){
//        log.info("채팅방 번호 :{}", req.getParameter("chatNo"));
        String chatNo =req.getParameter("chatNo");

        // 참여자
        Map<String,List> list = new HashMap<>();
        list.put("list",service.enteredMem(chatNo));

        // 채팅 내용
        list.put("messageContent",service.messageContent(chatNo));

        return list;
    }
}

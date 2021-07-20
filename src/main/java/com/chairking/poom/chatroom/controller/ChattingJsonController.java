package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public Map getMyChatroomData(HttpServletRequest req){
        String chatNo =req.getParameter("chatNo");

        Map<String,List> list = new HashMap<>();
        list.put("list",getEnteredMem(chatNo));
        list.put("messageContent",getPastChattingList(chatNo,7));
        return list;
    }
    // 채팅방 참여자 리스트 가져옴
    public List getEnteredMem(String chatNo){
        return service.enteredMem(chatNo);
    }

    //채팅 내용
    public List getPastChattingList(String chatNo,int ref){
        return service.messageContent(chatNo,ref);
    }
}

package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@Slf4j
public class ChattingJsonController {
    // JSON 전송용 Controller
    @Autowired
    private ChattingService service;

    @GetMapping("/chat/mychat/list")
    public Map<String,Object>  getMyChatList(){
        Map<String,Object> list = new HashMap<>();
        Map myChatList = service.getMyChatList();
        list.put("list",myChatList);

        String chatNo = (String) myChatList.get("CHAT_NO");
        list.put("countMember",getEnteredMem(chatNo).size());

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

    @GetMapping("/chat/list/data")
    public Map getChatList(){
        Map<String,Object> result = new HashMap<>();

        List<Map<String,Object>> chatList = service.getChatList();

        String chatNo = "";
        List memCount = new ArrayList();

        for(int i=0; i<chatList.size(); i++ ) {
//            log.info("채팅리스트 : {}", chatList.get(i).get("CHAT_NO"));
            chatNo = (String)chatList.get(i).get("CHAT_NO");
            memCount.add(i,getEnteredMem(chatNo).size());
        }

        result.put("chatRoomMemCount",memCount);
        result.put("chatList",chatList);

        return result;
    }
}

package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@Slf4j
public class ChattingJsonController {
    // JSON 전송용 Controller
    @Autowired
    private ChattingService service;

    // 채팅방 참여자 리스트 가져옴
    public List getEnteredMem(String chatNo){
        return service.enteredMem(chatNo);
    }

    //채팅 내용
    public List getPastChattingList(String chatNo,int ref){
        return service.messageContent(chatNo,ref);
    }
//    자료형 수정해야함
    @GetMapping("/chat/mychat/list")
    public Map<String,Object> getMyChatList(){
        Map<String,Object> list = new HashMap<>();

        List<Map> myChatList = service.getMyChatList();
        String chatNo = "";
        List memCount = new ArrayList();

        for(int i =0; i<myChatList.size(); i++) {
            chatNo = (String) myChatList.get(i).get("CHAT_NO");
            memCount.add(i,getEnteredMem(chatNo).size());
        }
        list.put("countMember",memCount);
        list.put("list",myChatList);

        return list;
    }

    @GetMapping("/chat/mychat/member")
    public Map getMyChatroomData(HttpServletRequest req){
        String chatNo =req.getParameter("chatNo");

        Map<String,Object> list = new HashMap<>();
        list.put("list",getEnteredMem(chatNo));
//       1주일 전까지 메세지만 가져옴 기준 -> int ref = 7
        list.put("messageContent",getPastChattingList(chatNo,7));
        list.put("chatData",service.getChatroomData(chatNo));

        return list;
    }
    @GetMapping("/chat/chatroom/enter")
    public int enterChatroom(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId =req.getParameter("memberId");

        return service.enterChatRoom(memberId,chatNo);
    }
    @GetMapping("/chat/chatroom/check")
    public int checkEnterChatroom(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId =req.getParameter("memberId");

        return service.checkEnterChatroom(memberId,chatNo);
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

    // 채팅방 세부화면
    @GetMapping("/chat/detail/data")
    public Map getChatListDetailData(HttpServletRequest req){
        String chatNo =req.getParameter("chatNo");
//        log.info(chatNo);
        Map result = new HashMap();
        result.put("chatData",service.getChatroomData(chatNo));
        result.put("memCount",getEnteredMem(chatNo).size());

        return result;
    }

    @GetMapping("/chat/room/data")
    public void createChatroom(HttpServletRequest req){
        Map<String,Object> data = new HashMap<>();

        data.put("category",req.getParameter("category"));
        data.put("title",req.getParameter("title"));
        data.put("content",req.getParameter("content"));
        data.put("condition",req.getParameter("condition"));
        data.put("memCount",Integer.parseInt(req.getParameter("memCount")));
        data.put("date",req.getParameter("date"));
        data.put("memberId",req.getParameter("memberId"));

        // 채팅방 생성
        service.insertChatroomData(data);
        // 생성된 채팅방 번호 가져옴
        String chatNo = service.getChatNo();
        // 생성자 채팅방 입장
        service.enterChatRoom((String)data.get("memberId"),chatNo);
    }

    // 채팅방 신고, 관심채팅방에 등록됐는지 조회
    @GetMapping("/chat/room/check")
    public int checkAlreadyChatroom(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId = req.getParameter("memberId");
        String ref = req.getParameter("ref");

        String refTable ="";
        String refId = "";
        String refNo = "";

        if (ref.equals("inter")){
            refTable ="CHAT_BLAME";
            refId ="CH_AIM_ID";
            refNo ="CH_TARGET_CHAT";
        }else{
            refTable = "LIKECHATROOM";
            refId= "MEMBER_ID";
            refNo ="CHAT_NO";
        }

        return service.checkAlreadyChatroom(chatNo,memberId,refTable,refId,refNo);
    }

    @GetMapping("/chat/room/like")
    public int likeChatroom(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId = req.getParameter("memberId");

        return service.likeChatroom(chatNo,memberId);
    }

    @GetMapping("/chat/room/blame")
    public int blameChatroom(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId = req.getParameter("memberId");

        return service.blameChatroom(chatNo,memberId);
    }
}

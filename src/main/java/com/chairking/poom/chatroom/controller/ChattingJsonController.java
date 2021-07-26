package com.chairking.poom.chatroom.controller;

import com.chairking.poom.chatroom.model.service.ChattingService;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Slf4j
public class ChattingJsonController {
    // JSON 전송용 Controller
    @Autowired
    private ChattingService service;

    // 채팅방 몇명 참여했는지
    public List<Map> getEnteredMem(String chatNo){
        return service.enteredMem(chatNo);
    }

    //채팅 내용
    public List getPastChattingList(String chatNo,int ref){
        return service.messageContent(chatNo,ref);
    }
    @GetMapping("/chat/mychat/list")
    public Map<String,Object> getMyChatList(HttpServletRequest req){
        HttpSession session = req.getSession();
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String memberId = val.get("MEMBER_ID");

        // 아이디로 내가 참가한 채팅방 번호 가져옴
        List<String> myChatroomNum = service.getMyChatroomNum(memberId);

        // 내가 참여한 채팅방 리스트 전부
        List<List<Map>> myChatList = new ArrayList<>();

        // 채팅방에 몇명 참여했는지
        List memCount = new ArrayList();

        for(int i =0; i<myChatroomNum.size(); i++){
            myChatList.add(i, service.getMyChatList(myChatroomNum.get(i)));
            memCount.add(i,getEnteredMem(myChatroomNum.get(i)).size());
        }

        Map<String,Object> list = new HashMap<>();

        list.put("loginId",memberId);
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
        list.put("loginMember",req.getSession().getAttribute("loginMember"));

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
    public Map getChatList(HttpServletRequest req){
        int numPerPage = 4;
        int cPage = Integer.parseInt(req.getParameter("cPage"));
        Map<String,Object> result = new HashMap<>();

        List<Map<String,Object>> chatList = service.getChatList(cPage,numPerPage);

        String chatNo = "";
        List memCount = new ArrayList();

        for(int i=0; i<chatList.size(); i++ ) {
//            log.info("채팅리스트 : {}", chatList.get(i).get("CHAT_NO"));
            chatNo = (String)chatList.get(i).get("CHAT_NO");
            memCount.add(i,getEnteredMem(chatNo).size());
        }

        result.put("chatRoomMemCount",memCount);
        result.put("chatList",chatList);
        result.put("loginMember",req.getSession().getAttribute("loginMember"));

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
        result.put("loginMember",req.getSession().getAttribute("loginMember"));

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
        data.put("loginMember",req.getSession().getAttribute("loginMember"));

        // 채팅방 생성
        service.insertChatroomData(data);
        // 생성된 채팅방 번호 가져옴
        String chatNo = service.getChatNo();
        // 생성자 채팅방 입장
        service.enterChatRoom((String)data.get("memberId"),chatNo);
    }

    // 채팅방 신고, 관심채팅방에 등록됐는지 조회
    @GetMapping("/chat/room/check/inter")
    public int checkAlreadyInterested(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId = req.getParameter("memberId");

        return service.checkAlreadyInterested(chatNo,memberId);
    }
    @GetMapping("/chat/room/check/blame")
    public int checkAlreadyBlame(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId = req.getParameter("memberId");

        return service.checkAlreadyBlame(chatNo,memberId);
    }

    @GetMapping("/chat/room/like")
    public int likeChatroom(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId = req.getParameter("memberId");

        log.info("like : {},{}", chatNo, memberId);
        return service.likeChatroom(chatNo,memberId);
    }

    @GetMapping("/chat/room/blame")
    public int blameChatroom(HttpServletRequest req){
        String chatNo = req.getParameter("chatNo");
        String memberId = req.getParameter("memberId");

        return service.blameChatroom(chatNo,memberId);
    }
}

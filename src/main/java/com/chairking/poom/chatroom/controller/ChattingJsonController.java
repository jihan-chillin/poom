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
    @Autowired
    private ChattingController chattingController;

    // 채팅방 몇명 참여했는지
    public List<Map> getEnteredMem(String chatNo){
        return service.enteredMem(chatNo);
    }

    //채팅 내용
    public List getPastChattingList(String chatNo,int ref){

        try {
            chattingController.chatTypeChange(chatNo);
        }catch (Exception e){
            log.warn("타입변경하다가 오류 발생");
            e.printStackTrace();
        }

        return service.messageContent(chatNo,ref);
    }

    // 세션에서 아이디 가져와서 리턴하는 메소드
    public String memberId(HttpServletRequest req){
        HttpSession session = req.getSession();
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        return  val.get("MEMBER_ID");
    }

    /*
     * 채팅방 번호 리스트, 아이디를 입력받고
     * 채팅방 참여한 리스트, 그 채팅방에 몇명참여했는지 리턴하는 메소드
     */
    public Map<String,Object> returnMyChatListData(List<String> myChatNoList,String memberId){
        // 내가 참여한 채팅방 리스트 전부
        List<List<Map>> myChatList = new ArrayList<>();

        // 채팅방에 몇명 참여했는지
        List memCount = new ArrayList();

        for(int i =0; i<myChatNoList.size(); i++){
            myChatList.add(i, service.getMyChatList(myChatNoList.get(i)));
            memCount.add(i,getEnteredMem(myChatNoList.get(i)).size());
        }

        Map<String,Object> list = new HashMap<>();

        list.put("loginId",memberId);
        list.put("countMember",memCount);
        list.put("list",myChatList);

        return list;
    }

    @GetMapping("/chat/mychat/list")
    public Map<String,Object> getMyChatList(HttpServletRequest req){
        String memberId =memberId(req);

        // 아이디로 내가 참가한 채팅방 번호 가져옴
        List<String> myChatroomNum = service.getMyChatroomNum(memberId);

        return returnMyChatListData(myChatroomNum,memberId);
    }

    @GetMapping("/chat/mychat/list/interest")
    public Map<String,Object> getInterestedChatList(HttpServletRequest req){
        String memberId = memberId(req);

        // 아이디로 관심채팅방 번호 가져옴
        List<String> myInterestChatNo = service.getInterestedChatNo(memberId);

        return returnMyChatListData(myInterestChatNo,memberId);
    }

    @GetMapping("/chat/mychat/member")
    public Map getMyChatroomData(HttpServletRequest req,@RequestParam(value = "chatNo")String chatNo){

        Map<String,Object> list = new HashMap<>();
        list.put("list",getEnteredMem(chatNo));
//       1주일 전까지 메세지만 가져옴 기준 -> int ref = 7
//        list.put("messageContent",getPastChattingList(chatNo,7));
        list.put("chatData",service.getChatroomData(chatNo));
        list.put("loginMember",req.getSession().getAttribute("loginMember"));

        return list;
    }

    @GetMapping("/chat/mychat/chatlist")
    public Map getMyChatList(@RequestParam(value = "chatNo")String chatNo){
        Map<String,Object> list = new HashMap<>();

        list.put("messageContent",getPastChattingList(chatNo,7));
        return list;
    }

    @GetMapping("/chat/chatroom/enter")
    public int enterChatroom(
            @RequestParam(value = "chatNo")String chatNo,
            @RequestParam(value="memberId")String memberId){
        return service.enterChatRoom(memberId,chatNo);
    }

    @GetMapping("/chat/chatroom/quit")
    public void quitChatroom(  @RequestParam(value = "chatNo")String chatNo,
                              @RequestParam(value="memberId")String memberId){

        service.quitChatroom(chatNo,memberId);

        // 채팅방 인원수가 0명이면 채팅방, 채팅내용 삭제
        if(getEnteredMem(chatNo).size() == 0){
            service.deleteChatContent(chatNo);
            service.deleteChatroom(chatNo);
        }
    }

    @GetMapping("/chat/chatroom/check")
    public int checkEnterChatroom(@RequestParam(value = "chatNo")String chatNo,
                                  @RequestParam(value="memberId")String memberId){
        return service.checkEnterChatroom(memberId,chatNo);
    }


    @GetMapping("/chat/list/data")
    public Map getChatList(HttpServletRequest req,@RequestParam(value = "cPage")int cPage){
        int numPerPage = 4;
        Map<String,Object> result = new HashMap<>();

        List<Map<String,Object>> chatList = service.getChatList(cPage,numPerPage);

        String chatNo = "";
        List memCount = new ArrayList();

        for(int i=0; i<chatList.size(); i++ ) {
//            log.info("채팅리스트 : {}", chatList.get(i).get("CHAT_NO"));
            chatNo = (String)chatList.get(i).get("CHAT_NO");
            memCount.add(i,getEnteredMem(chatNo).size());
            try {
                chattingController.chatTypeChange(chatNo);
            }catch (Exception e){
                log.warn("타입변경하다가 오류 발생");
                e.printStackTrace();
            }
        }

        result.put("chatRoomMemCount",memCount);
        result.put("chatList",chatList);
        result.put("loginMember",req.getSession().getAttribute("loginMember"));

        return result;
    }
    // 모집중, 모집마감 분류
    @GetMapping("/chat/list/data/sort")
    public Map getChatListSort(HttpServletRequest req,
                               @RequestParam(value = "cPage")int cPage,
                               @RequestParam(value = "ref")String ref){
        int numPerPage = 4;

        String chatType = "";
        // 모집중일때
        if(ref.equals("ing")){
            chatType = "0";
        }else{ // 모집 마감일 때.
            chatType = "1";
        }
        Map<String,Object> result = new HashMap<>();

        List<Map<String,Object>> chatList = service.getChatListSort(cPage,numPerPage,chatType);

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
    public Map getChatListDetailData(HttpServletRequest req,
                                     @RequestParam(value = "chatNo")String chatNo){
        String memberId =memberId(req);

        Map result = new HashMap();
        result.put("chatData",service.getChatroomData(chatNo));
        result.put("memCount",getEnteredMem(chatNo).size());
        result.put("loginId",memberId);
        result.put("checkInterested",service.checkAlreadyInterested(chatNo,memberId));

        return result;
    }

    @GetMapping("/chat/room/data")
    public void createChatroom(@RequestParam Map data){
        // 채팅방 생성
        service.insertChatroomData(data);
        // 생성된 채팅방 번호 가져옴
        String chatNo = service.getChatNo();
        // 생성자 채팅방 입장
        service.enterChatRoom((String)data.get("memberId"),chatNo);
    }

    // 채팅방 신고, 관심채팅방에 등록됐는지 조회
    @GetMapping("/chat/room/check/inter")
    public int checkAlreadyInterested(@RequestParam(value = "chatNo")String chatNo,
                                      @RequestParam(value = "memberId")String memberId){

        return service.checkAlreadyInterested(chatNo,memberId);
    }
    @GetMapping("/chat/room/check/blame")
    public int checkAlreadyBlame(@RequestParam(value = "chatNo")String chatNo,
                                 @RequestParam(value = "memberId")String memberId){

        return service.checkAlreadyBlame(chatNo,memberId);
    }

    @GetMapping("/chat/room/like")
    public int likeChatroom(@RequestParam(value = "chatNo")String chatNo,
                            @RequestParam(value = "memberId")String memberId){

        return service.likeChatroom(chatNo,memberId);
    }
    @GetMapping("/chat/room/unlike")
    public int unlikeChatroom(@RequestParam(value = "chatNo")String chatNo,
                            @RequestParam(value = "memberId")String memberId){

        return service.unlikeChatroom(chatNo,memberId);
    }


    @GetMapping("/chat/room/blame")
    public int blameChatroom(@RequestParam(value = "chatNo")String chatNo,
                             @RequestParam(value = "memberId")String memberId){

        return service.blameChatroom(chatNo,memberId);
    }
}

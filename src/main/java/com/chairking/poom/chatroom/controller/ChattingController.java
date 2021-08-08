package com.chairking.poom.chatroom.controller;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.chatroom.model.service.ChattingService;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ChattingController {
    @Autowired
    private ChattingService service;

    @Autowired
    private BoardService boardService;
//    html 가져오기용 Controller

    // 내 채팅방 리스트
    @GetMapping("/chat/mylist/page")
    public String myChattingList(){
        return "chatting/my-chattingList";
    }

    // 채팅방 소모임
    @GetMapping("/chat/main")
    public ModelAndView chattingMain(ModelAndView mv){
        // 공지사항 가져와보기
        List<Map<String, Object>> notices = boardService.selectAllCateNotice("4");
        mv.addObject("cName", "스터디 / 소모임");
        mv.addObject("notices", notices);
        mv.setViewName("chatting/chattingMain");
        return mv;
    }
    // 채팅방 입장
    @GetMapping("/chat/chatroom/page")
    public ModelAndView chatroom(ModelAndView model, HttpServletRequest req,
                                 @RequestParam(value = "chatNo")String chatNo){
        Map memberId = ((Map)req.getSession().getAttribute("loginMember"));
        model.addObject("memberId",memberId.get("MEMBER_ID"));
        model.addObject("chatNo",chatNo);

        model.setViewName("chatting/chattingroom");
        return model;
    }
    // 메세지 보냄
    @MessageMapping("/chat/{chatNo}")
    @SendTo("/topic/chatroom/{chatNo}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // 메세지 저장 서비스.
        service.saveMessage(chatMessage);
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

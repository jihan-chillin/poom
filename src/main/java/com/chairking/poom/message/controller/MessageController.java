package com.chairking.poom.message.controller;


import com.chairking.poom.member.model.vo.Member;
import com.chairking.poom.message.model.service.MessageService;
import com.chairking.poom.message.model.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService service;
    @Autowired
    PasswordEncoder pwEncoder;
    //쪽지함 탭 보이는 메인페이지
    @GetMapping()
    public String message(String mType,Model m){
        //System.out.println("pw: " + pwEncoder.encode("1234"));
        m.addAttribute("mType",mType);
        return "message/message_main";
    }


    //ajax 받은 쪽지 페이지
    @RequestMapping("/receive")
    public ModelAndView receiveMessage(HttpServletRequest req, ModelAndView mv){
        HttpSession session = req.getSession();
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String myId = val.get("MEMBER_ID");

        System.out.println("myId:" + myId);
        String condition = "AND RECV_MEMBER = '" + myId + "' AND MSG_TYPE !=3";
        List<Map<String,Object>> list = service.getMessage(condition);
        mv.addObject("list",list);
        System.out.println(list);
        mv.setViewName("message/message_receiveMessage");
        return mv;
    }



    //ajax 보낸 쪽지 페이지
    @RequestMapping("/send")
    public ModelAndView sendMessage(HttpServletRequest req, ModelAndView mv){
        HttpSession session = req.getSession();
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String myId = val.get("MEMBER_ID");

        System.out.println("myId:" + myId);
        String condition = "AND MEMBER_ID = '" + myId + "' AND MSG_TYPE !=3";
        List<Map<String,Object>>list = service.getMessage(condition);
        if (list.size() != 0) {
            if (list.get(0).get("READ_CHECK") == null)
                System.out.println("null이다");
        }
        mv.addObject("list",list);
        mv.setViewName("message/message_sendMessage");
        return mv;
    }

    //ajax 휴지통
    @RequestMapping("/block")
    public ModelAndView blockMessage(HttpServletRequest req, ModelAndView mv){
        HttpSession session = req.getSession();
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String myId = val.get("MEMBER_ID");

        String condition = "AND (RECV_MEMBER = '" + myId + "' OR MEMBER_ID = '" + myId + "') AND MSG_TYPE=3";
        List<Map<String,Object>>list = service.getMessage(condition);
        mv.addObject("list",list);
        mv.setViewName("message/message_blockMessage");
        return mv;
    }



    //팝업창
    @RequestMapping("/popup")
    public String messagePopup(HttpServletRequest req, Model m){
        String rcvNm = req.getParameter("rcvNm");
        String rcvId = req.getParameter("rcvId");
        m.addAttribute("rcvNm", rcvNm);
        m.addAttribute("rcvId", rcvId);
        return"message/message_popup";
    }



    //받는사람 팝업
    @GetMapping ("/receiver")
    public String receiverPopup(Model m){
        List<Map<String,Object>> list = service.searchReceiver();
        m.addAttribute("list", list);
        return "/message/message_receiver";
    }

    //쪽지내용 팝업
    @GetMapping("/content")
    public String contentPopup(@RequestParam String msgNo, Model m){
        int result = 0;
        List<Map<String,Object>> list = service.messageContent(msgNo);
        m.addAttribute("list",list);

        if (list != null) {
            result = service.setMsgRead(msgNo);
        }

        if (result > 0)
            System.out.println("읽음처리 성공");
        return "message/message_content";
    }



    //메세지 차단함으로 이동
    @RequestMapping("/moveBlock")
    public String moveBlock(@RequestParam String msgNo, ModelAndView mv){
        System.out.println("move블럭 되나요? " + msgNo);
        int result= service.moveBlock(msgNo);
        System.out.println("나와라 " + result);
       return "/message/message_blockMessage";
    }

    //메세지 발송취소
    @RequestMapping ("/cancelMsg")
    public ModelAndView cancelMsg(@RequestParam String msgNo, ModelAndView mv){
        System.out.println("moveBlockCtrl " + msgNo);
        int result= service.cancelMsg(msgNo);
        if(result>0){
            mv.setViewName("message/message_sendMessage");
        }
        return mv;
    }

    //휴지통 비우기
    @RequestMapping("/emptyBlock")
    public String emptyBlock(){
        int result = service.emptyBlock();
        return "message/message_blockMessage";
    }

    //휴지통 선택 삭제
    @RequestMapping("/selectBlock")
    public String selectBlock(@RequestParam String msgNo){
        System.out.println("삭제할 메세지 번호" + msgNo);
        int result = service.selectBlock(msgNo);
        return "message/message_blockMessage";
    }


    //메세지 보내기
    @RequestMapping("/sendMsg")
    public ModelAndView sendMsg(HttpServletRequest request, HttpSession session, ModelAndView mv){
        //세션에서 내 아이디를 가져온다.
        Message msg = new Message();
        msg.setMemberId(request.getParameter("memberId"));
        msg.setRecvMember(request.getParameter("recvMember"));
        msg.setMsgContent(request.getParameter("msgContent"));
        int result= service.sendMsg(msg);
        mv.addObject("mType", "send");
        if(result>0){
            mv.setViewName("message/message_sendMessage");
        }
        return mv;
    }


    //받는사람 검색
    @RequestMapping("/rSearch")
    public ModelAndView searchReceiver(@RequestParam String sCondition, ModelAndView mv){
        String condition = "WHERE 1=1";

        if (sCondition != null || sCondition.equals("")) {
            condition += (" AND (member_id like '%" + sCondition + "%'");
            condition += (" OR member_name like '%" + sCondition + "%'");
            condition += (" OR member_email like '%" + sCondition + "%'");
            condition += (" OR member_nickname like '%" + sCondition + "%')");

        }

        System.out.println("검색조건" + condition);

        List<Map<String,Object>> list = service.searchReceiverCondition(condition);

        mv.addObject("list", list);
        mv.setViewName("message/message_receiver");
        return mv;
    }



}

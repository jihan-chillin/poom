package com.chairking.poom.message.controller;


import com.chairking.poom.member.model.vo.Member;
import com.chairking.poom.message.model.service.MessageService;
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

    @RequestMapping("/main")
    public ModelAndView main(String type, ModelAndView mv){

        mv.addObject("type", type);
        mv.setViewName("message/message");
        return mv;
    }


    //ajax 받은 쪽지 페이지
    @RequestMapping("/receive")
    public ModelAndView receiveMessage(HttpServletRequest req, ModelAndView mv){
        HttpSession session = req.getSession();
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String myId = val.get("MEMBER_ID");

        System.out.println("myId:" + myId);
        String condition = "AND RECV_MEMBER = '" + myId + "' AND MSG_TYPE!=3";
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
        String condition = "AND MEMBER_ID = '" + myId + "'";
        List<Map<String,Object>>list = service.getMessage(condition);

        if (list.get(0).get("READ_CHECK") == null)
            System.out.println("null이다");
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

        String condition = "AND RECV_MEMBER = '" + myId + "' AND MSG_TYPE=3";
        List<Map<String,Object>>list = service.getMessage(condition);
        mv.addObject("list",list);
        mv.setViewName("message/message_blockMessage");
        return mv;
    }



    //팝업창
    @GetMapping("/popup")
    public String messagePopup(HttpServletRequest req, Model m){
        String rcvNm = req.getParameter("rcvNm");
        String rcvId = req.getParameter("rcvId");
        m.addAttribute("rcvNm", rcvNm);
        m.addAttribute("rcvId", rcvId);
        return"/message/message_popup";
    }

    //받은 쪽지 리스트 출력
//    @GetMapping("/message/messagelist")
//    public String messageBox(Model m){
//        List<Map<String,Object>> list = service.messageBox();
//        m.addAttribute("list",list);
//        return "/message/message_box";
//    }
    
//    //보낸 쪽지 리스트 출력 
//    @GetMapping("/message/send")
//    public String sendMessage(Model m){
//        List<Map<String,Object>> list = service.sendMessage();
//        m.addAttribute("list", list);
//        return "/message/message_box";
//        
//    }


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

//    @RequestMapping("/delete")
//    public ModelAndView deleteMessage(@RequestParam String msgNo, @RequestParam String delType, ModelAndView mv){
//        System.out.println("delete: " + delType);
//        String returnUrl = "";
//        if (delType.equals("delRcv"))
//            System.out.println("delSend case");
//        switch(delType) {
//            case "delSend":
//                System.out.println("delSend case");
//                returnUrl = "message/message_sendMessage";
//                break;
//            case "delRcv":
//                System.out.println("delRcv case");
//                returnUrl = "message/message_receiveMessage";
//                break;
//            case "delBlock":
//                returnUrl = "message/message_blockMessage";
//                break;
//            default:
//                returnUrl = "message/message";
//        }
//        int result = service.deleteMessage(msgNo);
//        mv.addObject("result",result);
//        mv.addObject("delType", delType);
//        mv.setViewName("message/message_main");
//        return mv;
//    }


    //메세지 차단함으로 이동
    @RequestMapping("/moveBlock")
    public ModelAndView moveBlock(@RequestParam String msgNo, ModelAndView mv){
        System.out.println("moveBlockCtrl " + msgNo);
       int result= service.moveBlock(msgNo);
       //if(result>0){
           mv.setViewName("message/message_blockMessage");
       //}
       return mv;
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

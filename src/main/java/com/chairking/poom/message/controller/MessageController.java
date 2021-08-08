package com.chairking.poom.message.controller;


import com.chairking.poom.common.Pagination;
import com.chairking.poom.message.model.service.MessageService;
import com.chairking.poom.message.model.vo.Message;
import com.chairking.poom.noti.controller.NotiController;
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
    @Autowired
    private NotiController mc;
    //쪽지함 탭 보이는 메인페이지
    @GetMapping()
    public String message(String mType, HttpSession session, Model m){
        if (mType != null)
           session.setAttribute("mType", mType);
        return "message/message_main";
    }


    //ajax 받은 쪽지 페이지
    @RequestMapping("/receive")
    public ModelAndView receiveMessage(HttpServletRequest req, ModelAndView mv,
                                       @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                                       @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){
        HttpSession session = req.getSession();
        session.setAttribute("mType", "receive");
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String myId = val.get("MEMBER_ID");
        String condition = "AND RECV_MEMBER = '" + myId + "' AND MSG_TYPE =2 ORDER BY MSG_DATE DESC";


        Pagination pagination = new Pagination(currentPage,cntPerPage,pageSize);
        pagination.setTotalRecordCount(service.messageCount(condition));
        condition += (")A) WHERE RNUM BETWEEN " + pagination.getFirstRecordIndex() + " AND " + pagination.getLastRecordIndex());
        List<Map<String,Object>> list = service.getMessage(condition,pagination);
        mv.addObject("list",list);
        mv.addObject("pagination", pagination);

        mv.setViewName("message/message_receiveMessage");
        return mv;
    }



    //ajax 보낸 쪽지 페이지
    @RequestMapping("/send")
    public ModelAndView sendMessage(HttpServletRequest req, ModelAndView mv,
                                    @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                                    @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){
        HttpSession session = req.getSession();
        session.setAttribute("mType", "send");
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String myId = val.get("MEMBER_ID");
        String condition = "AND MEMBER_ID = '" + myId + "' AND MSG_TYPE = 1  ORDER BY MSG_DATE DESC";

        Pagination pagination = new Pagination(currentPage,cntPerPage,pageSize);
        pagination.setTotalRecordCount(service.messageCount(condition));
        condition += (")A) WHERE RNUM BETWEEN " + pagination.getFirstRecordIndex() + " AND " + pagination.getLastRecordIndex());

        List<Map<String,Object>>list = service.getMessage(condition,pagination);
        if (list.size() != 0) {
            if (list.get(0).get("READ_CHECK") == null)
                System.out.println("null이다");
        }
        mv.addObject("list",list);
        mv.addObject("pagination", pagination);
        mv.setViewName("message/message_sendMessage");
        return mv;
    }

    //ajax 휴지통
    @RequestMapping("/block")
    public ModelAndView blockMessage(HttpServletRequest req, ModelAndView mv,
                                     @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                                     @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){
        HttpSession session = req.getSession();
        session.setAttribute("mType", "block");
        // 세션에서 내 아이디 가져옴
        Map<String,String> val = (Map<String,String>)session.getAttribute("loginMember");
        String myId = val.get("MEMBER_ID");

        String condition = "AND ((RECV_MEMBER = '" + myId + "' AND MSG_TYPE = 4) OR (MEMBER_ID = '" + myId + "' AND MSG_TYPE=3))  ORDER BY MSG_DATE DESC";

        Pagination pagination = new Pagination(currentPage,cntPerPage,pageSize);
        pagination.setTotalRecordCount(service.messageCount(condition));
        condition += (")A) WHERE RNUM BETWEEN " + pagination.getFirstRecordIndex() + " AND " + pagination.getLastRecordIndex());

        List<Map<String,Object>>list = service.getMessage(condition,pagination);
        mv.addObject("list",list);
        mv.addObject("pagination", pagination);
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
        List<Map<String,Object>> list = service.messageContent(msgNo);
        m.addAttribute("list",list);
        return "message/message_content";
    }

    //받은쪽지 내용팝업
    @GetMapping("/receiveContent")
    public String receivePopup(@RequestParam String msgNo, Model m){
        int result = 0;
        List<Map<String,Object>> list = service.messageContent(msgNo);
        m.addAttribute("list",list);

        if (list != null) {
            result = service.setMsgRead(msgNo);
        }

        if (result > 0)
            System.out.println("읽음처리 성공");
        return "message/message_receiveContent";
    }




    //메세지 차단함으로 이동 하는중
    @RequestMapping("/moveBlock")
    public ModelAndView moveBlock(@RequestParam String msgNo, ModelAndView mv){
        int result= service.moveBlock(msgNo);
        if(result >0){
            mv.setViewName("/message/message_blockMessage");
        }
        return mv;
    }

    //메세지 발송취소
    @RequestMapping ("/cancelMsg")
    public ModelAndView cancelMsg(@RequestParam String msgNo, ModelAndView mv){
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
        int result = service.selectBlock(msgNo);
        return "message/message_blockMessage";
    }


    //메세지 보내기
    @RequestMapping("/sendMsg")
    public ModelAndView sendMsg(HttpServletRequest request, HttpSession session, ModelAndView mv){

        Message msg = new Message();
        String recvMember =request.getParameter("recvMember") ;
        msg.setMemberId(request.getParameter("memberId"));
        msg.setRecvMember(recvMember);
        msg.setMsgContent(request.getParameter("msgContent"));
        msg.setType(1);
        int result = 0;
        result += service.sendMsg(msg);
        msg.setType(2);
        result += service.sendMsg(msg);
        mv.addObject("mType", "send");

        // 알람 테이블에 데이터 넣는 메소드 by 희웅
        mc.insertMessageNotiData(getRecentMessageNo(),recvMember);

        if(result>0){
            mv.setViewName("message/message_sendMessage");
        }
        return mv;
    }

    // 촤근 보낸 메세지 번호 가져오는 메소드.
    public String getRecentMessageNo(){
        return service.getRecentMessageNo();
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

        List<Map<String,Object>> list = service.searchReceiverCondition(condition);

        mv.addObject("list", list);
        mv.setViewName("message/message_receiver");
        return mv;
    }



}

package com.chairking.poom.message.controller;


import com.chairking.poom.message.model.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService service;

    //쪽지함 탭 보이는 메인페이지
    @GetMapping()
    public String message(String type,Model m){
        m.addAttribute("type",type);
        return "message/message_main";
    }

    @GetMapping("/main")
    public ModelAndView main(String type, ModelAndView mv){
        mv.addObject("type", type);
        mv.setViewName("message/message");
        return mv;
    }


    //ajax 받은 쪽지 페이지
    @GetMapping("/receive")
    public ModelAndView receiveMessage(String type, ModelAndView mv){
        System.out.println(type);
        List<Map<String,Object>> list = service.receiveMessage();
        mv.addObject("list",list);
        System.out.println(list);
        mv.setViewName("message/message_receiveMessage");
        return mv;
    }



    //ajax 보낸 쪽지 페이지
    @GetMapping("/send")
    public ModelAndView sendMessage(ModelAndView mv){
        List<Map<String,Object>>list = service.sendMessage();
        mv.addObject("list",list);
        mv.setViewName("message/message_sendMessage");
        return mv;
    }



    //팝업창
    @GetMapping("/popup")
    public String messagePopup(){
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
    @GetMapping("/receiver")
    public String receiverPopup(Model m){
        List<Map<String,Object>> list = service.searchReceiver();
        m.addAttribute("list", list);
        return"message/message_receiver";
    }

    //쪽지내용 팝업
    @GetMapping("/message/content")
    public String contentPopup(@RequestParam String msgNo, Model m){
        List<Map<String,Object>> list = service.messageContent(msgNo);
        m.addAttribute("list",list);
        return "message/message_content";
    }

    //받는사람 검색
//    @GetMapping("/message/rSearch")
//    public ModelAndView searchReceiver(ModelAndView mv){
//
//        List<Member> list = service.searchReceiver();
//        mv.addObject("list", list);
//        mv.setViewName("message_receiver");
//        return mv;
//    }



}

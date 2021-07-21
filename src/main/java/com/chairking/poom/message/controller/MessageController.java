package com.chairking.poom.message.controller;


import com.chairking.poom.message.model.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {

    @Autowired
    private MessageService service;

    //쪽지함 보이는 페이지
    @GetMapping("/message")
    public String message(){
        return "/message/message_box";
    }

    //팝업창
    @GetMapping("message/popup")
    public String popup(){
        return"message/message_popup";
    }
}

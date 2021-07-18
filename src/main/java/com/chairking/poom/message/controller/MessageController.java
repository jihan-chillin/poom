package com.chairking.poom.message.controller;


import com.chairking.poom.message.model.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping()
    public String message(){
        return "/message/message_box";
    }
}

package com.chairking.poom.mywrite.controller;

import com.chairking.poom.mywrite.model.service.MywriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class MyWriteController {

//    @Autowired
//    private MywriteService service;

//    // 내가 쓴 글
    @GetMapping("/mywrite")
    public String mywrite(){
        return "/member/mywrite";
    }

    // 내가 쓴 댓글
    @GetMapping("/mycomment")
    public String mycomment(){
        return "/member/mycomment";
    }

    // 내가 찜한 글
    @GetMapping("/mylike")
    public String mylike(){
        return "/member/mylike";
    }

}


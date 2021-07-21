package com.chairking.poom.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    // 프로필정보 수정 html 연결
    @GetMapping("/modiprofile")
    public String membermodi(){
        return "/member/modiprofile";
    }

    // 개인정보 수정 html 연결
    @GetMapping("/modiprivacy")
    public String modiprivacy(){
        return "/member/modiprivacy";
    }

    // 내가 쓴 글
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

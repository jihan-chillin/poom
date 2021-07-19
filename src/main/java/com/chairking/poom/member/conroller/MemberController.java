package com.chairking.poom.member.conroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    // 정보수정 html 연결
    @GetMapping("/membermodi")
    public String membermodi(){
        return "member/mypage_profile";
    }
}

package com.chairking.poom.member.controller;

import com.chairking.poom.member.model.service.MemberService;
import com.chairking.poom.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService service;

    // 1. 프로필정보 수정창으로 이동
    // 화면 전환용
    @GetMapping("/modiprofile")
    public String membermodi() {
        return "/member/modiprofile";
    }

    // 2. 프로필 수정완료되면 프로필 수정페이지로 이동
    // 파라미터가 String인 것들만 받을 거기 때문에 RequestBody로 가져옴.
    @PostMapping("/updateProfile")
    public ModelAndView updateProfile(ModelAndView mv, Member m, MultipartFile[] inputfile, @RequestBody Map<String, String> param) {

        // 파라미터 값 변수에 저장
        String memberId = param.get("memberId");
        String nick = param.get("nick");
        String intro = param.get("intro");
//        String profile = param.get("inputfile");

        System.out.println(memberId + nick + intro);

        //

        int result = service.updatePrivacy(m);

        mv.setViewName("member/modiprofile");

        return mv;
    }


    // 3. 개인정보 수정창으로 이동
    @GetMapping("/modiprivacy")
    public String modiprivacy() {
        return "/member/modiprivacy";
    }

    // 4. 개인정보 update완료 시 개인정보 창으로 이동
    @PostMapping("/updatePrivacy")
    public ModelAndView updatePrivacy(HttpServletRequest req, ModelAndView mv, Member m, @RequestBody Map<String, String> param) {

        // ▼ 회원 아이디 sessionr값 가져올 때
        //HttpSession session = req.getSession();

        // 임의 아이디
        String id = "test";

        // 파라미터 값 변수에 저장
        String pw = param.get("pw");
        String email = param.get("email");
        String loc = param.get("loc");

        // 객체에 파라미터 set
        m.setMemberId(id);
        m.setMemberPw(pw);
        m.setMemberEmail(email);
        m.setMemberLoc(loc);

        System.out.println(m);

        int result = service.updatePrivacy(m);

        mv.addObject("m", m);
        mv.setViewName("member/modiprivacy");

        return mv;
    }

}

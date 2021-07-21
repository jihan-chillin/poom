package com.chairking.poom.member.controller;

import com.chairking.poom.member.model.service.MemberService;
import com.chairking.poom.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String membermodi(){
        return "/member/modiprofile";
    }

    // 2. 프로필 수정완료되면 프로필 수정페이지로 이동
    // 파라미터가 String인 것들만 받을 거기 때문에 RequestBody로 가져옴.
    @PostMapping("/updateProfile")
    public ModelAndView updateProfile(ModelAndView mv, MultipartFile[] inputfile, @RequestBody Map<String, String> param){
        String memberId = param.get("memberId");
        String nick = param.get("nick");
        String intro = param.get("intro");
        String profile = param.get("inputfile");

        System.out.println(memberId+nick+intro);

        mv.setViewName("member/modiprofile");

        return mv;
    }


    // 3. 개인정보 수정창으로 이동
    @GetMapping("/modiprivacy")
    public String modiprivacy(){
        return "/member/modiprivacy";
    }

    // 4. 개인정보 update완료 시 개인정보 창으로 이동
    @PostMapping("/updatePrivacy")
    public ModelAndView updatePrivacy(HttpServletRequest req, ModelAndView mv, Member m, @RequestBody Map<String, String> param ){

        // ▼ 회원 아이디 sessionr값 가져올 때
        //HttpSession session = req.getSession();

        String pw = param.get("pw");
        String email = param.get("email");
        String loc = param.get("loc");



        return mv;
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

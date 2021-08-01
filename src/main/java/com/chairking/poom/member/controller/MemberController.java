package com.chairking.poom.member.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.member.model.service.LoginService;
import com.chairking.poom.member.model.service.MemberService;
import com.chairking.poom.member.model.vo.Member;

@Controller
@RequestMapping("/member")
@SessionAttributes("loginMember")
public class MemberController {

    @Autowired
    private MemberService service;
    
    @Autowired	
	LoginService loginservice;
    
    @Autowired
	ResourceLoader resourceLoader;

    // 1. 프로필정보 수정창으로 이동
    // 화면 전환용
    @GetMapping("/modiprofile")
    public String membermodi() {
        return "member/modiprofile";
    }

    // 2. 프로필 수정완료되면 프로필 수정페이지로 이동
    @PostMapping("/updateProfile")
    public ModelAndView updateProfile(ModelAndView mv,
			@RequestParam(value="input_file",required=false) MultipartFile[] inputfile,
			@RequestParam Map param, HttpServletRequest req) {
    	
    	String absolutePath = System.getProperty("user.dir")+"/src/main/resources/static/images/profile/";
    	String oriName=inputfile[0].getOriginalFilename();
    	
    	if(oriName.equals("")) {
    		oriName="poom_profile.jpg";
    		param.put("memberImg", oriName);
    	}else {
			String ext=oriName.substring(oriName.lastIndexOf("."));
			//리네임규칙설정
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
			int rndNum=(int)(Math.random()*10000);
			String reName=sdf.format(System.currentTimeMillis())+"_"+rndNum+ext;
			param.put("memberImg", reName);
			//리네임으로 파일업로드하기
			try {
				inputfile[0].transferTo(new File(absolutePath+reName));
			}catch(IOException e) {
				e.printStackTrace();
			}
    	}

    	String msg="";
    	String loc="/login/main";
		int result = service.updateProfile(param);
		Map<String,Object> m = loginservice.selectMember(param);
		mv.addObject("loginMember",m);
		
		if(result>0) {
			msg="수정완료!";
		}else {
			msg="수정실패! 다시 시도해주세요.";
		}

		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");

        return mv;
    }

    // 3. 개인정보 수정창으로 이동
    @GetMapping("/modiprivacy")
    public String modiprivacy() {
        return "member/modiprivacy";
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

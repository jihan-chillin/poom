package com.chairking.poom.member.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.member.model.service.LoginService;
import com.chairking.poom.member.model.service.MemberService;

@Controller
@RequestMapping("/member")
@SessionAttributes("loginMember")
public class MemberController {

    @Autowired
    private MemberService service;
    
    @Autowired	
	private LoginService loginservice;
    
    //암호화
  	@Autowired
  	PasswordEncoder pwEncoder;

    //edit 클릭시 마이페이지로 이동
    @RequestMapping("/mypage")
    public String myPage() {
    	return "member/mypage";
    }
    
    //프로필 수정 창으로 이동 ajax -> 화면전환용
    @GetMapping("/modiprofile")
    public String membermodi() {
        return "member/modiprofile";
    }

    //프로필 수정 -> 완료시 메인화면으로
    @PostMapping("/updateProfile")
    public ModelAndView updateProfile(ModelAndView mv,
			@RequestParam(value="input_file",required=false) MultipartFile[] inputfile,
			@RequestParam Map param, HttpServletResponse res, HttpServletRequest req) throws Exception {
    	
//    	String directoryName = System.getProperty("user.dir");
//      String folderPath = directoryName + "\\src\\main\\resources\\static\\images\\profile\\";
    	
    	String path=req.getServletContext().getRealPath("/resources/profile/");
    	File dir=new File(path);
		//폴더가 없다면 생성
		if(!dir.exists()) dir.mkdirs();
    	String oriName=inputfile[0].getOriginalFilename();
    	
    	if(oriName.equals("")) {
    		oriName="poom_profile.jpg";
    		param.put("memberImg", oriName);
    	}else {
			String ext=oriName.substring(oriName.lastIndexOf("."));
			//리네임규칙설정
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int rndNum=(int)(Math.random()*10000);
			String reName=sdf.format(System.currentTimeMillis())+"_pro"+rndNum+ext;
			param.put("memberImg", reName);
			
			//리네임으로 파일업로드하기
			try {
				inputfile[0].transferTo(new File(path+reName));
			}catch(IOException e) {
				e.printStackTrace();
			}
			
    	}

    	String msg="";
    	String loc="";
		int result = service.updateProfile(param);
		Map<String,Object> m = loginservice.selectMember(param);
		mv.addObject("loginMember",m);
		if(m.get("INTRO")==null) {
			m.put("INTRO", null);
		}
		
		if(result>0) {
			msg="수정완료!";
			loc="/login/main";
		}else {
			msg="수정실패! 다시 시도해주세요.";
			loc="/member/mypage";
		}

		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");

        return mv;
    }

    //개인정보 수정 창으로 이동 ajax -> 화면전환용
    @GetMapping("/modiprivacy")
    public String modiprivacy() {
        return "member/modiprivacy";
    }

    //개인정보 수정 -> 완료시 메인화면으로
    @PostMapping("/updatePrivacy")
    public ModelAndView updatePrivacy(ModelAndView mv, @RequestParam Map<String, String> param) {
    	
    	//비밀번호 암호화
		param.put("pw", pwEncoder.encode((String)param.get("pw")));
    	
    	int result = service.updatePrivacy(param);
    	Map<String,Object> m = loginservice.selectMember(param);
		mv.addObject("loginMember",m);
		
		String msg="";
    	String loc="/login/main";
		if(result>0) {
			msg="수정완료!";
		}else {
			msg="수정실패! 다시 시도해주세요.";
			loc="/member/mypage";
		}

		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");

        return mv;
    }

}

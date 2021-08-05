package com.chairking.poom.mywrite.controller;

//import com.chairking.poom.mywrite.model.service.MywriteService;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.common.Pagination;
import com.chairking.poom.mywrite.model.service.MywriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MyWriteController {

    @Autowired
    private MywriteService service;

    // 내가 쓴 글 리스트 가져오기
    // 댓글수 넣는 거 수정해야 함.
    @GetMapping("/mywrite")
    public ModelAndView mywrite(ModelAndView mv, HttpServletRequest req,
                                // cPage : 페이지바에 숫 자 몇 개?
                                @RequestParam(value = "cPage", defaultValue = "1") int cPage
           ){

//             Map memberId  = (Map)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
//             System.out.println("멤버아이디 가져오기"+memberId);

            Object memberId = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
            System.out.println("memberId는  : " + memberId);

            // 내가 쓴 글 개수
            int totalData = service.countMyWrite();
            // 한 페이지 당 띄울 글 개수
            int numPerpage = 10;

            List<Map<String, Object>> list = service.MywriteList(cPage, numPerpage, memberId);
             // 1. 내가 쓴 글 리스트
            mv.addObject("list", list);


            // 2. 해당 글의 댓글 수, 타이틀 가져오자.
            List<Map<String, Object>> commentCount = service.commentCount(cPage, numPerpage);
            mv.addObject("c", commentCount);
            mv.setViewName("/member/mywrite");
            return mv;
    }


    // 내가 쓴 댓글 리스트
    @GetMapping("/mycomment")
    public String mycomment(ModelAndView mv,
                            @RequestParam(value = "cPage", defaultValue = "1") int cPage)
    {
        // 내가 쓴 댓글 개수
        int totalComment = service.countMyComment();
        // 한 페이지 당 띄울 댓글 개수
        int numPerpage = 10;

        List<Map<String, Object>> cList = service.MyCommentList(cPage, numPerpage);

        mv.addObject("cList", cList);

        return "/member/mycomment";
    }

    // 내가 찜한 글
    @GetMapping("/mylike")
    public String mylike(){
        return "/member/mylike";
    }

}


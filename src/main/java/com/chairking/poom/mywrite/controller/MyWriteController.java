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
                                @RequestParam(value = "cPage", defaultValue = "1") int cPage,
                                @RequestParam(value = "numPerpage", required = false, defaultValue = "10") int numPerpage,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize
           ){

            // 로그인한 멤버 session가져오기
            Object memberId = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
            System.out.println("memberId는  : " + memberId);
            // 페이징 처리
            Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
            // 전체 게시글 개수
            int totalData = service.countMyWrite();

//            System.out.println(totalData +" : 토탈데이터");

            // 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
            pagination.setTotalRecordCount(totalData);

            // 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
            List<Map<String, Object>> list = service.MywriteList(pagination, memberId);

            System.out.println("페이지네이션 : "+pagination);

            mv.addObject("list", list);
            mv.addObject("pagination", pagination);
            mv.setViewName("member/mywrite");
            return mv;
    }


    // 내가 쓴 댓글 리스트
    @GetMapping("/mycomment")
    public ModelAndView mycomment(ModelAndView mv, HttpServletRequest req,
                            // cPage : 페이지바에 숫 자 몇 개?
                            @RequestParam(value = "cPage", defaultValue = "1") int cPage,
                            @RequestParam(value = "numPerpage", required = false, defaultValue = "10") int numPerpage,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize
    ){
        Object memberId = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
        System.out.println("memberId는  : " + memberId);
        // 페이징 처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
        // 전체 게시글 개수
        int totalData = service.countMyComment();
        System.out.println(totalData +" : 코멘트 토탈데이터");
        // 페이징처리
        pagination.setTotalRecordCount(totalData);
        System.out.println("마이댓글 토탈데이터 : " + totalData);

        // 내가 쓴 댓글 가져오기
        List<Map<String, Object>> cList = service.MyCommentList(pagination, memberId);

        mv.addObject("cList", cList);
        mv.addObject("pagination", pagination);
        mv.setViewName("member/mycomment");

        System.out.println("코멘트 mv : " + mv);

        return mv;
    }

    // 내가 찜한 글
    @GetMapping("/mylike")
    public ModelAndView mylike(ModelAndView mv, HttpServletRequest req,
                               @RequestParam(value = "cPage", defaultValue = "1") int cPage,
                               @RequestParam(value = "numPerpage", required = false, defaultValue = "10") int numPerpage,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize
    ){

        // 로그인한 멤버 session가져오기
        Object memberId = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
        System.out.println("memberId는  : " + memberId);
        // 페이징 처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
        // 전체 찜한 게시글 개수
        int totalData = service.countMyLike();
        pagination.setTotalRecordCount(totalData);
        // 내가 찜한 글 가져오기
        List<Map<String, Object>> list = service.MyLikeList(pagination, memberId);

        System.out.println("리스트에 들어오는 값 잘 들어오는지?"+list);

        mv.addObject("list", list);
        mv.addObject("pagination",pagination);
        mv.setViewName("/member/mylike");

        return mv;

    }

}


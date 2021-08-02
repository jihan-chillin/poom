package com.chairking.poom.hashTag.controller;

import com.chairking.poom.board.controller.BoardController;
import com.chairking.poom.hashTag.model.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TagJsonController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BoardController boardController;

    @GetMapping("/tag/my/data")
    public List getMyTagData(HttpServletRequest req){
        String loginId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
//        log.info("아이디 : {}" , loginId);
        List tagData = tagService.getMyTagData(loginId);

        return tagData;
    }
    @GetMapping("/tag/add")
    public int addTag(
            @RequestParam(value = "keyword")String keyword,
            @RequestParam(value = "ref")String ref,
            HttpServletRequest req
                      ){
        String loginId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");

        if(ref.equals("member")) {
            // 멤버태그에 추가
            tagService.insertMemberTag(loginId, keyword);
        }else{
            // 게시물태그에 추가
            tagService.insertBoardTag(getBoardNo(),keyword);
        }

        return tagService.addTag(keyword);
    }
    @GetMapping("/tag/delete")
    public void deleteTag(@RequestParam(value = "tagName")String tagName){
        tagService.deleteTag(tagName);
    }

    @GetMapping("/tag/search")
    public List<Map<String,String>> searchTag(@RequestParam(value = "keyword")String keyword){
        List<Map<String,String>> tagData = tagService.searchTag(keyword);

        return tagData;
    }

    // 방금 등록된 게시글 제목 가져옴
    public String getBoardNo(){
        return tagService.getBoardNo();
    }

    // 태그 클릭시 게시물로 이동.
    @GetMapping("/tag/board")
    public void moveToBoardFromTag(@RequestParam String tagName){
        List<String> boardNo = tagService.getBoardNoFromTag(tagName);
//        boardController.selectBoardFromTag(boardNo);
    }

}

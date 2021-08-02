package com.chairking.poom.hashTag.controller;

import com.chairking.poom.board.controller.BoardController;
import com.chairking.poom.hashTag.model.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BoardController boardController;

    @GetMapping("/tag/my/page")
    public String moveMyTagPage(){
        return "hashtag/myhashtag";
    }

    public void insertTag(Map tag){
        try {
            tagService.insertTag((String) tag.get("keyword"));
        }catch (Exception e){
            log.info("태그 테이블에서 겹침 ");
        }
        log.info("태그 넣기 성공");
    }

    // 태그 클릭시 게시물로 이동.
    @GetMapping("/tag/board")
    public ModelAndView moveToBoardFromTag(@RequestParam(value = "tagName") String tagName, ModelAndView mv,
                                   @RequestParam(value = "cPage", defaultValue = "1")int cPage){
        int numPerPage= 5;
        List<Map<String, Object>> boardData = tagService.getBoardNoFromTag(tagName,cPage,numPerPage);

        mv.addObject("oList",boardData);
        mv.setViewName("board/board_list");
        return mv;
    }
}


package com.chairking.poom.hashTag.controller;

import com.chairking.poom.hashTag.model.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@Slf4j
public class TagController {
    @Autowired
    private TagService service;

    @GetMapping("/tag/my/page")
    public String moveMyTagPage(){
        return "hashtag/myhashtag";
    }

    public void insertTag(Map tag){
        try {
            service.insertTag((String) tag.get("keyword"));
        }catch (Exception e){
            log.info("태그 테이블에서 겹침 ");
        }
        log.info("태그 넣기 성공");
    }
}


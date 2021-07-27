package com.chairking.poom.hashTag.controller;

import com.chairking.poom.hashTag.model.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TagJsonController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tag/my/data")
    public List getMyTagData(HttpServletRequest req){
        String loginId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
        log.info("아이디 : {}" , loginId);
        List tagData = tagService.getMyTagData(loginId);

        return tagData;
    }
}

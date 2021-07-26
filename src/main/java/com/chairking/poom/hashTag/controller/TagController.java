package com.chairking.poom.hashTag.controller;

import com.chairking.poom.hashTag.model.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class TagController {
    @Autowired
    private TagService service;

    public void insertTag(Map tag){
        service.insertTag(tag);
    }
}


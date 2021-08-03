package com.chairking.poom.popuprank.controller;

import com.chairking.poom.popuprank.model.service.PopupRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PopupRankJsonController {
    @Autowired
    private PopupRankService service;

    @GetMapping("/popup/rank/data")
    public List<Map> getPopupData(){
        return service.memberData();
    }
}

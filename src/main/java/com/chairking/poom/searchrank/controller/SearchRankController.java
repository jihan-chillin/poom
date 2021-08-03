package com.chairking.poom.searchrank.controller;

import com.chairking.poom.searchrank.model.service.SearchRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchRankController {
    @Autowired
    private SearchRankService service;

    @GetMapping("/rank/searching")
    public List<Map>getSearchRank(){
        return service.getSearchRank();
    }
}

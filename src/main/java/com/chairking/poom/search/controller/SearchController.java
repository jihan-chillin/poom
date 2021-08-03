package com.chairking.poom.search.controller;

import com.chairking.poom.search.model.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.chairking.poom.common.Pagination;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService service;

    @GetMapping String search(){
        return "search/search_main";
    }

    @RequestMapping("/list")
    public ModelAndView search(ModelAndView mv,
        @RequestParam(value = "uInput", required = false, defaultValue="") String uInput,
        @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
        @RequestParam(value = "cntPerPage", required = false, defaultValue = "15") int cntPerPage,
        @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {


        Pagination pagination = new Pagination(currentPage,cntPerPage,pageSize);
        pagination.setTotalRecordCount(service.searchCount());
        String where = "WHERE rownum BETWEEN "
                + pagination.getFirstRecordIndex()
                + " AND "
                + pagination.getLastRecordIndex()
                + " AND (title like '%" + uInput + "%' OR "
                + "content like '%" + uInput + "%' OR "
                + "write_date like '%" + uInput + "%' OR "
                + "member_id like '%" + uInput + "%') "
                + "ORDER BY write_date desc";
        List<Map<String,Object>> list = service.searchList(where);
        mv.addObject("list", list);
        mv.setViewName("search/search_list");
        return mv;
    }
}

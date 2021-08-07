package com.chairking.poom.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.model.service.PayAdminService;
import com.chairking.poom.common.Pagination;

@Controller
@RequestMapping("/payAdmin")
public class PayAdminController {

	@Autowired
	private PayAdminService service;
	
	//결제관리 첫화면
	@GetMapping()
	public ModelAndView pay(ModelAndView mv,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage, //현재페이지
            @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage, //numPerpage
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		//어제~-7일꺼까지의 리스트 불러오는 쿼리
		//페이징처리
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		pagination.setTotalRecordCount(service.allPaymentCount());
		List<Map<String,Object>> list = service.allPayment(pagination);
		//rollup으로 총 합계금액 가져오는 쿼리
		List<Map<String,Object>> sumList=service.sumAllPayment();
		List<Map<String,Object>> real=new ArrayList();
		
		for(int i=0;i<sumList.size();i++) {
			if(sumList.get(i).size()==3) {
				Map<String,Object> map=new HashMap();
				map.put("S", sumList.get(i).get("S"));
				map.put("PAY_DATE", sumList.get(i).get("PAY_DATE"));
				map.put("ITEM_TYPE", sumList.get(i).get("ITEM_TYPE"));
				real.add(map);
			}
			if(i==sumList.size()-2) {
				Map<String,Object> map=new HashMap();
				map.put("S", Integer.parseInt(String.valueOf(sumList.get(i).get("S"))));
				map.put("PAY_DATE", sumList.get(i).get("PAY_DATE"));
				map.put("ITEM_TYPE", sumList.get(i).get("ITEM_TYPE"));
				real.add(map);
			}
		}
		//-1~-7일 날짜 구하기
		Calendar c1 = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("M월 d일"); // 날짜 포맷 
		String[] dayArr=new String[7];
		for(int i=0; i<7; i++) {
			c1.add(Calendar.DATE, -1); //
			String d = sdf.format(c1.getTime()); // String으로 저장
			dayArr[i]=d;
		}
		mv.addObject("type","pay");
		mv.addObject("list",list);
		mv.addObject("sumList",real);
		mv.addObject("days", dayArr);
		mv.setViewName("admin/admin_pay");
		return mv;
	}
	//최근결제내역상세
	@GetMapping("/orderDetail")
	public ModelAndView orderDetail(ModelAndView mv,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage, //현재페이지
            @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage, //numPerpage
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		//최근구매내역 상세페이지 (리스트 불러오기), 페이지바 해야함
		//페이징처리
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		pagination.setTotalRecordCount(service.allPaymentCount());
		List<Map<String,Object>> list = service.allPayment(pagination);
		mv.addObject("list", list);
		mv.addObject("pagination",pagination);
		mv.setViewName("admin/order_detail");
		return mv;
	}
	
	//매출상세
	@GetMapping("/payDetail")
	public ModelAndView payDetail(@RequestParam (required=false) Map<String,String> map,ModelAndView mv) {
		List<Map<String,String>> list;
		String first="";
		String second="";
		if(map.isEmpty()) {
			Calendar c1 = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷 
			c1.add(Calendar.DATE, -1); //
			String d = sdf.format(c1.getTime()); // String으로 저장
			Map<String,String> param = new HashMap();
			param.put("first",d);
			param.put("second",d);
			first=param.get("first");
			second=param.get("second");
			list = service.selectPayDetail(param);
		}else {
			list = service.selectPayDetail(map);
			first=map.get("first");
			second=map.get("second");
		}
		mv.addObject("first",first);
		mv.addObject("second",second);
		mv.addObject("list", list);
		mv.setViewName("admin/paydetail");
		return mv;
	}
		
}

package com.chairking.poom.board.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import ch.qos.logback.core.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService service;

	//게시글 등록 페이지로 이동
	@RequestMapping(path="/board/form", method=RequestMethod.GET)

	public String boardForm(){

		return "board/board_form";
	}

	// 게시글 등록
//	@PostMapping("/board/insert")
//	public ModelAndView insertBoard(HttpServletRequest req, ModelAndView mv,
//									@RequestParam Map param){
//
//
//	}

	//모든 게시글 리스트 가져오는 서비스
	@GetMapping("/board/all")
	public ModelAndView selectAllBoard(ModelAndView mv,
									   @RequestParam(value="cPage", defaultValue = "1") int cPage) {

		// 게시글 조회수
		// 얘는 나중에 로그인 session값으로 들어오는 거 적용되면 할 것
		//int readcount = service.readcount();
		int numPerpage = 5;

		List<Map<String, Object>> oList = service.selectAllBoard(cPage, numPerpage);

		mv.addObject("oList", oList);
		mv.setViewName("board/board_list");
		return mv;
	}
	
	//게시글 상세 조회
	@GetMapping("/board/view")
	public ModelAndView boardView(@RequestParam String boardNo, ModelAndView mv) {
		System.out.println(boardNo);
		mv.setViewName("board/board_view");
		mv.addObject("board", service.selectBoard(boardNo));
		mv.addObject("commentList", service.selectCommentList(boardNo));
		return mv;
	}

	//메인피드등록하기(파일X, 텍스트만 가능)
	@PostMapping("/board/feedWrite")
	public ModelAndView feedWrite(@RequestParam Map param, ModelAndView mv) {

		//게시글 등록
		int result=service.insertFeed(param);

		String msg="";
		String loc="/login/main";
		if(result>0) {
			msg = "게시글 등록 완료!";
		}else {
			msg = "등록실패! 다시 시도해주세요.";
		}
		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");

		return mv;
	}
	
	//메인피드글 불러오기
	@RequestMapping("/board/feedNew")
	public ModelAndView feedNew(@RequestParam Map param, ModelAndView mv) {
		
		if(param.get("loc").equals("전국")) {
			param.put("loc","");
		}
		
		List<Map<String, Object>> feedList = service.feedList(param);
		System.out.println(feedList.get(0));
		mv.addObject("feedList",feedList);
		mv.setViewName("main/feedList");
		
		return mv;
	}
}

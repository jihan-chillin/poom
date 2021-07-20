package com.chairking.poom.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.board.model.service.BoardService;

@Controller
@RequestMapping("/")
public class BoardController {

	@Autowired
	private BoardService service;
	
	@RequestMapping(path="/board/boardForm", method=RequestMethod.GET)
	public String boardForm() {
		return "board/board_form.html";
	}
	
	@PostMapping("/board/insert")
	public ModelAndView insertBoard(ModelAndView mv) {
		mv.setViewName("board/board_view.html");
		return mv;
	}
}

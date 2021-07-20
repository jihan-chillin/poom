package com.chairking.poom.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Controller
@RequestMapping("/")
public class BoardController {

	@Autowired
	private BoardService service;
	
	@RequestMapping(path="/board/boardForm", method=RequestMethod.GET)
	public String boardForm() {
		return "board/board_form";
	}
	
	@PostMapping("/board/insert")
	public ModelAndView insertBoard(Board board, ModelAndView mv, MultipartFile[] upFile, HttpServletRequest req) {
		board.setMemberId("test");
		board.setBoardLoc("1");
		
		//받아온 게시글 첨부파일을 imgs객체로 저장하기
		List<BoardImage> imgs=new ArrayList<>();
		if(upFile !=null) {
			String path=req.getServletContext().getRealPath("/resources/static/images/board/");
			File dir=new File(path);
			if(!dir.exists()) dir.mkdirs();
			
			for(MultipartFile f:upFile) {
				if(!f.isEmpty()) {
					String oriName=f.getOriginalFilename();
					String ext=oriName.substring(oriName.lastIndexOf("."));
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
					int rndNum=(int)(Math.random()*10000);
					String reName=sdf.format(System.currentTimeMillis())+"_"+rndNum+ext+"_"+board.getBoardCate();
					try {
						f.transferTo(new File(path+reName));
						imgs.add(BoardImage.builder().boardNo(Integer.parseInt(board.getBoardNo())).originImg(oriName).renameImg(reName).build());
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		board.setImages(imgs);
		
		int result=service.insertBoard(board);
		
		mv.setViewName("board/board_view");
		return mv;
	}
	
	@GetMapping("/board/selectAll")
	public ModelAndView selectAllBoard(ModelAndView mv) {
		mv.setViewName("board/board_list");
		mv.addObject("list", service.selectAllBoard());
		return mv;
	}
	
	
}

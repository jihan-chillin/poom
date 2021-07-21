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
	
	//게시글 등록 페이지로 이동
	@RequestMapping(path="/board/boardForm", method=RequestMethod.GET)
	public String boardForm() {
		return "board/board_form";
	}
	
	//게시글 등록 서비스
	@PostMapping("/board/insert")
	public ModelAndView insertBoard(Board board, ModelAndView mv, MultipartFile[] boardImg, HttpServletRequest req) {
		board.setMemberId("test");
		board.setBoardLoc("1");
		
		//받아온 게시글 첨부파일을 imgs객체로 저장하기
		List<BoardImage> imgs=new ArrayList<>();
		
		if(boardImg !=null) {
			String path=req.getServletContext().getRealPath("/images/board/");
			File dir=new File(path);
			if(!dir.exists()) dir.mkdirs();
			
			for(MultipartFile f:boardImg) {
				if(!f.isEmpty()) {
					String oriName=f.getOriginalFilename();
					String ext=oriName.substring(oriName.lastIndexOf("."));
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
					int rndNum=(int)(Math.random()*10000);
					String reName=sdf.format(System.currentTimeMillis())+"_"+rndNum+ext+"_"+board.getBoardCate();
					
					System.out.println(oriName+" -> "+reName);
					
					
					try {
						f.transferTo(new File(path+reName));
						BoardImage bi=new BoardImage();
						bi.setOriginImg(oriName);
						bi.setRenameImg(reName);
						System.out.println(bi);
						imgs.add(bi);
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		board.setImages(imgs);

		//게시글 등록
		int result=service.insertBoard(board);
		
		mv.addObject("board", service.selectBoard(String.valueOf(service.selectBoardNo(board))));
		mv.setViewName("board/board_view");
		return mv;
	}
	
	//모든 게시글 리스트 가져오는 서비스
	@GetMapping("/board/selectAll")
	public ModelAndView selectAllBoard(ModelAndView mv) {
		mv.setViewName("board/board_list");
		mv.addObject("list", service.selectAllBoard());
		return mv;
	}
	
	//게시글 조회
	@GetMapping("/board/boardView")
	public ModelAndView boardView(String boardNo, ModelAndView mv) {
		mv.setViewName("board/board_view");
		mv.addObject("board", service.selectBoard(boardNo));
		return mv;
	}
	
}

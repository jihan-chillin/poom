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

	// ckeditor 파일 업로드

	@RequestMapping(value="/images/ckeditor", method = RequestMethod.POST)
	public void imageUpload(HttpServletResponse res, HttpServletRequest req,
							MultipartHttpServletRequest multireq, @RequestParam MultipartFile upload) throws Exception{


		res.setCharacterEncoding("utf-8"); // 입력된 파일 받을 때 한글깨짐 방지
		res.setContentType("text/html;charset=utf-8"); // textarea상으로 이미지 받을 대 한글깨짐 방지

		OutputStream out = null;
		PrintWriter writer = null;

		// 나중에 rename할 때 필요한 것들
		int rndNum=(int)(Math.random()*100000);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

		// 폴더 경로 구해보자
		Path path = Paths.get("");
		String directoryName = path.toAbsolutePath().normalize().toString(); // C:\Users\JIHAN\IdeaProjects\poom
		String folderPath = directoryName + "\\src\\main\\resources\\static\\images\\ckImage\\";


		// 파일 원래 이름
		// 얘를 서버에 전송해줘야 함.
		String filename = upload.getOriginalFilename();
//		byte[] bytes = filename.getBytes(StandardCharsets.UTF_8);

		// 파일명이랑 확장자 분리
		String subname  = filename.substring(0, filename.lastIndexOf("."));
		String ext  = filename.substring(filename.lastIndexOf(".")).toLowerCase();
		System.out.println(subname+ " : subname, 얘는 확장자 :" + ext);

		System.out.println("이거 OKKY " + req.getSession().getServletContext().getRealPath("/"));



		try{

			// 랜덤문자로 rename
			String renamedFile =sdf.format(System.currentTimeMillis())+"_"+rndNum+"_"+subname+ext;
			System.out.println("renamedFile은 얘 : "+renamedFile +"/ renamed 형"+ renamedFile.getClass().getName());
			byte[] bytes = renamedFile.getBytes();

			String absolute = req.getContextPath().concat("resources");

			// 로컬 폴더주소
			String filepath = absolute+File.separator+ "/images/ckeditor";

			System.out.println("폴더주소" + filepath);

			// 로컬폴더 만들기
			File uploadDir = new File(filepath);
			System.out.println("새로운 폴더 만들어봄");
			if(!uploadDir.exists()){
				uploadDir.mkdirs();
				System.out.println ("기존에 폴더가 없어서 새로운 폴더 생성");
			}

			File uploadFile = new File(uploadDir,renamedFile);
			// 해당 파일이 없을 경우 filepath
			if(!uploadFile.exists()){
				uploadFile.mkdirs();
			}

			// 이미지 보내야지
			String fileUrl = req.getContextPath()+"/images/ckeditor";

			out = new FileOutputStream(new File(fileUrl+renamedFile));
			out.write(bytes);
			// outputStream에 저장된 데이터를 전송 + 초기화
			out.flush();


			String callback = req.getParameter("CKEditorFuncNum");
			writer = res.getWriter();
			// json으로 보내기
			writer.println("{\"filename\" : \"" + renamedFile + "\", \"uploaded\" : 1, \"url\":\"" + fileUrl + "\"}");
			writer.flush();




		}catch (IOException e){
			e.printStackTrace();
		}finally {
			if(out!=null){
				out.close();
			}
			if(writer !=null){
				writer.close();
			}
		}

		return;
	}

	// 이미지 다운로드 받기
//	@RequestMapping("/ckeditor/fileDownload")
//	public void ckSubmit(@RequestParam(value="fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
//		File file = FileUtilities.getDownloadFile(fileName, "files/ckeditor");
//		try {
//			byte[] data = FileUtils.readFileToByteArray(file);

//			response.setContentType(FileUtilities.getMediaType(fileName).toString());
//			response.setContentLength(data.length);
//			response.setHeader("Content-Transfer-Encoding", "binary");
//			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";"); response.getOutputStream().write(data);
//			response.getOutputStream().flush(); response.getOutputStream().close();
//			} catch (IOException e) {
//				throw new RuntimeException("파일 다운로드에 실패하였습니다.");
//			} catch (Exception e) {
//				throw new RuntimeException("시스템에 문제가 발생하였습니다.");
//		} }







		//게시글 등록 서비스
	@PostMapping("/board/insert")
	public ModelAndView insertBoard(Board board, ModelAndView mv, MultipartFile[] boardImg) throws IOException {
		board.setMemberId("test");
		board.setBoardLoc("1");
		
		//받아온 게시글 첨부파일을 imgs객체로 저장하기
		List<BoardImage> imgs=new ArrayList<>();
		
		if(boardImg !=null) {
//			String path=req.getServletContext().getRealPath("/images/board/");
			String path="";
			File dir=new File(path);
			if(!dir.exists()) dir.mkdirs();
			
			for(MultipartFile f:boardImg) {
				if(!f.isEmpty()) {
					String oriName=f.getOriginalFilename();
					String ext=oriName.substring(oriName.lastIndexOf("."));
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
					int rndNum=(int)(Math.random()*10000);
					String reName=sdf.format(System.currentTimeMillis())+"_"+rndNum+ext+"_"+board.getBoardCate();
					
//					System.out.println(oriName+" -> "+reName);
					
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
		
		if(result!=0) {
			mv.addObject("board", service.selectBoard(String.valueOf(service.selectBoardNo(board))));
			mv.setViewName("board/board_view");
		}else {
			//에러 처리
			mv.setViewName("index");
		}
		return mv;
	}

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
	
}

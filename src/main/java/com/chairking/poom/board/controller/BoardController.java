package com.chairking.poom.board.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import ch.qos.logback.core.util.FileUtil;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService service;

	//게시글 등록 페이지로 이동
	@RequestMapping(path="/board/form", method=RequestMethod.GET)

	public String boardForm(){

		return "board/board_form";
	}

	// ckeditor로 첨부한 이미지 서버로 전송 처리 : 첫 번째 방법
//	@PostMapping("/images/ckeditor")
//	@SneakyThrows
//	//@RequestPart를 사용하면 Json 파일로 넘어온 데이터를 바인딩 가능
//	public String upload(HttpServletRequest req, HttpServletResponse res,
//						 @RequestPart MultipartFile upload) throws Exception{
//
//		System.out.println(upload);
//
//		// 파일 전송시 한글깨짐 방지
//		res.setCharacterEncoding("utf-8");
//		// 파일 받아올 때 한글깨짐 방지
//		res.setContentType("text/html; charset=utf-8");
//
//		// 파일의 originalname 변수에 저장
//		String sourceName = upload.getOriginalFilename();
//		// 파일 확장자 추출
//		String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();
//
//		File destFile;
//		// 랜덤 알파벳으로 rename시켜줄 파일명
//		String destFileName;
//
//		// 파일 업로드 경로
//		String uploadPath = "/images/ckeditor/";
//
//		do{
//			// '랜덤알파벳 8글자 + 확장자'로 rename된 파일 저장경로 설정
//			destFileName = RandomStringUtils.randomAlphabetic(8).concat(".").concat(sourceExt);
//			destFile = new File(uploadPath + destFileName);
//
//		}while (destFile.exists());
//
//		// 파일 생성시 부모폴더 생성
//		destFile.getParentFile().mkdirs();
//		// 파일저장
//		upload.transferTo(destFile);
//
//		return destFileName;
//	}


//	 ckeditor로 첨부한 이미지 서버로 전송 처리  : 두 번째 방법
//@PostMapping("/images/ckeditor")
//public void uploadImg(HttpServletRequest req, HttpServletResponse res,
//					  MultipartFile upload) throws Exception{
//
//	System.out.println(upload);
//
//
//	// 파일 전송시 한글깨짐 방지
//	res.setCharacterEncoding("utf-8");
//	// 파일 받아올 때 한글깨짐 방지
//	res.setContentType("text/html; charset=utf-8");
//
//	String fileName = upload.getOriginalFilename();
//	byte[] bytes = upload.getBytes();
//
//	// 이미지 업로드할 디렉토리 정해주기
//	String uploadPath = req.getContextPath()+"/images/ckeditor/";
//	System.out.println(uploadPath);
//	OutputStream out = new FileOutputStream(new File(uploadPath+fileName));
//
//	// 서버에 write
//	out.write(bytes);
//
//	String callback = req.getParameter("CKEditorFuncNum");
//
//	PrintWriter writer = res.getWriter();
//	if(!callback.equals("1")){
//		writer.println("<script>alert('이미지 업로드에 실패했습니다.');"+"</script>");
//		System.out.println("이미지 업로드 실패됨");
//	}else{
//		System.out.println("이미지 업로드 됨");
//	}
//	writer.flush();
//}


	// Ckeditor : 세 번째 방법
//	@RequestMapping(value="/images/ckeditor", method = RequestMethod.POST)
//	@ResponseBody
//	public  String fileUpload(HttpServletRequest req, HttpServletResponse res,
//							  MultipartHttpServletRequest multireq) throws Exception {
//
//		JsonObject json = new JsonObject();
//
//		PrintWriter writer = null;
//		OutputStream out = null;
//
//		MultipartFile file = multireq.getFile("upload");
//
//		if (file != null) {
//			if (file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
//					try {
//						String fileName = file.getName();
////						String subname  = fileName.substring(0, fileName.lastIndexOf("."));
////						String ext  = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
//
//						byte[] bytes = file.getBytes();
//						String uploadPath =  req.getServletContext()+"/src/main/resources/static/images/ckeditor/";
//						String renamedFile = UUID.randomUUID().toString() +"_"+fileName;
//
//						System.out.println(renamedFile);
//
//						File uploadFile = new File(uploadPath + renamedFile);
//						if (!uploadFile.exists()) {
//							uploadFile.mkdirs();
//						}
//
//						out = new FileOutputStream(new File(uploadPath+renamedFile));
//
//						out.write(bytes);
//
//						writer = res.getWriter();
//						res.setContentType("text/html; charset=utf-8");
//						String fileUrl = req.getContextPath() + "/img/" + renamedFile;
//
//						// json 데이터로 등록
//						json.addProperty("uploaded", 1);
//						json.addProperty("renamedFile", renamedFile);
//						json.addProperty("url", fileUrl);
//
//						System.out.println(json);
//
//
//					} catch (IOException e) {
//						e.printStackTrace();
//					} finally {
//						if (out != null) {
//							out.close();
//						}
//						if (writer != null) {
//							writer.close();
//						}
//
//				}
//			}
//
//		}
//		return null;
//	}

	// 다섯 번째 방법

	@RequestMapping(value="/images/ckeditor", method = RequestMethod.POST)
	public void imageUpload(HttpServletResponse res, HttpServletRequest req,
							MultipartHttpServletRequest multireq, @RequestParam MultipartFile upload) throws Exception{

		JsonObject json = new JsonObject();
		// 나중에 rename할 때 필요한 것들
		int rndNum=(int)(Math.random()*100000);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

		String filename = upload.getOriginalFilename();

		// 파일명이랑 확장자 분리
		String subname  = filename.substring(0, filename.lastIndexOf("."));
		String ext  = filename.substring(filename.lastIndexOf(".")).toLowerCase();
		System.out.println(subname+ " : subname, 얘는 확장자 :" + ext);



		OutputStream out = null;
		PrintWriter writer = null;

		// 입력된 파일 받을 때 한글깨짐 방지
		res.setCharacterEncoding("utf-8");
		// textarea상으로 이미지 받을 대 한글깨짐 방지
		res.setContentType("text/html;charset=utf-8");

		try{
			byte[] bytes = upload.getBytes();

			// 랜덤문자로 rename
			String renamedFile =sdf.format(System.currentTimeMillis())+"_"+rndNum+"_"+subname+ext;


			// 이미지 경로
			// req.getContextPath() : 한 웹 어플리케이션 프로젝트 명
			// req.getRealPath(); : 현 어플리케이션의 절대경로
			// 1. ckeditor로 업로드 되는 이미지 저장 폴더
			String filepath = req.getServletContext()+"/src/main/resources/static/images/ckeditor/";
			String saveFile = filepath + renamedFile;

					File uploadFile = new File(filepath);
			if(!uploadFile.exists()){
				uploadFile.mkdirs();
			}
			out = new FileOutputStream(new File(saveFile));
			out.write(bytes);
			// outputStream에 저장된 데이터를 전송 + 초기화
			out.flush();

			String callback = req.getParameter("CKEditorFuncNum");
			writer = res.getWriter();
			String fileUrl = "/images/ckeditor?File=" + renamedFile;

//			writer.println("renamedfile"+renamedFile + "uploaded"+fileUrl);
			writer.flush();

			// json 데이터로 등록
			json.addProperty("uploaded", 1);
			json.addProperty("renamedFile", renamedFile);
			json.addProperty("url", fileUrl);
			json.addProperty("error", "파일업로드에 실패했습니다.");

			System.out.println(json);


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



	// 이미지 정보 Editor화면에 이미지 출력


	
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

	
}

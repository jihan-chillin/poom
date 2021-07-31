package com.chairking.poom.board.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import ch.qos.logback.core.util.FileUtil;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
	@RequestMapping(value="/images/ckeditor", method = RequestMethod.POST)
	@ResponseBody
	public  String fileUpload(HttpServletRequest req, HttpServletResponse res,
							  MultipartHttpServletRequest multireq) throws Exception {

		JsonObject json = new JsonObject();

		PrintWriter writer = null;
		OutputStream out = null;

		MultipartFile file = multireq.getFile("upload");

		if (file != null) {
			if (file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
				if (file.getContentType().toLowerCase().startsWith("image/")) {
					try {
						String fileName = file.getName();
						byte[] bytes = file.getBytes();
						String uploadPath = req.getServletContext().getRealPath("/img");

						File uploadFile = new File(uploadPath);
						if (!uploadFile.exists()) {
							uploadFile.mkdirs();
						}
						fileName = UUID.randomUUID().toString();
						uploadPath = uploadPath + "/" + fileName;
						out = new FileOutputStream(new File(uploadPath));

						out.write(bytes);

						writer = res.getWriter();
						res.setContentType("text/html; charset=utf-8");
						String fileUrl = req.getContextPath() + "/img/" + fileName;

						// json 데이터로 등록
						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl);

						System.out.println(json);


					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (out != null) {
							out.close();
						}
						if (writer != null) {
							writer.close();
						}
					}
				}
			}

		}
		return null;
	}

	// 다섯 번째 방법
//	@RequestMapping(value="/images/ckeditor", method = RequestMethod.POST)
//	public void imageUpload(HttpServletRequest request,HttpServletResponse response, @RequestParam MultipartFile upload) {
//
//
//		OutputStream out = null;
//
//		PrintWriter printWriter = null;
//
//
//
//		response.setCharacterEncoding("utf-8");
//
//		response.setContentType("text/html;charset=utf-8");
//
//
//
//
//
//		try {
//
//
//
//			//CKEDITOR에서 업로드된 파일의 이름을 참조
//
//			String fileName = upload.getOriginalFilename();
//
//
//
//			//CKEDITOR에서 업로드된 파일을 byte 배열로 참조
//
//			byte[] bytes = upload.getBytes(); /*이미지 포함 모든 데이터는 바이트*/
//
//
//
//			//실제 업로드 될 톰캣서버의 물리적 경로
//
//			HttpSession session = request.getSession();
//
//			String root_path = session.getServletContext().getRealPath("/");
//
//
//
//			//실서버 톰캣 스왑시 주석변경
//
//
//
//			String uploadPath =root_path+"resources\\upload\\ckeditor\\" + fileName; //윈도우
//
//			//String uploadPath =root_path+"resources/upload/ckeditor/" + fileName; //리눅스
//
//
//
//
//
//			System.out.println(uploadPath);
//
//			//C:\\swk\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\chaumi\\resources\\upload\\
//
//			//출력스트객체 생성(파일생성)
//
//			out = new FileOutputStream(new File(uploadPath)); /* 빈폴더 생성*/
//
//			//업로드된 파일의 바이트배열을 출력스트림에 사용.
//
//			out.write(bytes); /*실제 파일에대한 정보를 담고있음*/
//
//			out.flush();
//
//
//
//			String callback = request.getParameter("CKEditorFuncNum");
//
//
//
//			printWriter = response.getWriter(); ///responese 서버측에서 클라이언트로 정보를 보내고자 할때 그역활을 담당하는 객체...
//
//			//CKEDITOR 에 업로드 된 서버측의 파일경로를 반환하는 목적
//
//
//
//			String fileUrl = "/upload/ckeditor/" + fileName;   ///resources/upload/ + fileName
//
//			System.out.println(fileUrl+"fileUrl");
//
//			printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
//
//					+ callback
//
//					+ ",'"
//
//					+ fileUrl
//
//					+"','이미지를 업로드 하였습니다.'"
//
//					+ ")</script>");
//
//
//
//			printWriter.flush();
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		} finally {
//
//			try {
//
//				if ( out != null) {
//
//					out.close();
//
//				}
//
//				if (printWriter != null) {
//
//					printWriter.close();
//
//				}
//
//			}catch(IOException e) {
//
//				e.printStackTrace();
//
//			}
//
//		}
//
//		return;
//
//
//
//	}
	
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

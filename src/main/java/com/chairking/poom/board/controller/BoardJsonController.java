package com.chairking.poom.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.board.model.vo.CkFileupload;
import com.chairking.poom.hashTag.controller.TagJsonController;
import com.chairking.poom.noti.controller.NotiController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BoardJsonController {

    @Autowired
    private NotiController nc;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private BoardService service;

    @Autowired
    private TagJsonController tagJsonController;

    // 프로젝트 내에 첨부파일 저장하고
    // 그 파일 서버로 전송
    @RequestMapping(value = "/images/ckeditor", method = RequestMethod.POST)
    public void imageUpload(HttpServletResponse res, HttpServletRequest req,
                            MultipartHttpServletRequest multireq, @RequestParam MultipartFile upload) throws Exception {


        res.setCharacterEncoding("utf-8"); // 입력된 파일 받을 때 한글깨짐 방지
        res.setContentType("text/html;charset=utf-8"); // textarea상으로 이미지 받을 대 한글깨짐 방지

        OutputStream out = null;
        PrintWriter writer = null;

        // 폴더 경로설정
//        Path path = Paths.get("");
//        String directoryName = path.toAbsolutePath().normalize().toString();
        String directoryName = System.getProperty("user.dir"); // C:\Users\JIHAN\IdeaProjects\poom
        String folderPath = directoryName + "\\src\\main\\resources\\static\\uploadCKImage\\";
        //서버 : WEB-INF/classes/template/


        // 폴더 생성 / 없으면 생성
        File uploadDir = new File(folderPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String oriName = upload.getOriginalFilename();
        String subname = oriName.substring(0, oriName.lastIndexOf("."));
        String ext = oriName.substring(oriName.lastIndexOf(".")).toLowerCase();

        System.out.println("실제 이름 : " + subname + " / 확장자명 : " + ext);

        // rename 하기
        int rndNum = (int) (Math.random() * 10000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String renamedFile = sdf.format(System.currentTimeMillis()) + "_" + rndNum + "_" + subname + ext;

        System.out.println(renamedFile);

        // renmaedFile 폴더에 업로드 하기
        try {
            upload.transferTo(new File(folderPath + renamedFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 서버로 파일 전송
        try {
            String fileUrl = req.getContextPath() + "/ckeditor/fileDownload?fileName=" + renamedFile;

            System.out.println("fileURL : " + fileUrl);

            writer = res.getWriter();
            writer.println("{\"filename\" : \"" + renamedFile + "\", \"uploaded\" : 1, \"url\":\"" + fileUrl + "\"}");
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

// 전송된 이미지 정보 & 편집기능 가져오기
@RequestMapping("/ckeditor/fileDownload")
public void ckSubmit(@RequestParam(value = "fileName") String fileName,
                     HttpServletRequest req, HttpServletResponse res) {

    String directoryName = System.getProperty("user.dir"); // C:\Users\JIHAN\IdeaProjects\poom
    String folderPath = directoryName + "\\src\\main\\resources\\static\\uploadCKImage\\";

    File file = CkFileupload.getDownloadFile(fileName, folderPath);

    try {
        byte[] data = FileUtils.readFileToByteArray(file);

//        res.setContentType(CkFileupload.getMediatype(fileName).toString());
        res.setContentLength(data.length);
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setHeader("Content-Disposition", "attachment; fileName=\""
                        + URLEncoder.encode(fileName, "UTF-8") +"\";");

        res.getOutputStream().write(data);
        res.getOutputStream().flush();
        res.getOutputStream().close();

    } catch (IOException ie) {
        ie.printStackTrace();
    }

    }

// ckeditor로 작성한 게시글 등록하기
@PostMapping("/board/insert")
public ModelAndView insertBoard(ModelAndView mv,@RequestParam Map param ){


   System.out.println("파라미터 일단 이렇게 들어옴 : " + param);


   // 썸네일 이미지 따로 분리 할것
    String boardContent = param.get("boardContent").toString(); // boardContent에 태그 포함 다 들어감.
    // 1. 사진 첨부시 첫 번째 이미지파일 썸네일 컬럼에 넣기
    // 2. 사진 첨부가 안되어 있을 경우,
    boolean flag = boardContent.contains("img");
    String imgName = "";

    if(flag == true){
        String firstTarget = "fileName=";
        String lastTarget = ".";
        imgName = boardContent.substring(boardContent.indexOf(firstTarget)+9,boardContent.indexOf(lastTarget)+4 );
    }else if (flag != true)
    {
        imgName = "preview_poom.jpg";
    }

    System.out.println("이미지 네임이 어떻게 나오는지 보자" + imgName);

        int result =  service.insertBoard(param, imgName);


        String msg="";
        String loc="redirect:/login.";

        if(result>0){
            msg="게시글 등록 성공";
        }else{
            msg = "동륵실패! 다시 시도해주세요.";
        }

        mv.addObject("msg", msg);
        mv.addObject("loc",loc);
        mv.setViewName("redirect:/");

        return mv;
    }

    @GetMapping("/board/dupleTagCheck")
    public List<Map<String, String>> dupleTagCheck(
                            @RequestParam(value = "tagText") String tagText){

        return service.dupleTagCheck(tagText);
    }

    //좋아요=> +1하기
    @RequestMapping("/board/changeLike")
    public void changeLike(@RequestParam Map<String,String> map) {
        //해당 no로 board테이블에 like count 추가하고
        //좋아요 테이블에 컬럼 추가하기
        int result=service.changeLike(map);

        // 알림테이블에 좋아요 데이터 넣기
        if(map.get("like").equals("안누름")){
            nc.insertLikesNotiData(map.get("no"),nc.getBoardWriter(map.get("no")));
        }
    }

    // 상세 글에서 해시태그 추가하는 controller
    @GetMapping("/board/addTag")
    public void addTagFromForm(@RequestParam(value = "tagText") String tagText){

        System.out.println("택텍스트 들어오는지"+tagText);

        // 방금전에 등록한 게시글 번호 가져오기
        int boardNo = Integer.parseInt(getBoardNoFromForm())+1;
        String strBoardNo = Integer.toString(boardNo);

//      boardTag 추가
        int result = service.boardTagFromform(strBoardNo, tagText);

        try{
            int result2 = service.TagFromform(tagText);
        }catch (Exception e){}

    }

    //댓글 작성하는 메소드
    @PostMapping("/comment/write")
    public Map commentWrite(String boardNo, String commentContent, HttpServletRequest req) {
    	String commentWriter=(String)((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_ID");

//    	System.out.println("boardNo : "+boardNo);
//    	System.out.println("commentContent : "+commentContent);
//    	System.out.println("commentWriter : "+commentWriter);

    	Map<String, String> param=new HashMap<String, String>();
    	param.put("boardNo", boardNo);
    	param.put("commentContent", commentContent);
    	param.put("commentWriter", commentWriter);

    	int result=service.commentWrite(param);
    	if(result>0) result=service.commentCountUpdate(1, boardNo);
    	// 댓글 작성시 알림테이블에 데이터 넣는 메소드
        nc.insertCommentNotiData(boardNo,nc.getBoardWriter(boardNo));
    	return param;
    }
    
    //댓글목록 ajax로 가져오는 메소드
    @GetMapping("/comment/list")
    public ModelAndView commentList(String boardNo, ModelAndView mv) {
    	mv.addObject("commentList", service.selectCommentList(boardNo));
    	mv.setViewName("board/board_comment_ajax");
    	return mv;
    }

    //댓글삭제
    @RequestMapping("/comment/delete")
    public String commentDelete(String boardNo, String commentNo) {
    	int result=service.commentDelete(boardNo, commentNo);
    	if(result>0) service.commentCountUpdate(-1, boardNo);
    	return "";
    }
    
    //댓글 수정
    @RequestMapping("/comment/modify")
    public String commentModify(@RequestParam Map<String, String> param) {
//    	System.out.println(param.get("boardNo"));
//    	System.out.println(param.get("commentNo"));
//    	System.out.println(param.get("commentContent"));
    	int result=service.commentModify(param);
    	return "";
    }

    // 방금전에 등록한 게시글 번호 가져오기
    public String getBoardNoFromForm(){
        return service.getBoardNoFromForm();
    }
    
    //게시글 삭제하기
    @RequestMapping("/board/boardDelete")
    public String boardDelete(String no) {
    	System.out.println(no);
    	int result = service.boardDelete(no);
    	return "";
    }
}

package com.chairking.poom.board.controller;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.board.model.vo.CkFileupload;
import com.chairking.poom.common.Pagination;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
public class BoardJsonController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private BoardService service;

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


        System.out.println(param); // DB에 태그로 들어가는데 어떡하지
        int result =  service.insertBoard(param);


        String msg="";
        String loc="redirect:/";

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

    //모든 게시글 리스트 가져오는 서비스
    @GetMapping("/board/all")
    public ModelAndView selectAllBoard(ModelAndView mv, HttpServletRequest req,
                                       @RequestParam(value="cPage", defaultValue = "1") int cPage,
                                       @RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){

        //멤버 지역 가져오기
        Object memberloc = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_LOC");
        // 페이징처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
        // 전체 게시글 개수
        int totalData = service.allBoardCount();
        // 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
        pagination.setTotalRecordCount(totalData);
        // 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
        List<Map<String, Object>> list = service.allBoard(pagination, memberloc);
        //------------------------------------------------------------------------------------------

        // 좋아요 가져오기
        String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
        // 공지사항 가져오기
        List<Map<String,Object>> notices=service.selectAllBoardNotice();

//        System.out.print("공지사항 : " + notices);
        System.out.println("전체글보드리스트"+list);
        mv.addObject("list", list);
        mv.addObject("likeTable", likeTable);
        mv.addObject("notices", notices);
        mv.setViewName("/board/board_alllist");
        return mv;
    }

    @GetMapping("board/cateList")
    public ModelAndView selectCateBoard(ModelAndView mv, HttpServletRequest req,
                                        @RequestParam(value = "cate") String cate,
                                        @RequestParam(value="cPage", defaultValue = "1") int cPage,
                                        @RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){

        //멤버 지역 가져오기
        Object memberloc = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_LOC");

       System.out.println("파라미터 카테고리 : " + cate + "/ cate의 형 " + cate.getClass().getName()) ;
        // 페이징처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
        // 전체 게시글 개수
        int totalData = service.allcateBoardCount(cate);
        // 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
        pagination.setTotalRecordCount(totalData);
        // 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
        List<Map<String, Object>> list = service.allCateBoard(pagination, cate, memberloc);

        // 카테고리 이름 가져오기
        Map<String, Object> cateName = service.selectCateName(cate);
        Object cName = cateName.get("CATEGORY_NAME");

        // 공지사항 가져와보기
        List<Map<String, Object>> notices = service.selectAllCateNotice(cate);

        mv.addObject("cName", cName);
        mv.addObject("list", list);
        mv.addObject("notices", notices);
        mv.setViewName("/board/board_cate_list");
        return mv;
    }
}

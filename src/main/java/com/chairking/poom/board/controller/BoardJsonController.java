package com.chairking.poom.board.controller;

import com.chairking.poom.board.model.vo.CkFileupload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BoardJsonController {

    @Autowired
    private ResourceLoader resourceLoader;
//    @RequestMapping(value="/images/ckeditor", method = RequestMethod.POST)
//    public void imageUpload(HttpServletResponse res, HttpServletRequest req,
//                            MultipartHttpServletRequest multireq, @RequestParam MultipartFile upload) throws Exception{
//
////        Map map = new HashMap();
//
//        res.setCharacterEncoding("utf-8"); // 입력된 파일 받을 때 한글깨짐 방지
//        res.setContentType("text/html;charset=utf-8"); // textarea상으로 이미지 받을 대 한글깨짐 방지
//
//        OutputStream out = null;
//        PrintWriter writer = null;
//
//        // rename할 때 필요한 것들
//        int rndNum=(int)(Math.random()*100000);
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//        String orifilename = upload.getOriginalFilename(); // filename
//        System.out.println("파일이름 : " + orifilename);
//        String subname  = orifilename.substring(0, orifilename.lastIndexOf("."));
//        String ext  = orifilename.substring(orifilename.lastIndexOf(".")).toLowerCase();
//
//
//        // 폴더 경로 구해보자
//        Path path = Paths.get("");
//        String directoryName = path.toAbsolutePath().normalize().toString(); // C:\Users\JIHAN\IdeaProjects\poom
////        String folderPath = directoryName + "\\src\\main\\resources\\static\\images\\ckImage\\";
////        String path = BoardJsonController.class.
//        String folderPath = directoryName + "\\src\\main\\resources\\static\\images\\ckImage\\";
//        System.out.println("프로젝트 내 로컬 폴더 주소 :" + folderPath);
//
//        String mypath = this.getClass().getResource("resources/static/images/ckImage").getPath();
//        System.out.println("현재경로는 >?" + mypath);
//
//
//        try{
//            // 업로드 파일 rename 하기
//            String fileName =sdf.format(System.currentTimeMillis())+"_"+rndNum+"_"+subname+ext;
//            System.out.println("renamedFile은 얘 : " + fileName);
//
//            byte[] bytes = upload.getBytes();
//
//            // 로컬폴더 만들기
//            File uploadDir = new File(folderPath);
//            if(!uploadDir.exists()){
//                uploadDir.mkdirs();
//                System.out.println ("기존에 폴더가 없어서 새로운 폴더 생성");
//            }
//
//            File uploadFile = new File(folderPath,fileName);
//            // 해당 파일이 없을 경우 filepath
//            if(!uploadFile.exists()){
//                uploadFile.mkdirs();
//            }
//
//            // 이미지 보내야지
//            String fileUrl = req.getContextPath() +"/images/ckeditor?fileName="+fileName;
////            String fileUrl = folderPath + fileName;
//            System.out.println("서버 주소로 보내는 거 : " + fileUrl+fileName);
//
////			String callback = req.getParameter("CKEditorFuncNum");
//
//            // json으로 보내기
//            String callback = req.getParameter("CKEditorFuncNum");
//            writer = res.getWriter();
////
////            map.put("fileName", fileName);
////            map.put("uploaded", 1);
////            map.put("url", fileUrl);
//            writer.println("{\"fileName\" : \"" + fileName + "\", \"uploaded\" : 1, \"url\":\"" + fileUrl + "\"}");
////            writer.println(map);
//            writer.flush();
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if(out!=null){
//                out.close();
//            }
//            if(writer !=null){
//                writer.close();
//            }
//        }
//
//    }

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
        int rndNum = (int) (Math.random() * 100000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
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

}


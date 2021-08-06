package com.chairking.poom.noti.controller;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.noti.model.service.NotiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class NotiController {
    @Autowired
    private NotiService notiService;

    // 알림테이블에 데이터 넣기
    public void insertLikesNotiData(String number,String loginId){
        notiService.insertLikesNotiData(number,loginId);
    }
    public int insertCommentNotiData(String number,String loginId){
        return notiService.insertCommentNotiData(number,loginId);
    }
    public int insertMessageNotiData(String number,String loginId){
        return notiService.insertMessageNotiData(number,loginId);
    }

    public String getBoardWriter(String no){
        return notiService.getBoardWriter(no);
    }

    @GetMapping("/noti/my/data")
    public Map<String,Object> myNotiData(@RequestParam(value = "loginid")String loginId){
//        String loginId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
        //결과 데이터
        Map<String,Object> data = new HashMap<>();
        // 알림 정보
        List<Map<String,String>> myNotiData = notiService.getMyNotiData(loginId);
        // 게시물 제목 from boardNo
        List<String>  getBoardTitleFromBoardNo= new ArrayList<>();
        // 게시물 제목 from commentNo
        List<String>  getBoardTitleFromCommentNo=new ArrayList<>();
        // 쪽지 내용 from MsgNo
        List<String>  getMsgContentFromMsgNo= new ArrayList<>();


        for(int i =0; i<myNotiData.size(); i++){

            // 삭제처리된 게시물은 알림테이블에서 삭제
            notiService.deleteNotiBoardDelStatus(myNotiData.get(i).get("BOARD_NO"));

            if(myNotiData.get(i).get("BOARD_NO") != null){
                getBoardTitleFromBoardNo.add(i,
                        notiService.getBoardTitleFromBoardNo(myNotiData.get(i).get("BOARD_NO"))
                );

            }else if(myNotiData.get(i).get("COMMENT_NO") != null){
                getBoardTitleFromCommentNo.add(i,
                    notiService.getBoardTitleFromCommentNo(myNotiData.get(i).get("COMMENT_NO"))
                );

            }else if(myNotiData.get(i).get("MSG_NO") != null){
                getMsgContentFromMsgNo.add(i,
                        notiService.getMsgContentFromMsgNo(myNotiData.get(i).get("MSG_NO"))
                );

            }
        }

        data.put("notiData",myNotiData);
        data.put("boardTitleFromBoardNo",getBoardTitleFromBoardNo);
        data.put("getBoardTitleFromCommentNo",getBoardTitleFromCommentNo);
        data.put("getMsgContentFromMsgNo",getMsgContentFromMsgNo);

        return data;
    }

    @MessageMapping("/notification/alarm")
    @SendTo("/receive/noti")
    public  String sendData(){
//        log.info(loginId);
        return "1";
    }
}

package com.chairking.poom.noti.controller;

import com.chairking.poom.noti.model.service.NotiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Map<String,Object> myNotiData(HttpServletRequest req){
        String loginId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
        //결과 데이터
        Map<String,Object> data = new HashMap<>();
        // 알림 정보
        List<Map<String,String>> myNotiData = notiService.getMyNotiData(loginId);
        // 게시물 제목 from boardNo
        List<Map<String,String>> getBoardTitleFromBoardNo= new ArrayList<>();
        // 게시물 제목 from commentNo
        List<Map<String,String>> getBoardTitleFromCommentNo=new ArrayList<>();
        // 쪽지 내용 from MsgNo
        List<Map<String,String>> getMsgContentFromMsgNo= new ArrayList<>();
        Map<String,String> emptyData = new HashMap<>();
        emptyData.put("BOARD_TITLE","");
        emptyData.put("BOARD_NO","");

        Map<String,String> emptyMessage = new HashMap<>();
        emptyData.put("MSG_CONTENT","");
        emptyData.put("MSG_NO","");

        for(int i =0; i<myNotiData.size(); i++) {
            // 삭제처리된 게시물은 알림테이블에서 삭제
            notiService.deleteNotiBoardDelStatus(myNotiData.get(i).get("BOARD_NO"));
            String comtNo = myNotiData.get(i).get("COMMENT_NO");
            String msgNo = myNotiData.get(i).get("MSG_NO");
            String boardNo = myNotiData.get(i).get("BOARD_NO");

            if (Integer.parseInt(comtNo) != 0) {
                getBoardTitleFromCommentNo.add(i,notiService.getBoardTitleFromCommentNo(comtNo));
            }else{
                getBoardTitleFromCommentNo.add(i,emptyData);
            }

            if (Integer.parseInt(msgNo) != 0) {
                getMsgContentFromMsgNo.add(i,notiService.getMsgContentFromMsgNo(msgNo));
            }else{
                getMsgContentFromMsgNo.add(i,emptyMessage);
            }

            if (Integer.parseInt(boardNo) != 0) {
                getBoardTitleFromBoardNo.add(i,notiService.getBoardTitleFromBoardNo(boardNo));
            }else{
                getBoardTitleFromBoardNo.add(i,emptyData);
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
    public String sendData(){
        return "1";
    }


    /*
        글번호, 쪽지번호, 댓글 번호를 받아서
        해당하는 알람을 지우고 알림 목록을 다시 받아오는 함수
        no => 번호
        ref
        =>
        1 : 댓글
        2 : 메세지
        3 : 좋아요

     */
    @RequestMapping("/noti/delete")
    public void deleteNotify(@RequestParam(value="no")String no,
                             @RequestParam(value = "ref")int ref){

        if(ref == 1){
            notiService.deleteNotifyComment(no);
        }else if(ref==2){
            notiService.deleteNotifyMessage(no);
        }else if(ref ==3){
            notiService.deleteNotifyLikes(no);
        }
    }
    /*
    알림 읽었을 때 알림 readType 변경
     */
    @RequestMapping("/noti/read/type")
    public void updateNotifyRead(@RequestParam(value="no")String no,
                             @RequestParam(value = "ref")int ref){

        if(ref == 1){
            notiService.updateNotifyComment(no);
        }else if(ref==2){
            notiService. updateNotifyMessage(no);
        }else if(ref ==3){
            notiService. updateNotifyLikes(no);
        }
    }

}

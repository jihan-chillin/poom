package com.chairking.poom.noti.controller;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.noti.model.service.NotiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    private BoardService boardService;

    // 좋아요 눌린 게시글 제목 리턴 하는 메소드.
    public List getPushedLikeBoardInfoData(HttpServletRequest req){
        String loginId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");

        // 내가 쓴 게시글 번호
        List<String> getEnrolledMyBoardNo = getEnrolledMyBoardNo(req);
        // 내 어떤 게시글에 좋아요 눌렸나
        List<String> getEnrolledLikedNo = new ArrayList();
        // 좋아요 눌린 게시글의 제목
        List<String> getBoardTitle = new ArrayList();

        for(int i =0; i<getEnrolledMyBoardNo.size(); i++){
            getEnrolledLikedNo.add(i,getEnrolledLikedNo(getEnrolledMyBoardNo.get(i)));
        }

        for(int i=0; i<getEnrolledLikedNo.size(); i++){
            getBoardTitle.add(i,getBoardTitle(getEnrolledLikedNo.get(i)));
            // 좋아요 눌린 게시글을 알림 테이블에 등록
            insertLikesNotiData(getEnrolledLikedNo.get(i),loginId);
        }

        return getBoardTitle;
    }
    // 게시물 제목 가져오는 메소드
    public String getBoardTitle(String boardNo){
        return (String) boardService.selectBoard(boardNo).get("BOARD_TITLE");
    }
    // 좋아요 눌린 게시글 번호 가져오는 메소드
    public String getEnrolledLikedNo(String boardNo){
       return notiService.getEnrolledLikedNo(boardNo);
    }

    // 내가 쓴 게시글 번호
    public List getEnrolledMyBoardNo(HttpServletRequest req){
        String memberId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
        List boardNo = notiService.getEnrolledMyBoardNo(memberId);

        return boardNo;
    }

    // 내가 쓴 댓글 번호 가져오는 메소드
//    public List getEnrolledMyCommnetNo(){
//        return
//    }

    // 알림테이블에 데이터 넣기
    public int insertLikesNotiData(String number,String loginId){

        return notiService.insertLikesNotiData(number,loginId);
    }
    public int insertCommentNotiData(String number,String loginId){

        return notiService.insertCommentNotiData(number,loginId);
    }
    public int insertMessageNotiData(String number,String loginId){

        return notiService.insertMessageNotiData(number,loginId);
    }

    @GetMapping("/noti/my/data")
    public Map<String,Object> myNotiData(HttpServletRequest req){
        String loginId = (String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID");
        //결과 데이터
        Map<String,Object> data = new HashMap<>();
        // 알림 정보
        List<Map<String,String>> myNotiData = notiService.getMyNotiData(loginId);
        // 게시물 제목 from boardNo
        List<String>  boardTitleFromBoardNo= new ArrayList<>();
        // 게시물 제목 from commentNo
        List<String>  getBoardTitleFromCommentNo=new ArrayList<>();
        // 쪽지 내용 from MsgNo
        List<String>  getMsgContentFromMsgNo= new ArrayList<>();


        for(int i =0; i<myNotiData.size(); i++){
            if(myNotiData.get(i).get("BOARD_NO") != null){
                boardTitleFromBoardNo.add(i,
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
        data.put("boardTitleFromBoardNo",boardTitleFromBoardNo);
        data.put("getBoardTitleFromCommentNo",getBoardTitleFromCommentNo);
        data.put("getMsgContentFromMsgNo",getMsgContentFromMsgNo);

        return data;
    }
}

package com.chairking.poom.noti.controller;

import com.chairking.poom.board.model.service.BoardService;
import com.chairking.poom.noti.model.service.NotiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NotiController {
    @Autowired
    private NotiService notiService;
    @Autowired
    private BoardService boardService;

    // 좋아요 눌린 게시글 제목 리턴 하는 메소드.
    public List getPushedLikeBoardInfoData(HttpServletRequest req){
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
            insertLikesNotiData(getEnrolledLikedNo.get(i));
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
    public int insertLikesNotiData(String number){
        return notiService.insertLikesNotiData(number);
    }
    public int insertCommentNotiData(String number){
        return notiService.insertCommentNotiData(number);
    }
    public int insertMessageNotiData(String number){
        return notiService.insertMessageNotiData(number);
    }


}

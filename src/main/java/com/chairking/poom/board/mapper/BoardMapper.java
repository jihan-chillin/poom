package com.chairking.poom.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Mapper
public interface BoardMapper {

	//게시글 등록 쿼리
	//게시글 등록시 번호 가져오기
	@Insert("INSERT INTO BOARD VALUES(SEQ_BOARDNO.NEXTVAL, #{boardTitle}, "
			+ "#{boardContent}, DEFAULT, DEFAULT, DEFAULT, #{boardLoc}, DEFAULT, #{boardCate}, #{memberId})")
	public int insertBoard(Board b);
	
	@Select("SELECT BOARD_NO AS boardNo FROM BOARD WHERE BOARD_TITLE=#{boardTitle} AND MEMBER_ID=#{memberId}")	
	public int selectBoardNo(Board b);
	
	//게시글 사진 등록
	@Insert("INSERT INTO IMAGE VALUES(SEQ_IMAGENO.NEXTVAL, #{boardNo}, #{originImg}, #{renameImg})")
	public int insertBoardImg(BoardImage bi);
	
	//모든 게시글 조회
	@Select("SELECT * FROM BOARD")
	public List<Map> selectAllBoard();
	
	//게시글 조회
	@Select("SELECT * FROM BOARD WHERE BOARD_NO=#{boardNo}")
	public Map selectBoard(String boardNo);
}

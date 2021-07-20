package com.chairking.poom.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.chairking.poom.board.model.vo.Board;

@Mapper
public interface BoardMapper {
	
	//게시글 등록 쿼리
	@Insert("INSERT INTO BOARD VALUES(SEQ_BOARDNO.NEXTVAL, #{boardTitle}, "
			+ "#{boardContent}, DEFAULT, DEFAULT, DEFAULT, #{boardLoc}, DEFAULT, #{boardCate}, #{memberId})")
	public int insertBoard(Board b);
	
	//모든 게시글 조회
	@Select("SELECT * FROM BOARD")
	public List<Board> selectAllBoard();
	
}

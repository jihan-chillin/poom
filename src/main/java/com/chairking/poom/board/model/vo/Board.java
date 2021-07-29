package com.chairking.poom.board.model.vo;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	private String boardNo;
	private String boardTitle;
	private String boardContent;
	private int delStatus;
	private int boardCount;
	private Date boardDate;
	private String boardLoc;
	private int likeCount;
	private String boardCate;
	private String memberId;
	private List<BoardImage> images;
	private String[] tags;
	
}

package com.chairking.poom.board.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardImage {
	private String imgNumber;
	private int boardNo;
	private String originImg;
	private String renameImg;
}

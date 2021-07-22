package com.chairking.poom.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardImage {
	private String imgNumber;
	private int boardNo;
	private String originImg;
	private String renameImg;
}

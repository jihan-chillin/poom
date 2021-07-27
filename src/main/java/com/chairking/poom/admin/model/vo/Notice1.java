package com.chairking.poom.admin.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice1 {

	private String cate;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeDate;
	private int noticeStatus;
	
}

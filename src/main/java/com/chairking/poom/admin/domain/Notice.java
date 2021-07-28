package com.chairking.poom.admin.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_no")
	//@SequenceGenerator(name="notice_no", initialValue = 1, sequenceName = "seq_notice_no")
	private String noticeNo;
	private String categoryNo;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeDate;
	private int noticeStatus;
	
}

package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.admin.domain.Notice;
import com.chairking.poom.admin.domain.NoticeRepository;
import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.dao.AdminDao;
import com.chairking.poom.admin.model.vo.Notice1;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private NoticeRepository notirep;
	
	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private AdminDao dao;
	
	//전체 공지사항 수 세기
	@Override
	public int countAllNotice() {
		return dao.countAllNotice(mapper);
	}

	//전체 공지사항 리스트 가져오기
	@Override
	public List<Map<String,Object>> allNotice(int cPage, int numPerpage) {
		return dao.allNotice(mapper, cPage, numPerpage);
	}
	
	//공지사항 등록
	@Override
	public int insertNotice(Notice1 n) {
		return dao.insertNotice(n,mapper);
	}
//	public int insertNotice(Notice n) {
//		Notice notice=notirep.save(n);
//		return notice!=null?1:0;
//	}
	
	
	
	//공지사항 view
	@Override
	public Map<String, Object> selectNotice(String no) {
		return dao.selectNotice(mapper, no);
	}

	//공지사항 삭제 => status:1로 바꾸기
	@Override
	public int noticeDelete(String no) {
		return dao.noticeDelete(mapper,no);
	}

	//공지사항 수정 => 기존꺼 삭제하고 재등록하는 것
	@Override
	public int realDelete(String no) {
		return dao.realDelete(mapper, no);
	}

	//공지사항 재게시 => status:1->0으로 바꾸기
	@Override
	public int changeStatus(String no) {
		return dao.changeStatus(mapper, no);
	}
}

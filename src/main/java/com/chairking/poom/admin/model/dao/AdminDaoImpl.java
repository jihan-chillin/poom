package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.vo.Notice;
@Repository
public class AdminDaoImpl implements AdminDao {

	@Override
	public List<Map<String,Object>> allNotice(AdminMapper mapper, int cPage, int numPerpage) {
		return mapper.allNotice(cPage,numPerpage);
	}
	
	@Override
	public int countAllNotice(AdminMapper mapper) {
		return mapper.countAllNotice();
	}

	@Override
	public int insertNotice(Notice n,AdminMapper mapper) {
		return mapper.insertNotice(n);
	}

	@Override
	public Map<String, Object> selectNotice(AdminMapper mapper, String no) {
		return mapper.selectNotice(no);
	}

	@Override
	public int noticeDelete(AdminMapper mapper, String no) {
		return mapper.noticeDelete(no);
	}

	@Override
	public int realDelete(AdminMapper mapper, String no) {
		return mapper.realDelete(no);
	}

	@Override
	public int changeStatus(AdminMapper mapper, String no) {
		return mapper.changeStatus(no);
	}

	@Override
	public List<Map<String, Object>> allBoardBlame(AdminMapper mapper, int cPage, int numPerpage) {
		return mapper.allBoardBlame(cPage,numPerpage);
	}

	@Override
	public int insertBlame(AdminMapper mapper, Map<String, String> map) {
		return mapper.insertBlame(map);
	}

	

	
}

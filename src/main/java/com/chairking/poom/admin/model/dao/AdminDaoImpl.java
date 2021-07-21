package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.vo.Notice;
@Repository
public class AdminDaoImpl implements AdminDao {

//	@Override
//	public List<Notice> allNotice(AdminMapper mapper) {
//		return mapper.allNotice();
//	}

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


}

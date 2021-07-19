package com.chairking.poom.chatroom.model.dao;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface ChattingDao {
    Map<String, List> getMyChatList(ChattingMapper cm);
    List<Map> enteredMem(ChattingMapper cm,String chatNo);
    List<Map> messageContent(ChattingMapper cm,String chatNo);

}

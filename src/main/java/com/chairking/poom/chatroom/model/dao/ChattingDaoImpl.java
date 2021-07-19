package com.chairking.poom.chatroom.model.dao;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class ChattingDaoImpl implements ChattingDao{

    @Override
    public Map<String, List> getMyChatList(ChattingMapper cm) {
        return cm.getMyChatList();
    }

    @Override
    public List<Map> enteredMem(ChattingMapper cm, String chatNo) {
        return cm.enteredMem(chatNo);
    }

    @Override
    public List<Map> messageContent(ChattingMapper cm, String chatNo) {
        return cm.messageContent(chatNo);
    }
}

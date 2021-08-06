package com.chairking.poom.message.model.service;

import com.chairking.poom.common.Pagination;
import com.chairking.poom.message.mapper.MessageMapper;
import com.chairking.poom.message.model.dao.MessageDao;
import com.chairking.poom.message.model.vo.Message;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper mapper;

    @Autowired
    private MessageDao dao;

    @Autowired
    private SqlSessionTemplate session;

    @Override
    public List<Map<String,Object>> searchReceiver(){

        return dao.searchReceiver(mapper);
    };

    //메세지 리스트 불러오기용
    @Override
    public List<Map<String,Object>> getMessage(String condition, Pagination pagination){
        return dao.getMessage(mapper, condition, pagination);
    }

    @Override
    public List<Map<String,Object>> messageContent(String msgNo){
        return dao.messageContent(mapper, msgNo);
    }

    @Override
    public int deleteMessage(String msgNo) {
        return dao.deleteMessage(mapper, msgNo);
    }

    @Override
    public int moveBlock(String msgNo) {
        return dao.moveBlock(mapper,msgNo);
    }

    @Override
    public int setMsgRead(String msgNo) {
        return dao.setMsgRead(mapper, msgNo);
    }

    @Override
    public int cancelMsg(String msgNo) {
        return dao.cancelMsg(mapper, msgNo);
    }

    @Override
    public List<Map<String, Object>> searchReceiverCondition(String condition) {
        return dao.searchReceiverCondition(mapper, condition);
    }

    @Override
    public int sendMsg(Message msv) {
        return dao.sendMsg(mapper,msv);
    }


    @Override
    public int emptyBlock() {
        return dao.emptyBlock(mapper);
    }

    @Override
    public int selectBlock(String msgNo) {
        return dao.selectBlock(mapper,msgNo);
    }

    @Override
    public int messageCount(String condition) {
        return dao.messageCount(mapper,condition);
    }

    @Override
    public String getRecentMessageNo() {
        return dao.getRecentMessageNo(mapper);
    }


}

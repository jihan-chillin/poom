package com.chairking.poom.message.model.dao;

import com.chairking.poom.message.mapper.MessageMapper;
import com.chairking.poom.message.model.vo.Message;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MessageDaoImpl implements MessageDao{

    @Override
    public List<Map<String, Object>> searchReceiver(MessageMapper mapper){

        return mapper.searchReceiver();
    }

    @Override
    public List<Map<String, Object>> getMessage(MessageMapper mapper,String condition){

        return mapper.getMessage(condition);
    }

    @Override
    public List<Map<String, Object>> messageContent(MessageMapper mapper, String msgNo) {
        return mapper.messageContent(msgNo);
    }

    @Override
    public int deleteMessage(MessageMapper mapper, String msgNo) {

        return mapper.deleteMessage(msgNo);
    }

    @Override
    public int moveBlock(MessageMapper mapper, String msgNo) {
        return mapper.moveBlock(msgNo);
    }

    @Override
    public int setMsgRead(MessageMapper mapper, String msgNo) {
        return mapper.setMsgRead(msgNo);
    }

    @Override
    public int cancelMsg(MessageMapper mapper, String msgNo) {
        return mapper.cancelMsg(msgNo);
    }

    @Override
    public List<Map<String, Object>> searchReceiverCondition(MessageMapper mapper, String condition) {
        return mapper.searchReceiverCondition(condition);
    }

    @Override
    public int sendMsg(MessageMapper mapper, Message msv) {
        return mapper.sendMsg(msv);
    }


    @Override
    public int emptyBlock(MessageMapper mapper) {
        return mapper.emptyBlock();
    }

    @Override
    public int selectBlock(MessageMapper mapper, String msgNo) {
        return mapper.selectBlock(msgNo);
    }

}

package com.chairking.poom.message.model.service;

import com.chairking.poom.common.Pagination;
import com.chairking.poom.message.model.vo.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {

    List<Map<String,Object>> searchReceiver();

    List<Map<String,Object>> getMessage(String condition, Pagination pagination);

    List<Map<String,Object>> messageContent(String msgNo);

    int deleteMessage(String msgNo);

    int moveBlock(String msgNo);

    int setMsgRead(String msgNo);

    int cancelMsg(String msgNo);

    List<Map<String, Object>> searchReceiverCondition(String condition);

    int sendMsg(Message msgNo);

    int emptyBlock();

    int selectBlock(String msgNo);

    int messageCount(String condition);

    String getRecentMessageNo();
}

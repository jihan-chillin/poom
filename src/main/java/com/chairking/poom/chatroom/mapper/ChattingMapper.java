package com.chairking.poom.chatroom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChattingMapper {
    @Select("select * from chat where member_id='test'")
    public Map<String,List> getMyChatList();

}

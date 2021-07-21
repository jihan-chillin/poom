package com.chairking.poom.message.mapper;

import com.chairking.poom.member.model.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    @Select("SELECT * FROM MEMBER")
    public List<Map<String,Object>> searchReceiver();


}

package com.chairking.poom.hashTag.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper {
    @Insert("insert into tag values(#{tag})")
    public int insertTag(String tag);
    @Select("select TAG_NAME from MEMBERTAG where MEMBER_ID=#{loginId}")
    public List<Map> getMyTagData(String loginId);
}

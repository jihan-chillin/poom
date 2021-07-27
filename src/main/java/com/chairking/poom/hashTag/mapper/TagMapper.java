package com.chairking.poom.hashTag.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper {
    @Insert("insert into tag values(#{tag})")
    public int insertTag(String tag);
    @Select("select * from MEMBERTAG where MEMBER_ID=#{loginId}")
    public List<Map> getMyTagData(String loginId);
    @Delete("delete from membertag where tag_name=#{tagName}")
    public int deleteMyTag(String tagName);
    @Select("select tag_name from tag where tag_name like '%${keyword}%'")
    public List<Map<String,String>> searchTag(String keyword);
}

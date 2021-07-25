package com.chairking.poom.hashTag.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TagMapper {
    @Insert("insert into tag values(#{keyword})")
    public int insertTag(Map tag);
}

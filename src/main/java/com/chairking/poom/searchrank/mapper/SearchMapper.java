package com.chairking.poom.searchrank.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    @Select("select count(l.BOARD_NO) pushlikes,l.BOARD_NO,b.BOARD_TITLE from LIKES l join BOARD B on l.BOARD_NO = B.BOARD_NO where (rownum between 1 and 5) and (l.LIKES_TIME between sysdate-1 and sysdate) and (b.DEL_STATUS='0') group by l.BOARD_NO,b.BOARD_TITLE order by pushlikes desc")
    public List<Map> getSearchRank();
}

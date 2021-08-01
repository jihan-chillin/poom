package com.chairking.poom.hashTag.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper {
    // 파라미터가 map 일때
    @Insert("insert into tag values(#{tag})")
    public int insertTag(String tag);
    @Select("select * from MEMBERTAG where MEMBER_ID=#{loginId}")
    public List<Map> getMyTagData(String loginId);
    // 파라미터가 문자열 일때
    @Insert("insert into tag values(#{keyword})")
    public int addTag(String keyword);
    @Insert("INSERT INTO MEMBERTAG VALUES (SEQ_MTAGNO.NEXTVAL,#{loginId},#{keyword})")
    public int insertMemberTag(String loginId,String keyword);
    @Delete("delete from membertag where tag_name=#{tagName}")
    public int deleteTag(String tagName);
    @Select("select tag_name from tag where tag_name like '%${keyword}%'")
    public List<Map<String,String>> searchTag(String keyword);
    @Select("select board_no from board order by board_no desc")
    public String getBoardNo();
    @Insert("insert into BOARDTAG values (seq_boardtagno.nextval,#{boardNo},#{keyword})")
    public int insertBoardTag(String boardNo,String keyword);
}

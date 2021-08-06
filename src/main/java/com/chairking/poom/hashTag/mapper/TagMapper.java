package com.chairking.poom.hashTag.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
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
    @Select("select * from ( select board_no from board order by board_date desc) where rownum=1")
    public String getBoardNo();
    @Insert("insert into BOARDTAG values (seq_boardtagno.nextval,#{boardNo},#{keyword})")
    public int insertBoardTag(String boardNo,String keyword);
    @Select("select a.* from (select b2.BOARD_NO, b2.BOARD_TITLE, b2.BOARD_CONTENT, b2.BOARD_DATE, b2.BLAME_COUNT from BOARDTAG b join TAG t on b.TAG_NAME = t.TAG_NAME join BOARD B2 on b.BOARD_NO = B2.BOARD_NO where t.TAG_NAME = #{tagName} )a where ROWNUM between #{cPage} and #{numPerPage}")
    public List<Map<String, Object>> getBoardNoFromTag(String tagName,int cPage,int numPerPage);

}

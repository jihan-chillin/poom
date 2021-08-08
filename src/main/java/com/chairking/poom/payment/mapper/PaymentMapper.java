package com.chairking.poom.payment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PaymentMapper {
	
	@Select("SELECT * FROM ITEMS")
	public List<Map<String, String>> itemList();
	
	@Insert("INSERT INTO PAYMENT VALUES(SEQ_PAYNO.NEXTVAL, to_date(SYSDATE-1,'yy-MM-dd'), #{itemNo}, #{memberId})")
	public int buyItem(String memberId, String itemNo);
	
	@Update("UPDATE MEMBER SET PAYLEVEL=#{itemNo} WHERE MEMBER_ID=#{memerId}")
	public int changePayLevel(String memberId, String itemNo);
	
}

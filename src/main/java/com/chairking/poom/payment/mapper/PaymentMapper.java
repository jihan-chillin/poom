package com.chairking.poom.payment.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
	
	@Insert("INSERT INTO PAYMENT VALUES(SEQ_PAYNO, SYSDATE, #{itemNo}, #{memberId})")
	public int buyItem(String memberId, String itemNo);
	
}

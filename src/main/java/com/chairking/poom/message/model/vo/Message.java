package com.chairking.poom.message.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 생성
@Data //@getter @setter @requiredArgsConstructor @ToString @EqualsAndHashCode를 한꺼번에 설정해주는 어노테이션
@Builder  //자동으로 해당 클래스에 빌더를 추가.
public class Message {

    private Number type;
    private String msgNo;
    private String memberId;
    private String recvMember;
    private Date msgDate;
    private String msgContent;
    private Date readCheck;




}

//결제
function pay()	{
	var IMP = window.IMP; // 생략가능
	IMP.init('iamport'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
	// 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
	// i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드
	IMP.request_pay({
	    pg : 'kakao', // version 1.1.0부터 지원.
	    /*
			'kakao':카카오페이,
			html5_inicis':이니시스(웹표준결제)
			'nice':나이스페이
			'jtnet':제이티넷
			'uplus':LG유플러스
			'danal':다날
			'payco':페이코
			'syrup':시럽페이
			'paypal':페이팔
		*/
	    pay_method : 'card',
	    /*
			'samsung':삼성페이,
			'card':신용카드,
			'trans':실시간계좌이체,
			'vbank':가상계좌,
			'phone':휴대폰소액결제
		*/
	    merchant_uid : 'merchant_' + new Date().getTime(),
	    name : '7일 이용권',
	    /* 결제창에서 보여질 이름 */
	    amount : 1,
	    /* 가격 */
	    buyer_email : 'test@test.com',
	    buyer_name : '테스트',
	    item : 'item',
	    m_redirect_url : 'https://www.yourdomain.com/payments/complete'
	    /*
	    	모바일 결제시,
	    	결제가 끝나고 랜딩되는 URL을 지정
	    	(카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐)
	    */
	}, function(rsp) {
	    if ( rsp.success ) {
	        var msg = '결제가 완료되었습니다.';
	        msg += '고유ID : ' + 'test'; //유저아이디
	        msg += '상점 거래ID : ' + rsp.merchant_uid;
	        msg += '결제 금액 : ' + 1;
	        msg += '카드 승인번호 : ' + rsp.apply_num;
	        
	        /* db에 결제내역 전송 */
	        $.ajax({
	        	url:"/pay/payment",
	        	data:{
	        		memberId:"test",
	        		itemNo:"1"
	        	},
	        	success:data=>{
	        		$("#content").html(data);
	        	}
	        });
	        
	    } else {
	        var msg = '결제에 실패하였습니다.';
	        msg += '에러내용 : ' + rsp.error_msg;
	    }
	    alert(msg);
	});
}

$("div#pay_container li").click((e)=>{
	console.log($(e.target).find("input").attr("select"));
});

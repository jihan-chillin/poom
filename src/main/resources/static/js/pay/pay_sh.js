//결제
function pay()	{
	console.log("${loginMember}");
	var IMP = window.IMP; // 생략가능
	IMP.init('imp65464808'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
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
	    amount : 10,
	    /* 가격 */
	    buyer_email : 'test@test.com',
	    buyer_name : '테스트',
	    buyer_id : 'test'
	}, function(rsp) {
	    if ( rsp.success ) {
	    	alert("아임포트 서버 결제성공");
	        
	        /* db에 결제내역 전송 */
	        $.ajax({
	        	url:"/pay/payment",
	        	method:"post",
	        	data:{
					memberId:"test",
					itemNo:"1"
	        	},
	        	success:data=>{
	        		console.log("서버 저장 성공");
	        		//$("#content").html(data);
	        	}
	        });
	        
	    } else {
	        alert("아임포트 서버 결제실패");
	    }
	});
}

$("div#pay_container li").click((e)=>{
	console.log($(e.target).find("input").attr("select"));
});

var itemNo;

//이용권 리스트 가져오는 메소드
function getItemList(){
	$.ajax({
		url:getContextPath()+'/pay/itemList',
		success:data=>{
			let val='';
			if(data!=null){
				for(let i=1;i<data.length; i++){
					val+="<li>";
					val+="<input type='radio' style='display:none' name='itemType' value='"+data[i].ITEM_NO+"'>";
					val+="<label for='item"+data[i].ITEM_NO+"' id='"+data[i].ITEM_NO+"'>";
					val+=data[i].ITEM_TYPE+"일 이용권";
					val+="</label>";
					val+="<input type='radio' style='display:none' name='itemNo' value='"+data[i].ITEM_NO+"' id='item"+data[i].ITEM_NO+"'>";
					val+="<span>"+(data[i].ITEM_PRICE+3000)+"</span>";
					val+="<span>➔</span>";
					val+="<span>"+data[i].ITEM_PRICE+"</span>";
					val+="</li>";
				}
			}else{
				val+="<div>구매 가능한 상품이 없습니다.</div>";
			}
			$("div#pay_container ul").append(val);
			$("div#pay_container").find("li").find("input[name=itemNo]").click((e)=>{
				console.log($(e.target));
				$(e.target).prev().parent().siblings().removeClass("item_selected");
				$(e.target).prev().parent().addClass("item_selected");
				$(e.target).siblings("input[name=itemType]").click();
			});
			
		}
	});
}

//결제
function pay()	{
	console.log(itemNo);
	var IMP = window.IMP; // 생략가능
	IMP.init('imp65464808'); // 가맹점 식별코드
	IMP.request_pay({
	    pg : 'kakao', // version 1.1.0부터 지원.
	    /*
			'kakao':카카오페이,
			'html5_inicis':이니시스(웹표준결제)
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
	    name : $("div#pay_container li.item_selected>label").text(),
	    /* 결제창에서 보여질 이름 */
	    amount : $("div#pay_container li.item_selected>span:last-child").text(),
	    /* 가격 */
	    buyer_email : 'test@test.com',
	    buyer_name : '테스트',
	    /* m_redirect_url : 'https://www.yourdomain.com/payments/complete' */
	    /*
	    	모바일 결제시,
	    	결제가 끝나고 랜딩되는 URL을 지정
	    	(카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐)
	    */
	    
	}, function(rsp) {
	    if ( rsp.success ) {
		    	/* var msg = '결제가 완료되었습니다.';
		        msg += '고유ID : ' + rsp.imp_uid;
		        msg += '상점 거래ID : ' + rsp.merchant_uid;
		        msg += '카드 승인번호 : ' + rsp.apply_num;
		  		
		        msg += '결제 금액 : ' + rsp.paid_amount; */
		        
                $.ajax({
		        	url:getContextPath()+"/pay/end",
		        	type:"post",
		        	data:{
		        		itemNo:$("div#pay_container li.item_selected").find("input[type=radio]").val(),
		        		itemType:$("div#pay_container li.item_selected").find("label").attr("id")
		        	},
		        	success:data=>{
		        		toList();
		        	}
        
				});
				$("form#pay_form").submit();
				toList();
		    } else {
		    	alert('알수 없는 문제가 발생했습니다. 관리자에게 문의하세요.');
		    	 //var msg = rsp.error_msg;
		    }
		    //alert(msg);
	});
}

//게시글 목록으로 가는 함수
function toList(){
	location.replace(getContextPath()+"/board/all");
}

$(function(){
	getItemList();

});



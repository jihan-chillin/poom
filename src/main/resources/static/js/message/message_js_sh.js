
function fn_message_move(mType) {
    if (mType == 'receive') {
        $.ajax({
            url: getContextPath()+"/message/receive",
            data: {"mType": mType},
            type: 'POST',
        }).done(function (fragment) {
            $("#target").html(fragment);
        });
    }else if(mType=='send'){
        $.ajax({
            url:getContextPath()+"/message/send",
            data:{"mType":mType},
            type: 'POST',

        }).done(function (fragment){
                $("#target").html(fragment);
        });
    }else if(mType=='block'){
        $.ajax({
            url:getContextPath()+"/message/block",
            data:{"mType":mType},

        }).done(function (fragment){
            $("#target").html(fragment);
        });
    }
}

function showMsgDtl(msgNo) {
    window.open(getContextPath() + "/message/content?msgNo=" + msgNo, "content", "width=400,height=300");

}

//메세지 안읽음에 따른 발송취소
function cancelMsg(msgNo) {
    //alert("msgNo" + msgNo);
    if (confirm("발송취소하시겠습니까? 취소하시면 메세지가 삭제됩니다.")) {
        //alert('cancelMsg');
        $.ajax({
            url: getContextPath()+"/message/cancelMsg",
            data: {"msgNo": msgNo},
            type: 'POST',
        }).done(function () {
            alert("취소되었습니다.");
            fn_message_move('send');
            //$("#target").html(fragment);
        });
    }
}

function fn_move_Block() {
    if (confirm("휴지통으로 이동시키겠습니까?")) {
        $('.targetChk').each(function () {
            if ($(this).is(':checked')) {
                var msgNo = $(this).parents('td').next().html();
                $.ajax({
                    url: getContextPath() + "/message/moveBlock",
                    data: {"msgNo": msgNo},
                    type: 'POST',
                }).done(function () {
                    fn_message_move('block');
                });
            }
        });
    }
}

//메세지 보내기
function sendMsg(frm){
    if(confirm("메세지를 발송하시겠습니까?")){
        var formData = frm.serialize();
        $.ajax({
            url:"./sendMsg",
            data: formData,
            type:'POST',
        }).done(function (){
            alert("메시지가 전송되었습니다.");
            self.parent.close();
            self.close();
            parent.opener.location = getContextPath()+"/message/send";



            // 실시간 알림 보내는 메소드 by 희웅

            sendNoti();


        });
    }
}

function showPopup() {
    window.open(getContextPath() + "/message/receiver", "", "width=400,height=300");
}


//답장하기도 동일하게 넘기기
function showMsgPop(member) {
    let rcvNm = $(member).children('.find-name').html();
    let rcvId = $(member).children('.find-id').html();
    //alert(rcvNm);
    window.open("./popup?rcvNm=" + rcvNm + "&rcvId=" + rcvId, "", "width=400,height=300");
    //self.parent.close();
}


function showReplyPop(recvId){
    var rcvId = recvId.text();
    window.open(getContextPath() + "/message/popup?rcvNm=" + rcvId + "&rcvId=" + rcvId, "", "width=400,height=300")
}



function showMsgDtl(e, msgNo) {
    alert(e.target);
    window.open(getContextPath()+"/message/content?msgNo=" + msgNo,"content","width=400,height=300");

}

//메세지 검색
function tableFilter(e) {
    var value = e.value.toLowerCase();
    $('#seacrhBody tr').filter(function() {
        $(this).toggle($(this).children().text().toLowerCase().indexOf(value) > -1);
    });
}
$('.messages').click(function(e) {
    var msgNo = $(e.currentTarget).children('.msgNo').html();
    var tClassNm = e.target.className.toString();

    if(tClassNm == 'targetChk' || tClassNm == 'checkbox2 m-0') {
        return;
    } else
        window.open(getContextPath() + "/message/content?msgNo=" + msgNo, "content", "width=400,height=300");
});









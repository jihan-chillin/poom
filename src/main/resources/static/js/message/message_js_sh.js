
function fn_message_move(mType) {
    console.log("js파일 안: " + mType);
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
            type: 'POST',

        }).done(function (fragment){
            $("#target").html(fragment);
        });
    }
}

function showMsgDtl(msgNo) {
    //lert('showMsgDtl' + msgNo);
    window.open(getContextPath() + "/message/content?msgNo=" + msgNo, "content", "width=400,height=300");

}

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

function showPopup() {
    // if (type == 1)
    //     window.open("./popup", "", "width=400,height=300");
    // else if (type == 2 ) {
    //     alert(getContextPath() + "/message/receiver");
    window.open(getContextPath() + "/message/receiver", "", "width=400,height=300");
}

function showMsgPop(member) {
    let rcvNm = $(member).children('.find-name').html();
    let rcvId = $(member).children('.find-id').html();
    //alert(rcvNm);
    window.open("./popup?rcvNm=" + rcvNm + "&rcvId=" + rcvId, "", "width=400,height=300");
}



function showMsgDtl(msgNo) {
    window.open(getContextPath()+"/message/content?msgNo=" + msgNo,"content","width=400,height=300");

}




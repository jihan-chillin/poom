package com.chairking.poom.chatroom.model.vo;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChatMessage {
    private ChatType type;
    private String messageContent;
    private String memberId;
    private String chatNo;

    public ChatType getType(){
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getChatNo() {
        return chatNo;
    }

    public void setChatNo(String chatNo) {
        this.chatNo = chatNo;
    }
}

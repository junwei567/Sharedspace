package com.example.sharedspace;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUserID;
    private long messageTime;
    private int avatarNo;


    // Constructor for constructing new ChatMessage object from data in Firebase
    public ChatMessage(String messageText, String messageUserID, long messageTime) {
        this.messageText = messageText;
        this.messageUserID = messageUserID;

        // Initialize to current time
        this.messageTime = messageTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUserID() {
        return messageUserID;
    }

    public void setMessageUserID(String messageUserID) {
        this.messageUserID = messageUserID;
    }

    public long getMessageTime() {
        return messageTime;
    }

}

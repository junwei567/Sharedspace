package com.example.sharedspace;

public class ChatMessage {
    private String messageText;
    private String messageUserID;
    private long timestamp;
    private int avatarNo;


    // Constructor for constructing new ChatMessage object from data in Firebase
    public ChatMessage(String messageText, String messageUserID, long timestamp) {
        this.messageText = messageText;
        this.messageUserID = messageUserID;

        // Initialize to current time
        this.timestamp = timestamp;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUserID() {
        return messageUserID;
    }

    public long getTimestamp() {
        return timestamp;
    }

}

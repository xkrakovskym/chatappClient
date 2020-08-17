package com.company;

import java.io.Serializable;
import java.net.ServerSocket;

public class Message implements Serializable {
    private String sender;
    private String recipient;
    private String message;


    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public Message(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Message from %s:\n %s", sender, message);
    }
}

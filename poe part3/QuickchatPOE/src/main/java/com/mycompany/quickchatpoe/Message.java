package com.mycompany.quickchatpoe;

import java.util.Random;

public final class Message {
    // --- PART 2 FIELDS 
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    // --- PART 3 FIELDS 
    private String sender;
    private String flag; // Sent, Stored, Disregard

    // --- PART 2 CONSTRUCTOR
    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.sender = "Developer";
        this.flag = "Sent";
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // --- PART 3 
    public Message(int messageNumber, String recipient, String message, String sender, String flag) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.sender = sender;
        this.flag = flag;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // --- PART 3 
    public Message(String messageID, int messageNumber, String recipient, String message, String messageHash, String sender, String flag) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = messageHash;
        this.sender = sender;
        this.flag = flag;
    }

    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // --- PART 2 METHODS 
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            return "Message exceeds 250 characters by " + (message.length() - 250) + ", please reduce size.";
        }
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+") && recipient.length() <= 13 && recipient.length() >= 10) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    public String SentMessage(int option) {
        switch (option) {
            case 1 -> {
                flag = "Sent";
                return "Message successfully sent.";
            }
            case 2 -> {
                flag = "Disregard";
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                flag = "Stored";
                return "Message successfully stored.";
            }
            default -> {
            }
        }
        return "Invalid option.";
    }

    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
        String last = words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase();
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last;
    }

    // --- GETTERS (Part 2 + Part 3) 
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }
    public String getSender() { return sender; }
    public String getFlag() { return flag; }

    // --- PART 2 TEST HELPER 
    public void setMessageIDForTest(String id) {
        this.messageID = id;
        this.messageHash = createMessageHash();
    }

    // --- PART 3 HELPER 
    public void setMessageID(String id) {
        setMessageIDForTest(id);
    }

    public void setFlag(String flag) { this.flag = flag; }
    public void setSender(String sender) { this.sender = sender; }

    // --- PART 3 DETAILS FOR REPORT 
    public String getDetails() {
        return "Message Hash: " + messageHash +
               "\nMessage ID: " + messageID +
               "\nSender: " + sender +
               "\nRecipient: " + recipient +
               "\nMessage: " + message;
    }
}
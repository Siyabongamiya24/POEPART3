/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpoe;

/**
 *
 * @author Student
 */

  

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class MessageManager {
    public List<Message> sentMessages = new ArrayList<>();
    public List<Message> disregardedMessages = new ArrayList<>();
    public List<Message> storedMessages = new ArrayList<>();
    public List<String> messageHashes = new ArrayList<>();
    public List<String> messageIDs = new ArrayList<>();

    public static final String JSON_FILE = "storedMessages.json";

    public void addMessage(Message m) {
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageID());
        if (m.getFlag().equalsIgnoreCase("Sent")) {
            sentMessages.add(m);
        } else if (m.getFlag().equalsIgnoreCase("Stored")) {
            storedMessages.add(m);
            saveStoredMessagesToJSON();
        } else if (m.getFlag().equalsIgnoreCase("Disregard")) {
            disregardedMessages.add(m);
        }
    }

    public String displaySenderAndRecipientOfStored() {
        if (storedMessages.isEmpty()) return "No stored messages.";
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: ").append(m.getSender())
              .append(" | Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString();
    }

    public String displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) return "No stored messages.";
        Message longest = storedMessages.get(0);
        for (Message m : storedMessages) {
            if (m.getMessage().length() > longest.getMessage().length()) {
                longest = m;
            }
        }
        return longest.getMessage();
    }

    public String searchByMessageID(String id) {
        for (Message m : storedMessages) {
            if (m.getMessageID().equals(id)) {
                return "Message ID: " + m.getMessageID() + "\nRecipient: " + m.getRecipient() + "\nMessage: " + m.getMessage();
            }
        }
        for (Message m : sentMessages) {
            if (m.getMessageID().equals(id)) {
                return "Message ID: " + m.getMessageID() + "\nRecipient: " + m.getRecipient() + "\nMessage: " + m.getMessage();
            }
        }
        return "Message ID not found.";
    }

    public String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(recipient)) sb.append(m.getMessage()).append("\n");
        }
        for (Message m : sentMessages) {
            if (m.getRecipient().equals(recipient)) sb.append(m.getMessage()).append("\n");
        }
        if (sb.length() == 0) return "No messages found for this recipient.";
        return sb.toString().trim();
    }

    public String deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equalsIgnoreCase(hash)) {
                String msg = storedMessages.get(i).getMessage();
                storedMessages.remove(i);
                messageHashes.remove(hash);
                saveStoredMessagesToJSON();
                return "Message: \"" + msg + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    public String displayReport() {
        if (storedMessages.isEmpty()) return "No stored messages.";
        StringBuilder sb = new StringBuilder("--- STORED MESSAGES REPORT ---\n");
        for (Message m : storedMessages) {
            sb.append(m.getDetails()).append("\n--------------------------\n");
        }
        return sb.toString();
    }

    public void saveStoredMessagesToJSON() {
        try (FileWriter fw = new FileWriter(JSON_FILE)) {
            fw.write("[\n");
            for (int i = 0; i < storedMessages.size(); i++) {
                Message m = storedMessages.get(i);
                fw.write("  {\"messageID\":\"" + m.getMessageID() + "\",\"messageNumber\":" + m.getMessageNumber()
                        + ",\"recipient\":\"" + m.getRecipient() + "\",\"message\":\"" + m.getMessage().replace("\"", "'")
                        + "\",\"hash\":\"" + m.getMessageHash() + "\",\"sender\":\"" + m.getSender()
                        + "\",\"flag\":\"" + m.getFlag() + "\"}");
                if (i < storedMessages.size() - 1) fw.write(",");
                fw.write("\n");
            }
            fw.write("]");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving JSON: " + e.getMessage());
        }
    }

    public void loadStoredMessagesFromJSON() {
        storedMessages.clear();
        File f = new File(JSON_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"messageID\"")) {
                    String id = extract(line, "messageID");
                    int num = Integer.parseInt(extract(line, "messageNumber"));
                    String rec = extract(line, "recipient");
                    String msg = extract(line, "message");
                    String hash = extract(line, "hash");
                    String sender = extract(line, "sender");
                    String flag = extract(line, "flag");
                    storedMessages.add(new Message(id, num, rec, msg, hash, sender, flag));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
        }
    }

    private String extract(String line, String key) {
        String search = "\"" + key + "\":";
        int start = line.indexOf(search) + search.length();
        if (line.charAt(start) == '"') {
            int end = line.indexOf("\"", start + 1);
            return line.substring(start + 1, end);
        } else {
            int end = line.indexOf(",", start);
            if (end == -1) end = line.indexOf("}", start);
            return line.substring(start, end).trim();
        }
    }

    public void populateTestData() {
        sentMessages.clear(); disregardedMessages.clear(); storedMessages.clear();
        messageHashes.clear(); messageIDs.clear();

        Message m1 = new Message(1, "+27834557896", "Did you get the cake?", "Developer", "Sent");
        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored");
        Message m3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.", "Developer", "Disregard");
        Message m4 = new Message(4, "0838884567", "It is dinner time!", "Developer", "Sent");
        Message m5 = new Message(5, "+27838884567", "Ok, I am leaving without you.", "Developer", "Stored");

        m4.setMessageID("0838884567");

        addMessage(m1); addMessage(m2); addMessage(m3); addMessage(m4); addMessage(m5);
    }
}


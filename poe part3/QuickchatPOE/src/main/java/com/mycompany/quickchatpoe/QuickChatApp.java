package com.mycompany.quickchatpoe;



import java.util.Scanner;
import java.util.ArrayList;

public class QuickChatApp {
      
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ArrayList<Message> sentMessages = new ArrayList<>();
            
            MessageManager manager = new MessageManager();
            manager.loadStoredMessagesFromJSON(); 

            
            // Part 1 login - for Part 2 we assume success
            System.out.println("Welcome to QuickChat.");
            
            int option;
            do {
                System.out.println("\n--- QuickChat Menu ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose option: ");
                
                while (!sc.hasNextInt()) {
                    System.out.print("Please enter 1, 2 or 3: ");
                    sc.next();
                }
                option = sc.nextInt();
                sc.nextLine();
                                switch (option) {
                    case 1 -> {
                        System.out.print("How many messages do you wish to enter? ");
                        int numMessages = sc.nextInt();
                        sc.nextLine();
                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\nMessage " + (i+1) + " of " + numMessages);
                            
                            System.out.print("Enter recipient (e.g. +27718693002): ");
                            String recipient = sc.nextLine();
                            
                            System.out.print("Enter message (max 250 chars): ");
                            String text = sc.nextLine();
                            
                            Message msg = new Message(i, recipient, text);
                            
                            // Check ID
                            System.out.println("Message ID generated: " + msg.getMessageID());
                            
                            // Check recipient - from Table page 13
                            String recipientCheck = msg.checkRecipientCell();
                            System.out.println(recipientCheck);
                            if (recipientCheck.contains("incorrectly formatted")) {
                                System.out.println("Retrying this message...");
                                i--;
                                continue;
                            }
                            
                            // Check length - from Table page 13
                            String lengthCheck = msg.checkMessageLength();
                            System.out.println(lengthCheck);
                            if (lengthCheck.contains("exceeds")) {
                                System.out.println("Please enter a message of less than 250 characters.");
                                i--;
                                continue;
                            }
                            
                            // Show hash
                            System.out.println("Message Hash: " + msg.createMessageHash());
                            
                            System.out.println("\nChoose what to do:");
                            System.out.println("1) Send Message");
                            System.out.println("2) Disregard Message");
                            System.out.println("3) Store Message to send later");
                            System.out.print("Your choice: ");
                            int choice = sc.nextInt();
                            sc.nextLine();
                            
                            String result = msg.SentMessage(choice);
                            System.out.println(result);
                            
                            if (choice == 1) {
                                sentMessages.add(msg);
                                System.out.println("\n--- Full Details ---");
                                System.out.println("MessageID: " + msg.getMessageID());
                                System.out.println("Message Hash: " + msg.getMessageHash());
                                System.out.println("Recipient: " + msg.getRecipient());
                                System.out.println("Message: " + msg.getMessage());
                                System.out.println("--------------------");
                            }
                        }   System.out.println("\nTotal messages sent: " + sentMessages.size());
                    }
                    case 2 -> System.out.println("Coming Soon.");
                    case 3 -> System.out.println("Quitting application. Goodbye!");
                    default -> System.out.println("Invalid option. Try again.");
                }
                
            } while (option != 3);
        }
    }
}
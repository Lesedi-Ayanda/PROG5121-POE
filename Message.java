/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author lesed
 */
class Message {

    // variable declarations
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;

    // static list to hold all sent messages across the whole program
    static ArrayList<String> allSentMessages = new ArrayList<>();

    // static counter for total messages sent
    static int totalSent = 0;

    // constructor
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID(); // generate ID first
        this.messageHash = createMessageHash(); // hash needs ID so it goes second
    }

    // generate a random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + rand.nextInt(10);
        }
        return id;
    }

    // check that the message ID is not more than 10 characters
    public boolean checkMessageID() {
        if (messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }

    // check recipient starts with + international code
    // the assignment test data uses +27718693002 so we just check for + at the start
    public String checkRecipientCell() {
        if (recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an International code. Please correct the number and try again.";
        }
    }

    // check message is 250 characters or less
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = messageText.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
    }

    // build the hash: first 2 digits of ID : message number : FIRSTWORDLASTWORD
    private String createMessageHash() {
        // get first 2 characters of the message ID
        String idPart = messageID.substring(0, 2);

        // split the message into words
        String[] words = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // remove punctuation from the last word e.g. ? ! .
        String cleanLastWord = "";
        for (int i = 0; i < lastWord.length(); i++) {
            char c = lastWord.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                cleanLastWord = cleanLastWord + c;
            }
        }

        // join it all together and make it all caps
        String hash = idPart + ":" + messageNumber + ":" + firstWord + cleanLastWord;
        return hash.toUpperCase();
    }

    // let user choose what to do with the message
    // 1 = send, 2 = disregard, 3 = store
    public String sentMessage(int choice) {
        if (choice == 1) {
            // add to the sent list and increment counter
            totalSent++;
            allSentMessages.add("Message ID: " + messageID + "\n" +
                                 "Message Hash: " + messageHash + "\n" +
                                 "Recipient: " + recipient + "\n" +
                                 "Message: " + messageText);
            return "Message successfully sent.";

        } else if (choice == 2) {
            // fixed: added full stop at the end to match assignment exactly
            return "Press 0 to delete the message.";

        } else if (choice == 3) {
            // store the message to JSON file and add to list
            storeMessage();
            allSentMessages.add("Message ID: " + messageID + "\n" +
                                 "Message Hash: " + messageHash + "\n" +
                                 "Recipient: " + recipient + "\n" +
                                 "Message: " + messageText);
            return "Message successfully stored.";

        } else {
            return "Invalid choice.";
        }
    }

    // return all sent messages as one big string
    public static String printMessages() {
        String result = "";
        if (allSentMessages.isEmpty()) {
            result = "No messages sent yet.";
        } else {
            for (int i = 0; i < allSentMessages.size(); i++) {
                result = result + "\n--- Message " + (i + 1) + " ---\n";
                result = result + allSentMessages.get(i) + "\n";
            }
        }
        return result;
    }

    // return the total number of messages sent
    public static int returnTotalMessages() {
        return totalSent;
    }

    // store this message in a real JSON file on disk
    // each stored message gets added to messages.json
    public void storeMessage() {

        // build the json text for this message
        String json = "{\n" +
                      "  \"messageID\": \"" + messageID + "\",\n" +
                      "  \"messageHash\": \"" + messageHash + "\",\n" +
                      "  \"recipient\": \"" + recipient + "\",\n" +
                      "  \"message\": \"" + messageText + "\"\n" +
                      "}";

        // write the json to a file called messages.json
        // true means we ADD to the file instead of overwriting it each time
        try {
            FileWriter writer = new FileWriter("messages.json", true);
            writer.write(json + "\n");
            writer.close();
            System.out.println("Message saved to messages.json");
        } catch (IOException e) {
            System.out.println("Error saving message to file: " + e.getMessage());
        }
    }

    // getters
    public String getMessageID()   { return messageID;   }
    public String getMessageHash() { return messageHash; }
    public String getRecipient()   { return recipient;   }
    public String getMessageText() { return messageText; }
}

    
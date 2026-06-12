/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 *
 * @author lesed
 */
class Message {

    
    // declare instance variables
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int    messageNumber;
    private String flag;

    // --- Part 3: parallel arrays ---
    // these arrays store message data - all arrays use the same index number
    public static String[] sentMessages        = new String[100];
    public static String[] disregardedMessages = new String[100];
    public static String[] storedMessages      = new String[100];
    public static String[] messageHashArray    = new String[100];
    public static String[] messageIDArray      = new String[100];

    // counters to track how many items are in each array
    public static int sentCount        = 0;
    public static int disregardedCount = 0;
    public static int storedCount      = 0;
    public static int hashCount        = 0;
    public static int totalSent        = 0;

    // array to store all message objects so we can search them
    public static Message[] allMessages = new Message[100];
    public static int       allCount    = 0;

    // constructor - runs when we write: new Message(...)
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageNumber = messageNumber;
        this.flag          = "";
        this.messageID     = generateMessageID();
        this.messageHash   = createMessageHash();
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
        boolean valid = false;

        if (messageID.length() <= 10) {
            valid = true;
        }

        return valid;
    }

    // check recipient starts with + international code
    public String checkRecipientCell() {
        String result = "";

        if (recipient.startsWith("+")) {
            result = "Cell phone number successfully captured.";
        } else {
            result = "Cell phone number is incorrectly formatted or does not contain " +
                     "an International code. Please correct the number and try again.";
        }

        return result;
    }

    // check message is 250 characters or less
    public String checkMessageLength() {
        String result = "";

        if (messageText.length() <= 250) {
            result = "Message ready to send.";
        } else {
            int over = messageText.length() - 250;
            result = "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }

        return result;
    }

    // build the hash: first 2 digits of ID : message number : FIRSTWORDLASTWORD
    private String createMessageHash() {
        // get first 2 characters of the message ID
        String idPart = messageID.substring(0, 2);

        // split the message into words
        String[] words   = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];

        // remove punctuation from the last word
        String cleanLastWord = "";
        for (int i = 0; i < lastWord.length(); i++) {
            char c = lastWord.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                cleanLastWord = cleanLastWord + c;
            }
        }

        // put it all together in caps
        String hash = idPart + ":" + messageNumber + ":" + firstWord + cleanLastWord;
        return hash.toUpperCase();
    }

    // let the user choose what to do with the message
    // choice 1 = Send, choice 2 = Disregard, choice 3 = Store
    public String sentMessage(int choice) {
        String result = "";

        if (choice == 1) {
            flag = "Sent";
            totalSent++;
            sentMessages[sentCount] = messageText;
            sentCount++;
            messageHashArray[hashCount] = messageHash;
            messageIDArray[hashCount]   = messageID;
            hashCount++;
            allMessages[allCount] = this;
            allCount++;
            result = "Message successfully sent.";

        } else if (choice == 2) {
            flag = "Disregard";
            disregardedMessages[disregardedCount] = messageText;
            disregardedCount++;
            messageHashArray[hashCount] = messageHash;
            messageIDArray[hashCount]   = messageID;
            hashCount++;
            allMessages[allCount] = this;
            allCount++;
            result = "Press 0 to delete the message.";

        } else if (choice == 3) {
            flag = "Stored";
            storeMessage();
            storedMessages[storedCount] = messageText;
            storedCount++;
            messageHashArray[hashCount] = messageHash;
            messageIDArray[hashCount]   = messageID;
            hashCount++;
            allMessages[allCount] = this;
            allCount++;
            result = "Message successfully stored.";

        } else {
            result = "Invalid choice.";
        }

        return result;
    }

    // return all sent messages as a string
    public static String printMessages() {
        String result = "";

        if (sentCount == 0) {
            result = "No messages sent yet.";
        } else {
            for (int i = 0; i < sentCount; i++) {
                result = result + "--- Message " + (i + 1) + " ---\n";
                result = result + sentMessages[i] + "\n";
            }
        }

        return result;
    }

    // return the total number of messages sent
    public static int returnTotalMessages() {
        return totalSent;
    }

    // write this message to the messages.json file on disk
    // Source used to learn JSON writing: https://github.com/fangyidong/json-simple
    public void storeMessage() {
        String json = "{\n" +
                      "  \"messageID\": \""   + messageID   + "\",\n" +
                      "  \"messageHash\": \"" + messageHash + "\",\n" +
                      "  \"recipient\": \""   + recipient   + "\",\n" +
                      "  \"message\": \""     + messageText + "\"\n"  +
                      "}";

        try {
            // true means we add to the file instead of overwriting it
            FileWriter writer = new FileWriter("messages.json", true);
            writer.write(json + "\n");
            writer.close();
            System.out.println("Message saved to messages.json");
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }

    // =====================================================================
    // PART 3 FEATURES
    // =====================================================================

    // a. show sender and recipient of all stored messages
    public static String displayStoredSenderRecipient() {
        String result = "";

        if (storedCount == 0) {
            result = "No stored messages found.";
            return result;
        }

        result = "--- Stored Messages: Sender and Recipient ---\n";

        for (int i = 0; i < allCount; i++) {
            Message m = allMessages[i];
            if (m.flag.equals("Stored")) {
                result = result + "Message:   " + m.getMessageText() + "\n";
                result = result + "Recipient: " + m.getRecipient()   + "\n";
                result = result + "ID:        " + m.getMessageID()   + "\n\n";
            }
        }

        return result;
    }

    // b. find and return the longest message from sent and stored
    public static String displayLongestMessage() {
        String longest = "";
        String result  = "";

        // check sent messages
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].length() > longest.length()) {
                longest = sentMessages[i];
            }
        }

        // check stored messages
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }

        if (longest.equals("")) {
            result = "No messages found.";
        } else {
            result = "Longest message: \"" + longest + "\"";
        }

        return result;
    }

    // c. search for a message using its ID
    public static String searchByMessageID(String searchID) {
        String result = "";

        for (int i = 0; i < allCount; i++) {
            Message m = allMessages[i];
            if (m.getMessageID().equals(searchID)) {
                result = "Message found!\n" +
                         "Message ID: " + searchID          + "\n" +
                         "Recipient:  " + m.getRecipient()  + "\n" +
                         "Message:    " + m.getMessageText() + "\n";
                return result;
            }
        }

        result = "Message ID not found.";
        return result;
    }

    // d. search for all messages sent to a particular recipient
    public static String searchByRecipient(String searchRecipient) {
        String result = "";
        int    count  = 0;

        for (int i = 0; i < allCount; i++) {
            Message m = allMessages[i];
            if (m.getRecipient().equals(searchRecipient)) {
                if (m.flag.equals("Sent") || m.flag.equals("Stored")) {
                    result = result + "\"" + m.getMessageText() + "\"\n";
                    count++;
                }
            }
        }

        if (count == 0) {
            result = "No messages found for recipient: " + searchRecipient;
        } else {
            result = "Messages for " + searchRecipient + ":\n" + result;
        }

        return result;
    }

    // e. delete a message using its hash
    public static String deleteByHash(String searchHash) {
        String result = "";

        for (int i = 0; i < allCount; i++) {
            Message m = allMessages[i];

            if (m.getMessageHash().equals(searchHash)) {
                String deletedText = m.getMessageText();
                String deletedFlag = m.flag;

                // remove from the correct sub-array by shifting items left
                if (deletedFlag.equals("Sent")) {
                    for (int j = 0; j < sentCount; j++) {
                        if (sentMessages[j].equals(deletedText)) {
                            for (int k = j; k < sentCount - 1; k++) {
                                sentMessages[k] = sentMessages[k + 1];
                            }
                            sentCount--;
                            break;
                        }
                    }
                } else if (deletedFlag.equals("Stored")) {
                    for (int j = 0; j < storedCount; j++) {
                        if (storedMessages[j].equals(deletedText)) {
                            for (int k = j; k < storedCount - 1; k++) {
                                storedMessages[k] = storedMessages[k + 1];
                            }
                            storedCount--;
                            break;
                        }
                    }
                } else if (deletedFlag.equals("Disregard")) {
                    for (int j = 0; j < disregardedCount; j++) {
                        if (disregardedMessages[j].equals(deletedText)) {
                            for (int k = j; k < disregardedCount - 1; k++) {
                                disregardedMessages[k] = disregardedMessages[k + 1];
                            }
                            disregardedCount--;
                            break;
                        }
                    }
                }

                // remove from allMessages by shifting items left
                for (int j = i; j < allCount - 1; j++) {
                    allMessages[j] = allMessages[j + 1];
                }
                allCount--;

                result = "Message: \"" + deletedText + "\" successfully deleted.";
                return result;
            }
        }

        result = "Hash not found. No message deleted.";
        return result;
    }

    // f. display a full report of all messages
    public static String displayReport() {
        String result = "";

        if (allCount == 0) {
            result = "No messages to report.";
            return result;
        }

        result = "========== MESSAGE REPORT ==========\n";

        for (int i = 0; i < allCount; i++) {
            Message m = allMessages[i];
            result = result + "------------------------------------\n";
            result = result + "Message #  " + (i + 1)            + "\n";
            result = result + "Hash:      " + m.getMessageHash() + "\n";
            result = result + "ID:        " + m.getMessageID()   + "\n";
            result = result + "Recipient: " + m.getRecipient()   + "\n";
            result = result + "Message:   " + m.getMessageText() + "\n";
            result = result + "Flag:      " + m.flag             + "\n";
        }

        result = result + "====================================\n";
        result = result + "Total messages: " + allCount + "\n";

        return result;
    }

    // read the messages.json file and load messages into storedMessages array
    // Source: https://github.com/fangyidong/json-simple
    public static void loadStoredMessagesFromJSON(String filePath) {

        // create a new JSON parser object
        JSONParser parser = new JSONParser();

        try {
            // open the file
            FileReader reader = new FileReader(filePath);

            // parse the file into a JSONArray
            Object    obj       = parser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            // loop through each item in the array
            for (int i = 0; i < jsonArray.size(); i++) {

                // get the current JSON object
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                // get the message value from the object
                String message = (String) jsonObject.get("message");

                // add to storedMessages array if it is not empty
                if (message != null) {
                    storedMessages[storedCount] = message;
                    storedCount++;
                }
            }

            System.out.println("JSON file loaded. " + jsonArray.size() + " messages found.");
            reader.close();

        } catch (Exception e) {
            System.out.println("No existing JSON file found: " + e.getMessage());
        }
    }

    // getters
    public String getMessageID()   { return messageID;   }
    public String getMessageHash() { return messageHash; }
    public String getRecipient()   { return recipient;   }
    public String getMessageText() { return messageText; }
    public String getFlag()        { return flag;        }

}
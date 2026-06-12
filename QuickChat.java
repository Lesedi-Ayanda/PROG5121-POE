/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

import java.util.Scanner;

/**
 *
 * @author lesed
 */
public class QuickChat {

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("       Welcome to QuickChat             ");
        System.out.println("========================================");

        // ----------------------------------------------------------------
        // PART 1 - REGISTRATION
        // We call Login.java by creating a Login object
        // ----------------------------------------------------------------

        System.out.println("--- Register ---");

        // collect registration details
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter username (must contain _ and be 5 chars or less): ");
        String regUsername = input.nextLine();

        System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special char): ");
        String regPassword = input.nextLine();

        System.out.print("Enter cell phone number (e.g. +27821234567): ");
        String regPhone = input.nextLine();

        // create a Login object - this is how we call Login.java
        Login loginObject = new Login(firstName, lastName, regUsername, regPassword, regPhone);

        // call registerUser() from Login.java
        String regResult = "";
        regResult = loginObject.registerUser();
        System.out.println(regResult);

        // stop the program if registration failed
        boolean regSuccess = false;
        if (regResult.contains("successfully")) {
            regSuccess = true;
        }

        if (regSuccess == false) {
            System.out.println("Registration failed. Please restart and try again.");
            input.close();
            return;
        }

        // ----------------------------------------------------------------
        // PART 1 - LOGIN
        // We call loginUser() and returnLoginStatus() from Login.java
        // ----------------------------------------------------------------

        System.out.println("--- Log In ---");

        // collect login details
        System.out.print("Enter username: ");
        String loginUsername = "";
        loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = "";
        loginPassword = input.nextLine();

        // call loginUser() from Login.java
        boolean loggedIn = false;
        loggedIn = loginObject.loginUser(loginUsername, loginPassword);

        // call returnLoginStatus() from Login.java
        String loginStatus = "";
        loginStatus = loginObject.returnLoginStatus(loginUsername, loginPassword);
        System.out.println(loginStatus);

        // stop the program if login failed
        if (loggedIn == false) {
            System.out.println("Access denied. Goodbye.");
            input.close();
            return;
        }

        // ----------------------------------------------------------------
        // PART 3 - LOAD JSON FILE
        // We call loadStoredMessagesFromJSON() from Message.java
        // ----------------------------------------------------------------

        Message.loadStoredMessagesFromJSON("messages.json");

        // load the 5 test messages from the brief
        loadTestData();

        // ----------------------------------------------------------------
        // PART 2 - MAIN MENU
        // We call Message.java methods for sending and managing messages
        // ----------------------------------------------------------------

        System.out.print("\nHow many messages do you want to send? ");
        int numMessages = 0;
        numMessages = input.nextInt();
        input.nextLine();

        boolean running = true;

        while (running == true) {

            System.out.println("\n========================================");
            System.out.println("           QuickChat Menu               ");
            System.out.println("========================================");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");

            int menuChoice = 0;
            menuChoice = input.nextInt();
            input.nextLine();

            // option 1 - send messages
            if (menuChoice == 1) {

                for (int i = 0; i < numMessages; i++) {

                    System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");

                    // collect recipient number
                    System.out.print("Enter recipient cell number (e.g. +27718693002): ");
                    String recipient = "";
                    recipient = input.nextLine();

                    // collect message text
                    System.out.print("Enter your message (max 250 chars): ");
                    String text = "";
                    text = input.nextLine();

                    // create a Message object - this is how we call Message.java
                    Message msg = new Message(recipient, text, Message.totalSent + i + 1);

                    // call checkRecipientCell() from Message.java
                    String cellResult = "";
                    cellResult = msg.checkRecipientCell();
                    System.out.println(cellResult);

                    // if number is wrong redo this message
                    if (cellResult.equals("Cell phone number successfully captured.") == false) {
                        i = i - 1;
                        continue;
                    }

                    // call checkMessageLength() from Message.java
                    String lengthResult = "";
                    lengthResult = msg.checkMessageLength();
                    System.out.println(lengthResult);

                    // if message is too long redo this message
                    if (lengthResult.equals("Message ready to send.") == false) {
                        i = i - 1;
                        continue;
                    }

                    // show the generated message ID and hash from Message.java
                    System.out.println("Message ID:   " + msg.getMessageID());
                    System.out.println("Message Hash: " + msg.getMessageHash());

                    // ask what to do with the message
                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose: ");

                    int sendChoice = 0;
                    sendChoice = input.nextInt();
                    input.nextLine();

                    // call sentMessage() from Message.java
                    String sendResult = "";
                    sendResult = msg.sentMessage(sendChoice);
                    System.out.println(sendResult);

                    // if disregarded redo this message slot
                    if (sendChoice == 2) {
                        i = i - 1;
                    }
                }

                // call printMessages() from Message.java
                System.out.println("\n===== All Sent Messages =====");
                System.out.println(Message.printMessages());

                // call returnTotalMessages() from Message.java
                System.out.println("Total messages sent: " + Message.returnTotalMessages());

            // option 2 - show recently sent messages
            } else if (menuChoice == 2) {
                System.out.println("\n===== Recently Sent Messages =====");
                System.out.println(Message.printMessages());

            // option 3 - quit
            } else if (menuChoice == 3) {
                System.out.println("Goodbye!");
                running = false;

            // option 4 - stored messages menu (Part 3)
            } else if (menuChoice == 4) {
                openStoredMessagesMenu(input);

            } else {
                System.out.println("Invalid option. Please enter 1, 2, 3 or 4.");
            }
        }

        input.close();
    }

    // ----------------------------------------------------------------
    // PART 3 - LOAD TEST DATA
    // Creates the 5 test messages from the brief using Message.java
    // ----------------------------------------------------------------
    public static void loadTestData() {

        // Test Message 1 - Sent
        Message msg1 = new Message("+27834557896", "Did you get the cake?", 1);
        msg1.sentMessage(1);

        // Test Message 2 - Stored
        Message msg2 = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.sentMessage(3);

        // Test Message 3 - Disregard
        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", 3);
        msg3.sentMessage(2);

        // Test Message 4 - Sent
        Message msg4 = new Message("+27838884567", "It is dinner time !", 4);
        msg4.sentMessage(1);

        // Test Message 5 - Stored
        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.", 5);
        msg5.sentMessage(3);

        System.out.println("Test data loaded.");
    }

    // ----------------------------------------------------------------
    // PART 3 - STORED MESSAGES MENU
    // Opens the sub-menu and calls Message.java methods for each option
    // ----------------------------------------------------------------
    public static void openStoredMessagesMenu(Scanner input) {

        boolean inMenu = true;

        while (inMenu == true) {

            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search by message ID");
            System.out.println("d) Search messages for a particular recipient");
            System.out.println("e) Delete a message using message hash");
            System.out.println("f) Display full message report");
            System.out.println("x) Back to main menu");
            System.out.print("Choose an option: ");

            String choice = "";
            choice = input.nextLine();

            if (choice.equals("a")) {
                // call displayStoredSenderRecipient() from Message.java
                System.out.println(Message.displayStoredSenderRecipient());

            } else if (choice.equals("b")) {
                // call displayLongestMessage() from Message.java
                System.out.println(Message.displayLongestMessage());

            } else if (choice.equals("c")) {
                System.out.print("Enter message ID to search: ");
                String searchID = "";
                searchID = input.nextLine();
                // call searchByMessageID() from Message.java
                System.out.println(Message.searchByMessageID(searchID));

            } else if (choice.equals("d")) {
                System.out.print("Enter recipient number to search: ");
                String searchRecipient = "";
                searchRecipient = input.nextLine();
                // call searchByRecipient() from Message.java
                System.out.println(Message.searchByRecipient(searchRecipient));

            } else if (choice.equals("e")) {
                System.out.print("Enter message hash to delete: ");
                String searchHash = "";
                searchHash = input.nextLine();
                // call deleteByHash() from Message.java
                System.out.println(Message.deleteByHash(searchHash));

            } else if (choice.equals("f")) {
                // call displayReport() from Message.java
                System.out.println(Message.displayReport());

            } else if (choice.equals("x")) {
                inMenu = false;

            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
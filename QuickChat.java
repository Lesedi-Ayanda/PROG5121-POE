/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

import java.util.Scanner;

/**
 *
 * @author lesedi
 */
public class QuickChat {

    public static void main(String[] args) {
     
        Scanner input = new Scanner(System.in);

        // PART 1 - REGISTRATION AND LOGIN

        System.out.println("========================================");
        System.out.println("       Welcome to QuickChat             ");
        System.out.println("========================================");

        // --- PART 1: registration ---
        System.out.println("--- Register ---");

        // get user details: first name
        System.out.print("Enter first name: ");
        String firstName = "";
        firstName = input.nextLine();

        //last name
        System.out.print("Enter last name: ");
        String lastName = "";
        lastName = input.nextLine();

        //username
        System.out.print("Enter username (must contain _ and be 5 chars or less): ");
        String regUsername = "";
        regUsername = input.nextLine();

        //password
        System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special char): ");
        String regPassword = "";
        regPassword = input.nextLine();

        //cell phone number
        System.out.print("Enter cell phone number (must start with +27 and be <10 digits): ");
        String regPhone = "";
        regPhone = input.nextLine();

        // create the Login object using the details the user just entered
        Login loginSystem = new Login(firstName, lastName, regUsername, regPassword, regPhone);

        // call registerUser() from the Login class 
        String regResult = "";
        regResult = loginSystem.registerUser();
        System.out.println("" + regResult);

        // check if registration was successful
        boolean regSuccess = false;
        if (regResult.contains("successfully")) {
            regSuccess = true;
        }

        // if registration failed stop the program here
        if (regSuccess == false) {
            System.out.println("Registration failed. Please restart and try again.");
            input.close();
            return;
        }

        // --- PART 1: login ---
        System.out.println("--- Log In ---");

        // get login details: login username
        System.out.print("Enter username: ");
        String loginUsername = "";
        loginUsername = input.nextLine();

        //login password
        System.out.print("Enter password: ");
        String loginPassword = "";
        loginPassword = input.nextLine();

        // call loginUser() from the Login class to check if details match
        boolean loggedIn = false;
        loggedIn = loginSystem.loginUser(loginUsername, loginPassword);

        // call returnLoginStatus() to print welcome or error message
        String loginStatus = "";
        loginStatus = loginSystem.returnLoginStatus(loginUsername, loginPassword);
        System.out.println(loginStatus);

        // if login failed stop here - user cannot access Part 2
        if (loggedIn == false) {
            System.out.println("Access denied. Goodbye.");
            input.close();
            return;
        }

        // ================================================================
        // PART 2 - SENDING MESSAGES
        // ================================================================

        // ask how many messages the user wants to send
        System.out.print("How many messages do you want to send? ");
        int numMessages = 0;
        numMessages = input.nextInt();
        input.nextLine(); // clear the leftover newline after the number

        // this controls the main menu loop
        // when the user picks quit it becomes false and the loop stops
        boolean running = true;

        // keep showing the menu until the user chooses to quit
        while (running == true) {

            System.out.println("========================================");
            System.out.println("        Welcome to QuickChat            ");
            System.out.println("========================================");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            // read the menu choice
            int menuChoice = 0;
            menuChoice = input.nextInt();
            input.nextLine(); // clear the leftover newline after the number

            // option 1 - send messages
            if (menuChoice == 1) {

                // use a for loop to collect each message one by one
                // i starts at 0 and goes up by 1 each time until it reaches numMessages
                for (int i = 0; i < numMessages; i++) {

                    System.out.println("--- Message " + (i + 1) + " of " + numMessages + " ---");

                    // get the recipient cell number
                    System.out.print("Enter recipient cell number (must start with +27 and be <10 digits): ");
                    String recipient = "";
                    recipient = input.nextLine();

                    // get the message text
                    System.out.print("Enter your message (max 250 chars): ");
                    String text = "";
                    text = input.nextLine();

                    // create a new Message object using the Message class
                    Message msg = new Message(recipient, text, i);

                    // check the recipient number using checkRecipientCell()
                    String cellResult = "";
                    cellResult = msg.checkRecipientCell();
                    System.out.println(cellResult);

                    // if the number is wrong redo this message slot
                    if (cellResult.equals("Cell phone number successfully captured.") == false) {
                        i = i - 1; 
                        continue;  
                    }

                    // check the message length using checkMessageLength()
                    String lengthResult = "";
                    lengthResult = msg.checkMessageLength();
                    System.out.println(lengthResult);

                    // if the message is too long redo this message slot
                    if (lengthResult.equals("Message ready to send.") == false) {
                        i = i - 1; 
                        continue;  
                    }

                    // show the auto generated message ID and hash
                    System.out.println("Message ID:   " + msg.getMessageID());
                    System.out.println("Message Hash: " + msg.getMessageHash());

                    // ask the user what they want to do with the message
                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose: ");

                    // read the send choice
                    int sendChoice = 0;
                    sendChoice = input.nextInt();
                    input.nextLine(); 

                    // call sentMessage() from the Message class
                    String sendResult = "";
                    sendResult = msg.sentMessage(sendChoice);
                    System.out.println(sendResult);

                    // if the user chose to disregard redo this message slot
                    if (sendChoice == 2) {
                        i = i - 1; 
                    }
                }

                // after the loop show all sent messages and the total count
                System.out.println("===== All Sent Messages =====");
                System.out.println(Message.printMessages());
                System.out.println("Total messages sent: " + Message.returnTotalMessages());

            // option 2 - show recently sent (still being built)
            } else if (menuChoice == 2) {
                System.out.println("Coming Soon.");

            // option 3 - quit the app
            } else if (menuChoice == 3) {
                System.out.println("Goodbye!");
                running = false; 

            // anything else is invalid
            } else {
                System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }

        input.close();
    }
}
    


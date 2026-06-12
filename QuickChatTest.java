/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package com.mycompany.quickchat;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class QuickChatTest {

    // declare test objects

    // Part 1 - Login test objects
    Login validLogin;
    Login badUsername;
    Login badPassword;
    Login badPhone;

    // Part 2 - Message test data
    String recipient1 = "+27718693002";
    String text1      = "Hi Mike, can you join us for dinner tonight?";
    int    msgNum1    = 0;

    String recipient2 = "08575975889";
    String text2      = "Hi Keegan, did you receive the payment?";
    int    msgNum2    = 1;


    // SETUP - runs before every test to create fresh test objects
    
    @Before
    public void setUp() {

        // reset all static arrays and counters before each test
        Message.sentMessages        = new String[100];
        Message.disregardedMessages = new String[100];
        Message.storedMessages      = new String[100];
        Message.messageHashArray    = new String[100];
        Message.messageIDArray      = new String[100];
        Message.allMessages         = new Message[100];
        Message.sentCount           = 0;
        Message.disregardedCount    = 0;
        Message.storedCount         = 0;
        Message.hashCount           = 0;
        Message.allCount            = 0;
        Message.totalSent           = 0;

        // create Login test objects
        validLogin  = new Login("John", "Doe", "jd_01", "Pass@123", "+27821234567");
        badUsername = new Login("Jane", "Doe", "janedoe", "Pass@123", "+27821234567");
        badPassword = new Login("Jane", "Doe", "jd_01", "password", "+27821234567");
        badPhone    = new Login("Jane", "Doe", "jd_01", "Pass@123", "0821234567");

        // load the 5 brief test messages so Part 3 tests have data
        Message msg1 = new Message("+27834557896", "Did you get the cake?", 1);
        msg1.sentMessage(1);

        Message msg2 = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.sentMessage(3);

        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", 3);
        msg3.sentMessage(2);

        Message msg4 = new Message("+27838884567", "It is dinner time !", 4);
        msg4.sentMessage(1);

        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.", 5);
        msg5.sentMessage(3);
    }

    // PART 1 TESTS - Login class

    // test: valid username should return true
    @Test
    public void testCheckUserName() {
        boolean result = false;
        result = validLogin.checkUserName();
        assertTrue(result);
    }

    // test: username without underscore or too long should return false
    @Test
    public void testCheckUsernameFail() {
        boolean result = false;
        result = badUsername.checkUserName();
        assertFalse(result);
    }

    // test: valid password should pass the complexity check
    @Test
    public void testCheckPasswordComplexity() {
        boolean result = false;
        result = validLogin.checkPasswordComplexity();
        assertTrue(result);
    }

    // test: weak password should fail the complexity check
    @Test
    public void testCheckPasswordFail() {
        boolean result = false;
        result = badPassword.checkPasswordComplexity();
        assertFalse(result);
    }

    // test: valid +27 number should pass
    @Test
    public void testCheckCellPhoneNumber() {
        boolean result = false;
        result = validLogin.checkCellPhoneNumber();
        assertTrue(result);
    }

    // test: number without +27 should fail
    @Test
    public void testCheckCellphoneNumberFail() {
        boolean result = false;
        result = badPhone.checkCellPhoneNumber();
        assertFalse(result);
    }

    // test: valid registration should return a success message
    @Test
    public void testRegisterUser() {
        String result = "";
        result = validLogin.registerUser();
        assertTrue(result.contains("successfully"));
    }

    // test: bad username registration should return correct error
    @Test
    public void testRegisterUserBadUsername() {
        String result = "";
        result = badUsername.registerUser();
        assertTrue(result.contains("Username is not correctly formatted"));
    }

    // test: correct username and password should return true
    @Test
    public void testLoginUserSuccess() {
        boolean result = false;
        result = validLogin.loginUser("jd_01", "Pass@123");
        assertTrue(result);
    }

    // test: wrong password should return false
    @Test
    public void testLoginUserFailure() {
        boolean result = false;
        result = validLogin.loginUser("jd_01", "wrongpassword");
        assertFalse(result);
    }

    // test: successful login should return welcome message
    @Test
    public void testReturnLoginStatusSuccess() {
        String result = "";
        result = validLogin.returnLoginStatus("jd_01", "Pass@123");
        assertTrue(result.contains("it is great to see you again"));
    }

    // test: failed login should return error message
    @Test
    public void testReturnLoginStatusFailure() {
        String result = "";
        result = validLogin.returnLoginStatus("jd_01", "wrongpassword");
        assertEquals("Username or password incorrect, please try again.", result);
    }

    // PART 2 TESTS - Message class

    //check recipient cell number format
    public String checkRecipientCell(String recipient) {
        String result = "";
        if (recipient.startsWith("+")) {
            result = "Cell phone number successfully captured.";
        } else {
            result = "Cell phone number is incorrectly formatted or does not contain " +
                     "an International code. Please correct the number and try again.";
        }
        return result;
    }

    //check message length
    public String checkMessageLength(String text) {
        String result = "";
        if (text.length() <= 250) {
            result = "Message ready to send.";
        } else {
            int over = text.length() - 250;
            result = "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
        return result;
    }

    //build a message hash
    public String createMessageHash(String messageID, String text, int messageNumber) {
        String idPart    = messageID.substring(0, 2);
        String[] words   = text.trim().split(" ");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];

        String cleanLastWord = "";
        for (int i = 0; i < lastWord.length(); i++) {
            char c = lastWord.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                cleanLastWord = cleanLastWord + c;
            }
        }

        String hash = idPart + ":" + messageNumber + ":" + firstWord + cleanLastWord;
        return hash.toUpperCase();
    }

    // test: message under 250 characters should be ready to send
    @Test
    public void testMessageLengthSuccess() {
        String result = "";
        result = checkMessageLength(text1);
        assertEquals("Message ready to send.", result);
    }

    // test: message over 250 characters should fail
    @Test
    public void testMessageLengthFailure() {
        String longText = "";
        for (int i = 0; i < 260; i++) {
            longText = longText + "a";
        }
        String result = "";
        result = checkMessageLength(longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }

    // test: recipient starting with + should pass
    @Test
    public void testRecipientCellSuccess() {
        String result = "";
        result = checkRecipientCell(recipient1);
        assertEquals("Cell phone number successfully captured.", result);
    }

    // test: recipient without + should fail
    @Test
    public void testRecipientCellFailure() {
        String result = "";
        result = checkRecipientCell(recipient2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain " +
                     "an International code. Please correct the number and try again.", result);
    }

    // test: hash ends with correct pattern
    @Test
    public void testMessageHashMessage1() {
        String fakeID = "0012345678";
        String hash   = "";
        hash = createMessageHash(fakeID, text1, msgNum1);
        assertTrue(hash.endsWith(":0:HITONIGHT"));
    }

    // test: sentMessage choice 1 returns sent confirmation
    @Test
    public void testSentMessageSend() {
        Message msg = new Message(recipient1, text1, msgNum1);
        String result = "";
        result = msg.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    // test: sentMessage choice 2 returns disregard message
    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message(recipient1, text1, msgNum1);
        String result = "";
        result = msg.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    // test: sentMessage choice 3 returns stored confirmation
    @Test
    public void testSentMessageStore() {
        Message msg = new Message(recipient1, text1, msgNum1);
        String result = "";
        result = msg.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    // test: message ID should never be more than 10 characters
    @Test
    public void testCheckMessageID() {
        Message msg = new Message(recipient1, text1, msgNum1);
        boolean result = false;
        result = msg.checkMessageID();
        assertTrue(result);
    }

    // PART 3 TESTS

    // test: sentMessages array should contain message 1 and message 4
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        boolean foundMsg1 = false;
        boolean foundMsg4 = false;

        for (int i = 0; i < Message.sentCount; i++) {
            if (Message.sentMessages[i].equals("Did you get the cake?")) {
                foundMsg1 = true;
            }
            if (Message.sentMessages[i].equals("It is dinner time !")) {
                foundMsg4 = true;
            }
        }

        assertTrue(foundMsg1);
        assertTrue(foundMsg4);
        assertEquals(2, Message.sentCount);
    }

    // test: longest message should be message 2 from the brief
    @Test
    public void testDisplayLongestMessage() {
        String result = "";
        result = Message.displayLongestMessage();
        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."));
    }

    // test: searching by message ID should return the correct message
    @Test
    public void testSearchByMessageID() {
        String id4 = "";
        id4 = Message.allMessages[3].getMessageID();
        String result = "";
        result = Message.searchByMessageID(id4);
        assertTrue(result.contains("It is dinner time !"));
    }

    // test: searching by recipient should return message 2 and message 5
    @Test
    public void testSearchByRecipient() {
        String result = "";
        result = Message.searchByRecipient("+27838884567");
        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    // test: deleting message 2 by hash should confirm deletion
    @Test
    public void testDeleteByHash() {
        String hashToDelete = "";
        hashToDelete = Message.allMessages[1].getMessageHash();

        String result = "";
        result = Message.deleteByHash(hashToDelete);

        assertTrue(result.contains("successfully deleted"));

        // check the message is no longer in storedMessages
        boolean stillFound = false;
        for (int i = 0; i < Message.storedCount; i++) {
            if (Message.storedMessages[i].equals(
                    "Where are you? You are late! I have asked you to be on time.")) {
                stillFound = true;
            }
        }
        assertFalse(stillFound);
    }

    // test: display report should show hash, recipient and message for all messages
    @Test
    public void testDisplayReport() {
        String report = "";
        report = Message.displayReport();

        assertNotNull(report);
        assertFalse(report.isEmpty());
        assertTrue(report.contains("Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));
        assertTrue(report.contains("Did you get the cake?"));
    }

}
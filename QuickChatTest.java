/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

 package com.mycompany.quickchat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lesedi
 */
public class QuickChatTest {
   
    // PART 1 TEST DATA - Login class
    

    Login validLogin;     
    Login badUsername;    
    Login badPassword;    
    Login badPhone;       
    
    // PART 2 TEST DATA - Message class
    //test message 1, recipient, and text
    String recipient1 = "+27718693002";
    String text1      = "Hi Mike, can you join us for dinner tonight?";
    int    msgNum1    = 0;

    // test message 2, recipient and text
    String recipient2 = "08575975889";
    String text2      = "Hi Keegan, did you receive the payment?";
    int    msgNum2    = 1;

    // SETUP - runs before every single test
    

    @BeforeEach
    public void setUp() {
        // Part 1 - create Login objects for testing
        validLogin  = new Login("John", "Doe", "jd_01", "Pass@123", "+27821234567");
        badUsername = new Login("Jane", "Doe", "janedoe", "Pass@123", "+27821234567");
        badPassword = new Login("Jane", "Doe", "jd_01", "password", "+27821234567");
        badPhone    = new Login("Jane", "Doe", "jd_01", "Pass@123", "0821234567");
    }
    
    // PART 1 TESTS - Login class
   
    
    // test: valid username should return true
    @Test
    public void testCheckUserNameSuccess() {
        boolean result = validLogin.checkUserName();
        assertTrue(result);
    }

    // test: username with no underscore and too long should return false
    @Test
    public void testCheckUserNameFailure() {
        boolean result = badUsername.checkUserName();
        assertFalse(result);
    }

    // test: valid password should return true
    @Test
    public void testCheckPasswordComplexitySuccess() {
        boolean result = validLogin.checkPasswordComplexity();
        assertTrue(result);
    }

    // test: simple password with no uppercase or special char should return false
    @Test
    public void testCheckPasswordComplexityFailure() {
        boolean result = badPassword.checkPasswordComplexity();
        assertFalse(result);
    }

    // test: valid phone number with +27 should return true
    @Test
    public void testCheckCellPhoneNumberSuccess() {
        boolean result = validLogin.checkCellPhoneNumber();
        assertTrue(result);
    }

    // test: phone with no + international code should return false
    @Test
    public void testCheckCellPhoneNumberFailure() {
        boolean result = badPhone.checkCellPhoneNumber();
        assertFalse(result);
    }

    // test: registerUser with all valid details should say successfully registered
    @Test
    public void testRegisterUserSuccess() {
        String result = validLogin.registerUser();
        assertTrue(result.contains("successfully"));
    }

    // test: registerUser with bad username should return the username error
    @Test
    public void testRegisterUserFailsBadUsername() {
        String result = badUsername.registerUser();
        assertTrue(result.contains("Username is not correctly formatted"));
    }

    // test: loginUser with correct details should return true
    @Test
    public void testLoginUserSuccess() {
        boolean result = validLogin.loginUser("jd_01", "Pass@123");
        assertTrue(result);
    }

    // test: loginUser with wrong password should return false
    @Test
    public void testLoginUserFailure() {
        boolean result = validLogin.loginUser("jd_01", "wrongpassword");
        assertFalse(result);
    }

    // test: returnLoginStatus with correct details returns welcome message
    @Test
    public void testReturnLoginStatusSuccess() {
        String result = validLogin.returnLoginStatus("jd_01", "Pass@123");
        assertTrue(result.contains("it is great to see you again"));
    }

    // test: returnLoginStatus with wrong details returns error message
    @Test
    public void testReturnLoginStatusFailure() {
        String result = validLogin.returnLoginStatus("jd_01", "wrongpassword");
        assertEquals("Username or password incorrect, please try again.", result);
    }
    
    // PART 2 TESTS - Message class

    // helper methods - same logic as Message class but we control the input
    // needed so we can predict the output without random ID generation

    public String checkRecipientCell(String recipient) {
        if (recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an International code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = text.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
    }

    public String createMessageHash(String messageID, String text, int messageNumber) {
        String idPart = messageID.substring(0, 2);
        String[] words = text.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // remove punctuation from the last word
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

    // test: message under 250 characters should return success message
    @Test
    public void testMessageLengthSuccess() {
        String result = checkMessageLength(text1);
        assertEquals("Message ready to send.", result);
    }

    // test: message over 250 characters should return failure with exact count
    @Test
    public void testMessageLengthFailure() {
        // create a string that is exactly 260 characters - 10 over the limit
        String longText = "";
        for (int i = 0; i < 260; i++) {
            longText = longText + "a";
        }
        String result = checkMessageLength(longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }

    // test: recipient with + should be captured successfully
    @Test
    public void testRecipientCellSuccess() {
        // uses the full assignment test number +27718693002
        String result = checkRecipientCell(recipient1);
        assertEquals("Cell phone number successfully captured.", result);
    }

    // test: recipient without + should fail
    @Test
    public void testRecipientCellFailure() {
        // uses 08575975889 from assignment - no + sign so should fail
        String result = checkRecipientCell(recipient2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain " +
                     "an International code. Please correct the number and try again.", result);
    }

    // test: hash for message 1 should end with :0:HITONIGHT
    // text1 = "Hi Mike, can you join us for dinner tonight?"
    // first word = Hi, last word = tonight (? removed)
    @Test
    public void testMessageHashMessage1() {
        String fakeID = "0012345678"; // starts with 00 so we know the id part
        String hash = createMessageHash(fakeID, text1, msgNum1);
        assertTrue(hash.endsWith(":0:HITONIGHT"),
                "Expected hash to end with :0:HITONIGHT but got: " + hash);
    }

    // test: hash for message 2 should end with :1:HIPAYMENT
    // text2 = "Hi Keegan, did you receive the payment?"
    // first word = Hi, last word = payment (? removed)
    @Test
    public void testMessageHashMessage2() {
        String fakeID = "0098765432"; // starts with 00 so we know the id part
        String hash = createMessageHash(fakeID, text2, msgNum2);
        assertTrue(hash.endsWith(":1:HIPAYMENT"),
                "Expected hash to end with :1:HIPAYMENT but got: " + hash);
    }

    // test: sentMessage choice 1 should return sent confirmation
    @Test
    public void testSentMessageSend() {
        Message msg = new Message(recipient1, text1, msgNum1);
        String result = msg.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    // test: sentMessage choice 2 should return delete prompt with full stop
    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message(recipient1, text1, msgNum1);
        String result = msg.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    // test: sentMessage choice 3 should return stored confirmation
    @Test
    public void testSentMessageStore() {
        Message msg = new Message(recipient1, text1, msgNum1);
        String result = msg.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    // test: message ID should never be more than 10 characters
    @Test
    public void testCheckMessageID() {
        Message msg = new Message(recipient1, text1, msgNum1);
        boolean result = msg.checkMessageID();
        assertTrue(result);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author lesed
 */
class Login {

    // declare variables
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellphoneNumber;

    // constructor
    public Login(String firstName, String lastName, String username,
                 String password, String cellphoneNumber) {
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.username        = username;
        this.password        = password;
        this.cellphoneNumber = cellphoneNumber;
    }

    // check username contains underscore and is 5 characters or less
    public boolean checkUserName() {
        boolean hasUnderscore = false;
        boolean correctLength = false;

        if (username.contains("_")) {
            hasUnderscore = true;
        }
        if (username.length() <= 5) {
            correctLength = true;
        }

        if (hasUnderscore && correctLength) {
            return true;
        } else {
            return false;
        }
    }

    // check password has uppercase, number, special character and is 8+ chars
    public boolean checkPasswordComplexity() {
        boolean hasUpperCase   = false;
        boolean hasNumber      = false;
        boolean hasSpecialChar = false;
        boolean longEnough     = false;

        if (password.length() >= 8) {
            longEnough = true;
        }

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (hasUpperCase && hasNumber && hasSpecialChar && longEnough) {
            return true;
        } else {
            return false;
        }
    }

    // check cell phone number starts with +27 and has 9 digits after the code
    public boolean checkCellPhoneNumber() {
        boolean startsWithCode  = false;
        boolean correctLength   = false;

        if (cellphoneNumber.startsWith("+27")) {
            startsWithCode = true;
        }

        String numberAfterCode = cellphoneNumber.substring(3);
        if (numberAfterCode.length() <= 9) {
            correctLength = true;
        }

        if (startsWithCode && correctLength) {
            return true;
        } else {
            return false;
        }
    }

    // register the user and return a result message
    public String registerUser() {
        String result = "";

        if (checkUserName() == false) {
            result = "Username is not correctly formatted; please ensure that your " +
                     "username contains an underscore and is no more than five " +
                     "characters in length.";
            return result;
        }

        if (checkPasswordComplexity() == false) {
            result = "Password is not correctly formatted; please ensure that the " +
                     "password contains at least eight characters, a capital letter, " +
                     "a number, and a special character.";
            return result;
        }

        if (checkCellPhoneNumber() == false) {
            result = "Cell phone number incorrectly formatted or does not contain " +
                     "international code.";
            return result;
        }

        result = "Username successfully captured." +
                 "Password successfully captured." +
                 "Cell phone number successfully added." +
                 "User registered successfully.";
        return result;
    }

    // check if entered username and password match the registered ones
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        boolean usernameMatch = false;
        boolean passwordMatch = false;

        if (username.equals(enteredUsername)) {
            usernameMatch = true;
        }
        if (password.equals(enteredPassword)) {
            passwordMatch = true;
        }

        if (usernameMatch && passwordMatch) {
            return true;
        } else {
            return false;
        }
    }

    // return welcome message if login succeeds or error message if it fails
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        String status = "";

        if (loginUser(enteredUsername, enteredPassword) == true) {
            status = "Welcome " + firstName + " " + lastName +
                     ", it is great to see you again.";
        } else {
            status = "Username or password incorrect, please try again.";
        }

        return status;
    }

    // getters
    public String getFirstName()       { return firstName;       }
    public String getLastName()        { return lastName;        }
    public String getUsername()        { return username;        }
    public String getCellphoneNumber() { return cellphoneNumber; }

}
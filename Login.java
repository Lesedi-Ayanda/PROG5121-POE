/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author lesedi
 */
class Login {

   // variable declarations
    private String username;
    private String password;
    private String cellphoneNumber;
    private String firstName;
    private String lastName;

    // constructor
    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellPhoneNumber;
    }

    // check username contains underscore and is 5 characters or less
    public boolean checkUserName() {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        }
        return false;
    }

    // check password has uppercase, number, special character, and is 8+ chars
    public boolean checkPasswordComplexity() {
        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
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

        return hasUpperCase && hasNumber && hasSpecialChar && password.length() >= 8;
    }

    // check cell phone number starts with +27 and has 9 digits after the code
    public boolean checkCellPhoneNumber() {
        if (cellphoneNumber == null) {
            return false;
        }
        if (cellphoneNumber.startsWith("+27")) {
            String numberAfterCode = cellphoneNumber.substring(3);
            return numberAfterCode.length() <= 9;
        }
        return false;
    }

    // register the user - only succeeds if all three checks pass
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your " +
                   "username contains an underscore and is no more than five " +
                   "characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the " +
                   "password contains at least eight characters, a capital letter, " +
                   "a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain " +
                   "international code.";
        }
        return "Username successfully captured." +
               "Password successfully captured." +
               "Cell phone number successfully added." +
               "User registered successfully.";
    }

    // check if entered username and password match the registered ones
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    // return welcome message if login succeeds or error message if it fails
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + " " + lastName +
                   ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // getters
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public String getUsername()  { return username;  }
}

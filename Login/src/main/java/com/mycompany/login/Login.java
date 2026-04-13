/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.login;

/**
 *
 * @author lesed
 */
public class Login{
    
    @SuppressWarnings("FieldMayBeFinal")
    private  User registeredUser;          
    private String registrationMessage;
    private String loginStatus;

    public Login() {
        this.registeredUser = null;
        this.registrationMessage = "";
        this.loginStatus = "";
    }

    //Check if username contains an underscore and is no more than 5 characters long
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    //Check password accuracy
    public boolean checkPasswordAccuracy(String password) {
        if (password == null) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasCapitalLetter = true;
            else if (Character.isDigit(c))    
                hasNumber = true;
            else if (!Character.isLetterOrDigit(c))
                hasSpecialCharacter = true;
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    
    public boolean isOnlyDigits(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) 
                return false;
        }
        return true;
    }

    //Check if cellphone number starts with +27
    public boolean checkCellphoneNumber(String cellphoneNumber) {
        if (cellphoneNumber == null) {
            return false;
        }
        if (cellphoneNumber.startsWith("+27")) {
            String numberAfterCode = cellphoneNumber.substring(3);
            return numberAfterCode.length() == 9 && isOnlyDigits(numberAfterCode);
        }
        return false;
    }

    //Register the user
    public String registerUser(String firstName, String lastName,
                               String userName, String password,
                               String cellphoneNumber) {

        boolean isUsernameValid = checkUserName(userName);   
        boolean isPasswordValid = checkPasswordAccuracy(password); 
        boolean isPhoneValid    = checkCellphoneNumber(cellphoneNumber);

        StringBuilder message = new StringBuilder();

        if (!isUsernameValid) {
            message.append("Username is not correctly formatted; please ensure that your username ")
                   .append("contains an underscore and is no more than five characters long. ");
        } else {
            message.append("Username successfully captured. ");
        }

        if (!isPasswordValid) {                              
            message.append("Password is not correctly formatted; please ensure that the password ")
                   .append("contains at least eight characters, a capital letter, a number, ")
                   .append("and a special character. ");
        } else {
            message.append("Password successfully captured. ");
        }

        if (!isPhoneValid) {
            message.append("Cell phone number incorrectly formatted or does not contain international code.");
        } else {
            message.append("Cell phone number successfully added.");
        }

        //If all pass then register the user
        if (isUsernameValid && isPasswordValid && isPhoneValid) {
            this.registrationMessage = message.toString().trim();
            return this.registrationMessage;
        } else {
            this.registrationMessage = message.toString().trim();
            return null; 
        }
    }

    //Match verified credentials to registered user
    public boolean loginUser(String username, String password) {
        if (registeredUser == null) {
            return false;
        }
        return registeredUser.getUsername().equals(username)
            && registeredUser.getPassword().equals(password);
    }

    //Return a login status message
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password) && registeredUser != null) {
            loginStatus = "Welcome " + registeredUser.getUsername()
                        + ", it is great to see you again.";
            return loginStatus;
        } else {
            loginStatus = "Username or password incorrect, please try again.";
            return loginStatus;
        }
    }

   //Get user,registration message,login status from last attempt
    public User getRegisteredUser() {
        return registeredUser;
    }

    
    public String getRegistrationMessage() {
        return registrationMessage;
    }

    public String getLoginStatus() {
        return loginStatus;
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    


























    
    
    
    
    
    
    
    
    
    
    


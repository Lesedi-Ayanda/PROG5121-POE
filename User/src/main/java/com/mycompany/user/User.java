/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.user;

/**
 *
 * @author lesed
 */
public class User {

    //Declarations
     private String firstName;
     private String lastName;
     private String username;
     private String password;
     private String cellphoneNumber;
     
     //Constructor
     public User(String firstName,String lastName,String username,String password, String cellphoneNumber){
     
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.cellphoneNumber = cellphoneNumber;
    
     }
    //Getters

    /**
     *
     * @return
     */
     public String getFirstName(){
       return firstName;
    }
    public String getLastName(){
       return lastName;
    }
    public String getUserName(){
       return username;
    }
    public String getPassword(){
       return password;
    }
    public String getCellphoneNumber(){
       return cellphoneNumber;
    }
    
    
     }

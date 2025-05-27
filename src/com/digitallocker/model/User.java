package com.digitallocker.model;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

     // Getter for username
    public String getUsername() {
        return username;

    }

    // Getters for password
    public String getPassword() {
        return password;

    }


}

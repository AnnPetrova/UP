package main.java.models;

import javax.json.JsonObject;

public class Account{

    private String username;
    private String password;

    public Account(String password, String username) {
        this.password = password;
        this.username = username;
    }
    public Account(JsonObject object) {
        this.username = object.getString("login");
        this.password = object.getString("pass");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
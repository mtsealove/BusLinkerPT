package com.mtsealove.github.buslinkerpt.Restful.Request;

public class Login {
    String ID, Password, Token;
    int Cat;

    public Login(String ID, String password, String token) {
        this.ID = ID;
        Password = password;
        Cat=5;
        this.Token = token;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getCat() {
        return Cat;
    }

    public void setCat(int cat) {
        Cat = cat;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}

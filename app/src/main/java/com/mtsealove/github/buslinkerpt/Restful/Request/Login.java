package com.mtsealove.github.buslinkerpt.Restful.Request;

public class Login {
    String ID, Password;
    int Cat;

    public Login(String ID, String password) {
        this.ID = ID;
        Password = password;
        Cat=5;
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
}

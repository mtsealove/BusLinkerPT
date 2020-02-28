package com.mtsealove.github.buslinkerpt.Restful.Request;

public class Commute {
    String ID, Code;

    public Commute(String ID, String code) {
        this.ID = ID;
        Code = code;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}

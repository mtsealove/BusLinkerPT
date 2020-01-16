package com.mtsealove.github.buslinkerpt.Restful.Request;

public class Ask {
    String ID, Message;

    public Ask(String ID, String message) {
        this.ID = ID;
        Message = message;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

package com.mtsealove.github.buslinkerpt.Design;

public class AlertView {
    int ID;
    String Title, Message, Time;
    boolean Status;
    String Action;

    public AlertView(int ID, String title, String message, String time, boolean status, String action) {
        this.ID = ID;
        Title = title;
        Message = message;
        Time = time;
        Status = status;
        Action = action;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }
}

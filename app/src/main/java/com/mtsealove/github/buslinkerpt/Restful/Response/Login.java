package com.mtsealove.github.buslinkerpt.Restful.Response;

public class Login {
    String Name, ID, ProfilePath;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProfilePath() {
        return ProfilePath;
    }

    public void setProfilePath(String profilePath) {
        ProfilePath = profilePath;
    }

    @Override
    public String toString() {
        return "Login{" +
                "Name='" + Name + '\'' +
                ", ID='" + ID + '\'' +
                ", ProfilePath='" + ProfilePath + '\'' +
                '}';
    }
}

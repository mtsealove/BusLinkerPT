package com.mtsealove.github.buslinkerpt.Restful.Response;

public class Cnt {
    public int Owner, Logi;

    public Cnt(int owner, int logi) {
        Owner = owner;
        Logi = logi;
    }

    public int getOwner() {
        return Owner;
    }

    public void setOwner(int owner) {
        Owner = owner;
    }

    public int getLogi() {
        return Logi;
    }

    public void setLogi(int logi) {
        Logi = logi;
    }
}

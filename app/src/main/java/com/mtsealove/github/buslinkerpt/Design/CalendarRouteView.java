package com.mtsealove.github.buslinkerpt.Design;

public class CalendarRouteView {
    String Name, Addr, Time;

    public CalendarRouteView(String name, String addr, String time) {
        Name = name;
        Addr = addr;
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}

package com.mtsealove.github.buslinkerpt.Restful.Response;

public class Route {
    String Num, Locations, Name, PTID, Corp, PTName, LogiName, PTPhone;

    public Route(String num, String locations, String name, String PTID, String corp, String PTName, String logiName, String PTPhone) {
        Num = num;
        Locations = locations;
        Name = name;
        this.PTID = PTID;
        Corp = corp;
        this.PTName = PTName;
        LogiName = logiName;
        this.PTPhone = PTPhone;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getLocations() {
        return Locations;
    }

    public void setLocations(String locations) {
        Locations = locations;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPTID() {
        return PTID;
    }

    public void setPTID(String PTID) {
        this.PTID = PTID;
    }

    public String getCorp() {
        return Corp;
    }

    public void setCorp(String corp) {
        Corp = corp;
    }

    public String getPTName() {
        return PTName;
    }

    public void setPTName(String PTName) {
        this.PTName = PTName;
    }

    public String getLogiName() {
        return LogiName;
    }

    public void setLogiName(String logiName) {
        LogiName = logiName;
    }

    public String getPTPhone() {
        return PTPhone;
    }

    public void setPTPhone(String PTPhone) {
        this.PTPhone = PTPhone;
    }

    @Override
    public String toString() {
        return "RouteView{" +
                "Num='" + Num + '\'' +
                ", Locations='" + Locations + '\'' +
                ", Name='" + Name + '\'' +
                ", PTID='" + PTID + '\'' +
                ", Corp='" + Corp + '\'' +
                ", PTName='" + PTName + '\'' +
                ", LogiName='" + LogiName + '\'' +
                ", PTPhone='" + PTPhone + '\'' +
                '}';
    }
}

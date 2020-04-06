package com.mtsealove.github.buslinkerpt.Restful.Response;

public class Route {
    String Num, Locations, Name, Corp, LogiName, PTPhone, DriverPhone, DriverName;
    int RouteID;

    public Route(String num, String locations, String name, String corp, String logiName, String PTPhone, String driverPhone, String driverName, int routeID) {
        Num = num;
        Locations = locations;
        Name = name;
        Corp = corp;
        LogiName = logiName;
        this.PTPhone = PTPhone;
        DriverPhone = driverPhone;
        DriverName = driverName;
        RouteID = routeID;
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


    public String getCorp() {
        return Corp;
    }

    public void setCorp(String corp) {
        Corp = corp;
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

    public String getDriverPhone() {
        return DriverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        DriverPhone = driverPhone;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }

    @Override
    public String toString() {
        return "Route{" +
                "Num='" + Num + '\'' +
                ", Locations='" + Locations + '\'' +
                ", Name='" + Name + '\'' +
                ", Corp='" + Corp + '\'' +
                ", LogiName='" + LogiName + '\'' +
                ", PTPhone='" + PTPhone + '\'' +
                ", DriverPhone='" + DriverPhone + '\'' +
                ", DriverName='" + DriverName + '\'' +
                ", RouteID=" + RouteID +
                '}';
    }
}

package com.mtsealove.github.buslinkerpt.Restful.Response;

public class Timeline {
    int LocID, LocCat;
    String LocName, LocAddr, RcTime;
    double Lat, Lng, distance;

    public Timeline(int locID, String locName, String locAddr, String rcTime, double lat, double lng, int locCat) {
        LocID = locID;
        LocName = locName;
        LocAddr = locAddr;
        RcTime = rcTime;
        Lat = lat;
        Lng = lng;
        LocCat = locCat;
    }

    public int getLocCat() {
        return LocCat;
    }

    public void setLocCat(int locCat) {
        LocCat = locCat;
    }

    public int getLocID() {
        return LocID;
    }

    public void setLocID(int locID) {
        LocID = locID;
    }

    public String getLocName() {
        return LocName;
    }

    public void setLocName(String locName) {
        LocName = locName;
    }

    public String getLocAddr() {
        return LocAddr;
    }

    public void setLocAddr(String locAddr) {
        LocAddr = locAddr;
    }

    public String getRcTime() {
        return RcTime;
    }

    public void setRcTime(String rcTime) {
        RcTime = rcTime;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "LocID=" + LocID +
                ", LocName='" + LocName + '\'' +
                ", LocAddr='" + LocAddr + '\'' +
                ", RcTime='" + RcTime + '\'' +
                ", Lat=" + Lat +
                ", Lng=" + Lng +
                '}';
    }
}

package com.mtsealove.github.buslinkerpt.Restful.Response;

public class My {
    String RouteName, ProfilePath;
    int ItemCnt;

    public My(String routeName, String profilePath, int itemCnt) {
        RouteName = routeName;
        ProfilePath = profilePath;
        ItemCnt = itemCnt;
    }

    public String getRouteName() {
        return RouteName;
    }

    public void setRouteName(String routeName) {
        RouteName = routeName;
    }

    public int getItemCnt() {
        return ItemCnt;
    }

    public void setItemCnt(int itemCnt) {
        ItemCnt = itemCnt;
    }

    public String getProfilePath() {
        return ProfilePath;
    }

    public void setProfilePath(String profilePath) {
        ProfilePath = profilePath;
    }
}


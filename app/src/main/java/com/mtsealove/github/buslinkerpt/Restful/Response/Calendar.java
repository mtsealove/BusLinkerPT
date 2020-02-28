package com.mtsealove.github.buslinkerpt.Restful.Response;

public class Calendar {
    String RunDate;

    public Calendar(String runDate) {
        RunDate = runDate;
    }

    public String getRunDate() {
        return RunDate.substring(0, 10);
    }

    public void setRunDate(String runDate) {
        RunDate = runDate;
    }


    @Override
    public String toString() {
        return "Calendar{" +
                "RunDate='" + RunDate + '\'' +
                '}';
    }
}

package com.mtsealove.github.buslinkerpt.Restful.Response;

import java.util.List;

public class Route_Timeline {
    Route route;
    List<Timeline> timeline;

    public Route_Timeline(Route route, List<Timeline> timeline) {
        this.route = route;
        this.timeline = timeline;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Timeline> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<Timeline> timeline) {
        this.timeline = timeline;
    }

    @Override
    public String toString() {
        return "Route_Timeline{" +
                "route=" + route +
                ", timeline=" + timeline +
                '}';
    }
}

package com.mns.walker.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class WalkerDefinition implements Parcelable{

    private final String location;
    private final String title;
    private final String description;
    private final String length;
    private final List<WalkPoint> points;

    public WalkerDefinition(String location, String title, String description, String length, List<WalkPoint> points) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.length = length;
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class WalkPoint {

        private final double latitude;
        private final double longitude;
        private final float radius;
        private final String clue;
        private final String image;

        public WalkPoint(double latitude, double longitude, float radius, String clue, String image) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.clue = clue;
            this.image = image;
        }
    }
}
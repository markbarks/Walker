package com.mns.walker.game;

import java.util.List;

public class WalkerDefinition {

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

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLength() {
        return length;
    }

    public List<WalkPoint> getPoints() {
        return points;
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

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public float getRadius() {
            return radius;
        }

        public String getClue() {
            return clue;
        }

        public String getImage() {
            return image;
        }
    }
}

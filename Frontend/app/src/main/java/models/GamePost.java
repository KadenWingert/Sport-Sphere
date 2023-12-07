package models;

import com.mapbox.geojson.Point;

import java.util.Date;

public class GamePost {
    int maxPlayers;
    int minPlayers;
    String address;

    Point point;
    String sport;
    String postedBy;
    String playingOn;
    String firstName;
    String LastName;

    public GamePost(int maxPlayers, int minPlayers, String address, Point point, String sport, String postedBy, String playingOn, String firstName, String lastName) {
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.address = address;
        this.point = point;
        this.sport = sport;
        this.postedBy = postedBy;
        this.playingOn = playingOn;
        this.firstName = firstName;
        LastName = lastName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPlayingOn() {
        return playingOn;
    }

    public void setPlayingOn(String playingOn) {
        this.playingOn = playingOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}

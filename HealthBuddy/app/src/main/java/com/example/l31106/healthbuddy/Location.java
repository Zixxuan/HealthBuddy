package com.example.l31106.healthbuddy;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by L31106 on 9/7/2017.
 */

public class Location {
   private String group;
    private String username;
    private String location;
    private int time;
    private ArrayList fingerprint;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(ArrayList fingerprint) {
        this.fingerprint = fingerprint;
    }
}

package com.example.obstinatebrar.capturetheflag;

public class User {
    public String name;
    String team;
    public Double latitude;
    public Double longitude;
    boolean flagfound;

    public User()
    {

    }

    public User(Double lat, Double lon)
    {
        this.latitude = lat;
        this.longitude = lon;
    }
    public User(String name, String team)
    {
        this.name = name;
        this.team = team;
    }
    public User(String n, String team, Double lat, Double lon,boolean flagfound)
    {
        this.name = n;
        this.team = team;
        this.latitude = lat;
        this.longitude = lon;
        this.flagfound = flagfound;
    }

    public User(boolean flagfound)
    {
        this.flagfound = flagfound;
    }
}

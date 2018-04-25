package com.example.obstinatebrar.capturetheflag;

public class Flag {
    Double latitude;
    Double longitude;

    String flagStatus;

    public Flag(){

    }
    public Flag(Double latitude,Double longitude,String flagStatus){
        this.latitude=latitude;
        this.longitude=longitude;
        this.flagStatus=flagStatus;
    }
    public Double getLat() {
        return this.latitude;
    }
    public Double getLong() {
        return this.longitude;
    }
}

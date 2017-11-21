package com.example.ronan.assignment;

/**
 * Created by Ronan on 17/11/2017.
 *
 */

public class Event
{
    private int eventID;
    private String event;
    private String country;
    private String date;
    private String end_date;
    private String buyin;
    private String fee;
    private String address;

    public Event(String event, String country,String date,String end_date,String buyin,String fee)
    {

        this.event = event;
        this.country = country;
        this.date = date;
        this.end_date = end_date;
        this.buyin = buyin;
        this.fee = fee;
    }

    //getters
    public int getEventID(){return eventID;}

    public String getEvent() {
        return event;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getBuyin() {
        return buyin;
    }

    public String getFee() {
        return fee;
    }

    //setters
    public void setEventID(int id) {
        this.eventID = id;
    }

    public void setEvent(String event) {
        this.event = event;
    }



    public void setCountry(String country) {
        this.country = country;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setBuyin(String buyin) {
        this.buyin = buyin;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}

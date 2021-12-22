package com.lamzone.mareu.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Meeting {

    private String name;

    private String room;

    private int hours;

    private int minutes;

    private List<String> participants;

    private int color;

    private Date date;

    public Meeting(String name, String room, int hours, int minutes, List<String> participants, int color, Date date) {
        this.name = name;
        this.room = room;
        this.hours = hours;
        this.minutes = minutes;
        this.participants = participants;
        this.color = color;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

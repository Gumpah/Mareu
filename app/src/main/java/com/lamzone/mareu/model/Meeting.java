package com.lamzone.mareu.model;

import java.util.Arrays;
import java.util.List;

public class Meeting {

    private String name;

    private String room;

    private int hours;

    private int minutes;

    private List<String> participants;

    public Meeting(String name, String room, int hours, int minutes) {
        this.name = name;
        this.room = room;
        this.hours = hours;
        this.minutes = minutes;
        this.participants = Arrays.asList("Test","Test","Test","Test","Test","Test");
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

    public void addParticipant(String participant) {
        this.participants.add(participant);
    }

    public List<String> getParticipants() {
        return participants;
    }

}

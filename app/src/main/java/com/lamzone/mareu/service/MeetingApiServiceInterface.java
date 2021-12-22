package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.Date;
import java.util.List;

public interface MeetingApiServiceInterface {

    List<String> getRooms();

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);

    List<Meeting> getMeetingsDateFilter(Date date, List<Meeting> meetings);

    List<Meeting> getMeetingsRoomFilter(String room, List<Meeting> meetings);
}

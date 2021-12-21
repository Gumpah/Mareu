package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiServiceInterface {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);
}

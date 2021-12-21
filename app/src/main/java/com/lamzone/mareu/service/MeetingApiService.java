package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingApiService implements MeetingApiServiceInterface {

    private List<Meeting> meetings = new ArrayList<>();


    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);

    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}

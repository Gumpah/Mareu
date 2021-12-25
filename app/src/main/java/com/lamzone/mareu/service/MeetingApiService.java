package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeetingApiService implements MeetingApiServiceInterface {

    private List<Meeting> meetings = MeetingGenerator.generateMeetings();

    private List<String> rooms = MeetingGenerator.generateRooms();

    @Override
    public List<String> getRooms() {
        return rooms;
    }

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void resetMeetings() {
        meetings = MeetingGenerator.generateMeetings();
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);

    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public List<Meeting> getMeetingsDateFilter(Date date, List<Meeting> meetings) {
        List<Meeting> list_filtered = new ArrayList<>();

        Calendar cal_filter = Calendar.getInstance();
        cal_filter.setTime(date);
        for (int i = 0; i < meetings.size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(meetings.get(i).getDate());
            boolean sameDay = cal_filter.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR) &&
                    cal_filter.get(Calendar.YEAR) == cal.get(Calendar.YEAR);
            if (sameDay) list_filtered.add(meetings.get(i));
        }
        return list_filtered;
    }

    @Override
    public List<Meeting> getMeetingsRoomFilter(String room, List<Meeting> meetings) {
        List<Meeting> list_filtered = new ArrayList<>();
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getRoom().equals(room)) list_filtered.add(meetings.get(i));
        }
        return list_filtered;
    }
}

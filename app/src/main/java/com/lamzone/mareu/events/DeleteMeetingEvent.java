package com.lamzone.mareu.events;

import com.lamzone.mareu.model.Meeting;

public class DeleteMeetingEvent {

    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) { this.meeting = meeting; }
}

package com.lamzone.mareu;

import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiServiceInterface;
import com.lamzone.mareu.service.MeetingGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingServiceTest {

    private MeetingApiServiceInterface service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = MeetingGenerator.meetings;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
        assertFalse(expectedMeetings.isEmpty());
    }

    @Test
    public void getRoomsWithSuccess() {
        List<String> actualRooms = service.getRooms();
        List<String> expectedRooms = MeetingGenerator.rooms;
        assertThat(actualRooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
        assertFalse(expectedRooms.isEmpty());
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void getFilteredByDateMeetingsListWithSuccess() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 1, 5);
        Date goodDate = cal.getTime();
        cal.set(2022, 1, 10);
        Date badDate = cal.getTime();
        List<String> participants = Arrays.asList("hazel52@hotmail.com");
        Meeting meetingA = new Meeting("A", "1", 1, 0, participants, 1, goodDate);
        Meeting meetingB = new Meeting("A", "2", 1, 0, participants, 1, goodDate);
        Meeting meetingC = new Meeting("A", "3", 1, 0, participants, 1, badDate);
        List<Meeting> allMeetings = Arrays.asList(meetingA,meetingB,meetingC);
        List<Meeting> expectedMeetings = Arrays.asList(meetingA,meetingB);
        List<Meeting> actualMeetings = service.getMeetingsDateFilter(goodDate,allMeetings);
        assertThat(actualMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void getFilteredByRoomMeetingsListWithSuccess() {
        String room = "1";
        List<String> participants = Arrays.asList("hazel52@hotmail.com");
        Meeting meetingA = new Meeting("A", room, 1, 0, participants, 1, Calendar.getInstance().getTime());
        Meeting meetingB = new Meeting("A", room, 1, 0, participants, 1, Calendar.getInstance().getTime());
        Meeting meetingC = new Meeting("A", "3", 1, 0, participants, 1, Calendar.getInstance().getTime());
        List<Meeting> allMeetings = Arrays.asList(meetingA,meetingB,meetingC);
        List<Meeting> expectedMeetings = Arrays.asList(meetingA,meetingB);
        List<Meeting> actualMeetings = service.getMeetingsRoomFilter(room,allMeetings);
        assertThat(actualMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void getMeetingsAttributesWithSuccess() {
        String expectedName = "Réunion A";
        String expectedRoom = "5";
        int expectedHours = 14;
        int expectedMinutes = 45;
        List<String> expectedParticipants = Arrays.asList("hazel52@hotmail.com", "rogers.wisozk@gmail.com", "rafael.smitham@gmail.com");
        int expectedColor = 1;
        Date expectedDate = Calendar.getInstance().getTime();
        Meeting meeting = new Meeting(expectedName,expectedRoom,expectedHours,expectedMinutes,expectedParticipants,expectedColor,expectedDate);
        service.createMeeting(meeting);
        assertEquals(expectedName, meeting.getName());
        assertEquals(expectedRoom, meeting.getRoom());
        assertEquals(expectedHours, meeting.getHours());
        assertEquals(expectedMinutes, meeting.getMinutes());
        assertEquals(expectedName, meeting.getName());
        assertThat(expectedParticipants, IsIterableContainingInAnyOrder.containsInAnyOrder(meeting.getParticipants().toArray()));
        assertEquals(expectedColor, meeting.getColor());
        assertEquals(expectedDate, meeting.getDate());
    }
}
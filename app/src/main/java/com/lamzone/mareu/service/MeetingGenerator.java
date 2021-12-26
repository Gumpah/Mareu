package com.lamzone.mareu.service;

import androidx.core.graphics.ColorUtils;

import com.lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public abstract class MeetingGenerator {

    static List<String> maillist = Arrays.asList("kulas.silas@sipes.org",
            "hazel52@hotmail.com",
            "rogers.wisozk@gmail.com",
            "rafael.smitham@gmail.com",
            "mmarquardt@hotmail.com",
            "koelpin.cordelia@langosh.info",
            "price.granville@gmail.com",
            "hodkiewicz.ransom@gmail.com",
            "cquigley@yahoo.com",
            "jeramie51@hotmail.com",
            "qwaelchi@gmail.com",
            "smith.frederik@yahoo.com",
            "lwilderman@gmail.com",
            "joconnell@hotmail.com");

    public static List<Meeting> meetings = Arrays.asList(
            new Meeting("Réunion A", "3", 15, 30, maillist, -5261361, Calendar.getInstance().getTime()),
            new Meeting("Réunion B", "4", 10, 0, maillist, -5255233, Calendar.getInstance().getTime()),
            new Meeting("Réunion C", "6", 16, 0, maillist, -5263409, Calendar.getInstance().getTime()),
            new Meeting("Réunion D", "2", 14, 0, maillist, -5259313, Calendar.getInstance().getTime()),
            new Meeting("Réunion E", "3", 9, 30, maillist, -5255241, Calendar.getInstance().getTime()),
            new Meeting("Réunion F", "1", 15, 30, maillist, -5261361, Calendar.getInstance().getTime()),
            new Meeting("Réunion G", "5", 11, 0, maillist, -5255225, Calendar.getInstance().getTime()),
            new Meeting("Réunion H", "3", 11, 30, maillist, -5255225, Calendar.getInstance().getTime()),
            new Meeting("Réunion I", "4", 10, 0, maillist, -5255233, Calendar.getInstance().getTime()),
            new Meeting("Réunion J", "6", 13, 30, maillist, -5257265, Calendar.getInstance().getTime())
    );

    public static List<String> rooms = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    static List<String> generateRooms() {
        return new ArrayList<>(rooms);
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(meetings);
    }

}

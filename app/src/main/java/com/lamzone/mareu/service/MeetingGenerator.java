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
            "koelpin.cordelia@langosh.info");

    public static List<Meeting> meetings = Arrays.asList(
            new Meeting("Réunion A", "3", 15, 30, maillist, ColorUtils.HSLToColor(new float[]{225,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion B", "4", 10, 0, maillist, ColorUtils.HSLToColor(new float[]{150,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion C", "6", 16, 0, maillist, ColorUtils.HSLToColor(new float[]{240,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion D", "2", 14, 0, maillist, ColorUtils.HSLToColor(new float[]{210,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion E", "3", 9, 30, maillist, ColorUtils.HSLToColor(new float[]{135,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion F", "1", 15, 30, maillist, ColorUtils.HSLToColor(new float[]{225,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion G", "5", 11, 0, maillist, ColorUtils.HSLToColor(new float[]{165,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion H", "3", 11, 30, maillist, ColorUtils.HSLToColor(new float[]{165,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion I", "4", 10, 0, maillist, ColorUtils.HSLToColor(new float[]{150,0.25f,0.75f}), Calendar.getInstance().getTime()),
            new Meeting("Réunion J", "6", 13, 30, maillist, ColorUtils.HSLToColor(new float[]{195,0.25f,0.75f}), Calendar.getInstance().getTime())
    );

    public static List<String> rooms = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    static List<String> generateRooms() {
        return new ArrayList<>(rooms);
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(meetings);
    }

}

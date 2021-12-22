package com.lamzone.mareu;

import android.content.Context;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.lamzone.mareu.service.MeetingApiServiceInterface;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsInstrumentedTest {

    private static int ITEMS_COUNT = 10;
    private MeetingApiServiceInterface service;

    @Test
    public void meetingsList_shouldNotBeEmpty() {

    }

    @Test
    public void meetingsList_deleteAction_shouldRemoveItem() {

    }

    @Test
    public void meetingCreation_addParticipant_shouldAddItem() {

    }


}
package com.lamzone.mareu;

import android.app.Instrumentation;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiServiceInterface;
import com.lamzone.mareu.utils.ClickViewAction;
import com.lamzone.mareu.utils.RecyclerViewItemCountAssertion;
import com.lamzone.mareu.view.AddMeetingActivity;
import com.lamzone.mareu.view.ListMeetingActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.lamzone.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class MeetingsInstrumentedTest {

    private static int ITEMS_COUNT = 10;


    private MeetingApiServiceInterface service;

    @Rule
    public ActivityScenarioRule<ListMeetingActivity> rule = new ActivityScenarioRule<>(ListMeetingActivity.class);

    @Rule
    public ActivityScenarioRule<AddMeetingActivity> rule2 = new ActivityScenarioRule<>(AddMeetingActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<ListMeetingActivity> scenario = rule.getScenario();
        ActivityScenario<AddMeetingActivity> scenario2 = rule2.getScenario();
        assertThat(scenario, notNullValue());
        assertThat(scenario2, notNullValue());
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void meetingsList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.show_meetings_recyclerView)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.show_meetings_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ClickViewAction.clickChildViewWithId(R.id.delete_meeting_btn)));
        // Then : the number of element is 11
        onView(withId(R.id.show_meetings_recyclerView)).check(withItemCount(ITEMS_COUNT-1));
    }


    @Test
    public void meetingCreation_clickToCreateMeeting_shouldAddItemToMeetingsList() {
        int itemsCount = RecyclerViewItemCountAssertion.getCountFromRecyclerView(R.id.show_meetings_recyclerView);
        onView(withId(R.id.show_meetings_recyclerView)).check(withItemCount(itemsCount));
        onView(withId(R.id.new_meeting_btn)).perform(click());
        onView(withId(R.id.add_meeting_constraintLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.name_textInput)).perform(typeText("test"));
        onView(withId(R.id.choose_room_autoCompleteTextView)).perform(click());
        onView(withText("1"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.add_participant_textInput)).perform(typeText("test"));
        onView(withId(R.id.date_picker_textButton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_participant_btn)).perform(click());
        pressBack();
        onView(withId(R.id.create_meeting_btn)).perform(click());
        onView(withId(R.id.list_meeting_constraintLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.show_meetings_recyclerView)).check(withItemCount(itemsCount+1));

    }

    @Test
    public void meetingsList_filterByDate_itemShouldHaveTheDate() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_date)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        int itemsCount = RecyclerViewItemCountAssertion.getCountFromRecyclerView(R.id.show_meetings_recyclerView);
        onView(withId(R.id.new_meeting_btn)).perform(click());
        onView(withId(R.id.add_meeting_constraintLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.name_textInput)).perform(typeText("test"));
        onView(withId(R.id.choose_room_autoCompleteTextView)).perform(click());
        onView(withText("1"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.add_participant_textInput)).perform(typeText("test"));
        onView(withId(R.id.date_picker_textButton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_participant_btn)).perform(click());
        pressBack();
        onView(withId(R.id.create_meeting_btn)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_date)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.show_meetings_recyclerView)).check(withItemCount(itemsCount+1));
    }

    @Test
    public void meetingsList_filterByRoom_itemShouldHaveTheRoom() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_room)).perform(click());
        onView(withText("1")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        int itemsCount = RecyclerViewItemCountAssertion.getCountFromRecyclerView(R.id.show_meetings_recyclerView);
        onView(withId(R.id.new_meeting_btn)).perform(click());
        onView(withId(R.id.add_meeting_constraintLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.name_textInput)).perform(typeText("test"));
        onView(withId(R.id.choose_room_autoCompleteTextView)).perform(click());
        onView(withText("1"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.add_participant_textInput)).perform(typeText("test"));
        onView(withId(R.id.date_picker_textButton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_participant_btn)).perform(click());
        pressBack();
        onView(withId(R.id.create_meeting_btn)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_room)).perform(click());
        onView(withText("1")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.show_meetings_recyclerView)).check(withItemCount(itemsCount+1));
    }
}
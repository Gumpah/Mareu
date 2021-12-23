package com.lamzone.mareu;

import android.app.DatePickerDialog;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.service.MeetingApiServiceInterface;
import com.lamzone.mareu.utils.ClickViewAction;
import com.lamzone.mareu.view.AddMeetingActivity;
import com.lamzone.mareu.view.ListMeetingActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
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
    public void meetingsList_shouldNotBeEmpty() {
        onView(withId(R.id.recyclerview))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void meetingsList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ClickViewAction.clickChildViewWithId(R.id.imagebutton_deletebutton)));
        // Then : the number of element is 11
        onView(withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void meetingCreation_addParticipant_shouldAddItem() {
        onView(withId(R.id.Button_newmeeting)).perform(click());
        onView(withId(R.id.recyclerview_participant)).check(withItemCount(0));
        onView(withId(R.id.textinput_mail)).perform(typeText("test"));
        onView(withId(R.id.button_addparticipant)).perform(click());
        onView(withId(R.id.recyclerview_participant)).check(matches(hasChildCount(1)));
    }

    @Test
    public void meetingCreation_deleteParticipant_shouldRemoveItem() {
        onView(withId(R.id.Button_newmeeting)).perform(click());
        onView(withId(R.id.recyclerview_participant)).check(withItemCount(0));
        onView(withId(R.id.textinput_mail)).perform(typeText("test"));
        onView(withId(R.id.button_addparticipant)).perform(click());
        onView(withId(R.id.recyclerview_participant)).check(matches(hasChildCount(1)));
        onView(withId(R.id.recyclerview_participant))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ClickViewAction.clickChildViewWithId(R.id.imagebutton_participantdelete)));
        onView(withId(R.id.recyclerview_participant)).check(withItemCount(0));
    }

    @Test
    public void meetingCreation_clickToCreateMeeting_shouldDisplayMainActivity() {
        onView(withId(R.id.Button_newmeeting)).perform(click());
        onView(withId(R.id.textinput_name)).perform(typeText("test"));
        onView(withId(R.id.textview_chooseroom)).perform(click());
        onView(withText("1"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.textinput_mail)).perform(typeText("test"));
        onView(withId(R.id.textButton_datepicker)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.button_addparticipant)).perform(click());
        onView(withId(R.id.Button_create)).perform(click());
        onView(withId(R.id.constraintlayout_listmeetings)).check(matches(isDisplayed()));

    }

    @Test
    public void meetingCreation_clickToCreateMeeting_shouldAddItemToMeetingsList() {
        onView(withId(R.id.Button_newmeeting)).perform(click());
        onView(withId(R.id.textinput_name)).perform(typeText("test"));
        onView(withId(R.id.textview_chooseroom)).perform(click());
        onView(withText("1"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.textinput_mail)).perform(typeText("test"));
        onView(withId(R.id.textButton_datepicker)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.button_addparticipant)).perform(click());
        onView(withId(R.id.Button_create)).perform(click());
        onView(withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT+1));

    }

    @Test
    public void meetingsList_clickToAddMeeting_shouldDisplayAddMeetingActivity() {
        onView(withId(R.id.Button_newmeeting)).perform(click());
        onView(withId(R.id.constraintlayout_addmeeting)).check(matches(isDisplayed()));
        pressBack();
    }
}
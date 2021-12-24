package com.lamzone.mareu.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.ActivityAddMeetingBinding;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.events.DeleteParticipantEvent;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiServiceInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddMeetingBinding binding;
    private MeetingApiServiceInterface mApiService = DI.getMeetingApiService();
    private int hours = 12;
    private int minutes = 0;
    private List<String> mParticipants = new ArrayList<>();
    private String room;
    private Date date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void setTimePicker() {
        binding.timePickerTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });
    }

    private void openTimePicker() {
        boolean isSystem24Hour = android.text.format.DateFormat.is24HourFormat(this);
        int clockFormat;
        if(isSystem24Hour) {
            clockFormat = TimeFormat.CLOCK_24H;
        } else {
            clockFormat = TimeFormat.CLOCK_12H;
        }
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(12)
                .setMinute(0)
                .setTitleText(getResources().getString(R.string.time_define))
                .build();
        picker.show(getSupportFragmentManager(), "fragment_tag");

        picker.addOnPositiveButtonClickListener(dialog -> {
            hours = picker.getHour();
            minutes = picker.getMinute();
            binding.timePickerTextButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hours, minutes));
        });
    }

    private void initUI() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.timePickerTextButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hours, minutes));
        setTimePicker();
        setDateButton();
        setButtonCreate();
        setButtonAddParticipant();
        setRoomMenu(mApiService.getRooms());
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.showParticipantRecyclerView.setLayoutManager(layoutManager);
        ParticipantsRecyclerViewAdapter ParticipantsRecyclerViewAdapter = new ParticipantsRecyclerViewAdapter(mParticipants, this);
        binding.showParticipantRecyclerView.setAdapter(ParticipantsRecyclerViewAdapter);
    }

    private void setButtonCreate() {
        binding.createMeetingBtn.setOnClickListener(this);
    }

    private void setButtonAddParticipant() {
        binding.addParticipantBtn.setOnClickListener(this);
    }

    private void onSubmit() {
        String name = binding.nameTextInputLayout.getEditText().getText().toString();
        room = binding.chooseRoomAutoCompleteTextView.getText().toString();

        if (name.isEmpty()) {
            binding.nameTextInputLayout.setError(getResources().getString(R.string.name_empty_error));
            return;
        }
        if (room.isEmpty()) {
            binding.chooseRoomTextInputLayout.setError(getResources().getString(R.string.no_room_selected_error));
            return;
        }
        if (mParticipants.isEmpty()) {
            binding.addParticipantTextInputLayout.setError(getResources().getString(R.string.no_participant_error));
            return;
        }
        if (date == null) {
            Toast.makeText(this, getResources().getString(R.string.no_date_selected_error), Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.createMeeting(new Meeting(name,room,hours,minutes, mParticipants, generateColor(hours), date));
        Toast.makeText(this, getResources().getString(R.string.meeting_created), Toast.LENGTH_SHORT).show();
        finish();
    }

    private int generateColor(int hours) {
        float hue = (float) (hours*360/24);
        int color = ColorUtils.HSLToColor(new float[]{hue,0.25f,0.75f});
        return color;
    }

    private void setDateButton() {
        binding.datePickerTextButton.setOnClickListener(this);
    }

    private void displayDateDialog() {
        int selectedYear = Calendar.getInstance().get(Calendar.YEAR);
        int selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        int selectedDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                date = cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                binding.datePickerTextButton.setText(dateFormat.format(date));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.Theme_AppCompat_Light_Dialog,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        long currentTime = new Date().getTime();
        datePickerDialog.getDatePicker().setMinDate(currentTime);

        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.createMeetingBtn) {
            onSubmit();
        }
        if (v == binding.addParticipantBtn) {
            if (binding.addParticipantTextInputLayout.getEditText().getText().toString().isEmpty()) {
                binding.addParticipantTextInputLayout.setError(getResources().getString(R.string.mail_empty_error));
            } else if (binding.addParticipantTextInputLayout.getEditText().getText().toString().contains(" ")) {
                binding.addParticipantTextInputLayout.setError(getResources().getString(R.string.mail_with_spaces_error));
            } else {
                mParticipants.add(binding.addParticipantTextInputLayout.getEditText().getText().toString());
                binding.addParticipantTextInputLayout.getEditText().getText().clear();
                initRecyclerView();
            }
        }
        if (v == binding.datePickerTextButton) {
            displayDateDialog();
        }
    }

    private void setRoomMenu(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
        binding.chooseRoomAutoCompleteTextView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onRemoveParticipant(DeleteParticipantEvent event) {
        mParticipants.remove(event.index);
        initRecyclerView();
    }
}
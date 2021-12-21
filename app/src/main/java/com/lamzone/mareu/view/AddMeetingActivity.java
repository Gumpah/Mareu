package com.lamzone.mareu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.ActivityAddMeetingBinding;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiServiceInterface;

import java.text.DateFormat;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddMeetingBinding binding;
    private MeetingApiServiceInterface mApiService = DI.getMeetingApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void setTimePicker() {
        binding.textButtonTimepicker.setOnClickListener(new View.OnClickListener() {
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
                .setTitleText("T")
                .build();
        picker.show(getSupportFragmentManager(), "fragment_tag");
    }

    private void initUI() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTimePicker();
        setButton();
    }

    private void setButton() {
        binding.ButtonCreate.setOnClickListener(this);
    }

    private void onSubmit() {
        String name = binding.textFieldName.getEditText().getText().toString();
        String room = binding.textFieldRoom.getEditText().getText().toString();

        if (name.isEmpty()) {
            binding.textFieldName.setError("Veuillez entrer le nom de la réunion");
            return;
        }
        if (room.isEmpty()) {
            binding.textFieldRoom.setError("Veuillez entrer un lieu");
            return;
        }
        mApiService.createMeeting(new Meeting(name,room,12,0));
        Toast.makeText(this, "Réunion créée", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ButtonCreate) {
            onSubmit();
        }
    }
}
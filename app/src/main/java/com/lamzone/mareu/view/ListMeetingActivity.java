package com.lamzone.mareu.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.ActivityAddMeetingBinding;
import com.lamzone.mareu.databinding.ActivityListMeetingBinding;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiServiceInterface;

import java.util.List;

public class ListMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityListMeetingBinding binding;
    private List<Meeting> mMeetingList;
    private MeetingApiServiceInterface mApiService = DI.getMeetingApiService();

    private void initUI() {
        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initData();
        setButton();
        initRecyclerView();
    }

    private void setButton() {
        binding.ButtonNewmeeting.setOnClickListener(this);
    }

    private void initData() { mMeetingList = mApiService.getMeetings(); }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(layoutManager);
        MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(mMeetingList, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerview.getContext(),
                layoutManager.getOrientation());
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        binding.recyclerview.setAdapter(myMeetingRecyclerViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ButtonNewmeeting) {
            startActivity(new Intent(this, AddMeetingActivity.class));
        }
    }
}
package com.lamzone.mareu.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.datepicker.CalendarConstraints;
import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.ActivityListMeetingBinding;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.events.DeleteMeetingEvent;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.service.MeetingApiServiceInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityListMeetingBinding binding;
    private List<Meeting> mMeetingList = new ArrayList<>();
    private MeetingApiServiceInterface mApiService = DI.getMeetingApiService();
    public MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter;

    private void initUI() {
        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setButton();
        initRecyclerView();
    }

    private void setButton() {
        binding.newMeetingBtn.setOnClickListener(this);
    }

    private void initData() { mMeetingList = new ArrayList<>(mApiService.getMeetings()); }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.showMeetingsRecyclerView.setLayoutManager(layoutManager);
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(mMeetingList, this);
        binding.showMeetingsRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.showMeetingsRecyclerView.getContext(),
                layoutManager.getOrientation());
        binding.showMeetingsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_by_date:
                displayDateDialog();
                return true;
            case R.id.filter_by_room:
                displayRoomDialog();
                return true;
            case R.id.reset_filter:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                mMeetingList.clear();
                mMeetingList.addAll(mApiService.getMeetingsDateFilter(cal.getTime(), mApiService.getMeetings()));
                dataChange();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.Theme_AppCompat_Light_Dialog,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        long currentTime = new Date().getTime();
        datePickerDialog.getDatePicker().setMinDate(currentTime);

        datePickerDialog.show();
    }

    private void displayRoomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.pick_room));
        String[] mRooms = new String[(mApiService.getRooms().size())];
        mApiService.getRooms().toArray(mRooms);
        int checkedItem = -1;
        builder.setSingleChoiceItems(mRooms, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filterByPlace(mApiService.getRooms().get(which));
            }
        });
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void filterByPlace(String room) {
        mMeetingList.clear();
        mMeetingList.addAll(mApiService.getMeetingsRoomFilter(room, mApiService.getMeetings()));
        dataChange();
    }

    private void resetFilter() {
        mMeetingList.clear();
        mMeetingList.addAll(mApiService.getMeetings());
        dataChange();
    }

    private void dataChange() {
        if (!mApiService.getMeetings().isEmpty()) {
            myMeetingRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initUI();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.newMeetingBtn) {
            startActivity(new Intent(this, AddMeetingActivity.class));
        }
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
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initData();
        initUI();
    }
}
package com.lamzone.mareu.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.events.DeleteMeetingEvent;
import com.lamzone.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;
    private final Context mContext;

    public MyMeetingRecyclerViewAdapter(List<Meeting> items, Context context) {
        this.mMeetings = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.displayMeeting(mMeetings.get(position));
        holder.deleteMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == holder.deleteMeetingBtn) {
                    EventBus.getDefault().post(new DeleteMeetingEvent(mMeetings.get(position)));
                }
            }
        });
        holder.meetingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == holder.meetingContainer) {
                    showParticipantDialog(holder, mMeetings.get(position).getParticipants());
                }
            }
        });

    }

    public void showParticipantDialog(final ViewHolder holder, List<String> participants) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_participants, null);



        RecyclerView list = convertView.findViewById(R.id.dialog_show_participants_recyclerView);
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setHasFixedSize(true);

        DialogParticipantsRecyclerViewAdapter dialogParticipantsRecyclerViewAdapter = new DialogParticipantsRecyclerViewAdapter(participants, mContext);
        list.setAdapter(dialogParticipantsRecyclerViewAdapter);
        alertDialog.setView(convertView);
        alertDialog.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setLayout(600, 400);




        dialog.show();
        dialogParticipantsRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView meetingIconImageView;
        private final TextView meetingFirstLineTextView;
        private final TextView meetingSecondLineTextView;
        private final ImageButton deleteMeetingBtn;
        private String roomPrefix;
        private ConstraintLayout meetingContainer;
        private RecyclerView showParticipantsRecyclerView;

        public ViewHolder(View view) {
            super(view);
            meetingIconImageView = itemView.findViewById(R.id.meeting_icon_imageView);
            meetingFirstLineTextView = itemView.findViewById(R.id.meeting_firstLine_textView);
            meetingSecondLineTextView = itemView.findViewById(R.id.meeting_secondLine_textView);
            deleteMeetingBtn = itemView.findViewById(R.id.delete_meeting_btn);
            meetingContainer = itemView.findViewById(R.id.meeting_container);
            showParticipantsRecyclerView = itemView.findViewById(R.id.dialog_show_participants_recyclerView);
            roomPrefix = view.getResources().getString(R.string.room_prefix);
        }

        public void displayMeeting(Meeting meeting) {
            meetingFirstLineTextView.setText(getFirstLine(meeting));
            meetingSecondLineTextView.setText(getSecondLine(meeting));
            meetingIconImageView.setColorFilter(meeting.getColor());
        }

        public String getFirstLine(Meeting meeting) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
            String firstLine = meeting.getName() + " - " + dateFormat.format(meeting.getDate()) + " " + String.format(Locale.getDefault(), "%02dh%02d", meeting.getHours(), meeting.getMinutes()) + " - " + roomPrefix + " " + meeting.getRoom();
            return firstLine;
        }

        public String getSecondLine(Meeting meeting) {
            String secondLine = TextUtils.join(", ", meeting.getParticipants());
            return secondLine;
        }
    }

}

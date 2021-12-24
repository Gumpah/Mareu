package com.lamzone.mareu.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
        private Meeting mymeeting;
        private String roomPrefix;

        public ViewHolder(View view) {
            super(view);
            meetingIconImageView = itemView.findViewById(R.id.meeting_icon_imageView);
            meetingFirstLineTextView = itemView.findViewById(R.id.meeting_firstLine_textView);
            meetingSecondLineTextView = itemView.findViewById(R.id.meeting_secondLine_textView);
            deleteMeetingBtn = itemView.findViewById(R.id.delete_meeting_btn);
            roomPrefix = view.getResources().getString(R.string.room_prefix);
        }

        public void displayMeeting(Meeting meeting) {
            mymeeting = meeting;
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

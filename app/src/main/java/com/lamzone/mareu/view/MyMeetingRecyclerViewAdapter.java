package com.lamzone.mareu.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.FragmentMeetingBinding;
import com.lamzone.mareu.events.DeleteMeetingEvent;
import com.lamzone.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == holder.delete) {
                    System.out.println("test");
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

        private final ImageView mImageView;
        private final TextView firstline;
        private final TextView secondline;
        private final ImageButton delete;
        private Meeting mymeeting;

        public ViewHolder(View view) {
            super(view);
            mImageView = itemView.findViewById(R.id.imageview_meeting);
            firstline = itemView.findViewById(R.id.TextView_firstline);
            secondline = itemView.findViewById(R.id.TextView_secondline);
            delete = itemView.findViewById(R.id.imagebutton_deletebutton);
        }

        public void displayMeeting(Meeting meeting) {
            mymeeting = meeting;
            firstline.setText(getFirstLine(meeting));
            secondline.setText(getSecondLine(meeting));
            mImageView.setColorFilter(meeting.getColor());
        }

        public String getFirstLine(Meeting meeting) {
            String firstLine = meeting.getName() + " - " + String.format(Locale.getDefault(), "%02dh%02d", meeting.getHours(), meeting.getMinutes()) + " - " + "Salle " + meeting.getRoom();
            return firstLine;
        }

        public String getSecondLine(Meeting meeting) {
            String secondLine = TextUtils.join(", ", meeting.getParticipants());
            return secondLine;
        }
    }

}

package com.lamzone.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.events.DeleteMeetingEvent;
import com.lamzone.mareu.events.DeleteParticipantEvent;
import com.lamzone.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantsRecyclerViewAdapter.ViewHolder> {

    private List<String> mParticipants;
    private final Context mContext;

    public ParticipantsRecyclerViewAdapter(List<String> items, Context context) {
        this.mParticipants = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_participant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParticipantsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.displayMeeting(mParticipants.get(position));
        holder.delete_participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == holder.delete_participant) {
                    EventBus.getDefault().post(new DeleteParticipantEvent(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mParticipants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView TextMail;
        private final ImageButton delete_participant;

        public ViewHolder(View view) {
            super(view);
            TextMail = itemView.findViewById(R.id.textview_participant);
            delete_participant = itemView.findViewById(R.id.imagebutton_participantdelete);
        }

        public void displayMeeting(String participant) {
            TextMail.setText(participant);
        }
    }
}

package com.lamzone.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.events.DeleteParticipantEvent;

import org.greenrobot.eventbus.EventBus;

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
        holder.deleteParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == holder.deleteParticipant) {
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

        private final TextView participantMailTextView;
        private final ImageButton deleteParticipant;

        public ViewHolder(View view) {
            super(view);
            participantMailTextView = itemView.findViewById(R.id.participant_mail_textView);
            deleteParticipant = itemView.findViewById(R.id.delete_participant_btn);
        }

        public void displayMeeting(String participant) {
            participantMailTextView.setText(participant);
        }
    }
}
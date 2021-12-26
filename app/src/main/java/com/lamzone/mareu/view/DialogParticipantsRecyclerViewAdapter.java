package com.lamzone.mareu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;

import java.util.List;

public class DialogParticipantsRecyclerViewAdapter extends RecyclerView.Adapter<DialogParticipantsRecyclerViewAdapter.ViewHolder>{

    private List<String> mParticipants;
    private final Context mContext;

    public DialogParticipantsRecyclerViewAdapter(List<String> items, Context context) {
        this.mParticipants = items;
        this.mContext = context;
    }

    @Override
    public DialogParticipantsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_participant_dialog, parent, false);
        return new DialogParticipantsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogParticipantsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.displayMeeting(mParticipants.get(position));
    }

    @Override
    public int getItemCount() {
        return mParticipants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView dialogParticipantMailTextView;

        public ViewHolder(View view) {
            super(view);
            dialogParticipantMailTextView = itemView.findViewById(R.id.dialog_participant_mail_textView);
        }

        public void displayMeeting(String participant) {
            dialogParticipantMailTextView.setText(participant);
        }
    }
}

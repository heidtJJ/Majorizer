package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.List;

public class MajorRecycleAdapter extends RecyclerView.Adapter<MajorRecycleAdapter.MajorViewHolder> {
    private List<String> availableMajors;
    private Context context;

    static class MajorViewHolder extends RecyclerView.ViewHolder {
        TextView majorView;

        MajorViewHolder(final View view) {
            super(view);
            majorView = view.findViewById(R.id.majorTitleView);
        }
    }

    public MajorRecycleAdapter(List<String> majors, Context context, List<String> currentMajors) {
        // filter out availableMajors one has
        availableMajors = new ArrayList<>();
        for (String major : majors) if (!currentMajors.contains(major)) this.availableMajors.add(major);
        this.context = context;
    }

    @NonNull
    @Override
    public MajorRecycleAdapter.MajorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.major_item_view, parent, false);
        return new MajorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MajorViewHolder holder, final int position) {
        holder.majorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String major = availableMajors.get(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Switch Major");
                alertDialogBuilder
                        .setMessage(String.format("Request to switch major to %s?", major))
                        .setPositiveButton("Request", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notifyDataSetChanged();
                                Toast.makeText(context, "YESSS", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        holder.majorView.setText(availableMajors.get(position));
    }
    @Override
    public int getItemCount() {
        return availableMajors.size();
    }
}
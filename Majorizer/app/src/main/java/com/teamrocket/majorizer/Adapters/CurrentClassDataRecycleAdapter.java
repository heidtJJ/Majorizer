package com.teamrocket.majorizer.Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.R;

import java.util.List;

public class CurrentClassDataRecycleAdapter extends RecyclerView.Adapter<CurrentClassDataRecycleAdapter.CurrentClassViewHolder> {
    private List<ClassData> classList = null;
    private FragmentManager fm;

    static class CurrentClassViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, creditView;

        CurrentClassViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.classNameView);
            creditView = view.findViewById(R.id.creditsView);
        }
    }

    public CurrentClassDataRecycleAdapter(List<ClassData> data, FragmentManager fm) {
        this.classList = data;
        this.fm = fm;
    }

    @NonNull
    @Override
    public CurrentClassDataRecycleAdapter.CurrentClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_courses_list_item, parent, false);
        return new CurrentClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentClassViewHolder holder, final int position) {
        holder.nameView.setText(classList.get(position).getCourseName());
        holder.creditView.setText(String.valueOf(classList.get(position).getCredits()));
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}

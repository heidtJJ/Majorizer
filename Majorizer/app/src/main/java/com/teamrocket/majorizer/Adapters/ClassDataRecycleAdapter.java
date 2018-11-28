package com.teamrocket.majorizer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.R;

import java.util.List;

public class ClassDataRecycleAdapter extends RecyclerView.Adapter<ClassDataRecycleAdapter.ClassViewHolder> {
    private List<ClassData> classList = null;

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, gradeView, creditView;

        ClassViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.classNameView);
            gradeView = view.findViewById(R.id.classGradeView);
            creditView = view.findViewById(R.id.classCreditView);
        }
    }

    public ClassDataRecycleAdapter(List<ClassData> data) {
        this.classList = data;
    }

    @NonNull
    @Override
    public ClassDataRecycleAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.classdata_text_view, parent, false);
        return new ClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.nameView.setText(classList.get(position).getCourseName());
        holder.gradeView.setText(classList.get(position).getGrade());
        holder.creditView.setText(String.valueOf(classList.get(position).getCredits()));
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}

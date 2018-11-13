package com.teamrocket.majorizer.Student;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.R;
import java.util.ArrayList;

public class ClassRecycleAdapter extends RecyclerView.Adapter<ClassRecycleAdapter.ClassViewHolder> {
    private ArrayList<ClassData> data;
    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, gradeView, creditView;
        ClassViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.classNameView);
            gradeView = v.findViewById(R.id.classGradeView);
            creditView = v.findViewById(R.id.classCreditView);
        }
    }

    ClassRecycleAdapter(ArrayList<ClassData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ClassRecycleAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_text_view, parent, false);
        return new ClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.nameView.setText(data.get(position).getCourseName());
        holder.gradeView.setText(data.get(position).getGrade());
        holder.creditView.setText(String.valueOf(data.get(position).getCredits()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

package com.teamrocket.majorizer.Student;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

import java.util.List;

public class CourseRecycleAdapter extends RecyclerView.Adapter<CourseRecycleAdapter.ClassViewHolder> {
    private List<Course> classList = null;

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, classCodeView, creditView;

        ClassViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.classNameView);
            classCodeView = view.findViewById(R.id.classCodeLabel);
            creditView = view.findViewById(R.id.classCreditView);
        }
    }

    public CourseRecycleAdapter(List<Course> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public CourseRecycleAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_text_view, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.nameView.setText(classList.get(position).getCourseName());
        holder.classCodeView.setText(classList.get(position).getCourseCode());
        holder.creditView.setText(String.valueOf(classList.get(position).getCredits()));
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}

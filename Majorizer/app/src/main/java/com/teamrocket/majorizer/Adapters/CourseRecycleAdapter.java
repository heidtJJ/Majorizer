package com.teamrocket.majorizer.Adapters;

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
        TextView nameView, classCodeView, creditView, preReqView;

        ClassViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.classNameView);
            classCodeView = view.findViewById(R.id.classCodeView);
            creditView = view.findViewById(R.id.classCreditView);
            preReqView = view.findViewById(R.id.classPreReqView);
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
        String preReqString = "";
        for (String preReq : classList.get(position).getPreReq()) preReqString += preReq + ", ";
        preReqString = preReqString.substring(0, preReqString.length() - 2);
        holder.preReqView.setText(preReqString);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}

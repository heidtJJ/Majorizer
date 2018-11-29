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
        private TextView nameView, classCodeView, creditView, preReqView, preReqLabel;

        ClassViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.classNameView);
            classCodeView = view.findViewById(R.id.classCodeView);
            creditView = view.findViewById(R.id.classCreditView);
            preReqView = view.findViewById(R.id.classPreReqView);
            preReqLabel = view.findViewById(R.id.classPreReqLabel);
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
        String preReqString = getPreRequisites(classList, position);
        if (preReqString.isEmpty()) {
            holder.preReqView.setVisibility(View.GONE);
            holder.preReqLabel.setVisibility(View.GONE);
        } else
            holder.preReqView.setText(preReqString);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    private String getPreRequisites(final List<Course> classList, final int position) {
        String preReqString = "";
        boolean firstPass = true;
        for (Course preRequsite : classList.get(position).getPreRequsites()) {
            if (!firstPass) {
                preReqString += ", ";
            }
            preReqString += preRequsite.getCourseCode();
            firstPass = false;
        }
        return preReqString;
    }
}

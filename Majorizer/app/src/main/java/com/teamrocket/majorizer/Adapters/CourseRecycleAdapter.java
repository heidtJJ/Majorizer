package com.teamrocket.majorizer.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.CourseDataDialogFragment;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

import java.util.List;

public class CourseRecycleAdapter extends RecyclerView.Adapter<CourseRecycleAdapter.ClassViewHolder> {
    private List<Course> classList = null;
    Activity activity;
    FragmentManager fm;

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        ClassViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.classNameView);
        }
    }

    public CourseRecycleAdapter(List<Course> classList, Activity activity, FragmentManager fm) {
        this.classList = classList;
        this.activity = activity;
        this.fm = fm;
    }

    @NonNull
    @Override
    public CourseRecycleAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_text_view, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, final int position) {
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDataDialogFragment cddf = new CourseDataDialogFragment();
                Bundle args = new Bundle();
                args.putString("code", classList.get(position).getCourseCode());
                args.putString("credits", String.valueOf(classList.get(position).getCredits()));
                args.putString("preReq", getPreRequisites(classList, position));
                cddf.setArguments(args);
                cddf.show(fm, "");
            }
        });
        holder.nameView.setText(classList.get(position).getCourseName());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    private String getPreRequisites(final List<Course> classList, final int position) {
        StringBuilder preReqString = new StringBuilder();
        boolean firstPass = true;
        for (Course preRequsite : classList.get(position).getPreRequsites()) {
            if (!firstPass) preReqString.append("\n");
            preReqString.append(preRequsite.getCourseCode());
            firstPass = false;
        }
        return preReqString.toString();
    }
}

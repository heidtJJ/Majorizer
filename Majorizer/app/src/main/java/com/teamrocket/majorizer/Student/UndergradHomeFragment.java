package com.teamrocket.majorizer.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrocket.majorizer.MainActivity;
import com.teamrocket.majorizer.R;

public class UndergradHomeFragment extends Fragment {
    Student student = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_undergrad_home, container, false);

        // Get account object from the MainActivity.
        MainActivity mainActivity = (MainActivity) getActivity();
        student = (Student) mainActivity.account;

        view.findViewById(R.id.courseHistoryCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courseHistoryIntent = new Intent(getActivity(), UndergradClassHistoryActivity.class);
                courseHistoryIntent.putExtra(getText(R.string.AccountObject).toString(), student);
                startActivity(courseHistoryIntent);
            }
        });

        view.findViewById(R.id.coursesNeededCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schedulerIntent = new Intent(getActivity(), UndergradClassesNeededActivity.class);
                schedulerIntent.putExtra(getText(R.string.AccountObject).toString(), student);
                startActivity(schedulerIntent);
            }
        });

        view.findViewById(R.id.currentCoursesCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schedulerIntent = new Intent(getActivity(), UndergradCurrentClassesActivity.class);
                schedulerIntent.putExtra(getText(R.string.AccountObject).toString(), student);
                startActivity(schedulerIntent);
            }
        });
        return view;
    }
}

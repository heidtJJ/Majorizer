package com.teamrocket.majorizer.AppUtility;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teamrocket.majorizer.R;

public class ClassDataDialogFragment extends DialogFragment {
    String grade, credits;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        grade = args.getString("grade");
        credits = args.getString("credits");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_data_dialog_fragment, container);
        TextView gradeView = view.findViewById(R.id.classGradeViewDialog);
        TextView creditsView = view.findViewById(R.id.classCreditsViewDialog);
        gradeView.setText(grade);
        creditsView.setText(credits);
        return view;
    }
}

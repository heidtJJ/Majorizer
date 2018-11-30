package com.teamrocket.majorizer.AppUtility;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teamrocket.majorizer.R;

public class CourseDataDialogFragment extends DialogFragment {
    String code, credits, preReq;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        credits = args.getString("credits");
        code = args.getString("code");
        preReq = args.getString("preReq");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_data_dialog_fragment, container);
        TextView creditsView = view.findViewById(R.id.classCreditsViewDialog);
        TextView codeView = view.findViewById(R.id.classCodeViewDialog);
        TextView preReqView = view.findViewById(R.id.classPreReqViewDialog);
        TextView preReqLabel = view.findViewById(R.id.classPreReqLabelDialog);
        if (preReq.isEmpty()) {
            preReqView.setVisibility(View.GONE);
            preReqLabel.setVisibility(View.GONE);
        }
        creditsView.setText(credits);
        codeView.setText(code);
        preReqView.setText(preReq);
        return view;
    }
}

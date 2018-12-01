package com.teamrocket.majorizer.AppUtility;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrocket.majorizer.R;

public class AdviseeDataDialogFragment extends DialogFragment {
    String userName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        userName = args.getString("userName");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advisee_data_dialog_fragment, container);
        TextView userNameView = view.findViewById(R.id.adviseeUserNameViewDialog);
        userNameView.setText(userName);
        return view;
    }
}

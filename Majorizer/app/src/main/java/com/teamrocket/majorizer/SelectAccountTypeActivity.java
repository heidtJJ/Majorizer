package com.teamrocket.majorizer;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectAccountTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);
    }

    public void createStudentAccount(View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), CreateStudentAccountActivity.class);
        view.getContext().startActivity(selectAccountActivity);
    }

    public void createAdvisorAccount(View view) {
        Intent selectAccountActivity = new Intent(view.getContext(), CreateAdvisorAccountActivity.class);
        view.getContext().startActivity(selectAccountActivity);
    }
}

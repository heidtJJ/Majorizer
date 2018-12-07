package com.teamrocket.majorizer.Advisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.teamrocket.majorizer.Adapters.DropAdviseeRecycleAdapter;
import com.teamrocket.majorizer.R;

public class AdvisorDropAdviseeTileActivity extends AppCompatActivity {
    private Advisor advisor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_drop_advisee_tile);
        getSupportActionBar().hide();

        // Retrieve advisor object from previous activity.
        advisor = (Advisor) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());


        // Set all classes taken in the recycler view.
        RecyclerView cRecyclerView = findViewById(R.id.coursesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        RecyclerView.Adapter classAdapter = new DropAdviseeRecycleAdapter(advisor, this);
        cRecyclerView.setAdapter(classAdapter);

        String circleText = advisor.getNumAdvisees() + "\nadvisees";
        SpannableString ss = new SpannableString(circleText);
        ss.setSpan(new RelativeSizeSpan(1.7f), 0, String.valueOf(4).length(), 0);
        TextView numberAdviseesView = findViewById(R.id.numberAdviseesView);
        numberAdviseesView.setText(ss);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(getText(R.string.AccountObject).toString(), advisor);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra(getText(R.string.AccountObject).toString(), advisor);
        setResult(RESULT_OK, intent);
        finish();
        super.onDestroy();
    }
}

package com.teamrocket.majorizer.Advisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teamrocket.majorizer.Adapters.DropAdviseeRecycleAdapter;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.util.ArrayList;
import java.util.Map;

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
        RecyclerView cRecyclerView = findViewById(R.id.adviseesRecyclerView);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);

        RecyclerView.Adapter classAdapter = new DropAdviseeRecycleAdapter(advisor, this);
        cRecyclerView.setAdapter(classAdapter);
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

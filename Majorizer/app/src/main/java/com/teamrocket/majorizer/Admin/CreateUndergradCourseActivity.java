package com.teamrocket.majorizer.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teamrocket.majorizer.R;

public class CreateUndergradCourseActivity extends AppCompatActivity {
    private EditText courseNumberEditText = null;
    private EditText courseNameEditText = null;
    private EditText numCreditsEditText = null;
    private RadioGroup departmentRadioGroup = null;
    private RadioGroup majorMinorRadioGroup = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_undergrad_course);

        courseNumberEditText = findViewById(R.id.courseNumberEditText);
        courseNameEditText = findViewById(R.id.courseNameEditText);
        numCreditsEditText = findViewById(R.id.numCreditsEditText);
        departmentRadioGroup = findViewById(R.id.courseDepartment);
        majorMinorRadioGroup = findViewById(R.id.majorMinorRadioGroup);
    }

    public void createNewGraduateCourse(final View view) {
        String courseNumberString = courseNumberEditText.getText().toString();
        String courseName = courseNameEditText.getText().toString();
        String numCreditsString = numCreditsEditText.getText().toString();

        // Check if any of the text-inputs are empty.
        if (courseNumberString.isEmpty() || courseName.isEmpty() || numCreditsString.isEmpty()) {
            Toast.makeText(this, getText(R.string.MissingInput).toString(), Toast.LENGTH_LONG).show();
            return;
        }

        // Check if any of the radio groups are unchecked.
        if (majorMinorRadioGroup.getCheckedRadioButtonId() == -1 || departmentRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getText(R.string.MissingCheckBox).toString(), Toast.LENGTH_LONG).show();
            return;
        }
        
        //



    }
}

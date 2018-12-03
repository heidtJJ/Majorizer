package com.teamrocket.majorizer.Advisor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.LoginManager;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradCurrentClassesActivity;
import com.teamrocket.majorizer.Student.UndergradClassHistoryActivity;
import com.teamrocket.majorizer.Student.UndergradClassesNeededActivity;
import com.teamrocket.majorizer.Student.UndergradStudent;

public class StudentInfoActivity extends AppCompatActivity {
    private Advisor advisor = null;
    private Student student = null;
    private TextView adviseeActivityHeader = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);


        student = (Student) getIntent().getSerializableExtra(getText(R.string.StudentObject).toString());
        LoginManager.getStudentData(student, this);
        advisor = (Advisor) getIntent().getSerializableExtra(getText(R.string.AccountObject).toString());

        adviseeActivityHeader = findViewById(R.id.adviseeActivityHeader);
        adviseeActivityHeader.setText(adviseeActivityHeader.getText()+student.getFirstName() + " " + student.getLastName());

    }

    public void SeeCoursesTaken(final View view) {
        Context context = view.getContext();
        Intent studentInfoIntent = null;
        if (student instanceof UndergradStudent)
            studentInfoIntent = new Intent(this, UndergradClassHistoryActivity.class);
        else // Grad student
            studentInfoIntent = new Intent(this, UndergradClassHistoryActivity.class);
        studentInfoIntent.putExtra(context.getText(R.string.AccountObject).toString(), student);
        context.startActivity(studentInfoIntent);
    }

    public void SeeCoursesNeeded(final View view) {
        Context context = view.getContext();
        Intent studentInfoIntent = null;
        if (student instanceof UndergradStudent)
            studentInfoIntent = new Intent(this, UndergradClassesNeededActivity.class);
        else // Grad student
            studentInfoIntent = new Intent(this, UndergradClassesNeededActivity.class);
        studentInfoIntent.putExtra(context.getText(R.string.AccountObject).toString(), student);
        context.startActivity(studentInfoIntent);
    }

    public void SeeCoursesTaking(final View view) {
        Context context = view.getContext();
        Intent studentInfoIntent = null;
        if (student instanceof UndergradStudent)
            studentInfoIntent = new Intent(this, UndergradCurrentClassesActivity.class);
        else // grad student
            studentInfoIntent = new Intent(this, UndergradCurrentClassesActivity.class);

        studentInfoIntent.putExtra(context.getText(R.string.AccountObject).toString(), student);
        context.startActivity(studentInfoIntent);
    }
}

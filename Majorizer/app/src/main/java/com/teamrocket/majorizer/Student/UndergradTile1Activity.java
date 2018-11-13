package com.teamrocket.majorizer.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teamrocket.majorizer.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class UndergradTile1Activity extends AppCompatActivity {
    private RecyclerView cRecyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager cLayoutManager;
    private ArrayList<ClassData> classData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergrad_tile1);

        cRecyclerView = findViewById(R.id.classesRecyclerView);
        cLayoutManager = new LinearLayoutManager(this);
        cRecyclerView.setLayoutManager(cLayoutManager);
        classData = new ArrayList<>();
        classData.add(new ClassData("Software Design and Development", "B+", 3));
        classData.add(new ClassData("Automota Theory", "A+", 3));
        classData.add(new ClassData("Programming Languages", "A", 3));
        cAdapter = new ClassRecycleAdapter(classData);
        cRecyclerView.setAdapter(cAdapter);
        updateInfo();
    }

    void updateInfo() {
        TextView classesTakenView = findViewById(R.id.classesTakenView);
        TextView creditsTakenView = findViewById(R.id.creditsTakenView);
        TextView gpaView = findViewById(R.id.gpaView);

        classesTakenView.setText(String.valueOf(classData.size()));

        int totalCredits = 0;
        for (ClassData course : classData) totalCredits += course.getCredits();
        creditsTakenView.setText(String.valueOf(totalCredits));

        double scores = 0;
        for (ClassData course: classData) {
            double score;
            if (course.getGrade().equals("A+") || course.getGrade().equals("A")) score = 4;
            else if (course.getGrade().equals("A-")) score = 3.6667;
            else if (course.getGrade().equals("B+")) score = 3.3333;
            else if (course.getGrade().equals("B")) score = 3;
            else if (course.getGrade().equals("B-")) score = 2.6667;
            else if (course.getGrade().equals("C+")) score = 2.3333;
            else if (course.getGrade().equals("C")) score = 2;
            else if (course.getGrade().equals("C-")) score = 1.6667;
            else if (course.getGrade().equals("D+")) score = 1.3333;
            else if (course.getGrade().equals("D")) score = 1;
            else if (course.getGrade().equals("D-")) score = .6667;
            else score = 0;
            scores += score * course.getCredits();
        }
        double gpa = scores / totalCredits;
        DecimalFormat df = new DecimalFormat("##.###");
        gpaView.setText(df.format(gpa));
    }
}

package com.teamrocket.majorizer.Advisor;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Account;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.HashMap;
import java.util.Map;

public class Advisor extends Account {

    // This is a map of the Advisor's advisees.
    // The key is the student's username, the value is the student's full name.
    private Map<String, Student> studentsMap = new HashMap<>();

    // This string is the Advisors department. Example: CS, PH, CE
    private String department;

    // Student map methods.
    public boolean hasStudent(String studentUserName) {
        if (studentsMap.containsKey(studentUserName))
            return true;
        else
            return false;
    }

    public void addStudent(String studentUserName, Student student) {
        studentsMap.put(studentUserName, student);
    }

    public Map<String, Student> getStudents() {
        return studentsMap;
    }

    public int getNumAdvisees() {
        return studentsMap.size();
    }

    // Department methods.
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void dropStudentFromAdvisees(final Student student, final Context context) {
        // Remove student from advisees in memory.
        studentsMap.remove(student.getUserName());
        System.out.println("FUCK " + studentsMap);

        // Remove student from advisees in database.
        // First, remove student from advisee's in Advisor account.
        FirebaseDatabase.getInstance().getReference().getRoot().child(context
                .getText(R.string.Accounts) + "/" + this.userName + "/Advisees/" + student.getUserName()).removeValue();


        // Second, remove adviser from student's Advisors in Student's account.
        FirebaseDatabase.getInstance().getReference().getRoot().child(context
                .getText(R.string.Accounts) + "/" + student.getUserName() + "/Advisors/" + this.userName).removeValue();
    }

}

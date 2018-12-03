package com.teamrocket.majorizer.Advisor;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Account;
import com.teamrocket.majorizer.AppUtility.Utility;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.GradStudent;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static android.support.constraint.Constraints.TAG;
import static com.teamrocket.majorizer.AppUtility.Utility.getAccountType;

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

    public void getSearchableAccounts(final List<Student> studentsToSearch, final BaseAdapter arrayAdapter, final Context context) {
        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("/Accounts/");
        final String Type = context.getText(R.string.Type).toString();
        // Create a mutex lock for adding items to the RecyclerView
        final Lock mutexLock = new ReentrantLock();

        // Query to search for all undergrad students.
        // Beware: this event listener is asynchronous.
        Query undergradStudentQuery = accountsRef.orderByChild(Type).equalTo(context.getText(R.string.Undergrad).toString());
        undergradStudentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot student : snapshot.getChildren()) {
                    String username = student.getKey();
                    String firstName = student.child(context.getText(R.string.FirstNameKey).toString()).getValue().toString();
                    String lastName = student.child(context.getText(R.string.LastNameKey).toString()).getValue().toString();
                    Student searchableStudent = new UndergradStudent(firstName, lastName, username);
                    if (!studentsMap.containsKey(username)) {
                        mutexLock.lock();
                        studentsToSearch.add(searchableStudent);
                        mutexLock.unlock();
                    }
                }
                mutexLock.lock();
                arrayAdapter.notifyDataSetChanged();
                mutexLock.unlock();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        // Query to search for all undergrad students.
        // Beware: this event listener is asynchronous.
        Query gradStudentQuery = accountsRef.orderByChild(Type).equalTo(context.getText(R.string.Grad).toString());
        gradStudentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot student : snapshot.getChildren()) {
                    String username = student.getKey();
                    String firstName = student.child(context.getText(R.string.FirstNameKey).toString()).toString();
                    String lastName = student.child(context.getText(R.string.LastNameKey).toString()).toString();
                    Student searchableStudent = new GradStudent(firstName, lastName, username);
                    if (!studentsMap.containsKey(username)) {
                        mutexLock.lock();
                        studentsToSearch.add(searchableStudent);
                        mutexLock.unlock();
                    }
                }
                mutexLock.lock();
                arrayAdapter.notifyDataSetChanged();
                mutexLock.unlock();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
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

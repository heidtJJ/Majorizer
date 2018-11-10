package com.teamrocket.majorizer.SharedFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.teamrocket.majorizer.MainActivity;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Account;
import com.teamrocket.majorizer.UserGroups.Advisor;
import com.teamrocket.majorizer.UserGroups.GradStudent;
import com.teamrocket.majorizer.UserGroups.Student;
import com.teamrocket.majorizer.UserGroups.UndergradStudent;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    Account account = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ListView listView = view.findViewById(R.id.listView);
        ImageView imageView = view.findViewById(R.id.accountImage);

        // Get account object from the MainActivity.
        MainActivity mainActivity = (MainActivity) getActivity();
        account = mainActivity.account;

        // Populate the userDataList with the user's general information.
        ArrayList<String> userDataList = new ArrayList<>();
        userDataList.add("Name: " + account.getFirstName() + " " + account.getLastName());
        userDataList.add("Clarkson ID: " + account.getId());
        userDataList.add("Clarkson Username: " + account.getUserName());


        if (account instanceof Advisor) {
            imageView.setImageResource(R.mipmap.advisoricon);
            /*String studentsList = "Students: ";
             // Show listing of students.
            boolean firstPass = true;
            for (String student : ((Advisor) account).getStudents().values()) {
                if (!firstPass) studentsList += ", ";
                studentsList += student;
                firstPass = false;
            }
            userDataList.add(studentsList);// Add this list of advisees to the listView.
            */
            userDataList.add("Department: " + ((Advisor) account).getDepartment());
        }

        // Show information for student if the user is a student.
        if (account instanceof Student) {
            if (((Student) account).getAdvisor2() == null) {
                // Student has one advisor.
                userDataList.add("Advisor : " + ((Student) account).getAdvisor1());
            } else {
                // Student has two advisors.
                userDataList.add("Advisor 1: " + ((Student) account).getAdvisor1());
                userDataList.add("Advisor 2: " + ((Student) account).getAdvisor2());
            }
        }
        // Show information for undergrad student if the user is a student.
        if (account instanceof UndergradStudent) {
            if (((UndergradStudent) account).getMajor2() == null) {
                // UndergradStudent has one major.
                userDataList.add("Major: " + ((UndergradStudent) account).getMajor1());
            } else {
                // UndergradStudent has two majors.
                userDataList.add("Major 1: " + ((UndergradStudent) account).getMajor1());
                userDataList.add("Major 2: " + ((UndergradStudent) account).getMajor2());
            }
        }

        // Graduate student has only one major.
        if (account instanceof GradStudent)
            userDataList.add("Major: " + ((GradStudent) account).getMajor());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, userDataList);
        listView.setAdapter(arrayAdapter);

        return view;
    }

}

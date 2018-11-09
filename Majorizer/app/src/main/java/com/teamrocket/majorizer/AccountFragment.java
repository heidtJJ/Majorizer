package com.teamrocket.majorizer;

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

import com.teamrocket.majorizer.UserGroups.Account;
import com.teamrocket.majorizer.UserGroups.Administrator;
import com.teamrocket.majorizer.UserGroups.Advisor;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    Account account = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ListView listView = view.findViewById(R.id.listView);
        ImageView imageView = view.findViewById(R.id.accountImage);

        // Get account object from the MainActivity.
        MainActivity mainActivity = (MainActivity) getActivity();
        account = mainActivity.account;

        if (account instanceof Advisor)
            imageView.setImageResource(R.mipmap.advisoricon);

        if (account instanceof Administrator)
            imageView.setImageResource(R.mipmap.adminicon);

        // Populate the userDataList with the user's information.
        ArrayList<String> userDataList = new ArrayList<>();
        userDataList.add("Name: " + account.getFirstName() + " " + account.getLastName());
        userDataList.add("Clarkson ID: " + account.getId());
        userDataList.add("Clarkson username: " + account.getUserName());
        if (account instanceof Advisor) {
            String studentsList = "Students: ";
            boolean firstPass = true;
            for (String student : ((Advisor) account).getStudents().values()) {
                if (!firstPass) studentsList += ", ";
                studentsList += student;
                firstPass = false;
            }
            userDataList.add(studentsList);
            userDataList.add("Department: " + ((Advisor) account).getDepartment());
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, userDataList);
        listView.setAdapter(arrayAdapter);

        return view;
    }

}

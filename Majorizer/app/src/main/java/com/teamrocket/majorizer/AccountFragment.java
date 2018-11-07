package com.teamrocket.majorizer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_account_fragment, container, false);

        ListView listView = view.findViewById(R.id.listView);

        ArrayList<String> list = new ArrayList<>();

        list.add("Yo1");
        list.add("Yo2");
        list.add("Yo3");
        list.add("Yo4");list.add("Yo4");list.add("Yo4");list.add("Yo4");list.add("Yo4");list.add("Yo4");list.add("Yo4");list.add("Yo4");list.add("Yo4");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        return view;
    }

}

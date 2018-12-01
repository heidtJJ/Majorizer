package com.teamrocket.majorizer.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.teamrocket.majorizer.MainActivity;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Account;

public class UndergradHomeFragment extends Fragment {

    CardView tile1, tile2;
    Account account = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_undergrad_home, container, false);

        // Get account object from the MainActivity.
        MainActivity mainActivity = (MainActivity) getActivity();
        account = mainActivity.account;

        tile1 = view.findViewById(R.id.card1);
        tile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courseHistoryIntent = new Intent(getActivity(), UndergradTile1Activity.class);
                courseHistoryIntent.putExtra(getText(R.string.AccountObject).toString(), account);
                startActivity(courseHistoryIntent);
            }
        });

        tile2 = view.findViewById(R.id.card2);
        tile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schedulerIntent = new Intent(getActivity(), UndergradTile2Activity.class);
                schedulerIntent.putExtra(getText(R.string.AccountObject).toString(), account);
                startActivity(schedulerIntent);
            }
        });
        return view;
    }
}

package com.teamrocket.majorizer.Advisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrocket.majorizer.Account;
import com.teamrocket.majorizer.MainActivity;
import com.teamrocket.majorizer.R;

public class AdvisorHomeFragment extends Fragment {

    Account account = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advisor_home, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        account = mainActivity.account;

        view.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adviseesTileIntent = new Intent(getActivity(), AdvisorMyAdviseesTileActivity.class);
                adviseesTileIntent.putExtra(getText(R.string.AccountObject).toString(), account);
                startActivity(adviseesTileIntent);
            }
        });

        view.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchTileIntent = new Intent(getActivity(), AdvisorSearchStudentsTileActivity.class);
                searchTileIntent.putExtra(getText(R.string.AccountObject).toString(), account);
                startActivity(searchTileIntent);
            }
        });

        view.findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dropAdviseeIntent = new Intent(getActivity(), AdvisorDropAdviseeTileActivity.class);
                dropAdviseeIntent.putExtra(getText(R.string.AccountObject).toString(), account);
                startActivity(dropAdviseeIntent);
            }
        });

        return view;
    }


}

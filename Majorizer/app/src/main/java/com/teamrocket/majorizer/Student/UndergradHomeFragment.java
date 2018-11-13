package com.teamrocket.majorizer.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.teamrocket.majorizer.R;

public class UndergradHomeFragment extends Fragment {

    ImageButton tile1, tile2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_undergrad_home, container, false);
        tile1 = view.findViewById(R.id.tile1);
        tile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UndergradTile1Activity.class);
                startActivity(intent);
            }
        });
        tile2 = view.findViewById(R.id.tile2);
        tile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Tile2", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void onTileOneClicked(View view) {

    }

}

package com.teamrocket.majorizer.SharedFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrocket.majorizer.Adapters.NotificationAdapter;
import com.teamrocket.majorizer.MainActivity;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Account;

public class NotificationsFragment extends Fragment {
    private Account account = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        account = mainActivity.account;

        // Create recycler view for notifications.
        RecyclerView notificationRecyclerView = view.findViewById(R.id.NotificationsAdapter);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(this.getContext());
        notificationRecyclerView.setLayoutManager(cLayoutManager);

        // Create RecyclerView with data.
        RecyclerView.Adapter notificationsAdapter = new NotificationAdapter(account, getActivity());
        notificationRecyclerView.setAdapter(notificationsAdapter);

        return view;
    }
}

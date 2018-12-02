package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.R;

import java.util.List;

public class UnlockItemRecycleAdapter extends RecyclerView.Adapter<UnlockItemRecycleAdapter.UnlockItemViewHolder>{
    private List<String> lockedStudents = null;
    private FragmentManager fm;
    private Context context;
    private Administrator administrator;

    static class UnlockItemViewHolder extends RecyclerView.ViewHolder {
        TextView userNameView;

        UnlockItemViewHolder(final View view) {
            super(view);
            userNameView = view.findViewById(R.id.unlockUserNameView);
        }
    }

    public UnlockItemRecycleAdapter(List<String> lockedStudents, FragmentManager fm, Context context, Administrator administrator) {
        this.lockedStudents = lockedStudents;
        this.fm = fm;
        this.context = context;
        this.administrator = administrator;
    }

    @NonNull
    @Override
    public UnlockItemRecycleAdapter.UnlockItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.unlock_account_list_item, parent, false);
        return new UnlockItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UnlockItemViewHolder holder, final int position) {
        holder.userNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userNameToUnlock = lockedStudents.get(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Account Unlock Confirmation");
                alertDialogBuilder
                        .setMessage("Unlock %s's account?".format(userNameToUnlock))
                        .setPositiveButton("Unlock", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                administrator.unlockAccount(userNameToUnlock.trim(), context);
                                // Update the UI's listview.
                                Toast.makeText(context, context.getText(R.string.UnlockSuccess).toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lockedStudents.size();
    }
}
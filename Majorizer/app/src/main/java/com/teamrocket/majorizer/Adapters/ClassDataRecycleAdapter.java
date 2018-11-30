package com.teamrocket.majorizer.Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrocket.majorizer.AppUtility.ClassData;
import com.teamrocket.majorizer.AppUtility.ClassDataDialogFragment;
import com.teamrocket.majorizer.AppUtility.CourseDataDialogFragment;
import com.teamrocket.majorizer.R;

import java.util.List;

public class ClassDataRecycleAdapter extends RecyclerView.Adapter<ClassDataRecycleAdapter.ClassViewHolder> {
    private List<ClassData> classList = null;
    private FragmentManager fm;

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        ClassViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.classNameView);
        }
    }

    public ClassDataRecycleAdapter(List<ClassData> data, FragmentManager fm) {
        this.classList = data;
        this.fm = fm;
    }

    @NonNull
    @Override
    public ClassDataRecycleAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.classdata_text_view, parent, false);
        return new ClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, final int position) {
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassDataDialogFragment cddf = new ClassDataDialogFragment();
                Bundle args = new Bundle();
                args.putString("credits", String.valueOf(classList.get(position).getCredits()));
                args.putString("grade", classList.get(position).getGrade());
                cddf.setArguments(args);
                cddf.show(fm, "");
            }
        });
        holder.nameView.setText(classList.get(position).getCourseName());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}

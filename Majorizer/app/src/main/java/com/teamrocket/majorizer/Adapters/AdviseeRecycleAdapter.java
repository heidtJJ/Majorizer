package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrocket.majorizer.Advisor.Advisor;
import com.teamrocket.majorizer.Advisor.StudentInfoActivity;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.List;

import static com.teamrocket.majorizer.AppUtility.Utility.getActivity;

public class AdviseeRecycleAdapter extends RecyclerView.Adapter<AdviseeRecycleAdapter.AdviseeViewHolder> {
    private List<Student> students = null;
    private FragmentManager fm;
    private Advisor advisor = null;

    static class AdviseeViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        AdviseeViewHolder(final View view) {
            super(view);
            nameView = view.findViewById(R.id.adviseeNameView);
        }
    }

    public AdviseeRecycleAdapter(final List<Student> students, final FragmentManager fm, final Advisor advisor) {
        this.students = students;
        this.fm = fm;
        this.advisor = advisor;
    }

    @NonNull
    @Override
    public AdviseeRecycleAdapter.AdviseeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.advisee_text_view, parent, false);
        return new AdviseeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdviseeViewHolder holder, final int position) {
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Context context = view.getContext();
                Intent studentInfoIntent = new Intent(getActivity(view), StudentInfoActivity.class);
                studentInfoIntent.putExtra(context.getText(R.string.AccountObject).toString(), advisor);
                studentInfoIntent.putExtra(context.getText(R.string.StudentObject).toString(), students.get(position));
                context.startActivity(studentInfoIntent);
            }
        });
        holder.nameView.setText(students.get(position).getFirstName() + " " + students.get(position).getLastName());

        holder.nameView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
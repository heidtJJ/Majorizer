package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;
import java.util.ArrayList;

public class AdviseeSearchAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<Student> students;
    private ArrayList<Student> orig;

    public AdviseeSearchAdapter(Context context, ArrayList<Student> students) {
        super();
        this.context = context;
        this.students = students;
    }

    public class AdviseeHolder {
        TextView nameView;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Student> results = new ArrayList<>();
                if (orig == null) orig = students;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Student s : orig) {
                            String name = s.getFirstName().toLowerCase() + " " + s.getLastName().toLowerCase();
                            if (name.toLowerCase().contains(constraint.toString().toLowerCase())) results.add(s);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                students = (ArrayList<Student>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int size;
        try {
            size = students.size();
        } catch (NullPointerException npe) {
            size = 0;
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AdviseeHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.advisee_search_item, parent,false);
            holder = new AdviseeHolder();
            holder.nameView = convertView.findViewById(R.id.adviseeNameView);
            convertView.setTag(holder);
        } else holder = (AdviseeHolder) convertView.getTag();
        holder.nameView.setText(students.get(position).getLastName() + ", " + students.get(position).getFirstName());
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Add Student");
                alertDialogBuilder
                        .setMessage(String.format("Are you sure you want to be %s adviser?", students.get(position).getFirstName() + " " + students.get(position).getLastName() + "'s"))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDo: Edit students adviser
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return convertView;
    }
}

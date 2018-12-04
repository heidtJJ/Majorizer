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

import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.Advisor.Advisor;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.Student.Student;

import java.util.ArrayList;
import java.util.List;

public class CourseSearchAdapter extends BaseAdapter implements Filterable {
    private Context context = null;
    private List<Course> coursesToSearch = null;
    private List<Course> orig = null;
    private Administrator administrator = null;

    public CourseSearchAdapter(final Context context, final Administrator administrator, final List<Course> coursesToSearch) {
        super();
        this.context = context;
        this.coursesToSearch = coursesToSearch;
        this.administrator = administrator;
    }

    public class AdviseeHolder {
        TextView nameView = null;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults filterResults = new FilterResults();
                final List<Course> results = new ArrayList<>();
                if (orig == null) orig = coursesToSearch;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Course s : orig) {
                            String name = s.getCourseName() + " " + s.getCourseCode();
                            if (name.toLowerCase().contains(constraint.toString().toLowerCase()))
                                results.add(s);
                        }
                    }
                    filterResults.values = results;
                }
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                coursesToSearch = (List<Course>) results.values;
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
            size = coursesToSearch.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.advisee_search_item, parent, false);
            holder = new AdviseeHolder();
            holder.nameView = convertView.findViewById(R.id.adviseeNameView);
            convertView.setTag(holder);
        } else holder = (AdviseeHolder) convertView.getTag();
        holder.nameView.setText(coursesToSearch.get(position).getCourseName() + ", " + coursesToSearch.get(position).getCourseCode());
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Add Student");
                alertDialogBuilder
                        .setMessage(String.format("Are you sure you want to add %s to the computer science major?", coursesToSearch.get(position).getCourseName() + ", " + coursesToSearch.get(position).getCourseCode()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*

                                // Create the advisor-advisee link between the two accounts.
                                administrator.addStudentToAdvisees(coursesToSearch.get(position), context);

                                // Remove the searchable account from memory.
                                coursesToSearch.remove(position);
                                notifyDataSetChanged();
                                */

                                // Notify the user of successful completion.
                                Toast.makeText(context, "Successfully added this course to the major", Toast.LENGTH_SHORT).show();
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

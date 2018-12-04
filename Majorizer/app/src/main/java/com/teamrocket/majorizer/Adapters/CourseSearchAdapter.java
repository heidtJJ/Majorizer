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

import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.List;

public class CourseSearchAdapter extends BaseAdapter implements Filterable {
    private Context context = null;
    private List<Course> coursesToSearch = null;
    private List<Course> orig = null;
    private Administrator administrator = null;

    // Either Grad or Undergrad
    private String courseType = null;

    // Either AddCourse or RemoveCourse
    private String adminAction = null;

    // Course level is where in the database to insert/remove this data.
    private String courseLevel = null;

    // Department name is name of department.
    private String departmentName = null;

    public CourseSearchAdapter(final Context context, final Administrator administrator,
                               final List<Course> coursesToSearch, final String courseType,
                               final String adminAction, final String courseLevel,
                               final String departmentName) {
        super();
        this.context = context;
        this.coursesToSearch = coursesToSearch;
        this.administrator = administrator;
        this.courseType = courseType;
        this.adminAction = adminAction;
        this.departmentName = departmentName;
        this.courseLevel = courseLevel;
    }

    public class CourseHolder {
        private TextView nameView = null;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Course courseSelected = coursesToSearch.get(position);
        CourseSearchAdapter.CourseHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.object_search_item, parent, false);
            holder = new CourseSearchAdapter.CourseHolder();
            holder.nameView = convertView.findViewById(R.id.objectNameView);
            convertView.setTag(holder);
        } else holder = (CourseSearchAdapter.CourseHolder) convertView.getTag();
        holder.nameView.setText(courseSelected.getCourseName() + ", " + courseSelected.getCourseCode());
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addOrRemove = null;
                if (adminAction.equals(context.getText(R.string.AddCourse)))
                    addOrRemove = "add";
                else
                    addOrRemove = "remove";

                String areYouSure = null;
                if (courseType.equals(context.getText(R.string.Undergrad))) {
                    areYouSure = String.format("Are you sure that you want to %s this class to the %s ", addOrRemove, courseType);
                    if (courseLevel.equals(context.getText(R.string.Major)))
                        areYouSure += "Major of " + departmentName;
                    else
                        areYouSure += "Minor of " + departmentName;
                } else
                    areYouSure = String.format("Are you sure that you want to %s this class to the Graduate department of %s?", addOrRemove, departmentName);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Add Course");
                alertDialogBuilder
                        .setMessage(areYouSure)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (adminAction.equals(context.getText(R.string.AddCourse)))
                                    administrator.addCourseToCurriculum(courseSelected, courseType, adminAction, departmentName, courseLevel, context);
                                else
                                    administrator.removeCourseFromCurriculum(courseSelected, courseType, adminAction, departmentName, courseLevel, context);
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

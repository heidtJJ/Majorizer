package com.teamrocket.majorizer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.Admin.DepartmentSelectionActivity;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.R;

import java.util.ArrayList;
import java.util.List;

public class CourseSearchAdapter extends BaseAdapter implements Filterable {
    private Context context = null;
    private List<Course> coursesToSearch = null;
    private List<Course> orig = null;
    private Administrator administrator = null;

    private String courseLevel = null;
    private String adminAction = null;

    public CourseSearchAdapter(final Context context, final Administrator administrator,
                               final List<Course> coursesToSearch, final String adminActionType,
                               final String adminAction) {
        super();
        this.context = context;
        this.coursesToSearch = coursesToSearch;
        this.administrator = administrator;
        this.courseLevel = adminActionType;
        this.adminAction = adminAction;
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
        CourseHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.object_search_item, parent, false);
            holder = new CourseHolder();
            holder.nameView = convertView.findViewById(R.id.objectNameView);
            convertView.setTag(holder);
        } else holder = (CourseHolder) convertView.getTag();
        holder.nameView.setText(coursesToSearch.get(position).getCourseCode() + ": " + coursesToSearch.get(position).getCourseName());
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course currentCourse = coursesToSearch.get(position);

                // Change curriculum for undergraduate program.
                final Intent selectAccountActivity = new Intent(context, DepartmentSelectionActivity.class);

                // Send administrator to the next activity.
                selectAccountActivity.putExtra(context.getText(R.string.AccountObject).toString(), administrator);
                selectAccountActivity.putExtra(context.getText(R.string.AdminAction).toString(), adminAction);
                selectAccountActivity.putExtra(context.getText(R.string.CourseType).toString(), courseLevel);
                selectAccountActivity.putExtra(context.getText(R.string.Course).toString(), currentCourse);
                view.getContext().startActivity(selectAccountActivity);


            }
        });
        return convertView;
    }
}

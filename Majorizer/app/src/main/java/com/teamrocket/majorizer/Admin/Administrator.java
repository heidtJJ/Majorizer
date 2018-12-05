package com.teamrocket.majorizer.Admin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.Account;
import com.teamrocket.majorizer.Adapters.CourseSearchAdapter;
import com.teamrocket.majorizer.AppUtility.Course;
import com.teamrocket.majorizer.AppUtility.Utility;
import com.teamrocket.majorizer.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.support.constraint.Constraints.TAG;

public class Administrator extends Account {
    private static final String NULL = "NULL";

    //************************************************ ACCOUNT LOCKING **********************************************************

    public void unlockAccount(final String accountUserName, final Context context) {
        // Get reference this user's LoginAttempts field, then set it to 0.
        DatabaseReference accountsReference = FirebaseDatabase.getInstance().getReference("/Accounts/" + accountUserName + "/" + context.getText(R.string.LoginAttempts));
        accountsReference.setValue("0");
    }

    /**
     * This method retrieves a list of locked accounts from the database and stores them in the usernameList input.
     * The arrayAdapter is needed to update the UI with the usernameList data.
     *
     * @param usernameList A
     * @param arrayAdapter
     * @return
     */
    public List<String> getLockedAccounts(final List<String> usernameList, final ArrayAdapter arrayAdapter) {
        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("/Accounts/");
        Query query = accountsRef.orderByChild("LoginAttempts").equalTo("3");

        // Beware: this event listener is asynchronous.
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot username : snapshot.getChildren()) {
                    usernameList.add(username.getKey());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        return usernameList;
    }
    //********************************************** END OF ACCOUNT LOCKING ********************************************************
    //************************************************ CURRICULUM CHANGES **********************************************************

    public void addCourseToMasterList(final String courseName, final String courseCode, final String numCourseCredits, final Context context) {
        final String MASTER_COURSE_LIST = context.getText(R.string.MasterCourseList).toString();
        final String CREDITS = context.getText(R.string.Credits).toString();
        final String COURSE_NAME = context.getText(R.string.CourseName).toString();

        String masterCourseListURL = "/" + MASTER_COURSE_LIST + "/" + courseCode;
        // Make query to database to add new course to MasterCourseList.
        FirebaseDatabase.getInstance().getReference(masterCourseListURL).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Toast.makeText(context, context.getText(R.string.CourseExists).toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                dataSnapshot.child(CREDITS).getRef().setValue(numCourseCredits);
                dataSnapshot.child(COURSE_NAME).getRef().setValue(courseName);
                Toast.makeText(context, context.getText(R.string.SuccessfulCourseAddition).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.TryAgainLater), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void addCourseToDatabase(final List<Course> coursesToSearch, final int position,
                                    final CourseSearchAdapter searchAdapter, final String coursesURL,
                                    final Context context) {
        final Course course = coursesToSearch.get(position);
        final String CREDITS = context.getText(R.string.Credits).toString();
        final String COURSE_NAME = context.getText(R.string.CourseName).toString();
        final String PREREQUISITES = context.getText(R.string.Prerequisites).toString();

        // Make query to database to add new course to Curriculum.
        FirebaseDatabase.getInstance().getReference(coursesURL).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Toast.makeText(context, context.getText(R.string.CourseExists).toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                dataSnapshot.child(CREDITS).getRef().setValue(course.getCredits());
                dataSnapshot.child(COURSE_NAME).getRef().setValue(course.getCourseName());
                DataSnapshot preReqsSnapshot = dataSnapshot.child(PREREQUISITES);
                int i = 0;
                for (Course preReqCourseCode : course.getPreRequsites()) {
                    preReqsSnapshot.child(String.valueOf(i++)).getRef().setValue(preReqCourseCode.getCourseCode());
                }

                // Notify user of success.
                Toast.makeText(context, "Successfully added this course to our records!", Toast.LENGTH_LONG).show();

                // Remove this course from the available list in memory.
                coursesToSearch.remove(position);
                if (searchAdapter != null)
                    searchAdapter.notifyDataSetChanged();
                //((Activity) context).finish();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.TryAgainLater), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void removeCourseFromDatabase(final List<Course> coursesToSearch, final int position,
                                         final CourseSearchAdapter searchAdapter, final String coursesURL,
                                         final Context context) {

        // Make query to database to add new course to Curriculum.
        FirebaseDatabase.getInstance().getReference(coursesURL).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    Toast.makeText(context, context.getText(R.string.CourseDNE).toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                // Remove the node for this course in the database.
                dataSnapshot.getRef().removeValue();

                // Notify user of success.
                Toast.makeText(context, "Successfully removed this course from our records!", Toast.LENGTH_LONG).show();

                // Remove this course from the available list in memory.
                coursesToSearch.remove(position);
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.TryAgainLater), Toast.LENGTH_SHORT).show();
            }

        });
    }


    public void populateCoursesForSearch(final Context context, final List<Course> courseToSearch,
                                         final CourseSearchAdapter courseSearchAdapter, final String URLInDatabase) {
        final String COURSE_NAME = context.getText(R.string.CourseName).toString();
        final String CREDITS = context.getText(R.string.Credits).toString();
        final String PRE_REQUISITES = context.getText(R.string.Prerequisites).toString();
        // Make asynchronous query to Firebase database to fill classes needed in UI.
        FirebaseDatabase.getInstance().getReference(URLInDatabase).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot courseList) {
                // Iterate through courseList pulling data for each course.
                for (DataSnapshot course : courseList.getChildren()) {
                    // Pull information from database.
                    String courseCode = course.getKey();
                    String courseName = course.child(COURSE_NAME).getValue().toString();
                    Integer numCredits = Integer.valueOf(course.child(CREDITS).getValue().toString());

                    // Add prerequisite information to each course.
                    Set<Course> preReqs = new HashSet<>();
                    DataSnapshot preReqSnapshot = course.child(PRE_REQUISITES);
                    for (DataSnapshot preRequisite : preReqSnapshot.getChildren()) {
                        String preReqCourseCode = preRequisite.getValue().toString();
                        preReqs.add(new Course(null, preReqCourseCode, 3, new HashSet<Course>()));
                    }

                    courseToSearch.add(new Course(courseName, courseCode, numCredits, preReqs));
                    courseSearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                System.err.println("Could not access database in MasterCourseList");
            }
        });

    }

    //*********************************************** END OF CURRICULUM CHANGES *****************************************************
    //*************************************************** ACCOUNT CREATION **********************************************************

    public static void createNewUndergradStudentAccount(final Context context, final String username, final String password,
                                                        final String firstName, final String lastName, final String major1,
                                                        final String major2, final String minor1, final String minor2) {
        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Check if the username already exists in the database.
                if (dataSnapshot.hasChild(username)) {
                    // This username already exists
                    Toast.makeText(context, context.getText(R.string.UsernameExists), Toast.LENGTH_LONG).show();
                    return;
                }

                // Insert the user's credentials into the a map.
                Map<String, String> undergradFields = new HashMap<>();
                undergradFields.put(context.getText(R.string.Major1).toString(), major1);
                undergradFields.put(context.getText(R.string.Major2).toString(), major2);
                undergradFields.put(context.getText(R.string.Minor1).toString(), minor1);
                undergradFields.put(context.getText(R.string.Minor2).toString(), minor2);
                undergradFields.put(context.getText(R.string.FirstNameKey).toString(), firstName);
                undergradFields.put(context.getText(R.string.Id).toString(), String.valueOf("0000010"));
                undergradFields.put(context.getText(R.string.LastNameKey).toString(), lastName);
                undergradFields.put(context.getText(R.string.LoginAttempts).toString(), String.valueOf(0));
                undergradFields.put(context.getText(R.string.PasswordKey).toString(), password);
                undergradFields.put(context.getText(R.string.Type).toString(), context.getText(R.string.Undergrad).toString());
                undergradFields.put(context.getText(R.string.UsernameKey).toString(), username);

                // Insert the map into the database and notify the user.
                DatabaseReference newAccountRef = FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts) + "/" + username);
                newAccountRef.setValue(undergradFields);
                Toast.makeText(context, context.getText(R.string.SuccessfulAccountCreation), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static void createNewGradStudentAccount(final Context context, final String major, final String username,
                                                   final String password, final String firstName, final String lastName) {
        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Check if the username already exists in the database.
                if (dataSnapshot.hasChild(username)) {
                    // This username already exists
                    Toast.makeText(context, context.getText(R.string.UsernameExists), Toast.LENGTH_LONG).show();
                    return;
                }
                // Insert the user's credentials into the a map.
                Map<String, String> undergradFields = new HashMap<>();
                undergradFields.put(context.getText(R.string.Major1).toString(), major);
                undergradFields.put(context.getText(R.string.FirstNameKey).toString(), firstName);
                undergradFields.put(context.getText(R.string.Id).toString(), String.valueOf("0000010"));
                undergradFields.put(context.getText(R.string.LastNameKey).toString(), lastName);
                undergradFields.put(context.getText(R.string.LoginAttempts).toString(), String.valueOf(0));
                undergradFields.put(context.getText(R.string.PasswordKey).toString(), password);
                undergradFields.put(context.getText(R.string.Type).toString(), context.getText(R.string.Grad).toString());
                undergradFields.put(context.getText(R.string.UsernameKey).toString(), username);

                // Insert the map into the database and notify the user.
                DatabaseReference newAccountRef = FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts) + "/" + username);
                newAccountRef.setValue(undergradFields);
                Toast.makeText(context, context.getText(R.string.SuccessfulAccountCreation), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static void createNewAdvisorAccount(final Context context, final String department, final String username,
                                               final String password, final String firstName, final String lastName) {
        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Check if the username already exists in the database.
                if (dataSnapshot.hasChild(username)) {
                    // This username already exists
                    Toast.makeText(context, context.getText(R.string.UsernameExists), Toast.LENGTH_LONG).show();
                    return;
                }

                // Insert the user's credentials into the a map.
                Map<String, String> advisorFields = new HashMap<>();
                advisorFields.put(context.getText(R.string.Department).toString(), department);
                advisorFields.put(context.getText(R.string.FirstNameKey).toString(), firstName);
                advisorFields.put(context.getText(R.string.Id).toString(), String.valueOf("0000010"));
                advisorFields.put(context.getText(R.string.LastNameKey).toString(), lastName);
                advisorFields.put(context.getText(R.string.LoginAttempts).toString(), String.valueOf(0));
                advisorFields.put(context.getText(R.string.PasswordKey).toString(), password);
                advisorFields.put(context.getText(R.string.Type).toString(), context.getText(R.string.Advisor).toString());
                advisorFields.put(context.getText(R.string.UsernameKey).toString(), username);

                // Insert the map into the database and notify the user.
                DatabaseReference newAccountRef = FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts) + "/" + username);
                newAccountRef.setValue(advisorFields);
                Toast.makeText(context, context.getText(R.string.SuccessfulAccountCreation), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.InvalidName), Toast.LENGTH_SHORT).show();
            }

        });
    }

    //************************************************ END OF ACCOUNT CREATION *******************************************************

}

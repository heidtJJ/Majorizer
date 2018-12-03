package com.teamrocket.majorizer.Admin;

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
import com.teamrocket.majorizer.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void removeCourseFromCurriculum() {

    }

    public void addUndergradCourseToCurriculum(final String courseName, final String courseCode, final String department, final String numCourseCredits, final String classType, final Context context) {
        final String MASTER_COURSE_LIST = context.getText(R.string.MasterCourseList).toString();
        final String CREDITS = context.getText(R.string.Credits).toString();
        final String COURSE_NAME = context.getText(R.string.CourseName).toString();

        String masterCourseListURL = "/" + MASTER_COURSE_LIST + "/" + courseCode;
        // Make query to database to add new course to MasterCourseList.
        FirebaseDatabase.getInstance().getReference(masterCourseListURL).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                dataSnapshot.child(CREDITS).getRef().setValue(numCourseCredits);
                dataSnapshot.child(COURSE_NAME).getRef().setValue(courseName);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.TryAgainLater), Toast.LENGTH_SHORT).show();
            }

        });

        String curriculumURL = "/" + classType + "/" + department + "/" + courseCode;
        // Make query to database to add new course to MasterCourseList.
        FirebaseDatabase.getInstance().getReference(curriculumURL).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                dataSnapshot.child(CREDITS).getRef().setValue(numCourseCredits);
                dataSnapshot.child(COURSE_NAME).getRef().setValue(courseName);

                // Notify the user that the changes were made. Note, this function is asynchronous.
                Toast.makeText(context, context.getText(R.string.SuccessfulCourseAddition).toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, context.getText(R.string.TryAgainLater), Toast.LENGTH_SHORT).show();
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

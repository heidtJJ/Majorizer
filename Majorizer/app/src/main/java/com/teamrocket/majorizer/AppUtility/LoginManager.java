package com.teamrocket.majorizer.AppUtility;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrocket.majorizer.MainActivity;
import com.teamrocket.majorizer.R;
import com.teamrocket.majorizer.UserGroups.Account;
import com.teamrocket.majorizer.UserGroups.Administrator;
import com.teamrocket.majorizer.UserGroups.Advisor;
import com.teamrocket.majorizer.UserGroups.GradStudent;
import com.teamrocket.majorizer.UserGroups.Student;
import com.teamrocket.majorizer.UserGroups.UndergradStudent;

import java.io.Serializable;
import java.util.ArrayList;

import static com.teamrocket.majorizer.AppUtility.Utility.getAccountType;

public class LoginManager implements Serializable {

    private static final String BAD_CREDENTIALS = "Your username and/or password was incorrect!";
    private static final String EMPTY_FIELD = "Your username and/or password was empty!";
    private static final String BAD_QUERY = "Could not verify your credentials!";
    private static final int NUM_LOCKED_OUT_ATTEMPTS = 3;

    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public boolean isUserLockedOut(final DataSnapshot dataSnapshot, final View view) {
        Resources resources = view.getResources();
        // Check if this user is already locked out. Admins will not have LoginAttempts in their account fields.
        if (dataSnapshot.hasChild(resources.getString(R.string.LoginAttempts))) {
            // Find the number of LoginAttempts value in the database for this user.
            DataSnapshot numLoginAttemptsDataSnap = dataSnapshot.child(resources.getText(R.string.LoginAttempts).toString());
            String numLoginAttemptsString = numLoginAttemptsDataSnap.getValue().toString();

            // Increment and update the number of LoginAttempts for this user.
            int numLoginAttemptsInt = Integer.parseInt(numLoginAttemptsString);
            if (numLoginAttemptsInt >= NUM_LOCKED_OUT_ATTEMPTS) {
                // User is already locked out. Nofify them.
                Toast.makeText(view.getContext(), resources.getText(R.string.UserLockedOut), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public void incrementLoginAttempts(final DataSnapshot dataSnapshot, final String enteredClarksonId, final View view) {
        Resources resources = view.getResources();
        // Check if database has a LoginAttempts field for this user. Admins will not have this field.
        if (dataSnapshot.hasChild(view.getResources().getString(R.string.LoginAttempts))) {
            // Find the number of LoginAttempts value in the database for this user.
            DataSnapshot numLoginAttemptsDataSnap = dataSnapshot.child(resources.getText(R.string.LoginAttempts).toString());
            String numLoginAttemptsString = numLoginAttemptsDataSnap.getValue().toString();

            // Increment and update the number of LoginAttempts for this user.
            int numLoginAttemptsInt = Integer.parseInt(numLoginAttemptsString) + 1;
            mDatabase.child("/" + resources.getText(R.string.Accounts) + "/" + enteredClarksonId + "/" +
                    resources.getString(R.string.LoginAttempts)).setValue(String.valueOf(numLoginAttemptsInt));
        }
    }

    public void resetLoginAttempts(final DataSnapshot dataSnapshot, final String enteredClarksonId, final View view) {
        Resources resources = view.getResources();
        // Check if database has a LoginAttempts field for this user. Admins will not have this field.
        if (dataSnapshot.hasChild(resources.getString(R.string.LoginAttempts))) {
            // Reset the number of LoginAttempts for this user.
            mDatabase.child("/" + resources.getText(R.string.Accounts) + "/" + enteredClarksonId + "/" +
                    resources.getString(R.string.LoginAttempts)).setValue(String.valueOf(0));
        }
    }

    /**
     * Changes the account object to be the correct instantiation of an Account based on its accountType.
     * Also populates the data members of the account.
     *
     * @param dataSnapshot
     * @param account
     */
    public void populateAccount(final View view, final DataSnapshot dataSnapshot, Account account) {
        Resources resources = view.getResources();

        // Set all data members for the account.
        String clarksonUserName = dataSnapshot.child(resources.getText(R.string.Username).toString()).getValue().toString();
        account.setUserName(clarksonUserName);

        String clarksonId = dataSnapshot.child(resources.getText(R.string.Id).toString()).getValue().toString();
        account.setId(clarksonId);

        if (account instanceof Student) {
            // Load the student's advisors.
            String advisor1 = dataSnapshot.child(resources.getText(R.string.Advisor1).toString()).getValue().toString();
            ((Student) account).setAdvisor1(advisor1);

            String advisor2 = dataSnapshot.child(resources.getText(R.string.Advisor2).toString()).getValue().toString();
            if (!advisor2.equals(resources.getText(R.string.NullString)))
                ((Student) account).setAdvisor2(advisor2);

            // Load the student's course history.
            DataSnapshot courseHistory = dataSnapshot.child(resources.getText(R.string.CourseHistory).toString());
            for (DataSnapshot course : courseHistory.getChildren()) {
                String courseCode = course.getKey();
                String courseName = course.child("CourseName").getValue().toString();
                String grade = course.child("Grade").getValue().toString();
                Integer numCredits = Integer.valueOf(course.child("Credits").getValue().toString());
                ((Student) account).addCourseTaken(courseName, courseCode, grade, numCredits);
            }
        }

        if (!(account instanceof Administrator)) {
            String firstName = dataSnapshot.child(resources.getText(R.string.FirstName).toString()).getValue().toString();
            account.setFirstName(firstName);

            String lastName = dataSnapshot.child(resources.getText(R.string.LastName).toString()).getValue().toString();
            account.setLastName(lastName);
        }

        if (account instanceof UndergradStudent) {
            String major1 = dataSnapshot.child(resources.getText(R.string.Major1).toString()).getValue().toString();
            ((UndergradStudent) account).setMajor1(major1);

            String major2 = dataSnapshot.child(resources.getText(R.string.Major2).toString()).getValue().toString();
            if (!major2.equals(resources.getText(R.string.NullString)))
                ((UndergradStudent) account).setMajor2(major2);

            String minor1 = dataSnapshot.child(resources.getText(R.string.Minor1).toString()).getValue().toString();
            if (!minor1.equals(resources.getText(R.string.NullString)))
                ((UndergradStudent) account).setMinor1(minor1);

            String minor2 = dataSnapshot.child(resources.getText(R.string.Minor2).toString()).getValue().toString();
            if (!minor2.equals(resources.getText(R.string.NullString)))
                ((UndergradStudent) account).setMinor2(minor2);
        }

        if (account instanceof GradStudent) {
            String major = dataSnapshot.child(resources.getText(R.string.Major1).toString()).getValue().toString();
            ((GradStudent) account).setMajor(major);
        }


        // Populate hashmap for the advisors students. Key is student id, value is students full name.
        if (account instanceof Advisor) {
            // Get list of student usernames.
            DataSnapshot studentUserNames = dataSnapshot.child(resources.getText(R.string.StudentUserNames).toString());
            ArrayList<String> userNameList = new ArrayList();
            for (DataSnapshot studentUserName : studentUserNames.getChildren()) {
                String userName = studentUserName.getValue().toString();
                userNameList.add(userName);
            }
            // Iterate through student names and populate map.
            DataSnapshot studentNames = dataSnapshot.child(resources.getText(R.string.StudentNames).toString());
            int index = 0;
            for (DataSnapshot studentName : studentNames.getChildren()) {
                String name = studentName.getValue().toString();
                ((Advisor) account).addStudent(userNameList.get(index++), name);
            }

            String department = dataSnapshot.child(resources.getText(R.string.Department).toString()).getValue().toString();
            ((Advisor) account).setDepartment(department);

        }

    }


    // Launch main activity if credentials are correct.

    /**
     * If credentials are correct, create an account object of the correct type and pass this object to the main activity.
     *
     * @param clarksonUsernameField
     * @param passwordField
     */
    public void Login(final EditText clarksonUsernameField, final EditText passwordField) {
        final View view = clarksonUsernameField;
        // Retrieve entered trimmed clarksonId and password from EditTexts.
        final String enteredClarksonID = clarksonUsernameField.getText().toString().trim();
        final String enteredPassword = passwordField.getText().toString().trim();

        // Hide the keyboard for user visibility.
        Utility.hideKeyboard(view.getContext(), view);

        // Check if either user field is empty.
        if (enteredClarksonID.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(view.getContext(), EMPTY_FIELD, Toast.LENGTH_SHORT).show();
            return;
        }

        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + view.getContext().getText(R.string.Accounts) + "/" + enteredClarksonID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Context context = view.getContext();
                // Check if this clarkson ID exists in the database under /Accounts.
                if (dataSnapshot.getChildrenCount() == 0) {
                    // Entered clarkson ID was incorrect. Notify user.
                    Toast.makeText(context, BAD_CREDENTIALS, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isUserLockedOut(dataSnapshot, view)) return;

                // Check if actual password matches entered password.
                String actualPassword = dataSnapshot.child(context.getText(R.string.Password).toString()).getValue().toString();
                if (actualPassword.equals(enteredPassword)) {
                    // Entered password is correct. Advance to main activity.
                    Intent mainActivity = new Intent(context, MainActivity.class);

                    // Reset the user's number of login attempts.
                    resetLoginAttempts(dataSnapshot, enteredClarksonID, view);

                    // This account object will be passed to the MainActivity.
                    Account account = null;

                    // Depending on the type of account, instantiate the correct object.
                    String strAccountType = dataSnapshot.child(context.getText(R.string.Type).toString()).getValue().toString();
                    Account.AccountType accountType = getAccountType(strAccountType);

                    // Determine which type of user this account is. Instantiate that Account type.
                    if (accountType.equals(Account.AccountType.ADMIN))
                        account = new Administrator();
                    else if (accountType.equals(Account.AccountType.UNDERGRAD))
                        account = new UndergradStudent();
                    else if (accountType.equals(Account.AccountType.GRAD))
                        account = new GradStudent();
                    else account = new Advisor();

                    // Uses the dataSnapshot to populate the data members of the Account.
                    populateAccount(view, dataSnapshot, account);

                    // Pass this Account object to the main activity.
                    mainActivity.putExtra("MyClass", account);

                    // Startup the main activity.
                    context.startActivity(mainActivity);
                } else {
                    // Entered password was incorrect. Notify user.
                    Toast.makeText(context, BAD_CREDENTIALS, Toast.LENGTH_SHORT).show();

                    // If user is not an admin, increment LoginAttempts field in their account.
                    incrementLoginAttempts(dataSnapshot, enteredClarksonID, view);
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(view.getContext(), BAD_QUERY, Toast.LENGTH_SHORT).show();
            }

        });

    }
}

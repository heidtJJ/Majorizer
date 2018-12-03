package com.teamrocket.majorizer.AppUtility;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
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
import com.teamrocket.majorizer.Account;
import com.teamrocket.majorizer.Admin.Administrator;
import com.teamrocket.majorizer.Advisor.Advisor;
import com.teamrocket.majorizer.Student.GradStudent;
import com.teamrocket.majorizer.Student.Student;
import com.teamrocket.majorizer.Student.UndergradStudent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.teamrocket.majorizer.Account.AccountType.UNDERGRAD;
import static com.teamrocket.majorizer.AppUtility.Utility.getAccountType;

public class LoginManager implements Serializable {

    private static final String BAD_CREDENTIALS = "Your username and/or password was incorrect!";
    private static final String COULD_NOT_GET_ACCOUNT = "We could not retrieve this user's information!";
    private static final String EMPTY_FIELD = "Your username and/or password was empty!";
    private static final String BAD_QUERY = "Could not verify your credentials!";
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    /**
     * Checks if the current user queried in the snapshot has been locked out. If yes, notify the user via the UI.
     *
     * @param dataSnapshot the account query in the Firebase database.
     * @param view
     * @return whether the user is locked out or not
     */
    public boolean isUserLockedOut(final DataSnapshot dataSnapshot, final View view) {
        Resources resources = view.getResources();
        // Check if this user is already locked out. Admins will not have LoginAttempts in their account fields.
        if (dataSnapshot.hasChild(resources.getString(R.string.LoginAttempts))) {
            // Find the number of LoginAttempts value in the database for this user.
            DataSnapshot numLoginAttemptsDataSnap = dataSnapshot.child(resources.getText(R.string.LoginAttempts).toString());
            String numLoginAttemptsString = numLoginAttemptsDataSnap.getValue().toString();

            // Increment and update the number of LoginAttempts for this user.
            int numLoginAttemptsInt = Integer.parseInt(numLoginAttemptsString);
            if (numLoginAttemptsInt >= MAX_LOGIN_ATTEMPTS) {
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
            if (numLoginAttemptsInt == MAX_LOGIN_ATTEMPTS) {
                NotificationManager.notifyAdminLockedUser(dataSnapshot.getKey());
            }

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
    public static void populateAccount(final Resources resources, final DataSnapshot dataSnapshot, final Account account) {
        // Set all data members for the account.
        String clarksonUserName = dataSnapshot.child(resources.getText(R.string.UsernameKey).toString()).getValue().toString();
        account.setUserName(clarksonUserName);

        // Load the user's notifications into their Account.
        DataSnapshot notificationSnapShot = dataSnapshot.child(resources.getText(R.string.TitleNotifications).toString());
        NotificationManager.getNotifications(notificationSnapShot, resources, account);

        String clarksonId = dataSnapshot.child(resources.getText(R.string.Id).toString()).getValue().toString();
        account.setId(clarksonId);

        if (account instanceof Student) {
            // Load the student's advisors.
            String advisor1 = dataSnapshot.child(resources.getText(R.string.Advisor1).toString()).getValue().toString();
            if (!advisor1.equals(resources.getText(R.string.NullString)))
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
                ((Student) account).addCoursePrevTaken(courseName, courseCode, grade, numCredits);
            }

            // Load the student's current courses taking.
            DataSnapshot coursesTaking = dataSnapshot.child(resources.getText(R.string.CurrentCourses).toString());
            for (DataSnapshot course : coursesTaking.getChildren()) {
                String courseCode = course.getKey();
                System.out.println("FUCK " + courseCode);
                String courseName = course.child(resources.getText(R.string.CourseName).toString()).getValue().toString();
                Integer numCredits = Integer.valueOf(course.child(resources.getText(R.string.Credits).toString()).getValue().toString());
                ((Student) account).addCourseCurTaking(courseName, courseCode, numCredits);
            }
        }

        if (!(account instanceof Administrator)) {
            String firstName = dataSnapshot.child(resources.getText(R.string.FirstNameKey).toString()).getValue().toString();
            account.setFirstName(firstName);

            String lastName = dataSnapshot.child(resources.getText(R.string.LastNameKey).toString()).getValue().toString();
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
            DataSnapshot studentUserNames = dataSnapshot.child(resources.getText(R.string.Advisees).toString());
            for (DataSnapshot studentUserName : studentUserNames.getChildren()) {
                // Pull student information from database.
                String firstName = studentUserName.child(resources.getText(R.string.FirstNameKey).toString()).getValue().toString();
                String lastName = studentUserName.child(resources.getText(R.string.LastNameKey).toString()).getValue().toString();
                String username = studentUserName.getKey();
                String stringAccountType = studentUserName.child(resources.getText(R.string.Type).toString()).getValue().toString();
                Account.AccountType accountType = getAccountType(stringAccountType);

                // Create new student object for this advisee.
                Student advisee = null;
                if (accountType.equals(UNDERGRAD)) {
                    advisee = new UndergradStudent(firstName, lastName, username);
                } else {
                    advisee = new GradStudent(firstName, lastName, username);
                }

                account.setAccountType(accountType);

                ((Advisor) account).addStudent(username, advisee);
            }

            String department = dataSnapshot.child(resources.getText(R.string.Department).toString()).getValue().toString();
            ((Advisor) account).setDepartment(department);
        }

    }


    public static void getStudentData(final Student student, final Context context) {
        FirebaseDatabase.getInstance().getReference("/" + context.getText(R.string.Accounts) + "/" + student.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if this clarkson ID exists in the database under /Accounts.
                if (dataSnapshot.getChildrenCount() == 0) {
                    // Entered clarkson ID was incorrect. Notify user.
                    Toast.makeText(context, COULD_NOT_GET_ACCOUNT, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Uses the dataSnapshot to populate the data members of the Account.
                populateAccount(context.getResources(), dataSnapshot, student);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Database query was not successful.
                Toast.makeText(context, BAD_QUERY, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * If credentials are correct, create an account object of the correct type and pass this object to the main activity.
     *
     * @param clarksonUsernameField
     * @param passwordField
     */
    public void Login(final EditText clarksonUsernameField, final EditText passwordField) {
        final View view = clarksonUsernameField;
        // Retrieve entered trimmed clarksonId and password from EditTexts.
        final String enteredClarksonUsername = clarksonUsernameField.getText().toString().trim();
        final String enteredPassword = passwordField.getText().toString().trim();

        // Hide the keyboard for user visibility.
        Utility.hideKeyboard(view.getContext(), view);

        // Check if either user field is empty.
        if (enteredClarksonUsername.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(view.getContext(), EMPTY_FIELD, Toast.LENGTH_SHORT).show();
            return;
        }

        // Make query to Firebase database to validate user.
        FirebaseDatabase.getInstance().getReference("/" + view.getContext().getText(R.string.Accounts) + "/" + enteredClarksonUsername).addListenerForSingleValueEvent(new ValueEventListener() {
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
                String actualPassword = dataSnapshot.child(context.getText(R.string.PasswordKey).toString()).getValue().toString();
                if (actualPassword.equals(enteredPassword)) {
                    // Entered password is correct. Advance to main activity.
                    Intent mainActivity = new Intent(context, MainActivity.class);

                    // Reset the user's number of login attempts.
                    resetLoginAttempts(dataSnapshot, enteredClarksonUsername, view);

                    // This account object will be passed to the MainActivity.
                    Account account = null;

                    // Depending on the type of account, instantiate the correct object.
                    String strAccountType = dataSnapshot.child(context.getText(R.string.Type).toString()).getValue().toString();
                    Account.AccountType accountType = getAccountType(strAccountType);

                    // Determine which type of user this account is. Instantiate that Account type.
                    if (accountType.equals(Account.AccountType.ADMIN))
                        account = new Administrator();
                    else if (accountType.equals(UNDERGRAD))
                        account = new UndergradStudent();
                    else if (accountType.equals(Account.AccountType.GRAD))
                        account = new GradStudent();
                    else account = new Advisor();

                    // Uses the dataSnapshot to populate the data members of the Account.
                    populateAccount(view.getResources(), dataSnapshot, account);

                    // Pass this Account object to the main activity.
                    mainActivity.putExtra(context.getText(R.string.AccountObject).toString(), account);

                    // Startup the main activity.
                    context.startActivity(mainActivity);
                } else {
                    // Entered password was incorrect. Notify user.
                    Toast.makeText(context, BAD_CREDENTIALS, Toast.LENGTH_SHORT).show();

                    // If user is not an admin, increment LoginAttempts field in their account.
                    incrementLoginAttempts(dataSnapshot, enteredClarksonUsername, view);
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

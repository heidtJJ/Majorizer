package com.teamrocket.majorizer.AppUtility;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.teamrocket.majorizer.Account;
import com.teamrocket.majorizer.R;

import static com.teamrocket.majorizer.Account.AccountType.ADMIN;
import static com.teamrocket.majorizer.Account.AccountType.ADVISOR;
import static com.teamrocket.majorizer.Account.AccountType.ERROR;
import static com.teamrocket.majorizer.Account.AccountType.GRAD;
import static com.teamrocket.majorizer.Account.AccountType.UNDERGRAD;
import static java.lang.String.valueOf;

// Final so that this class cannot be extended.
public final class Utility {
    private static final String databaseAdminKey = "admin";
    private static final String databaseAdvisorKey = "advisor";
    private static final String databaseUndergradKey = "undergrad";
    private static final String databaseGradKey = "grad";
    private static final int requiredUsernameLength = 6;

    // A utility method for hiding the keyboard on the screen.
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * @param accountType comes from the database. It is converted into AccountType.
     * @return the account of type AccountType based on the string given. The string
     * accountType comes from the database.
     */
    public static Account.AccountType getAccountType(final String accountType) {
        switch (accountType) {
            case databaseAdminKey:
                return ADMIN;
            case databaseAdvisorKey:
                return ADVISOR;
            case databaseUndergradKey:
                return UNDERGRAD;
            case databaseGradKey:
                return GRAD;
            default:
                return ERROR;
        }
    }

    /**
     * Checks if an entered username is of length 6 and lowercase letters.
     *
     * @param username the name of the string to check.
     * @return whether the input string is valid or not.
     */
    public static boolean isValidUserName(final String username) {
        if (username.length() != requiredUsernameLength || !isLowerAlpha(username))
            return false;
        else
            return true;
    }

    /**
     * Checks if a string is lowercase and alphabetic.
     *
     * @param name the name of the string to check.
     * @return whether the input string is lowercase and alphabetic or not.
     */
    public static boolean isLowerAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars)
            if (!Character.isLetter(c) || Character.isUpperCase(c))
                return false;

        return true;
    }

    public static boolean isValidName(final String name) {
        char[] chars = name.toCharArray();

        for (char c : chars)
            if (!Character.isLetter(c))
                return false;

        return true;
    }

    public static Activity getActivity(final View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static boolean isValidPassword(final String password) {
        // Check if password is valid
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        String specialCharacters = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        for(int i = 0; i < password.length(); i++){
            if(Character.isLowerCase(password.charAt(i)))
                hasLower = true;
            else if(Character.isUpperCase(password.charAt(i)))
                hasUpper = true;
            else if(Character.isDigit(password.charAt(i)))
                hasNumber = true;
            else if(specialCharacters.contains(String.valueOf(password.charAt(i))))
                hasSpecial = true;
        }
        if (!hasLower || !hasUpper || !hasNumber || !hasSpecial ||
                password.length() < 6 || password.length() > 12){
            return false;
        }
        return true;
    }

    public static boolean isNumber(final String str) {
        return str.replaceAll("[0-9]", "").length() == 0;
    }


    public static boolean isValidUndergradCourseNumber(final String courseNumber) {
        if (!isNumber(courseNumber))
            return false;
        Integer courseNumInt = Integer.valueOf(courseNumber);
        return courseNumInt > 0 && courseNumInt < 500;
    }

    public static boolean isValidGradCourseNumber(final String courseNumber) {
        if (!isNumber(courseNumber))
            return false;
        Integer courseNumInt = Integer.valueOf(courseNumber);
        return courseNumInt >= 500 && courseNumInt < 1000;
    }

    public static boolean isValidNumberCredits(final String numberOfCredits) {
        if (!isNumber(numberOfCredits))
            return false;
        Integer numberOfCreditsInt = Integer.valueOf(numberOfCredits);
        return numberOfCreditsInt >= 3 && numberOfCreditsInt <= 4;
    }


    public static String getSuccessMessage(final String courseType, final String adminAction,
                                           final String departmentName, final String courseMajorOrMinor,
                                           final Context context) {
        String pageTitle = "Successfully ";
        if (adminAction.equals(context.getText(R.string.AddCourse))) {
            pageTitle += "added this course to ";
        } else {
            pageTitle += "removed this course from ";
        }
        if (courseType.equals(context.getText(R.string.Undergrad))) {
            pageTitle += "the Undergraduate ";
            if (courseMajorOrMinor.equals(context.getText(R.string.UndergradMajors)))
                pageTitle += context.getText(R.string.Major).toString();
            else
                pageTitle += context.getText(R.string.Minor).toString();

            pageTitle += " of ";
        } else {
            pageTitle += "the Graduate department of ";
        }
        return pageTitle + departmentName;
    }

}

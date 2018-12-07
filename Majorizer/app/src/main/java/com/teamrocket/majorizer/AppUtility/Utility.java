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

/**
 * This class is used as a standard utility class throughout the application.
 */
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
     * @return True if the input string is lowercase and alphabetic. False otherwise
     */
    public static boolean isLowerAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars)
            if (!Character.isLetter(c) || Character.isUpperCase(c))
                return false;

        return true;
    }

    /**
     * Checks if the input name is valid. Cannot contain numbers or special characters.
     *
     * @param name
     * @return True if the name is valid. False otherwise.
     */
    public static boolean isValidName(final String name) {
        char[] chars = name.toCharArray();

        for (char c : chars)
            if (!Character.isLetter(c))
                return false;

        return true;
    }

    /**
     * Retrieves the activity of the passed view.
     *
     * @param view the current view passed this function.
     * @return the activity of the view.
     */
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

    /**
     * Checks if the input string is in the form of a number.
     *
     * @param str
     * @return True if the input is in the form of a number. False otherwise.
     */
    public static boolean isNumber(final String str) {
        return str.replaceAll("[0-9]", "").length() == 0;
    }

    /**
     * Checks if the input was a valid undergraduate course number.
     *
     * @param courseNumber an input string course number.
     * @return True if the string is valid. False otherwise.
     */
    public static boolean isValidUndergradCourseNumber(final String courseNumber) {
        if (!isNumber(courseNumber))
            return false;
        Integer courseNumInt = Integer.valueOf(courseNumber);
        return courseNumInt > 0 && courseNumInt < 500;
    }

    /**
     * Checks if the input is a valid graduate course number.
     *
     * @param courseNumber a input string course number.
     * @return True if the string is valid. False otherwise.
     */
    public static boolean isValidGradCourseNumber(final String courseNumber) {
        if (!isNumber(courseNumber))
            return false;
        Integer courseNumInt = Integer.valueOf(courseNumber);
        return courseNumInt >= 500 && courseNumInt < 1000;
    }

    /**
     * Checks if the input number of credits as a string is a valid amount.
     * @param numberOfCredits a string representing a number (of credits).
     * @return True if the string is a valid number of credits. False otherwise.
     */
    public static boolean isValidNumberCredits(final String numberOfCredits) {
        if (!isNumber(numberOfCredits))
            return false;
        Integer numberOfCreditsInt = Integer.valueOf(numberOfCredits);
        return numberOfCreditsInt >= 3 && numberOfCreditsInt <= 4;
    }

}

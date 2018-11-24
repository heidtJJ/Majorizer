package com.teamrocket.majorizer.AppUtility;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.teamrocket.majorizer.UserGroups.Account;

import static com.teamrocket.majorizer.UserGroups.Account.AccountType.ADMIN;
import static com.teamrocket.majorizer.UserGroups.Account.AccountType.ADVISOR;
import static com.teamrocket.majorizer.UserGroups.Account.AccountType.ERROR;
import static com.teamrocket.majorizer.UserGroups.Account.AccountType.GRAD;
import static com.teamrocket.majorizer.UserGroups.Account.AccountType.UNDERGRAD;

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

}

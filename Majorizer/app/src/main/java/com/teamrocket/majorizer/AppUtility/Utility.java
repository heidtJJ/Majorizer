package com.teamrocket.majorizer.AppUtility;

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

    // A utility method for hiding the keyboard on the screen.
    public static void hideKeyboard(final View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

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

}

package com.teamrocket.majorizer.AppUtility;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

// Final so that this class cannot be extended.
public final class Utility {

    // A utility method for hiding the keyboard on the screen.
    public static void hideKeyboard(final View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

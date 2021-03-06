package com.wasn.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.score.senzc.pojos.User;
import com.wasn.exceptions.InvalidAccountException;
import com.wasn.exceptions.InvalidIDNumberException;
import com.wasn.exceptions.InvalidInputFieldsException;
import com.wasn.exceptions.InvalidPhoneNoException;

import java.util.regex.Pattern;

/**
 * Utility class to handle activity related common functions
 *
 * @author erangaeb@gmail.com (eranga herath)
 */
public class ActivityUtils {

    private static ProgressDialog progressDialog;

    /**
     * Hide keyboard
     * Need to hide soft keyboard in following scenarios
     * 1. When starting background task
     * 2. When exit from activity
     * 3. On button submit
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getApplicationContext().getSystemService(activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Create and show custom progress dialog
     * Progress dialogs displaying on background tasks
     * <p/>
     * So in here
     * 1. Create custom layout for message dialog
     * 2, Set messages to dialog
     *
     * @param context activity context
     * @param message message to be display
     */
    public static void showProgressDialog(Context context, String message) {
        progressDialog = ProgressDialog.show(context, null, message, true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);

        progressDialog.show();
    }

    /**
     * Cancel progress dialog when background task finish
     */
    public static void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    /**
     * Validate input fields of registration form,
     * Need to have
     * 1. non empty valid phone no
     * 2. non empty username
     * 3. non empty passwords
     * 4. two passwords should be match
     *
     * @param user User object
     * @return valid or not
     */
    public static boolean isValidRegistrationFields(User user) throws InvalidInputFieldsException, InvalidAccountException {
        if (user.getUsername().isEmpty()) {
            throw new InvalidInputFieldsException();
        }

        if (user.getUsername().length()!=12) {
            throw new InvalidAccountException();
        }

        return true;
    }

    public static boolean isValidTransactionFields(String account, int amount, String phoneNo) throws InvalidInputFieldsException, InvalidAccountException, InvalidPhoneNoException {
        if (account == null || account.isEmpty() || amount == 0 || phoneNo == null || phoneNo.isEmpty()) {
            throw new InvalidInputFieldsException();
        }

        if (account.length()!=12) {
            throw new InvalidAccountException();
        }

        if (phoneNo.length() != 10) {
            throw new InvalidPhoneNoException();
        }

        return true;
    }

    public static boolean isValidIDNumber(String idNumber) throws InvalidIDNumberException{
        /*if (idNumber.length() != 10){
            throw new InvalidIDNumberException();
        }
        */

        String regeX = "[0-9]{9}[vVxX]";
        if (!Pattern.matches(regeX, idNumber)) {
            Log.d("DEBUG", idNumber);
            throw new InvalidIDNumberException();
        }
        else{
            Log.d("DEBUG","right");
        }

        return true;

    }

    /**
     * validate input fields of login form
     *
     * @param user login user
     * @return valid of not
     */
    public static boolean isValidLoginFields(User user) {
        return !(user.getUsername().isEmpty());
    }

}

package com.asgatech.sharjahmuseums.Tools;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.R;



public class ValidationTool {

    private ValidationTool validation;
    private Context context;

    public ValidationTool(Context context) {
        this.context = context;
    }

//    public ValidationTool() {
//
//    }

    public static ValidationTool getInstance(Context context) {
        return new ValidationTool(context);
    }

    public boolean validateEmail(EditText editText, String errorMassage) {
        String email = editText.getText().toString();
        if (isNotEmpty(email)) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true;
            } else {
                editText.setError(errorMassage);
                return false;
            }
        } else {
            editText.setError(context.getResources().getString(R.string.email_hint));
            return false;
        }
    }

    public boolean validateEmail(EditText editText) {
        String email = editText.getText().toString();
        if (isNotEmpty(email)) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }




    public boolean validatePhone(EditText editText, String errorMassage) {
        String content = editText.getText().toString();
        String splitContent;
        if (isNotEmpty(content)) {

            if (content.startsWith("0")) {
                if (checkIsNumber(content)) { // true
                    return true;
                } else {
                    editText.setError(errorMassage);
                    return false;
                }
            } else if (content.startsWith("+")) {
                splitContent = content.replace("+", "");
                if (checkIsNumber(splitContent)) { // true
                    return true;
                } else {
                    editText.setError(errorMassage);
                    return false;
                }
            } else {
                editText.setError(errorMassage);
                return false;
            }
        } else {
            editText.setError(context.getResources().getString(R.string.phone_hint));
            return false;
        }
    }

    public boolean validatenNumber(EditText editText, String errorMassage) {
        String content = editText.getText().toString();
        String regex = "[0-9]+";
        if (isNotEmpty(content)) {

            if (content.matches(regex)) {
                if (content.toString().length() == 11 | content.toString().length() <= 13) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isMinimum6Char(String text) {
        if (text.length() < 6) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNotEmpty(String text) {
        if (text != null) {
            if (!text.trim().equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkIsNumber(String content) {
        String regex = "[0-9]+";
        if (content.matches(regex)) {
            if (content.toString().length() == 10 | content.toString().length() <= 15) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean isEmailMatch(String password, EditText confirmEditText, String errorMassage) {
        String confirmPasswrod = confirmEditText.getText().toString();
        if (isNotEmpty(confirmPasswrod)) {
            if (password.equals(confirmPasswrod)) {
                return true;
            }
        } else {
            confirmEditText.setError(errorMassage);
            return false;

        }
        confirmEditText.setError(errorMassage);
        return false;
    }


    public boolean validateRequiredField(EditText editText, String error) {
        String content = editText.getText().toString();
        if (content.isEmpty()) {
            editText.setError(error);
            //event_share_layout_background_red
            return false;
        } else {
            return true;
        }
    }

    public boolean validateRequiredField(EditText editText) {
        String content = editText.getText().toString();
        if (content.isEmpty()) {
            //event_share_layout_background_red
            return false;
        } else {
            return true;
        }
    }


    public boolean validateField(EditText editText, String error) {
        String content = editText.getText().toString();
        if (isNotEmpty(content)) {

            return true;
        } else {
            editText.setError(error);
            return false;
        }
    }

    public boolean validateRequiredField(TextView textView) {
        String content = textView.getText().toString();
        if (content.trim().equalsIgnoreCase("")) {

            return false;
        } else {
            return true;
        }
    }

    public boolean validateRequiredField(String string, TextView textView, String error) {
        if (string.trim().equalsIgnoreCase("")) {
            textView.setError(error);
            return false;
        } else {
            return true;
        }


    }

}

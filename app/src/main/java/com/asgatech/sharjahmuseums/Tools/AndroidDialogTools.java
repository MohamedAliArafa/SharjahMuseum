package com.asgatech.sharjahmuseums.Tools;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.R;

/**
 * Created by halima.reda on 4/12/2016.
 */
public class AndroidDialogTools {
    public static void customToastView(Activity activity, String content) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_custom_toast, null);

        android.widget.TextView text = (android.widget.TextView) layout.findViewById(R.id.text);
        text.setText(content);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}

package com.asgatech.sharjahmuseums.Tools.DialogTool;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import com.asgatech.sharjahmuseums.R;

/**
 * Created by halima.reda on 3/12/2016.
 */
public class ErrorDialog {
    public Dialog showDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_dialog_error);
        dialog.setCancelable(true);
        try {
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return dialog;
    }

}

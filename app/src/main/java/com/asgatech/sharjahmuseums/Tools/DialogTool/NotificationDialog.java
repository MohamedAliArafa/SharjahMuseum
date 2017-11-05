package com.asgatech.sharjahmuseums.Tools.DialogTool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Events.EventDetails.EventDetailsActivity;
import com.asgatech.sharjahmuseums.Activities.NotificationDetailActivity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.ButtonLight;

/**
 * Created by halima.reda on 3/12/2016.
 */
public class NotificationDialog {

    public Dialog showDialog(Context context, int notificationId, int type, String notificationTitle, String notificationDescription) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_dialog_notification);
        dialog.setCancelable(true);
        try {
            dialog.show();
            TextView titleTextView = dialog.findViewById(R.id.tv_title);
            TextView descTextView = dialog.findViewById(R.id.tv_desc);
            ButtonLight openButton = dialog.findViewById(R.id.btn_open);
            ButtonLight cancelButton = dialog.findViewById(R.id.btn_cancel);

            titleTextView.setText(notificationTitle);
            descTextView.setText(notificationDescription);

            openButton.setOnClickListener(v -> {
                Intent intent;
                if (type == 1)
                    intent = new Intent(context, NotificationDetailActivity.class);
                else {
                    intent = new Intent(context, EventDetailsActivity.class);
                }
                intent.putExtra("title", notificationTitle);
                intent.putExtra("text", notificationDescription);
                intent.putExtra("type", type);
                intent.putExtra("id", notificationId);
                context.startActivity(intent);
            });
            cancelButton.setOnClickListener(v -> dialog.dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

}

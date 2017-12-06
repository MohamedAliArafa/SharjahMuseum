package com.asgatech.sharjahmuseums.Tools.DialogTool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Events.EventDetails.EventDetailsActivity;
import com.asgatech.sharjahmuseums.Activities.NotificationDetailActivity;
import com.asgatech.sharjahmuseums.Models.InnerLocationClass;
import com.asgatech.sharjahmuseums.Models.InnerLocationModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.ButtonLight;
import com.asgatech.sharjahmuseums.Tools.DataBaseHandler.DBHelper;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

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
                Intent intent = null;
                if (type == -1) {
//                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
//                    context.startActivity(intent);
                    dialog.dismiss();
                } else if (type == 1) {
                    intent = new Intent(context, NotificationDetailActivity.class);
                    intent.putExtra("title", notificationTitle);
                    intent.putExtra("text", notificationDescription);
                    intent.putExtra("type", type);
                    intent.putExtra("id", notificationId);
                    context.startActivity(intent);
                    dialog.dismiss();
                } else {
                    intent = new Intent(context, EventDetailsActivity.class);
                    intent.putExtra("title", notificationTitle);
                    intent.putExtra("text", notificationDescription);
                    intent.putExtra("type", type);
                    intent.putExtra("id", notificationId);
                    context.startActivity(intent);
                    dialog.dismiss();
                }

            });
            cancelButton.setOnClickListener(v -> dialog.dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public Dialog showDialogs(Context context, String notificationTitle, InnerLocationClass innerLocationClass) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_geo);
        dialog.setCancelable(true);
        try {
            dialog.show();
            TextView titleTextView = dialog.findViewById(R.id.tv_title);
            TextView descTextView = dialog.findViewById(R.id.tv_desc);
            ButtonLight openButton = dialog.findViewById(R.id.btn_open);
            ButtonLight cancelButton = dialog.findViewById(R.id.btn_cancel);

            for (int i = 0; i < innerLocationClass.getInnerLocationModels().size(); i++) {
                titleTextView.append(notificationTitle);
//                titleTextView.append("\n" + innerLocationClass.getInnerLocationModels().get(i).getTitle());
            }

            openButton.setOnClickListener(v -> {
                if (Utils.validList(innerLocationClass.getInnerLocationModels())) {
                    DBHelper db = new DBHelper(context);
                    List<InnerLocationModel> innerLocationModels = db.getAllCachedDates(context, innerLocationClass.getInnerLocationModels());
                    db.close();
                    if (Utils.validList(innerLocationModels)) {
                        for (int i = 0; i < innerLocationModels.size(); i++) {
                            String uriBegin = "geo:" + innerLocationModels.get(i).getLat() + "," + innerLocationModels.get(i).getLang();
                            String query = innerLocationModels.get(i).getLat() + "," + innerLocationModels.get(i).getLang();
                            String encodedQuery = Uri.encode(query);
                            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                            Uri uri = Uri.parse(uriString);
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                }


            });
            cancelButton.setOnClickListener(v -> dialog.dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }


}

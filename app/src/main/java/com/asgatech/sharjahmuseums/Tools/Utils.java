package com.asgatech.sharjahmuseums.Tools;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asgatech.sharjahmuseums.Models.InsertDeviceTokenRequestModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.asgatech.sharjahmuseums.Tools.AndroidDialogTools.customToastView;


public class Utils {
    public static void hideKeypad(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getDarkColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        return Color.HSVToColor(hsv);
    }

    public static InsertDeviceTokenRequestModel insertDeviceToken(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        InsertDevicetokenRequestModel insertDevicetokenRequestModel=new InsertDevicetokenRequestModel(androidID, FirebaseInstanceId.getInstance().getToken());
        return new InsertDeviceTokenRequestModel(androidID, "");
    }

    public static Bitmap decodeUri(Uri selectedImage, Context context) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 200;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o2);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        String filePath = "";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        }
        cursor.close();
        return filePath;
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static boolean validString(String string) {
        if (string != null) {
            if (string.length() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;

        }
    }

    public static String spliteDate(String date) {
        String[] parts = date.split("T");
        String part1 = parts[0]; // date
        String part2 = parts[1]; // time
        return part1;
    }

    public static boolean validObject(Object myObject) {
        if (myObject != null) {
            return true;
        }
        return false;

    }

    public static boolean validList(List list) {
        if (list != null) {
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public static void openWebPage(Context context, String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Uri webPage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            context.startActivity(intent);
        } catch (Exception ex) {
//            CustomToast.customToastView((Activity) context, context.getResources().getString(R.string.general_error));
        }
    }

    public static void loadSimplePic(final Context context, Object url, final ImageView pic) {
        GlideApp.with(context).asBitmap()
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .load(url).into(new BitmapImageViewTarget(pic) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(2);
                pic.setImageDrawable(circularBitmapDrawable);
            }
        });

    }

    public static void loadSimplePic(final Context context, Object url, final ImageView pic, Drawable drawable) {
        GlideApp.with(context)
                .asBitmap()
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .load(url)
                .into(new BitmapImageViewTarget(pic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(20);
                        pic.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mConnectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivity.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            customToastView((Activity) context, context.getString(R.string.no_connection));
            return false;
        }

    }

    public static Animation expand(final View item) {
        if (item.getVisibility() != View.VISIBLE) {
            item.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            final int targetHeight = item.getMeasuredHeight();
            // Older versions of android (pre API 21) cancel animations for views with aaa height of 0.
            item.getLayoutParams().height = 1;
            item.setVisibility(View.VISIBLE);
            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    item.getLayoutParams().height = interpolatedTime == 1
                            ? LinearLayout.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    item.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            anim.setDuration((int) (targetHeight / item.getContext().getResources().getDisplayMetrics().density));
            item.startAnimation(anim);
            return anim;
        }
        return null;
    }

    public static Animation collapse(final View item) {
        if (item.getVisibility() == View.VISIBLE) {
            final int initialHeight = item.getMeasuredHeight();
            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        item.setVisibility(View.GONE);
                    } else {
                        item.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        item.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            // 1dp/ms
            anim.setDuration((int) (initialHeight / item.getContext().getResources().getDisplayMetrics().density));
            item.startAnimation(anim);
            return anim;
        }
        return null;
    }


    public static String getDate(String date) {
        if (date != null && !date.isEmpty()) {
            if (date.contains("T")) {
                return date.split("T")[0];
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static boolean check_CurrentBiggerBirthDay(int current, int birth) {
        int result = (current - birth);
        System.out.println("result" + result);
        boolean falg = false;
        if (result >= 15) {
            falg = true;
        } else {
            falg = false;
        }
        System.out.println("flag" + falg);

        return falg;
    }

    public static String formateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }


}

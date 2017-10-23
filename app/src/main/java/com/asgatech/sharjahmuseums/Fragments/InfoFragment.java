package com.asgatech.sharjahmuseums.Fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Activities.ViewLocationMapActivity;
import com.asgatech.sharjahmuseums.Models.ContactUsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.PermissionTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.call_txt)
    TextView callTextView;

    @BindView(R.id.email_txt)
    TextView emailTextView;

    @BindView(R.id.location_main)
    TextView locationTextView;

    @BindView(R.id.fbImg)
    ImageView fbImageView;

    @BindView(R.id.twitterImg)
    ImageView twitterImageView;

    @BindView(R.id.instaImg)
    ImageView instaImageView;

    @BindView(R.id.call_img)
    ImageView callImageView;

    @BindView(R.id.email_img)
    ImageView emailImageView;

    @BindView(R.id.location_img)
    ImageView locationImageView;

    @BindView(R.id.youtubeImg)
    ImageView youtubeImageView;

    public InfoFragment() {
        // Required empty public constructor
    }


    ContactUsModel model;
    double longtude, latitude;


    private String facebookString, twitterString, instgramString, youtubeString, emailString, tetephoneNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);


        getContactData(UserData.getLocalization(getActivity()));
        return view;
    }


    private void getContactData(int lang) {
        ServerTool.getContactUs(getActivity(), lang, new ServerTool.APICallBack<ContactUsModel>() {
            @Override
            public void onSuccess(ContactUsModel response) {
                if (Utils.validObject(response)) {
                    callTextView.setText(response.getPhone());
                    emailTextView.setText(response.getEmail());
                    locationTextView.setText(getCompleteAddressString(response.getLatitude(),
                            response.getLongitute()));

                    tetephoneNum = response.getPhone();
                    emailString = response.getEmail();

                    instgramString = response.getInstgram();
                    facebookString = response.getFB();
                    twitterString = response.getTwitter();
                    youtubeString = response.getYoutube();
                    latitude = response.getLatitude();
                    longtude = response.getLongitute();

                    assignControls();
                } else {
                    Toast.makeText(getActivity(), "failure to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
                Toast.makeText(getActivity(), "failure to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void assignControls() {

        fbImageView.setOnClickListener(this);
        twitterImageView.setOnClickListener(this);
        instaImageView.setOnClickListener(this);
        youtubeImageView.setOnClickListener(this);

        emailImageView.setOnClickListener(this);
        callImageView.setOnClickListener(this);
        locationImageView.setOnClickListener(this);

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

//                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                if (returnedAddress.getAddressLine(0) != null && !returnedAddress.getAddressLine(0).isEmpty()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(0)).append(",");
                }
//                }
                strAdd = strReturnedAddress.toString();
                Log.e("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.e("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_img:
                if (tetephoneNum != null) {
                    if (PermissionTool.checkPermission(getActivity(), PermissionTool.PERMISSION_PHONE_CALL)) {
                        String uri = "tel:" + tetephoneNum.trim();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    }
                }

                break;

            case R.id.email_img:
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailString, null));
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, " ");
                startActivity(Intent.createChooser(mailIntent, ""));
                break;

            case R.id.location_img:
                Intent intent1 = new Intent(getActivity(), ViewLocationMapActivity.class);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_lATITUDE, latitude);
                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_LONGTUDE, longtude);
                startActivity(intent1);
                break;

            case R.id.fbImg:
                if (Utils.validString(facebookString)) {
                    Utils.openWebPage(getActivity(), facebookString);
                }
                break;

            case R.id.instaImg:
                if (Utils.validString(instgramString)) {
                    Utils.openWebPage(getActivity(), instgramString);
                }
                break;

            case R.id.twitterImg:
                if (Utils.validString(twitterString)) {
                    Utils.openWebPage(getActivity(), twitterString);
                }
                break;

            case R.id.youtubeImg:
                if (Utils.validString(youtubeString)) {
                    Utils.openWebPage(getActivity(), youtubeString);
                }
                break;

        }
    }


}

package com.asgatech.sharjahmuseums.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

public class SettingFragment extends Fragment {


    private RadioGroup changeLanguageRadioGroub;
    private RadioButton changeLanguageArabicRadioButton;
    private RadioButton changeLanguageEnglishRadioButton;
    private Switch togleBtnNotifiState;
    private UserData userData;
    private int language, state;


    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        ((HomeActivity)getActivity()).changeToolbarTitle(getString(R.string.setting));
        changeLanguageRadioGroub = (RadioGroup) view.findViewById(R.id.change_language_radio_groub);
        changeLanguageArabicRadioButton = (RadioButton) view.findViewById(R.id.change_language_arabic_radio_button);
        changeLanguageEnglishRadioButton = (RadioButton) view.findViewById(R.id.change_language_english_radio_button);
        togleBtnNotifiState = (Switch) view.findViewById(R.id.togle_btn_notifi_state);
        setUpView();
    }

    private void setUpView() {
        language = UserData.getLocalization(getActivity());
        switch (language) {
            case Localization.ARABIC_VALUE:
                changeLanguageArabicRadioButton.setChecked(true);
                break;
            case Localization.ENGLISH_VALUE:
                changeLanguageEnglishRadioButton.setChecked(true);
                break;

        }

        state = userData.getNotificationState(getActivity());
        switch (state) {
            case 1:
                togleBtnNotifiState.setChecked(true);
                break;
            case 0:
                togleBtnNotifiState.setChecked(false);
                break;
        }

        changeLanguageRadioGroub.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = changeLanguageRadioGroub.findViewById(i);
                int index = changeLanguageRadioGroub.indexOfChild(radioButton);
                UserData userData = new UserData();
                switch (index) {
                    case 0:
                        new Localization().changeLanguage(URLS.TAG_ENGLISH_String, getActivity());
                        userData.saveLocalization(getActivity(), Localization.ENGLISH_VALUE);
                        break;
                    case 1:
                        new Localization().changeLanguage(URLS.TAG_ARABIC_String, getActivity());
                        userData.saveLocalization(getActivity(), Localization.ARABIC_VALUE);

                        break;

                }
            }
        });

        togleBtnNotifiState.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    userData.saveNotificationState(getActivity(), 1);
                } else {
                    userData.saveNotificationState(getActivity(), 0);
                }

            }


        });


    }

}

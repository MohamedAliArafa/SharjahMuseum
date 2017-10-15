package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInnerFragment extends Fragment {





    EditText nameEditText;

//    @BindView(R.id.email_et)
    EditText emailEditText;

//    @BindView(R.id.phone_et)
    EditText phoneEditText;

//    @BindView(R.id.message_et)
    EditText messageEditText;

//    @BindView(R.id.send_btn)
    Button sendButton;


    public ContactInnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_inner, container, false);
//        ButterKnife.bind(this , view);

        nameEditText = view.findViewById(R.id.name_et);
        phoneEditText = view.findViewById(R.id.phone_et);
        messageEditText = view.findViewById(R.id.message_et);
        emailEditText = view.findViewById(R.id.email_et);
        sendButton = view.findViewById(R.id.send_btn);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameEditText.getText()==null || nameEditText.getText().toString().trim().equals("")){
                    nameEditText.setError("Name required");
                    return;
                }
                if (phoneEditText.getText()==null || phoneEditText.getText().toString().trim().equals("")){
                    phoneEditText.setError("phone required");
                    return;
                }
                if (emailEditText.getText()==null || emailEditText.getText().toString().trim().equals("")){
                    emailEditText.setError("email required");
                    return;
                }
                if (messageEditText.getText()==null || messageEditText.getText().toString().trim().equals("")){
                    messageEditText.setError("message required");
                    return;
                }
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String message = messageEditText.getText().toString();


                ServerTool.sendFeedback(getActivity(), name,email , phone , message, new ServerTool.APICallBack<Integer>() {
                    @Override
                    public void onSuccess(Integer response) {
                        if (response==1){
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "failure to send feedback", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(int statusCode, ResponseBody responseBody) {
                    }
                });
            }
        });

        return view;
    }

}

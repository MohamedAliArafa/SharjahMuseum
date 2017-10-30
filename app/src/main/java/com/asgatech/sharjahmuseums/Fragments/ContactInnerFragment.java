package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.ValidationTool;

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
    private ValidationTool validationTool;

    public ContactInnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_inner, container, false);
        setupView(view);
        sendButton.setOnClickListener(view1 -> {
            if (isValid()) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String message = messageEditText.getText().toString();
                ServerTool.sendFeedback(getActivity(), name, email, phone, message, new ServerTool.APICallBack<Integer>() {
                    @Override
                    public void onSuccess(Integer response) {
                        if (response == 1) {
                            Toast.makeText(getActivity(), getString(R.string.send_messege_succsess), Toast.LENGTH_SHORT).show();
                            ((HomeActivity) getActivity()).getPresenter().openHome();
                        } else {

                            Toast.makeText(getActivity(), getString(R.string.send_messege_fail), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(int statusCode, ResponseBody responseBody) {
                        Toast.makeText(getActivity(), getString(R.string.no_connection), Toast.LENGTH_SHORT).show();

                    }
                });

            }



        });

        return view;
    }

    void emptyFields() {
        nameEditText.clearComposingText();
        phoneEditText.clearComposingText();
        emailEditText.clearComposingText();
        messageEditText.clearComposingText();

    }

    void setupView(View view) {

        nameEditText = view.findViewById(R.id.name_et);
        phoneEditText = view.findViewById(R.id.phone_et);
        messageEditText = view.findViewById(R.id.message_et);
        emailEditText = view.findViewById(R.id.email_et);
        sendButton = view.findViewById(R.id.send_btn);
        validationTool = new ValidationTool(getActivity());


    }

    private boolean isValid() {
        boolean vaildName = validationTool.validateRequiredField(nameEditText, getString(R.string.name_hint));
        boolean vaildMail = validationTool.validateEmail(emailEditText, getString(R.string.invalid_email));
        boolean vaildMessage = validationTool.validateRequiredField(messageEditText, getString(R.string.messeage_hint));
        boolean vaildPhone = validationTool.validateRequiredField(phoneEditText, getString(R.string.phone_hint));

        if (vaildMail && vaildName && vaildMessage && vaildPhone) {
            return true;
        } else {
            return false;
        }
    }

}

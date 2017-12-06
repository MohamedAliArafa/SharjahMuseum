package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.ValidationTool;

import okhttp3.ResponseBody;

import static com.asgatech.sharjahmuseums.Tools.AndroidDialogTools.customToastView;

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
                            customToastView(getActivity(), getResources().getString(R.string.send_messege_succsess));
                            ((HomeActivity) getActivity()).getPresenter().openHome();
                        } else {
                            customToastView(getActivity(), getResources().getString(R.string.send_messege_succsess));
                        }
                    }

                    @Override
                    public void onFailed(int statusCode, ResponseBody responseBody) {
                        customToastView(getActivity(), getResources().getString(R.string.no_connection));
//                        Toast.makeText(getActivity(), getString(R.string.no_connection), Toast.LENGTH_SHORT).show();

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
        boolean vaildName = validationTool.validateRequiredField(nameEditText);
        boolean vaildMail = validationTool.validateEmail(emailEditText);
        boolean vaildMessage = validationTool.validateRequiredField(messageEditText);
        if (vaildName) {
            nameEditText.setBackground(getResources().getDrawable(R.drawable.event_share_layout_background));
            if (vaildMail) {
                emailEditText.setBackground(getResources().getDrawable(R.drawable.event_share_layout_background));
                if (vaildMessage) {
                    messageEditText.setBackground(getResources().getDrawable(R.drawable.event_share_layout_background));
                    return true;
                } else {
                    messageEditText.setBackground(getResources().getDrawable(R.drawable.event_share_layout_background_red));
                    customToastView(getActivity(), getResources().getString(R.string.messeage_hint));
                    return false;
                }
            } else {
                emailEditText.setBackground(getResources().getDrawable(R.drawable.event_share_layout_background_red));
                customToastView(getActivity(), getResources().getString(R.string.invalid_email));
                return false;
            }
        } else {
            nameEditText.setBackground(getResources().getDrawable(R.drawable.event_share_layout_background_red));
            customToastView(getActivity(), getResources().getString(R.string.name_hint));
            return false;
        }
    }

}

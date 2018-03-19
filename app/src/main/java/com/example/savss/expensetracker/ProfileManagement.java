package com.example.savss.expensetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.Button;

/**
 * Created by Saumya on 26-02-2018.
 */

public class ProfileManagement extends Fragment {

    private ViewSwitcher viewSwitcher;
    Button btnNext, btnPrev;
    private TextView name;
    private TextView email;
    private TextView address;
    private TextView phone;
    private TextView dob;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewapp = inflater.inflate(R.layout.fragment_profile_management,container, false);

        btnNext = (Button) viewapp.findViewById(R.id.changeProfile);
        btnPrev = (Button) viewapp.findViewById(R.id.updateProfile);
        viewSwitcher = (ViewSwitcher) viewapp.findViewById(R.id.profileViewSwitcher);

        setName();
        setEmail();
        setAddress();
        setPhoneNumber();
        setDOB();
        btnNext.setOnClickListener(setViewSwitcherNext);
        btnPrev.setOnClickListener(setViewSwitcherPrev);
        return viewapp;

    }

    private View.OnClickListener setViewSwitcherNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewSwitcher.setDisplayedChild(1);
            //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener setViewSwitcherPrev = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewSwitcher.setDisplayedChild(0);
            //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
        }
    };
    private void setName(){

        name = viewSwitcher.findViewById(R.id.textView6);
        name.setText(UserData.Name);
        

    }
    private void setEmail(){
        email = viewSwitcher.findViewById(R.id.textView7);
        email.setText(UserData.email);

    }
    private void setAddress(){
        address = viewSwitcher.findViewById(R.id.textView8);
        address.setText(UserData.address);

    }
    private void setPhoneNumber(){
        phone = viewSwitcher.findViewById(R.id.textView3);
        phone.setText(UserData.phoneNumber);

    }
    private void setDOB(){
        dob = viewSwitcher.findViewById(R.id.textView10);
        dob.setText(UserData.dateOfBirth);

    }
}

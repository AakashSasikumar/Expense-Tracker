package com.example.savss.expensetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import android.widget.Button;

/**
 * Created by Saumya on 26-02-2018.
 */

public class ProfileManagement extends Fragment {

    private ViewSwitcher viewSwitcher;
    Button btnNext, btnPrev;

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
}

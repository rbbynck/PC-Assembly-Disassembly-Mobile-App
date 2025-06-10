package com.example.thesis;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Fragment_Menu_Challenge extends Fragment {

    int ui_flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    Database database;
    ImageView time, build, logo, time_lock, build_lock;
    String status;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public Fragment_Menu_Challenge() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_challenge, container, false);

        database = new Database(getActivity());

        initialization(view);
        startUp();
        onClickListener();

        return view;
    }

    public void initialization(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        logo = view.findViewById(R.id.challenge_logo);
        time = view.findViewById(R.id.challenge_time);
        time_lock = view.findViewById(R.id.challenge_time_lock);
        build = view.findViewById(R.id.challenge_build);
        build_lock = view.findViewById(R.id.challenge_build_lock);
        build.setVisibility(View.GONE);
        build_lock.setVisibility(View.GONE);
    }

    public void startUp() {
        status = database.viewSimulationStatus("Time");
        switch (status) {
            case "Active":
            case "Done":
                time_lock.setVisibility(View.GONE);
                build_lock.setVisibility(View.GONE);
                time.setImageResource(R.drawable.challenge_time);
                build.setImageResource(R.drawable.challenge_build);
                break;
        }
    }

    public void onClickListener() {
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setClickable(false);
                Fragment_Menu_Main fragment = new Fragment_Menu_Main();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Challenge.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setClickable(false);
                status = database.viewSimulationStatus("Tutorial");
                switch (status) {
                    case "Active":
                    case "Done":
                        Fragment_Menu_Simulation_Time fragment = new Fragment_Menu_Simulation_Time();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Menu_Challenge.this);
                        fragmentTransaction.add(android.R.id.content, fragment);
                        fragmentTransaction.commit();
                        break;

                    case "Not_Active":
                        View view = getLayoutInflater().inflate(R.layout.error_simulation_challenge, null);
                        Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                        builder.setContentView(view);

                        view.findViewById(R.id.locked_simulation_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                            }
                        });

                        if (builder.getWindow() != null) {
                            builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }

                        builder.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

                        // Set full-sreen mode (immersive sticky):
                        builder.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
                        builder.show();
                        break;

                    default:
                        break;
                }
            }
        });

        build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = database.viewSimulationStatus("Tutorial");
                switch (status) {
                    case "Done":
                        Fragment_Menu_Challenge_BuildPC fragment = new Fragment_Menu_Challenge_BuildPC();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Menu_Challenge.this);
                        fragmentTransaction.add(android.R.id.content, fragment);
                        fragmentTransaction.commit();
                        break;

                    case "Active":
                    case "Not_Active":
                        View view = getLayoutInflater().inflate(R.layout.error_simulation, null);
                        Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                        builder.setContentView(view);

                        view.findViewById(R.id.locked_simulation_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                            }
                        });

                        if (builder.getWindow() != null) {
                            builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }

                        builder.getWindow().
                                setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

                        // Set full-sreen mode (immersive sticky):
                        builder.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
                        builder.show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

}

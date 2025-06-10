package com.example.thesis;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Fragment_Menu_Simulation extends Fragment {

    Database database;
    ImageView tutorial, practice, logo, tutorial_lock, practice_lock;
    String status;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int ui_flags =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    public Fragment_Menu_Simulation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_simulation, container, false);

        database = new Database(getActivity());

        initialization(view);
        startUp();
        onClickListener();

        return view;
    }

    public void initialization(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        logo = view.findViewById(R.id.simulation_logo);
        tutorial = view.findViewById(R.id.simulation_tutorial);
        tutorial_lock = view.findViewById(R.id.simulation_tutorial_lock);
        practice = view.findViewById(R.id.simulation_practice);
        practice_lock = view.findViewById(R.id.simulation_practice_lock);
    }

    public void startUp() {
        status = database.viewSimulationStatus("Tutorial");
        switch (status) {
            case "Active":
                tutorial_lock.setVisibility(View.GONE);
                tutorial.setImageResource(R.drawable.simulation_tutorial);
                break;

            case "Done":
                tutorial_lock.setVisibility(View.GONE);
                tutorial.setImageResource(R.drawable.simulation_tutorial);
                break;
        }

        status = database.viewSimulationStatus("Practice");
        switch (status) {
            case "Active":
                practice_lock.setVisibility(View.GONE);
                practice.setImageResource(R.drawable.simulation_practice);
                break;

            case "Done":
                practice_lock.setVisibility(View.GONE);
                practice.setImageResource(R.drawable.simulation_practice);
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
                fragmentTransaction.hide(Fragment_Menu_Simulation.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = database.viewSimulationStatus("Practice");
                switch (status) {
                    case "Active":
                        Fragment_Menu_Simulation_Practice fragment = new Fragment_Menu_Simulation_Practice();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Menu_Simulation.this);
                        fragmentTransaction.add(android.R.id.content, fragment);
                        fragmentTransaction.commit();
                        break;

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

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = database.viewSimulationStatus("Tutorial");
                switch (status) {
                    case "Active":
                    case "Done":
                        View view = getLayoutInflater().inflate(R.layout.simulation_reminder, null);
                        Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                        builder.setContentView(view);

                        view.findViewById(R.id.simulation_reminder_ok).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.findViewById(R.id.reminder_1).getVisibility() == View.VISIBLE) {
                                    view.findViewById(R.id.reminder_1).setVisibility(View.GONE);
                                    view.findViewById(R.id.reminder_2).setVisibility(View.VISIBLE);
                                } else if (view.findViewById(R.id.reminder_2).getVisibility() == View.VISIBLE) {
                                    view.findViewById(R.id.reminder_2).setVisibility(View.GONE);
                                    view.findViewById(R.id.reminder_3).setVisibility(View.VISIBLE);

                                    VideoView videoView = (VideoView) view.findViewById(R.id.reminder_3_video);
                                    String videopath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.reminder;
                                    Uri uri = Uri.parse(videopath);
                                    videoView.setVideoURI(uri);
                                    videoView.start();
                                } else if (view.findViewById(R.id.reminder_3).getVisibility() == View.VISIBLE) {
                                    view.findViewById(R.id.reminder_3).setVisibility(View.GONE);
                                    view.findViewById(R.id.reminder_4).setVisibility(View.VISIBLE);
                                    view.findViewById(R.id.simulation_reminder_skip).setVisibility(View.GONE);
                                } else if (view.findViewById(R.id.reminder_4).getVisibility() == View.VISIBLE) {
                                    Fragment_Menu_Simulation_Tutorial fragment = new Fragment_Menu_Simulation_Tutorial();
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.hide(Fragment_Menu_Simulation.this);
                                    fragmentTransaction.add(android.R.id.content, fragment);
                                    fragmentTransaction.commit();
                                    builder.dismiss();
                                }
                            }
                        });

                        view.findViewById(R.id.simulation_reminder_skip).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Fragment_Menu_Simulation_Tutorial fragment = new Fragment_Menu_Simulation_Tutorial();
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.hide(Fragment_Menu_Simulation.this);
                                fragmentTransaction.add(android.R.id.content, fragment);
                                fragmentTransaction.commit();
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

                    case "Not_Active":
                        view = getLayoutInflater().inflate(R.layout.error_simulation, null);
                        builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
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

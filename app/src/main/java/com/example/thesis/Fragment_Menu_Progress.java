package com.example.thesis;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;


public class Fragment_Menu_Progress extends Fragment {

    Database database;
    Vibrator vibrator;
    ImageView logo;

    LinearLayout[] progress_box = new LinearLayout[8];
    TextView[] progress_box_text = new TextView[8];
    ImageView[] progress_circle = new ImageView[8];
    LinearLayout progress_simulation_layout;
    ProgressBar progressBar;

    TextView fast, slow, average, progress_text;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public Fragment_Menu_Progress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_progress, container, false);

        database = new Database(getActivity());

        initialization(view);
        startUp();
        onClickListener();

        return view;
    }

    public void initialization(View view) {
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        logo = view.findViewById(R.id.progress_logo);
        progressBar = view.findViewById(R.id.progress_progressbar);
        progress_text = view.findViewById(R.id.progress_progress_text);
        fast = view.findViewById(R.id.progress_simulation_fastest);
        slow = view.findViewById(R.id.progress_simulation_slowest);
        average = view.findViewById(R.id.progress_simulation_average);
        progress_simulation_layout = view.findViewById(R.id.progress_simulation_layout);

        for (int i = 0; i < progress_box.length; i++) {
            int id = view.getResources().getIdentifier(
                    "progress_box_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            progress_box[i] = view.findViewById(id);
        }
        for (int i = 0; i < progress_box_text.length; i++) {
            int id = view.getResources().getIdentifier(
                    "progress_text_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            progress_box_text[i] = view.findViewById(id);
        }
        for (int i = 0; i < progress_circle.length; i++) {
            int id = view.getResources().getIdentifier(
                    "progress_circle_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            progress_circle[i] = view.findViewById(id);
        }
    }

    public void startUp() {
        for (int i = 0; i < progress_box.length; i++) {
            progress_box_text[i].setText(database.viewLessonProgress(i + 1) + "%");

            switch (database.viewLessonStatus(i + 1)) {
                case "Not_Active":
                    if (i == 0) {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_1_locked);
                    } else if (i == 7) {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_8_locked);
                    } else {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_locked);
                    }
                    progress_circle[i].setImageResource(R.drawable.progress_lock);
                    break;
                case "Active":
                    if (i == 0) {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_1_ongoing);
                    } else if (i == 7) {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_8_ongoing);
                    } else {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_ongoing);
                    }
                    progress_circle[i].setImageResource(R.drawable.progress_ongoing);
                    break;
                case "Done":
                    if (i == 0) {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_1_complete);
                    } else if (i == 7) {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_8_complete);
                    } else {
                        progress_box[i].setBackgroundResource(R.drawable.progress_lesson_complete);
                    }
                    progress_circle[i].setImageResource(R.drawable.progress_complete);
                    break;
            }
        }

        progressBar.setMax(100);
        progressBar.setProgress(Integer.parseInt(database.viewAppProgress()));
        progress_text.setText(database.viewAppProgress() + "%");
    }

    public void onClickListener() {
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setClickable(false);
                Fragment_Menu_Main fragment = new Fragment_Menu_Main();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Progress.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });
    }

}


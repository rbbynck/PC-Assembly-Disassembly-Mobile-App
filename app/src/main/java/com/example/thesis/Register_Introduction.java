package com.example.thesis;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.appintro.*;

import org.jetbrains.annotations.Nullable;

import static com.github.appintro.AppIntroPageTransformerType.*;

public class Register_Introduction extends AppIntro {

    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //methods
        decorView();
        addSlide();
        setOthers();
        
    }


    public void decorView() {
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 9) {
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }


    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void addSlide() {
        addSlide(AppIntroFragment.newInstance("Welcome to our PC Assembly and Disassembly Mobile App", "Welcome To PCAD!\nThis mobile app aims to help students like you to enhance and gain more knowledge about building a PC\n*Note: Whenever you see click this logo, it redirects you to the main menu",
                R.drawable.logo, Color.rgb(173, 216, 230), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //MAIN MENU
        addSlide(AppIntroFragment.newInstance("Main Menu", "As seen in the picture, the highlighted buttons redirects you to a specific page.",
                R.drawable.reg_intro_1, Color.rgb(193, 225, 236), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //TRIVIA
        addSlide(AppIntroFragment.newInstance("Trivia", "Every time you go to the main menu, this \"Cool Facts\" appear, you can close it by clicking the OK button.\nYou can also trigger it to open by" +
                        "clicking the owl button in the main menu.\nIf you don't want it to appear every time, you can close the trivia feature in the settings.",
                R.drawable.reg_intro_2, Color.rgb(212, 235, 242), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //LESSON
        addSlide(AppIntroFragment.newInstance("Lesson", "We offer a complete crash course of PC Assembly and Disassembly.\n It consist of 8 lessons with quiz module at the end of the lesson." +
                        "\nYou must have a complete score for every quiz module for you to finish it.",
                R.drawable.reg_intro_3, Color.rgb(232, 244, 248), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //LESSON
        addSlide(AppIntroFragment.newInstance("Lesson", "Each topics from a lesson gives a overview or summary.",
                R.drawable.reg_intro_4, Color.rgb(212, 235, 242), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //QUIZ
        addSlide(AppIntroFragment.newInstance("Quiz", "Every end of a topic, you must need to pass the quiz to get past the current topic." +
                        "\nEvery quiz has a timer where if you fail to finish it by the given time, you'll fail the test.",
                R.drawable.reg_intro_5, Color.rgb(193, 225, 236), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //SIMULATION AND CHALLENGE
        addSlide(AppIntroFragment.newInstance("Simulation", "After finishing all the lessons, you will unlock the simulation feature of the mobile app." +
                        "\nTutorial Mode will guide users who have just finish all the lessons and the Practice Mode is for users who want to do it again without guidance.",
                R.drawable.reg_intro_6, Color.rgb(173, 216, 230), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //SIMULATION SAMPLE
        addSlide(AppIntroFragment.newInstance("Simulation", "Example of the Simulation.",
                R.drawable.reg_intro_7, Color.rgb(193, 225, 236), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //PROGRESS
        addSlide(AppIntroFragment.newInstance("Progress", "You can check your progress, by clicking the progress bag button in the Main Menu (Above the Owl button).",
                R.drawable.reg_intro_8, Color.rgb(212, 235, 242), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //SETTINGS
        addSlide(AppIntroFragment.newInstance("Settings", "You can close the trivia feature, change your profile, give a feedback and logout in the settings.",
                R.drawable.reg_intro_9, Color.rgb(232, 244, 248), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //ADD ON
        addSlide(AppIntroFragment.newInstance("Class Lesson", "After you have finish all the lessons and take the Simulation Tutorial Mode, this button will appear on your Main Menu." +
                        "\nIt will redirect you to a page where you can view your professors uploaded lessons.",
                R.drawable.reg_intro_10, Color.rgb(212, 235, 242), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
        //ADD ON
        addSlide(AppIntroFragment.newInstance("Are you ready to start?", "",
                R.drawable.logo, Color.rgb(193, 225, 236), Color.rgb(111, 112, 112), Color.rgb(111, 112, 112)));
    }

    private void setOthers() {
        setColorTransitionsEnabled(true);
        setDoneText("START");
        setColorDoneText(Color.rgb(111, 112, 112));
        setColorSkipButton(Color.rgb(111, 112, 112));
        setSeparatorColor(Color.rgb(111, 112, 112));
        setNextArrowColor(Color.rgb(111, 112, 112));
    }
}

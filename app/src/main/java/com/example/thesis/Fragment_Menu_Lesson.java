package com.example.thesis;


import android.app.Dialog;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Fragment_Menu_Lesson extends Fragment {

    String name;
    String[] lessonStatus = new String[8];
    FrameLayout[] lesson = new FrameLayout[8];
    ImageView[] lesson_background = new ImageView[8];
    TextView welcome;
    ImageView lesson_logo;
    int id;
    TextView lesson_title;
    String lesson_titles[] = {"Familiarization of Motherboard", "Familiarization of System Unit", "Safety Procedures", "Basic Troubleshooting", "Basic PC Set-Up",
            "Proper Use of Tools", "PC Disassembly", "PC Assembly"};
    int previous_selected;
    LinearLayout lesson_lesson[] = new LinearLayout[8];
    LinearLayout lesson_module_background[][] = new LinearLayout[8][6];
    ImageView lesson_module_icon[][] = new ImageView[8][6];

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Bundle bundle;

    public Fragment_Menu_Lesson() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_lesson, container, false);

        //methods
        initialize(view);
        setOnClickListeners();
        setOnClickListenersModule();
        startUp();

        return view;
    }

    public void initialize(View view) {
        bundle = new Bundle();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        lesson_title = view.findViewById(R.id.lesson_title);

        for (int i = 0; i < 8; i++) {
            id = view.getResources().getIdentifier(
                    "lesson_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            lesson[i] = view.findViewById(id);//lesson
            id = view.getResources().getIdentifier(
                    "lesson_" + (i + 1) + "_background",
                    "id",
                    getActivity().getPackageName());
            lesson_background[i] = view.findViewById(id);//lesson background
            id = view.getResources().getIdentifier(
                    "lesson_lesson_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            lesson_lesson[i] = view.findViewById(id);//lesson_lesson
        }

        lesson_logo = view.findViewById(R.id.lesson_logo);
        lesson_module_background[0][0] = view.findViewById(R.id.lesson_module_1_1);
        lesson_module_background[0][1] = view.findViewById(R.id.lesson_module_1_2);
        lesson_module_background[0][2] = view.findViewById(R.id.lesson_module_1_3);
        lesson_module_background[0][3] = view.findViewById(R.id.lesson_module_1_4);
        lesson_module_background[0][4] = view.findViewById(R.id.lesson_module_1_5);
        lesson_module_background[1][0] = view.findViewById(R.id.lesson_module_2_1);
        lesson_module_background[1][1] = view.findViewById(R.id.lesson_module_2_2);
        lesson_module_background[1][2] = view.findViewById(R.id.lesson_module_2_3);
        lesson_module_background[2][0] = view.findViewById(R.id.lesson_module_3_1);
        lesson_module_background[2][1] = view.findViewById(R.id.lesson_module_3_2);
        lesson_module_background[2][2] = view.findViewById(R.id.lesson_module_3_3);
        lesson_module_background[2][3] = view.findViewById(R.id.lesson_module_3_4);
        lesson_module_background[3][0] = view.findViewById(R.id.lesson_module_4_1);
        lesson_module_background[3][1] = view.findViewById(R.id.lesson_module_4_2);
        lesson_module_background[3][2] = view.findViewById(R.id.lesson_module_4_3);
        lesson_module_background[3][3] = view.findViewById(R.id.lesson_module_4_4);
        lesson_module_background[3][4] = view.findViewById(R.id.lesson_module_4_5);
        lesson_module_background[3][5] = view.findViewById(R.id.lesson_module_4_6);
        lesson_module_background[4][0] = view.findViewById(R.id.lesson_module_5_1);
        lesson_module_background[4][1] = view.findViewById(R.id.lesson_module_5_2);
        lesson_module_background[4][2] = view.findViewById(R.id.lesson_module_5_3);
        lesson_module_background[5][0] = view.findViewById(R.id.lesson_module_6_1);
        lesson_module_background[5][1] = view.findViewById(R.id.lesson_module_6_2);
        lesson_module_background[5][2] = view.findViewById(R.id.lesson_module_6_3);
        lesson_module_background[6][0] = view.findViewById(R.id.lesson_module_7_1);
        lesson_module_background[6][1] = view.findViewById(R.id.lesson_module_7_2);
        lesson_module_background[7][0] = view.findViewById(R.id.lesson_module_8_1);
        lesson_module_background[7][1] = view.findViewById(R.id.lesson_module_8_2);
        lesson_module_icon[0][0] = view.findViewById(R.id.lesson_module_1_1_icon);
        lesson_module_icon[0][1] = view.findViewById(R.id.lesson_module_1_2_icon);
        lesson_module_icon[0][2] = view.findViewById(R.id.lesson_module_1_3_icon);
        lesson_module_icon[0][3] = view.findViewById(R.id.lesson_module_1_4_icon);
        lesson_module_icon[0][4] = view.findViewById(R.id.lesson_module_1_5_icon);
        lesson_module_icon[1][0] = view.findViewById(R.id.lesson_module_2_1_icon);
        lesson_module_icon[1][1] = view.findViewById(R.id.lesson_module_2_2_icon);
        lesson_module_icon[1][2] = view.findViewById(R.id.lesson_module_2_3_icon);
        lesson_module_icon[2][0] = view.findViewById(R.id.lesson_module_3_1_icon);
        lesson_module_icon[2][1] = view.findViewById(R.id.lesson_module_3_2_icon);
        lesson_module_icon[2][2] = view.findViewById(R.id.lesson_module_3_3_icon);
        lesson_module_icon[2][3] = view.findViewById(R.id.lesson_module_3_4_icon);
        lesson_module_icon[3][0] = view.findViewById(R.id.lesson_module_4_1_icon);
        lesson_module_icon[3][1] = view.findViewById(R.id.lesson_module_4_2_icon);
        lesson_module_icon[3][2] = view.findViewById(R.id.lesson_module_4_3_icon);
        lesson_module_icon[3][3] = view.findViewById(R.id.lesson_module_4_4_icon);
        lesson_module_icon[3][4] = view.findViewById(R.id.lesson_module_4_5_icon);
        lesson_module_icon[3][5] = view.findViewById(R.id.lesson_module_4_6_icon);
        lesson_module_icon[4][0] = view.findViewById(R.id.lesson_module_5_1_icon);
        lesson_module_icon[4][1] = view.findViewById(R.id.lesson_module_5_2_icon);
        lesson_module_icon[4][2] = view.findViewById(R.id.lesson_module_5_3_icon);
        lesson_module_icon[5][0] = view.findViewById(R.id.lesson_module_6_1_icon);
        lesson_module_icon[5][1] = view.findViewById(R.id.lesson_module_6_2_icon);
        lesson_module_icon[5][2] = view.findViewById(R.id.lesson_module_6_3_icon);
        lesson_module_icon[6][0] = view.findViewById(R.id.lesson_module_7_1_icon);
        lesson_module_icon[6][1] = view.findViewById(R.id.lesson_module_7_2_icon);
        lesson_module_icon[7][0] = view.findViewById(R.id.lesson_module_8_1_icon);
        lesson_module_icon[7][1] = view.findViewById(R.id.lesson_module_8_2_icon);
        welcome = view.findViewById(R.id.lesson_hello_title);
    }

    public void startUp() {
        Database db = new Database(getActivity());
        Cursor cursor = db.viewLessonStatus();
        if (cursor.getCount() > 0) {
            int i = 0;
            while (cursor.moveToNext()) {
                lessonStatus[i] = (cursor.getString(0));
                switch (lessonStatus[i]) {
                    case "Active":
                        previous_selected = i;
                        lesson_background[i].setImageDrawable(getResources().getDrawable(R.drawable.lesson_lesson_iconbackground_ongoing));
                        lesson[i].setPadding(0, 0, 0, 0);
                        lesson_title.setText(lesson_titles[i]);
                        lesson_lesson[i].setVisibility(View.VISIBLE);
                        break;
                    case "Not_Active":
                        lesson_background[i].setImageDrawable(getResources().getDrawable(R.drawable.lesson_lesson_iconbackground_locked));
                        break;
                    case "Done":
                        if (i == 7) {
                            previous_selected = i;
                            lesson[i].setPadding(0, 0, 0, 0);
                            lesson_title.setText(lesson_titles[i]);
                            lesson_lesson[i].setVisibility(View.VISIBLE);
                            lesson_lesson[i].setVisibility(View.VISIBLE);
                        }
                        lesson_background[i].setImageDrawable(getResources().getDrawable(R.drawable.lesson_lesson_iconbackground_done));
                        break;
                    default:
                        break;
                }
                i++;
            }
        }

        for (int i = 0; i < 8; i++) {
            cursor = db.viewLesson1ModuleStatus(i + 1);
            if (cursor.getCount() > 0) {
                int c = 0;
                int finalI = i;
                int finalC = c;
                while (cursor.moveToNext()) {
                    switch (cursor.getString(0)) {
                        case "Active":
                            lesson_module_background[i][c].setBackgroundResource(R.drawable.lesson_lesson_ongoing);
                            lesson_module_icon[i][c].setImageDrawable(getResources().getDrawable(R.drawable.lesson_progress));
                            break;
                        case "Not_Active":
                            lesson_module_background[i][c].setBackgroundResource(R.drawable.lesson_lesson_locked);
                            lesson_module_icon[i][c].setImageDrawable(getResources().getDrawable(R.drawable.lesson_locked));
                            lesson_module_background[i][c].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int ui_flags =
                                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                                    View view = getLayoutInflater().inflate(R.layout.error, null);
                                    Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                                    builder.setContentView(view);

                                    view.findViewById(R.id.locked_lesson_close).setOnClickListener(new View.OnClickListener() {
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
                                }
                            });

                            break;
                        case "Done":
                            lesson_module_background[i][c].setBackgroundResource(R.drawable.lesson_lesson_done);
                            lesson_module_icon[i][c].setImageDrawable(getResources().getDrawable(R.drawable.lesson_done));
                            break;
                        default:
                            break;
                    }
                    c++;
                }
            }
        }

        name = db.getName();
        String name_[] = name.split(" ", 2);
        welcome.setText("Hello " + name_[0] + "! What would you like to learn today?");
    }

    public void setOnClickListeners() {
        lesson_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lesson_logo.setClickable(false);
                Fragment_Menu_Main menu_main = new Fragment_Menu_Main();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, menu_main);
                fragmentTransaction.commit();
            }
        });

        for (int i = 0; i < lesson.length; i++) {
            int finalI = i;
            lesson[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lesson_title.setText(lesson_titles[finalI]);
                    lesson[finalI].setPadding(0, 0, 0, 0);
                    if (previous_selected != finalI) {
                        lesson[previous_selected].setPadding(15, 15, 15, 15);
                    }
                    lesson_lesson[previous_selected].setVisibility(View.GONE);
                    lesson_lesson[finalI].setVisibility(View.VISIBLE);
                    previous_selected = finalI;
                }
            });
        }

        for (int i = 0; i < lesson_module_background.length; i++) {
            for (int c = 0; c < lesson_module_background[i].length; c++) {
                int finalI = i;
                int finalC = c;
            }
        }
    }

    public void setOnClickListenersModule() {
        //Lesson 1
        lesson_module_background[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_1_1_Module module_1_1 = new Fragment_Lesson_1_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_1_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_1_2_Module module_1_2 = new Fragment_Lesson_1_2_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_1_2);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_1_3_Module module_1_3 = new Fragment_Lesson_1_3_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_1_3);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[0][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_1_4_Module module_1_4 = new Fragment_Lesson_1_4_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_1_4);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[0][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 1);
                bundle.putInt("module", 5);
                bundle.putInt("last", 1);
                bundle.putInt("number", 5);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });
        //Lesson 2
        lesson_module_background[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_2_1_Module module_2_1 = new Fragment_Lesson_2_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_2_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_2_2_Module module_2_2 = new Fragment_Lesson_2_2_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_2_2);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 2);
                bundle.putInt("module", 3);
                bundle.putInt("last", 1);
                bundle.putInt("number", 8);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });
        //Lesson 3
        lesson_module_background[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_3_1_Module module_3_1 = new Fragment_Lesson_3_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_3_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_3_2_Module module_3_2 = new Fragment_Lesson_3_2_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_3_2);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_3_3_Module module_3_3 = new Fragment_Lesson_3_3_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_3_3);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[2][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 3);
                bundle.putInt("module", 4);
                bundle.putInt("last", 1);
                bundle.putInt("number", 12);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });
        //Lesson 4
        lesson_module_background[3][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_4_1_Module module_4_1 = new Fragment_Lesson_4_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_4_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_4_2_Module module_4_2 = new Fragment_Lesson_4_2_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_4_2);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_4_3_Module module_4_3 = new Fragment_Lesson_4_3_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_4_3);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[3][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_4_4_Module module_4_4 = new Fragment_Lesson_4_4_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_4_4);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[3][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_4_5_Module module_4_5 = new Fragment_Lesson_4_5_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_4_5);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[3][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 4);
                bundle.putInt("module", 6);
                bundle.putInt("last", 1);
                bundle.putInt("number", 18);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });
        //Lesson 5
        lesson_module_background[4][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_5_1_Module module_5_1 = new Fragment_Lesson_5_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_5_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[4][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_5_2_Module module_5_2 = new Fragment_Lesson_5_2_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_5_2);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[4][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 5);
                bundle.putInt("module", 3);
                bundle.putInt("last", 1);
                bundle.putInt("number", 21);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });
        //Lesson 6
        lesson_module_background[5][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_6_1_Module module_6_1 = new Fragment_Lesson_6_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_6_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[5][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_6_2_Module module_6_2 = new Fragment_Lesson_6_2_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_6_2);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[5][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 6);
                bundle.putInt("module", 3);
                bundle.putInt("last", 1);
                bundle.putInt("number", 24);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });

        //Lesson 7
        lesson_module_background[6][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_7_1_Module module_7_1 = new Fragment_Lesson_7_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_7_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[6][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 7);
                bundle.putInt("module", 2);
                bundle.putInt("last", 1);
                bundle.putInt("number", 26);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });


        //Lesson 8
        lesson_module_background[7][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Lesson_8_1_Module module_8_1 = new Fragment_Lesson_8_1_Module();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_8_1);
                fragmentTransaction.commit();
            }
        });
        lesson_module_background[7][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("lesson", 8);
                bundle.putInt("module", 2);
                bundle.putInt("last", 1);
                bundle.putInt("number", 28);
                Fragment_Lesson_Module_Quiz module_quiz = new Fragment_Lesson_Module_Quiz();
                module_quiz.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Lesson.this);
                fragmentTransaction.add(android.R.id.content, module_quiz);
                fragmentTransaction.commit();
            }
        });
    }
}




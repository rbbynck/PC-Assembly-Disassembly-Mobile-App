package com.example.thesis;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.AutoSizeableTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Fragment_Lesson_Module_Quiz extends Fragment {

    Fragment_Lesson_1_1_Module module_1_1 = new Fragment_Lesson_1_1_Module();
    Fragment_Lesson_1_2_Module module_1_2 = new Fragment_Lesson_1_2_Module();
    Fragment_Lesson_1_3_Module module_1_3 = new Fragment_Lesson_1_3_Module();
    Fragment_Lesson_1_4_Module module_1_4 = new Fragment_Lesson_1_4_Module();
    Fragment_Lesson_2_1_Module module_2_1 = new Fragment_Lesson_2_1_Module();
    Fragment_Lesson_2_2_Module module_2_2 = new Fragment_Lesson_2_2_Module();
    Fragment_Lesson_3_1_Module module_3_1 = new Fragment_Lesson_3_1_Module();
    Fragment_Lesson_3_2_Module module_3_2 = new Fragment_Lesson_3_2_Module();
    Fragment_Lesson_3_3_Module module_3_3 = new Fragment_Lesson_3_3_Module();
    Fragment_Lesson_4_1_Module module_4_1 = new Fragment_Lesson_4_1_Module();
    Fragment_Lesson_4_2_Module module_4_2 = new Fragment_Lesson_4_2_Module();
    Fragment_Lesson_4_3_Module module_4_3 = new Fragment_Lesson_4_3_Module();
    Fragment_Lesson_4_4_Module module_4_4 = new Fragment_Lesson_4_4_Module();
    Fragment_Lesson_4_5_Module module_4_5 = new Fragment_Lesson_4_5_Module();
    Fragment_Lesson_5_1_Module module_5_1 = new Fragment_Lesson_5_1_Module();
    Fragment_Lesson_5_2_Module module_5_2 = new Fragment_Lesson_5_2_Module();
    Fragment_Lesson_6_1_Module module_6_1 = new Fragment_Lesson_6_1_Module();
    Fragment_Lesson_6_2_Module module_6_2 = new Fragment_Lesson_6_2_Module();
    Fragment_Lesson_7_1_Module module_7_1 = new Fragment_Lesson_7_1_Module();
    Fragment_Lesson_8_1_Module module_8_1 = new Fragment_Lesson_8_1_Module();
    Fragment_Menu_Lesson fragment = new Fragment_Menu_Lesson();

    //Timer
    ConstraintLayout timer_layout;
    TextView timer;
    long duration = 0;
    CountDownTimer countDownTimer;
    //progress
    int[] student_progress = {3, 7, 10, 14, 17, 21, 24, 28, 31, 35, 38, 42, 45, 49, 52, 56, 59, 63, 66, 70, 73, 77, 80, 84, 87, 91, 95, 100};
    int[][] student_lesson_progress = {{20, 40, 60, 80, 100}, {33, 64, 100}, {25, 50, 75, 100}, {17, 33, 49, 65, 81, 100}, {33, 64, 100}, {33, 64, 100}, {50, 100}, {50, 100}};
    int module, lesson, id, number_of_question, total_score, last;
    int question_number = 1;
    int try_no = new Random().nextInt((4));
    int currently_selected = 0;
    int correct_answers[], user_answers[];
    int number;
    String questions[];
    TextView quiz_number, question, quiz_next, done_result, result_done, result_review, total_questions, result_text, result_score;
    ImageView review_back, logo, back_lesson;
    LinearLayout quiz_layout, done_layout, result_layout, review_layout, review_parent;
    ScrollView review_scrollview;
    LinearLayout quiz_option[];
    TextView quiz_option_option[];
    ImageView quiz_option_cancel[];
    Database database;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Vibrator vibrator;
    Boolean active = false;
    //Start Layout
    LinearLayout start_layout;
    TextView start_text, start_start;


    public Fragment_Lesson_Module_Quiz() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_module_quiz, container, false);

        Bundle bundle = this.getArguments();
        module = bundle.getInt("module");
        lesson = bundle.getInt("lesson");
        last = bundle.getInt("last", 0);
        number = bundle.getInt("number", 0);

        database = new Database(getActivity());
        while (try_no == 0) {
            try_no = new Random().nextInt((4));
        }

        number_of_question = database.numberOfQuestions(lesson, module, try_no);
        correct_answers = new int[number_of_question];
        user_answers = new int[number_of_question];
        questions = new String[number_of_question];
        quiz_option = new LinearLayout[3];
        quiz_option_option = new TextView[3];
        quiz_option_cancel = new ImageView[3];

        initialization(view);
        onClickListener();
        gettingData();

        if (last == 1) {
            start_text.setText("You'll be given a 3 minutes complete the quiz, \n failed to do so is equivalent to failing the test.\n To pass the test, you need to have a zero wrong answer.");
        }
        return view;
    }

    public void initialization(View view) {
        //Initialization
        start_layout = view.findViewById(R.id.quiz_start_layout);
        start_text = view.findViewById(R.id.quiz_start_text);
        start_start = view.findViewById(R.id.quiz_start_start);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        back_lesson = view.findViewById(R.id.quiz_backtolesson);
        logo = view.findViewById(R.id.quiz_logo);
        quiz_layout = view.findViewById(R.id.quiz_question_layout);
        done_layout = view.findViewById(R.id.quiz_done_layout);
        result_layout = view.findViewById(R.id.quiz_result_layout);
        review_layout = view.findViewById(R.id.quiz_review_layout);
        review_parent = view.findViewById(R.id.quiz_review_parent);
        done_result = view.findViewById(R.id.quiz_done_result);
        result_done = view.findViewById(R.id.quiz_result_done);
        result_review = view.findViewById(R.id.quiz_result_review);
        result_text = view.findViewById(R.id.quiz_result_result_text);
        result_score = view.findViewById(R.id.quiz_result_score);
        review_back = view.findViewById(R.id.quiz_review_back);
        quiz_number = view.findViewById(R.id.quiz_question_number);
        total_questions = view.findViewById(R.id.quiz_total_question_number);
        question = view.findViewById(R.id.quiz_question);
        quiz_next = view.findViewById(R.id.quiz_quiz_next);
        review_scrollview = view.findViewById(R.id.quiz_review_scrollview);
        //Array initialization
        for (int i = 0; i < quiz_option.length; i++) {
            id = view.getResources().getIdentifier(
                    "quiz_option_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            quiz_option[i] = view.findViewById(id);

            id = view.getResources().getIdentifier(
                    "quiz_option_" + (i + 1) + "_option",
                    "id",
                    getActivity().getPackageName());
            quiz_option_option[i] = view.findViewById(id);

            id = view.getResources().getIdentifier(
                    "quiz_option_" + (i + 1) + "_cancel",
                    "id",
                    getActivity().getPackageName());
            quiz_option_cancel[i] = view.findViewById(id);
        }

        //Set question number. EX: 1/3
        total_questions.setText("/" + number_of_question);
        //Timer
        timer_layout = view.findViewById(R.id.quiz_timer_layout);
        timer = view.findViewById(R.id.quiz_timer);
    }

    public void onClickListener() {
        start_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_layout.setVisibility(View.GONE);
                quiz_layout.setVisibility(View.VISIBLE);
                timer_layout.setVisibility(View.VISIBLE);
                timer();
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Menu_Main fragment = new Fragment_Menu_Main();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Lesson_Module_Quiz.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        for (int i = 0; i < quiz_option.length; i++) {
            int final_i = i;
            quiz_option[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currently_selected = final_i + 1;
                    quiz_option[final_i].setBackgroundResource(R.drawable.quiz_option_selected);
                    quiz_option_cancel[final_i].setImageDrawable(getResources().getDrawable(R.drawable.quiz_cancel));
                    quiz_option_option[final_i].setTextColor(getResources().getColor(R.color.white));

                    for (int c = 0; c < quiz_option.length; c++) {
                        if (c != final_i) {
                            quiz_option[c].setClickable(false);
                        }
                    }
                }
            });

            quiz_option_cancel[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currently_selected > 0) {
                        currently_selected = 0;
                        quiz_option[final_i].setBackgroundResource(0);
                        quiz_option_cancel[final_i].setImageResource(0);
                        quiz_option_option[final_i].setTextColor(getResources().getColor(R.color.black_primary));

                        for (int c = 0; c < quiz_option.length; c++) {
                            if (c != final_i) {
                                quiz_option[c].setClickable(true);
                            }
                        }
                    }
                }
            });
        }
        //Quiz layout. Next button, if done it will go to the done layout
        quiz_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currently_selected > 0) {
                    user_answers[question_number - 1] = currently_selected;

                    if (question_number == number_of_question) {
                        timer_layout.setVisibility(View.GONE);
                        countDownTimer.cancel();
                        quiz_layout.setVisibility(View.GONE);
                        done_layout.setVisibility(View.VISIBLE);

                        for (int i = 0; i < correct_answers.length; i++) {
                            if (correct_answers[i] == user_answers[i]) {
                                total_score++;
                            }
                        }
                    } else {
                        for (int i = 0; i < quiz_option.length; i++) {
                            quiz_option[i].setVisibility(View.GONE);
                        }
                        currently_selected = 0;
                        question_number++;
                        question.setText(questions[question_number - 1]);
                        quiz_number.setText("" + question_number);

                        for (int i = 0; i < 3; i++) {
                            quiz_option[i].setVisibility(View.GONE);
                        }

                        for (int i = 0; i < database.numberOfOptions(lesson, module, question_number, try_no); i++) {
                            quiz_option[i].setVisibility(View.VISIBLE);
                            quiz_option[i].setBackgroundResource(0);
                            quiz_option_option[i].setText(database.viewOption(lesson, module, question_number, (i + 1), try_no));
                            quiz_option_option[i].setTextColor(getResources().getColor(R.color.black_primary));
                            quiz_option_cancel[i].setImageResource(0);
                            quiz_option[i].setClickable(true);
                        }
                    }
                } else {
                    vibrator.vibrate(100);
                }
            }
        });
        //Go to the result layout
        done_result.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                done_layout.setVisibility(View.GONE);
                result_layout.setVisibility(View.VISIBLE);

                result_score.setText(total_score + "/" + number_of_question);

                if (total_score == question_number) {
                    //Change Layout
                    result_layout.setBackgroundResource(R.drawable.quiz_result_passed_background);
                    result_score.setBackgroundResource(R.drawable.quiz_passed_circle);
                    result_text.setText("Passed");
                    result_done.setText("Done");
                    //Update database Quiz Log
                    database.insertQuizLog(lesson, module, "Passed");
                    quizLog(lesson, module, "Passed");
                    //Update Database
                    switch (database.viewModuleStatus(lesson + "_" + module)) {
                        case "Active":
                            active = true;
                            database.updateModuleStatus(lesson + "_" + module, "Done");
                            database.updateLessonProgress(lesson, student_lesson_progress[lesson - 1][module - 1]);
                            database.updateAppProgress(student_progress[number - 1]);

                            if (last == 1) {
                                switch (database.viewLessonStatus(lesson)) {
                                    case "Active":
                                        database.updateLessonStatus(lesson, "Done");
                                        if (lesson != 8) {
                                            database.updateStatus("Lesson " + (lesson + 1));
                                            database.updateLessonStatus(lesson + 1, "Active");
                                            database.updateModuleStatus((lesson + 1) + "_" + 1, "Active");
                                        } else {
                                            database.updateStatus("Simulation");
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                database.updateModuleStatus(lesson + "_" + (module + 1), "Active");
                            }
                            break;
                        default:
                            break;
                    }

                    if (isConnectedToInternet() == true) {
                        pass_data();
                    }
                } else {
                    result_layout.setBackgroundResource(R.drawable.quiz_result_failed_background);
                    result_score.setBackgroundResource(R.drawable.quiz_failed_circle);
                    result_text.setText("Failed");
                    result_done.setText("Try Again");
                    database.insertQuizLog(lesson, module, "Failed");
                    quizLog(lesson, module, "Failed");
                }
            }
        });
        //Result layout either it is done or try again
        result_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result_done.getText() == "Done") {
                    if (active == true) {
                        Bundle bundle = new Bundle();
                        if (last == 1) {
                            bundle.putString("text", "Lesson");
                        } else {
                            bundle.putString("text", "Module");
                        }
                        Fragment_Menu_Lesson_Congrats fragment = new Fragment_Menu_Lesson_Congrats();
                        fragment.setArguments(bundle);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Lesson_Module_Quiz.this);
                        fragmentTransaction.add(android.R.id.content, fragment);
                        fragmentTransaction.commit();
                    } else {
                        Fragment_Menu_Lesson lesson = new Fragment_Menu_Lesson();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Lesson_Module_Quiz.this);
                        fragmentTransaction.add(android.R.id.content, lesson);
                        fragmentTransaction.commit();
                    }
                } else if (result_done.getText() == "Try Again") {
                    timer_layout.setVisibility(View.VISIBLE);
                    timer();
                    quiz_layout.setVisibility(View.VISIBLE);
                    result_layout.setVisibility(View.GONE);
                    total_score = 0;

                    for (int i = 0; i < correct_answers.length; i++) {
                        user_answers[i] = 0;
                    }

                    question_number = 1;
                    for (int i = 0; i < quiz_option.length; i++) {
                        quiz_option[i].setVisibility(View.GONE);
                    }

                    currently_selected = 0;
                    quiz_number.setText("" + question_number);
                    question.setText(questions[question_number - 1]);
                    for (int i = 0; i < database.numberOfOptions(lesson, module, question_number, try_no); i++) {
                        quiz_option[i].setVisibility(View.VISIBLE);
                        quiz_option[i].setBackgroundResource(0);
                        quiz_option_option[i].setText(database.viewOption(lesson, module, question_number, (i + 1), try_no));
                        quiz_option_option[i].setTextColor(getResources().getColor(R.color.black_primary));
                        quiz_option_cancel[i].setImageResource(0);
                        quiz_option[i].setClickable(true);
                    }

                    int old_try = try_no;

                    try_no = new Random().nextInt((4));
                    while (old_try == try_no || try_no == 0) {
                        try_no = new Random().nextInt((4));
                    }

                    gettingData();
                }
            }
        });
        //Review layout view
        result_review.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                result_layout.setVisibility(View.GONE);
                review_layout.setVisibility(View.VISIBLE);

                for (int i = 0; i < number_of_question; i++) {
                    LinearLayout linearLayout = new LinearLayout(getActivity());
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    linearLayout.setPadding(0, 0, 0, 20);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    //Textview. EX: "Question 1"
                    TextView question_text = new TextView(getActivity());
                    question_text.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    )); // set Parameters
                    question_text.setPadding(50, 10, 0, 10); //set Paddings
                    question_text.setText(questions[i]); // set Question text
                    question_text.setTextColor(Color.parseColor("#9C9292")); //set Text Color
                    //Add to the linear view
                    linearLayout.addView(question_text);
                    //Line
                    View line = new View(getActivity());
                    line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    line.setBackgroundResource(R.color.gray);
                    //Add to the linear view
                    linearLayout.addView(line);
                    //options
                    for (int c = 0; c < database.numberOfOptions(lesson, module, (i + 1), try_no); c++) {
                        TextView option = new TextView(getActivity());
                        option.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        )); // set Parameters
                        option.setPadding(50, 5, 0, 10); //set Paddings
                        option.setTextColor(getResources().getColor(R.color.black_primary)); //set Text Color
                        option.setTextSize(20);

                        if (Build.VERSION.SDK_INT >= 27) {
                            option.setAutoSizeTextTypeUniformWithConfiguration(
                                    10, 20, 2, 1);
                        } else if (option instanceof AutoSizeableTextView) {
                            ((AutoSizeableTextView) option).setAutoSizeTextTypeUniformWithConfiguration(
                                    10, 20, 2, 1);
                        }

                        if (c == 0) {
                            option.setText("A. " + database.viewOption(lesson, module, (i + 1), (c + 1), try_no)); // set Option  text
                        } else if (c == 1) {
                            option.setText("B. " + database.viewOption(lesson, module, (i + 1), (c + 1), try_no)); // set Option text
                        } else if (c == 2) {
                            option.setText("C. " + database.viewOption(lesson, module, (i + 1), (c + 1), try_no)); // set Option text
                        }

                        if (user_answers[i] == (c + 1)) {
                            if (user_answers[i] == correct_answers[i]) {
                                option.setBackgroundResource(R.color.result_passed_background_option);
                            } else {
                                option.setBackgroundResource(R.color.result_failed_background_option);
                            }
                        }
                        //Add to the linear view
                        linearLayout.addView(option);
                    }

                    review_parent.addView(linearLayout);
                }

            }
        });
        //Review layout back button
        review_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_layout.scrollTo(0, 0);
                review_layout.setVisibility(View.GONE);
                result_layout.setVisibility(View.VISIBLE);
                review_parent.removeAllViews();
            }
        });
        //Go back to the previous lesson
        back_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lesson == 1) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_1_1);
                    } else if (module == 2) {
                        fragmentTransaction.add(android.R.id.content, module_1_2);
                    } else if (module == 3) {
                        fragmentTransaction.add(android.R.id.content, module_1_3);
                    } else if (module == 4) {
                        fragmentTransaction.add(android.R.id.content, module_1_4);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                } else if (lesson == 2) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_2_1);
                    } else if (module == 2) {
                        fragmentTransaction.add(android.R.id.content, module_2_2);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                } else if (lesson == 3) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_3_1);
                    } else if (module == 2) {
                        fragmentTransaction.add(android.R.id.content, module_3_2);
                    } else if (module == 3) {
                        fragmentTransaction.add(android.R.id.content, module_3_3);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                } else if (lesson == 4) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_4_1);
                    } else if (module == 2) {
                        fragmentTransaction.add(android.R.id.content, module_4_2);
                    } else if (module == 3) {
                        fragmentTransaction.add(android.R.id.content, module_4_3);
                    } else if (module == 4) {
                        fragmentTransaction.add(android.R.id.content, module_4_4);
                    } else if (module == 5) {
                        fragmentTransaction.add(android.R.id.content, module_4_5);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                } else if (lesson == 5) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_5_1);
                    } else if (module == 2) {
                        fragmentTransaction.add(android.R.id.content, module_5_2);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                } else if (lesson == 6) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_6_1);
                    } else if (module == 2) {
                        fragmentTransaction.add(android.R.id.content, module_6_2);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                } else if (lesson == 7) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_7_1);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                } else if (lesson == 8) {
                    if (module == 1) {
                        fragmentTransaction.add(android.R.id.content, module_8_1);
                    } else {
                        fragmentTransaction.add(android.R.id.content, fragment);
                    }
                }

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Lesson_Module_Quiz.this);
                fragmentTransaction.commit();
            }
        });
    }

    public void timer() {
        if (last == 1) {
            duration = TimeUnit.MINUTES.toMillis(4);
        } else {
            duration = TimeUnit.MINUTES.toMillis(1) + TimeUnit.SECONDS.toMillis(30);
        }

        countDownTimer = new CountDownTimer(duration, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                timer_layout.setVisibility(View.GONE);
                vibrator.vibrate(100);
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Times Up!!");
                alertDialog.setMessage("You have reach the time limit, go back to the lesson and try again.");
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (lesson == 1) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_1_1);
                            } else if (module == 2) {
                                fragmentTransaction.add(android.R.id.content, module_1_2);
                            } else if (module == 3) {
                                fragmentTransaction.add(android.R.id.content, module_1_3);
                            } else if (module == 4) {
                                fragmentTransaction.add(android.R.id.content, module_1_4);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        } else if (lesson == 2) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_2_1);
                            } else if (module == 2) {
                                fragmentTransaction.add(android.R.id.content, module_2_2);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        } else if (lesson == 3) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_3_1);
                            } else if (module == 2) {
                                fragmentTransaction.add(android.R.id.content, module_3_2);
                            } else if (module == 3) {
                                fragmentTransaction.add(android.R.id.content, module_3_3);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        } else if (lesson == 4) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_4_1);
                            } else if (module == 2) {
                                fragmentTransaction.add(android.R.id.content, module_4_2);
                            } else if (module == 3) {
                                fragmentTransaction.add(android.R.id.content, module_4_3);
                            } else if (module == 4) {
                                fragmentTransaction.add(android.R.id.content, module_4_4);
                            } else if (module == 5) {
                                fragmentTransaction.add(android.R.id.content, module_4_5);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        } else if (lesson == 5) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_5_1);
                            } else if (module == 2) {
                                fragmentTransaction.add(android.R.id.content, module_5_2);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        } else if (lesson == 6) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_6_1);
                            } else if (module == 2) {
                                fragmentTransaction.add(android.R.id.content, module_6_2);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        } else if (lesson == 7) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_7_1);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        } else if (lesson == 8) {
                            if (module == 1) {
                                fragmentTransaction.add(android.R.id.content, module_8_1);
                            } else {
                                fragmentTransaction.add(android.R.id.content, fragment);
                            }
                        }

                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Lesson_Module_Quiz.this);
                        fragmentTransaction.commit();
                    }
                });
            }
        }.start();
    }

    public void gettingData() {
        //Get the correct answers and questions from the database
        for (int i = 0; i < number_of_question; i++) {
            correct_answers[i] = database.answer(lesson, module, (i + 1), try_no);
            questions[i] = database.viewQuestion(lesson, module, (i + 1), try_no);
        }

        question.setText(questions[0]);
        quiz_number.setText("1");

        for (int i = 0; i < 3; i++) {
            quiz_option[i].setVisibility(View.GONE);
        }

        for (int i = 0; i < database.numberOfOptions(lesson, module, question_number, try_no); i++) {
            quiz_option[i].setVisibility(View.VISIBLE);
            quiz_option[i].setBackgroundResource(0);
            quiz_option_option[i].setText(database.viewOption(lesson, module, question_number, (i + 1), try_no));
            quiz_option_option[i].setTextColor(getResources().getColor(R.color.black_primary));
            quiz_option_cancel[i].setImageResource(0);
            quiz_option[i].setClickable(true);
        }
    }

    private void pass_data() {
        class updateData extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_update_lesson.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("lesson", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lesson), "UTF-8") + "&"
                            + URLEncoder.encode("module", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(module), "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(student_lesson_progress[lesson - 1][module - 1]), "UTF-8") + "&"
                            + URLEncoder.encode("progress", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(student_progress[number - 1]), "UTF-8") + "&"
                            + URLEncoder.encode("student_id", "UTF-8") + "=" + URLEncoder.encode(database.get_ID(), "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        updateData getJSON = new updateData();
        getJSON.execute();
    }

    private void quizLog(int lesson, int module, String status) {
        class updateData extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String lesson_no = String.valueOf(lesson);
                    String module_no = String.valueOf(module);
                    String id = database.get_ID();

                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_update_quiz_log.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("lesson_log", "UTF-8") + "=" + URLEncoder.encode(lesson_no, "UTF-8") + "&"
                            + URLEncoder.encode("module_log", "UTF-8") + "=" + URLEncoder.encode(module_no, "UTF-8") + "&"
                            + URLEncoder.encode("status_log", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8") + "&"
                            + URLEncoder.encode("student_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        updateData getJSON = new updateData();
        getJSON.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}


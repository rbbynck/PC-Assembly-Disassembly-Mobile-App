package com.example.thesis;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Intro extends AppCompatActivity {

    Database database;
    View decorView;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        decorView();
        database = new Database(this);
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                if (database.isTableExists() == true) {
                    if (database.checkIfEmpty() == true) {
                        if (isConnectedToInternet() == true) {
                            save_progress_lesson();
                            String[][] array = database.quizLog();
                            for (int i = 0; i < array.length; i++) {
                                quizLog(array[i][0], array[i][1], array[i][2]);
                            }
                        }
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(), Login_Register.class);
                        startActivity(i);
                    }
                    finish();
                } else {
                    Intent i = new Intent(getApplicationContext(), Login_Register.class);
                    startActivity(i);
                    finish();
                    New_Database new_database = new New_Database(Intro.this);
                    new_database.insert_Module();
                    new_database.insert_Simulation();
                    new_database.insert_Trivia();
                    new_database.insert_Answers_LessonOne();
                    new_database.insert_Answers_LessonTwo();
                    new_database.insert_Answers_LessonThree();
                    new_database.insert_Answers_LessonFour();
                    new_database.insert_Answers_LessonFive();
                    new_database.insert_Answers_LessonSix();
                    new_database.insert_Answers_LessonSeven();
                    new_database.insert_Answers_LessonEight();
                    new_database.insert_Options_One();
                    new_database.insert_Options_Two();
                    new_database.insert_Options_Three();
                    new_database.insert_Options_Four();
                    new_database.insert_Options_Five();
                    new_database.insert_Options_Six();
                    new_database.insert_Options_Seven();
                    new_database.insert_Options_Eight();
                }
            }
        }, SPLASH_TIME_OUT);
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

    //PHP CODE
    private void save_progress_lesson() {
        class save_progress extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String lesson_status_1 = database.get_LessonStatus(1);
                    String lesson_status_2 = database.get_LessonStatus(2);
                    String lesson_status_3 = database.get_LessonStatus(3);
                    String lesson_status_4 = database.get_LessonStatus(4);
                    String lesson_status_5 = database.get_LessonStatus(5);
                    String lesson_status_6 = database.get_LessonStatus(6);
                    String lesson_status_7 = database.get_LessonStatus(7);
                    String lesson_status_8 = database.get_LessonStatus(8);
                    String lesson_progress_1 = String.valueOf(database.get_LessonProgress(1));
                    String lesson_progress_2 = String.valueOf(database.get_LessonProgress(2));
                    String lesson_progress_3 = String.valueOf(database.get_LessonProgress(3));
                    String lesson_progress_4 = String.valueOf(database.get_LessonProgress(4));
                    String lesson_progress_5 = String.valueOf(database.get_LessonProgress(5));
                    String lesson_progress_6 = String.valueOf(database.get_LessonProgress(6));
                    String lesson_progress_7 = String.valueOf(database.get_LessonProgress(7));
                    String lesson_progress_8 = String.valueOf(database.get_LessonProgress(8));
                    String lesson_module_1 = String.valueOf((database.get_LessonProgress(1) / 20));
                    String lesson_module_2 = String.valueOf((int) (database.get_LessonProgress(2) / 33));
                    String lesson_module_3 = String.valueOf((database.get_LessonProgress(3) / 25));
                    String lesson_module_4 = String.valueOf((int) (database.get_LessonProgress(4) / 16));
                    String lesson_module_5 = String.valueOf((int) (database.get_LessonProgress(5) / 33));
                    String lesson_module_6 = String.valueOf((int) (database.get_LessonProgress(6) / 33));
                    String lesson_module_7 = String.valueOf((database.get_LessonProgress(7) / 50));
                    String lesson_module_8 = String.valueOf((database.get_LessonProgress(8) / 50));
                    String status = database.get_Status();
                    String progress = String.valueOf(database.get_Progress());
                    String id = database.get_ID();


                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_update_lesson.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("lesson_status_1", "UTF-8") + "=" + URLEncoder.encode(lesson_status_1, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_status_2", "UTF-8") + "=" + URLEncoder.encode(lesson_status_2, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_status_3", "UTF-8") + "=" + URLEncoder.encode(lesson_status_3, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_status_4", "UTF-8") + "=" + URLEncoder.encode(lesson_status_4, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_status_5", "UTF-8") + "=" + URLEncoder.encode(lesson_status_5, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_status_6", "UTF-8") + "=" + URLEncoder.encode(lesson_status_6, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_status_7", "UTF-8") + "=" + URLEncoder.encode(lesson_status_7, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_status_8", "UTF-8") + "=" + URLEncoder.encode(lesson_status_8, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_1", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_1, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_2", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_2, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_3", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_3, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_4", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_4, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_5", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_5, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_6", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_6, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_7", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_7, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_progress_8", "UTF-8") + "=" + URLEncoder.encode(lesson_progress_8, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_1", "UTF-8") + "=" + URLEncoder.encode(lesson_module_1, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_2", "UTF-8") + "=" + URLEncoder.encode(lesson_module_2, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_3", "UTF-8") + "=" + URLEncoder.encode(lesson_module_3, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_4", "UTF-8") + "=" + URLEncoder.encode(lesson_module_4, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_5", "UTF-8") + "=" + URLEncoder.encode(lesson_module_5, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_6", "UTF-8") + "=" + URLEncoder.encode(lesson_module_6, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_7", "UTF-8") + "=" + URLEncoder.encode(lesson_module_7, "UTF-8") + "&"
                            + URLEncoder.encode("lesson_module_8", "UTF-8") + "=" + URLEncoder.encode(lesson_module_8, "UTF-8") + "&"
                            + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8") + "&"
                            + URLEncoder.encode("progress", "UTF-8") + "=" + URLEncoder.encode(progress, "UTF-8") + "&"
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

        save_progress getJSON = new save_progress();
        getJSON.execute();
    }

    private void quizLog(String lesson, String module, String status) {
        class quizLog extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_update_quiz_log.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("lesson_log", "UTF-8") + "=" + URLEncoder.encode(lesson, "UTF-8") + "&"
                            + URLEncoder.encode("module_log", "UTF-8") + "=" + URLEncoder.encode(module, "UTF-8") + "&"
                            + URLEncoder.encode("status_log", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8") + "&"
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

        quizLog getJSON = new quizLog();
        getJSON.execute();
    }
    //

    //INTERNET CONNECTION
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
    //END
}

package com.example.thesis;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Fragment_Menu_AddOn_Lesson extends Fragment {

    LinearLayout container;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView logo;
    int number = 0;
    Database database;
    TextView add_on_title;
    LinearLayout loading;


    public Fragment_Menu_AddOn_Lesson() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_professor_lesson, container, false);

        database = new Database(getActivity());

        initialization(view);
        onClickListener();
        getProfName(view);
        getLesson(view);
        return view;
    }

    public void initialization(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        logo = view.findViewById(R.id.prof_logo);
        add_on_title = view.findViewById(R.id.addon_title);
        loading = view.findViewById(R.id.professor_constraintlayout);
    }

    public void onClickListener() {
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Menu_Main menu_progress = new Fragment_Menu_Main();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_AddOn_Lesson.this);
                fragmentTransaction.add(android.R.id.content, menu_progress);
                fragmentTransaction.commit();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createLessonContainer(View view, String title, String url) {
        container = view.findViewById(R.id.professor_lesson_container);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setBackgroundResource(R.drawable.lesson_lesson_ongoing);
        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 25);
        linearLayout.setLayoutParams(params);
        linearLayout.setBackgroundResource(R.drawable.lesson_lesson_ongoing);

        LinearLayout linearLayout_2 = new LinearLayout(getContext());
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
        linearLayout_2.setLayoutParams(params);
        linearLayout_2.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView = new ImageView(getContext());
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 6);
        imageView.setLayoutParams(params);
        imageView.setPadding(2, 2, 2, 2);
        imageView.setImageResource(R.drawable.lesson_progress);

        TextView textView = new TextView(getContext());
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1);
        textView.setLayoutParams(params);
        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        textView.setGravity(Gravity.LEFT);
        textView.setPadding(2, 24, 0, 24);
        textView.setText(title);
        textView.setTextColor(getResources().getColor(R.color.white));


        linearLayout_2.addView(imageView);
        linearLayout_2.addView(textView);

        linearLayout.addView(linearLayout_2);

        container.addView(linearLayout);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PDFActivity.class);
                i.putExtra("PATH", "https://pcad-gbbs.000webhostapp.com/files-upload/" + url);
                startActivity(i);
            }
        });

    }

    //PHP
    public void getLesson(View view) {
        class quizLog extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/show_pdf.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id_class", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(database.getClass_No()), "UTF-8");

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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String s) {
                if (s.matches("0 results")) {

                } else {
                    getLessons(s, view);
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        quizLog getJSON = new quizLog();
        getJSON.execute();
    }

    public void getProfName(View view) {
        class quizLog extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/prof_name.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id_class", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(database.getClass_No()), "UTF-8");

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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String s) {
                if (s.matches("No prof existing") || s.matches("No prof existing")) {

                } else {
                    add_on_title.setText("Prof." + s + "'s Lesson");
                }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getLessons(String string, View view) {
        JSONArray jsonArray = null;
        try {
            if (string.matches("")) {
                loading.setVisibility(View.GONE);
            } else {
                jsonArray = new JSONArray(string);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    createLessonContainer(view, (String) jsonObject.get("title"), (String) jsonObject.get("url"));
                    number++;
                }
                loading.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(Fragment_Menu_AddOn_Lesson fragment) {
        ConnectivityManager connectivityManager = (ConnectivityManager) fragment.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi_connection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile_connection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi_connection != null && wifi_connection.isConnected()) || (mobile_connection != null && mobile_connection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

}



package com.example.thesis;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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


public class Fragment_Menu_Lesson_Congrats extends Fragment {

    TextView text, next;
    String string;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Database database;

    public Fragment_Menu_Lesson_Congrats() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_lesson_congrats, container, false);

        Bundle bundle = this.getArguments();
        string = bundle.getString("text");
        database = new Database(getActivity());

        initialization(view);
        if (string == "Module") {
            text.setText("A new topic is now available.");
        } else if (string == "Lesson") {
            text.setText("A new lesson is now available.");
        } else if (string == "Last") {
            text.setText("You're almost there. You can now practice your knowledge by playing the simulation tutorial mode.");
        } else if (string == "Done") {
            text.setText("You have finished the crash course, click the next button to see your certificate.");
        }

        onClickListener();

        return view;
    }

    public void initialization(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        next = view.findViewById(R.id.lesson_congrats_next);
        text = view.findViewById(R.id.lesson_congrats_text);
    }

    public void onClickListener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (string == "Done") {
                    database.insertCertificate("Yes");
                    Fragment_Certificate certificate = new Fragment_Certificate();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.hide(Fragment_Menu_Lesson_Congrats.this);
                    fragmentTransaction.add(android.R.id.content, certificate);
                } else {
                    Fragment_Menu_Lesson lesson = new Fragment_Menu_Lesson();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.hide(Fragment_Menu_Lesson_Congrats.this);
                    fragmentTransaction.add(android.R.id.content, lesson);
                }
                fragmentTransaction.commit();
            }
        });
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

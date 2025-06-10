package com.example.thesis;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Register extends AppCompatActivity {

    View decorView;
    Database database;
    String token;
    LinearLayout login_layout, register_layout, forgot_email_layout, forgot_code_layout, forgot_password_layout, register_verification_layout;
    TextView switch_button, submit_button, forgot_password, register_verification_back;
    EditText login_username, login_password, register_name, register_email, register_class, register_username, register_password, register_confirm, forgot_code, forgot_email,
            newpassword_password, newpassword_confirm, register_verification_code;
    AlertDialog alertDialog;
    String student_id;
    int ui_flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    String string_register_name, string_register_email, string_register_class, string_register_username, string_register_password;
    String string_register_class_id;
    CheckBox privacy;
    LinearLayout loading;
    TextView privacy_policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    token = task.getResult().getToken();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        decorView();
        initialization();
        setOnClickListener();
    }

    public void initialization() {
        privacy_policy = findViewById(R.id.login_register_privacy_link);
        privacy = findViewById(R.id.login_register_privacy);
        loading = findViewById(R.id.login_loading);
        alertDialog = new AlertDialog.Builder(Login_Register.this).create();
        database = new Database(getApplicationContext());
        login_layout = findViewById(R.id.login_login_layout);
        register_layout = findViewById(R.id.login_register_layout);
        forgot_code_layout = findViewById(R.id.login_forgot_code_layout);
        forgot_email_layout = findViewById(R.id.login_forgot_email_layout);
        forgot_password_layout = findViewById(R.id.login_newpassword_layout);
        switch_button = findViewById(R.id.login_switch_button);
        submit_button = findViewById(R.id.login_submit);
        login_username = findViewById(R.id.login_login_username);
        login_password = findViewById(R.id.login_login_password);
        register_name = findViewById(R.id.login_register_name);
        register_email = findViewById(R.id.login_register_email);
        register_class = findViewById(R.id.login_register_classcode);
        register_username = findViewById(R.id.login_register_username);
        register_password = findViewById(R.id.login_register_password);
        register_confirm = findViewById(R.id.login_register_confirm);
        forgot_password = findViewById(R.id.login_forgot);
        forgot_code = findViewById(R.id.login_forgot_code);
        forgot_email = findViewById(R.id.login_forgot_email);
        newpassword_password = findViewById(R.id.login_newpassword_password);
        newpassword_confirm = findViewById(R.id.login_newpassword_confirm);
        register_verification_layout = findViewById(R.id.login_register_verification_layout);
        register_verification_back = findViewById(R.id.login_register_verification_back);
        register_verification_code = findViewById(R.id.login_register_verification_code);
    }

    public void setOnClickListener() {
        //Opening Privacy Policy
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.prviacy_policy, null);
                Dialog builder = new Dialog(Login_Register.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                builder.setContentView(view);

                view.findViewById(R.id.privacy_policy_close).setOnClickListener(new View.OnClickListener() {
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
        //switching layouts
        switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_button.getText().toString().equals("Register")) {
                    login_layout.setVisibility(View.GONE);
                    register_layout.setVisibility(View.VISIBLE);
                    forgot_password.setVisibility(View.GONE);
                    switch_button.setText("Login");
                    submit_button.setText("Register");
                } else {
                    register_layout.setVisibility(View.GONE);
                    forgot_password.setVisibility(View.VISIBLE);
                    forgot_code_layout.setVisibility(View.GONE);
                    forgot_email_layout.setVisibility(View.GONE);
                    login_layout.setVisibility(View.VISIBLE);
                    switch_button.setText("Register");
                    submit_button.setText("Login");
                }
            }
        });
        //Submit function
        submit_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet() == false) {
                    alertDialog.setTitle("Internet Connection Problem");
                    alertDialog.setMessage("Please connect to an internet");
                    alertDialog.show();
                } else {
                    if (login_layout.getVisibility() == View.VISIBLE) {
                        //login
                        if (login_username.getText().toString().equals("") || login_password.getText().toString().equals("")) {
                            alertDialog.setTitle("Login Failed");
                            alertDialog.setMessage("Please fill all the areas");
                            alertDialog.show();
                        } else {
                            loading.setVisibility(View.VISIBLE);
                            php_Login(login_username.getText().toString(), login_password.getText().toString(), token);
                        }
                    } else if (register_layout.getVisibility() == View.VISIBLE) {
                        //register
                        if (register_name.getText().toString().equals("") || register_email.getText().toString().equals("") || register_class.getText().toString().equals("") ||
                                register_username.getText().toString().equals("") || register_password.getText().toString().equals("") || register_confirm.getText().toString().equals("")) {
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please fill all the fields");
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(true);
                        } else {
                            string_register_name = register_name.getText().toString();
                            string_register_email = register_email.getText().toString();
                            string_register_class = register_class.getText().toString();
                            string_register_username = register_username.getText().toString();
                            string_register_password = register_password.getText().toString();
                            String email = register_email.getText().toString().trim();
                            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                            if (!(email.matches(emailPattern))) {
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Please give us a real email");
                                alertDialog.show();
                                alertDialog.setCanceledOnTouchOutside(true);
                            } else if (!(register_password.getText().toString().equals(register_confirm.getText().toString()))) {
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Password doesn't match");
                                alertDialog.show();
                                alertDialog.setCanceledOnTouchOutside(true);
                            } else if (register_password.getText().toString().length() < 8 && !isValidPassword(register_password.getText().toString())) {
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Please enter a strong password.");
                                alertDialog.show();
                                alertDialog.setCanceledOnTouchOutside(true);
                            } else if (register_username.getText().toString().length() < 8) {
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Username should be greater than 8 character.");
                                alertDialog.show();
                                alertDialog.setCanceledOnTouchOutside(true);
                            } else if (privacy.isChecked() == false) {
                                alertDialog.setTitle("Privacy Policy");
                                alertDialog.setMessage("You must have to agree to the privacy policy before we let you use the app.");
                                alertDialog.show();
                                alertDialog.setCanceledOnTouchOutside(true);
                            } else {
                                loading.setVisibility(View.VISIBLE);
                                php_Register(string_register_email, string_register_class, string_register_username);
                            }
                        }
                    } else if (forgot_email_layout.getVisibility() == View.VISIBLE) {
                        String email = forgot_email.getText().toString().trim();
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (!(email.matches(emailPattern))) {
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please give us a real email");
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(true);
                        } else {
                            loading.setVisibility(View.VISIBLE);
                            php_Forgot_Email(email);
                        }
                    } else if (forgot_code_layout.getVisibility() == View.VISIBLE) {
                        if (forgot_code.getText().toString().equals("")) {
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please fill out the field");
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(true);
                        } else {
                            loading.setVisibility(View.VISIBLE);
                            php_Forgot_Code(forgot_code.getText().toString());
                        }
                    } else if (forgot_password_layout.getVisibility() == View.VISIBLE) {
                        if (newpassword_password.getText().toString().equals("") || newpassword_confirm.getText().toString().equals("")) {
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please fill out the field");
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(true);
                        } else {
                            if (newpassword_password.getText().toString().equals(newpassword_confirm.getText().toString())) {
                                php_Forgot_Password(student_id, newpassword_password.getText().toString());
                            } else {
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Password didn't match");
                                alertDialog.show();
                                alertDialog.setCanceledOnTouchOutside(true);
                            }
                        }
                    } else if (register_verification_layout.getVisibility() == View.VISIBLE) {
                        if (register_verification_code.getText().toString().equals("")) {
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please fill out the field");
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(true);
                        } else {
                            loading.setVisibility(View.VISIBLE);
                            String string_register_verification_code = register_verification_code.getText().toString();
                            php_Register_Verification(string_register_verification_code, string_register_name, string_register_email, string_register_class_id, string_register_username,
                                    string_register_password, token);
                        }
                    }
                }
            }
        });
        //forgot password
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_layout.setVisibility(View.GONE);
                forgot_email_layout.setVisibility(View.VISIBLE);
                forgot_password.setVisibility(View.GONE);
                switch_button.setText("Login");
                submit_button.setText("Continue");
            }
        });
        //Register validation back button
        register_verification_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_layout.setVisibility(View.VISIBLE);
                register_verification_layout.setVisibility(View.GONE);
                submit_button.setText("Register");
            }
        });
    }

    private void php_Register(String email, String code, String username) {
        class getRegister extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_register.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.getReadTimeout();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("register", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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
                if (s.matches("Username is not available") || s.matches("Email is already in use") || s.matches("Email is not valid") || s.matches("Query Error") || s.matches("No Class Found")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Login_Register.this).create();
                    alertDialog.setTitle("Registration Failed");
                    alertDialog.setMessage(s);
                    alertDialog.show();
                    loading.setVisibility(View.GONE);
                } else {
                    registerVerification(s);
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getRegister getJSON = new getRegister();
        getJSON.execute();
    }

    private void php_Register_Verification(String verification_code, String name, String email, String id_class, String username, String password, String token) {
        class getRegister extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_register.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.getReadTimeout();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("verification", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&"
                            + URLEncoder.encode("verification_code", "UTF-8") + "=" + URLEncoder.encode(verification_code, "UTF-8") + "&"
                            + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("id_class", "UTF-8") + "=" + URLEncoder.encode(id_class, "UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                            + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
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
                if (s.matches("Invalid Code")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Login_Register.this).create();
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage(s);
                    alertDialog.show();
                    loading.setVisibility(View.GONE);
                } else {
                    try {
                        database_Register(s, name, email, username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getRegister getJSON = new getRegister();
        getJSON.execute();
    }

    private void php_Login(String username, String password, String tokens) {

        class getLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_login.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                            + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(tokens, "UTF-8");
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
                if (s.matches("Incorrect username and/or password!")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Login_Register.this).create();
                    alertDialog.setTitle("Login Failed");
                    alertDialog.setMessage(s);
                    alertDialog.show();
                    loading.setVisibility(View.GONE);
                } else {
                    try {
                        submit_button.setClickable(false);
                        database_login(s);
                    } catch (JSONException e) {
                        loading.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getLogin getJSON = new getLogin();
        getJSON.execute();
    }

    private void php_Forgot_Email(String email) {

        class getLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_forgot_password.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
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
                if (s.matches("Success")) {
                    forgotEmail();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Login_Register.this).create();
                    alertDialog.setTitle("Email Failed");
                    alertDialog.setMessage(s);
                    alertDialog.show();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getLogin getJSON = new getLogin();
        getJSON.execute();
    }

    private void php_Forgot_Code(String code) {

        class getLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_forgot_password.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");
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
                if (s.matches("Failed")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Login_Register.this).create();
                    alertDialog.setTitle("Wrong code");
                    alertDialog.setMessage("You have entered a wrong code");
                    alertDialog.show();
                } else {
                    forgotCode(s);
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getLogin getJSON = new getLogin();
        getJSON.execute();
    }

    private void php_Forgot_Password(String id_student, String password) {

        class getLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_forgot_password.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("student_id", "UTF-8") + "=" + URLEncoder.encode(id_student, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
                if (s.matches("Success")) {
                    forgotPassword();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Login_Register.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Request error" + s);
                    alertDialog.show();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getLogin getJSON = new getLogin();
        getJSON.execute();
    }

    public void registerVerification(String class_id) {
        string_register_class_id = class_id;
        register_layout.setVisibility(View.GONE);
        register_verification_layout.setVisibility(View.VISIBLE);
        submit_button.setText("Continue");
        loading.setVisibility(View.GONE);
    }

    public void forgotEmail() {
        forgot_email_layout.setVisibility(View.GONE);
        forgot_code_layout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    public void forgotCode(String id) {
        student_id = id;
        forgot_code_layout.setVisibility(View.GONE);
        forgot_password_layout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    public void forgotPassword() {
        AlertDialog alertDialog = new AlertDialog.Builder(Login_Register.this).create();
        alertDialog.setTitle("Successfully changed the password");
        alertDialog.setMessage("You have successfully change the password, you can now login");
        alertDialog.show();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                login_layout.setVisibility(View.VISIBLE);
                forgot_password_layout.setVisibility(View.GONE);
                switch_button.setText("Register");
                submit_button.setText("Login");
            }
        });
        loading.setVisibility(View.GONE);
    }

    public void database_login(String s) throws JSONException {
        String[] lines = s.split("<br>");

        JSONArray jsonArray = new JSONArray(lines[0]);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            database.login((int) jsonObject.get("id"),
                    (String) jsonObject.get("username"),
                    (String) jsonObject.get("email"),
                    (String) jsonObject.get("name"),
                    (int) jsonObject.get("class"),
                    (String) jsonObject.get("status"),
                    (int) jsonObject.get("progress"));
            database.login_Lesson((String) jsonObject.get("lesson_status_1"), (int) jsonObject.get("lesson_progress_1"),
                    (String) jsonObject.get("lesson_status_2"), (int) jsonObject.get("lesson_progress_2"),
                    (String) jsonObject.get("lesson_status_3"), (int) jsonObject.get("lesson_progress_3"),
                    (String) jsonObject.get("lesson_status_4"), (int) jsonObject.get("lesson_progress_4"),
                    (String) jsonObject.get("lesson_status_5"), (int) jsonObject.get("lesson_progress_5"),
                    (String) jsonObject.get("lesson_status_6"), (int) jsonObject.get("lesson_progress_6"),
                    (String) jsonObject.get("lesson_status_7"), (int) jsonObject.get("lesson_progress_7"),
                    (String) jsonObject.get("lesson_status_8"), (int) jsonObject.get("lesson_progress_8"));
            database.login_Module((int) jsonObject.get("current_module_no_1"), (int) jsonObject.get("current_module_no_2"),
                    (int) jsonObject.get("current_module_no_3"), (int) jsonObject.get("current_module_no_4"),
                    (int) jsonObject.get("current_module_no_5"), (int) jsonObject.get("current_module_no_6"),
                    (int) jsonObject.get("current_module_no_7"), (int) jsonObject.get("current_module_no_8"),
                    (int) jsonObject.get("lesson_progress_1"), (int) jsonObject.get("lesson_progress_2"),
                    (int) jsonObject.get("lesson_progress_3"), (int) jsonObject.get("lesson_progress_4"),
                    (int) jsonObject.get("lesson_progress_5"), (int) jsonObject.get("lesson_progress_6"),
                    (int) jsonObject.get("lesson_progress_7"), (int) jsonObject.get("lesson_progress_8"));
            database.login_Simulation_Time((int) jsonObject.get("minutes_lowest"), (int) jsonObject.get("seconds_lowest"),
                    (int) jsonObject.get("minutes_highest"), (int) jsonObject.get("seconds_highest"));
            database.login_Simulation((String) jsonObject.get("current_simulation"));
        }

        jsonArray = new JSONArray(lines[1]);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            database.login_QuizLog((int) jsonObject.get("lesson_no"), (int) jsonObject.get("module_no"), (String) jsonObject.get("status"));
        }

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
        loading.setVisibility(View.GONE);
    }

    public void database_Register(String s, String name, String email, String username) throws JSONException {
        submit_button.setClickable(false);
        ProgressDialog progress = new ProgressDialog(Login_Register.this);
        progress.setMessage("You have successfully register, please wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            database.register((int) jsonObject.get("id"), username, email, name, (int) jsonObject.get("class"));
            database.register_Lesson();
            database.register_Module();
            database.register_Simulation();
            database.register_Simulation_Time();
        }

        Intent i = new Intent(getApplicationContext(), Register_Introduction.class);
        startActivity(i);
        finish();
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

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

}

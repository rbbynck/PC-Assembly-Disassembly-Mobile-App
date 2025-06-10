package com.example.thesis;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
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
import java.util.Random;


public class Fragment_Menu_Main extends Fragment {

    LinearLayout enroll_loading, profile_loading, feedback_loading;
    Database database;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private Button lesson, progress, challenges, simulation, settings, trivia, add_on;
    String[] trivia_content = {"Depends on your needs and your budget, Gaming PC costs $300 to $2000 or Php. 14,419 to Php. 96,128.", "Building a PC can take at least two-three hours, if it's your first time. With help or experience, it shouldn’t ever take longer than one hour, especially once you really know what you’re doing.",
            "If you’re ever in doubt when building, consult your motherboard manual and the manual for the part that you’re attempting to install.", "Screwdriver will be a necessity for standoff screws and mounting other components, like your cooler or your drives. Your case will often come with thumbscrews to make installing expansion cards easier, as well.",
            "Windows will provide the best all-around compatibility and support, but you can use Linux if you want. Linux can provide a great gaming experience, and is always getting more and more support.", "You don't need to buy an anti-static wrist strap and mat, but they make the building process much easier and safer.",
            "It is recommended to at least having a case intake and exhaust fan for your PC.", "Dics Drive, GPU, Sound cards and Extra Storage Devices are other components of PC that is optional for your PC Build.",
            "If you want to overclock your CPU you must need a compatible motherboard first and also a CPU that allows to be overclocked.", "Intel “K” series CPUs are overclockable, as are all AMD CPUs. Older AMD motherboards will all support overclocking, but Ryzen and Intel-K CPUs will require certain motherboard chipsets to allow overclocking. Be sure to check before buying your motherboard if this is a feature you need.",
            "You can replace everything in your PC, but make sure it is not cover by your supplier's warranty and also you might need to call Microsoft if necessary to ensure you get to keep your Windows license.", "Liquid coolers requires a bit more work when it comes to long-term maintenance, so if you’re new to PC building it may be best to stick with air.", "" +
            "Liquid coolers are recommended when overclocking.", "When building your your PC, you must need to read your motherboard manual first so that there will be no unnecessary mistake when building.",
            "When thinking of building your own personal computer, research first before buying.", "Before starting to actually build anything, read all the instructions that come with the components and make sure everything is complete and looks undamaged.",
            "The process of building a computer by yourself requires some patience and skill, but can be quite rewarding.", "Many cases come with a pre-installed power supply, but you should be sure to study tests and reports since case vendors often install cheaper devices that waste energy and do not deal well with inevitable small fluctuations of mains power.",
            "When selecting a power supply, make sure it is strong enough to power all components, but do not overshoot - a 400 watts supply working at 80% is much more energy efficient than an 800 watts one working at 40%.", "Do not EVER open a power supply! Even after disconnecting it from the mains, several components still hold high voltage charges and can continue to do so for several hours. These charges are strong enough to give you dangerous electric shocks.",
            "Thinking of the motherboard as the spine of your computer is a very useful analogy for a non-technical person because it really fills the same functions as your spine does in your body. It connects the processor (the brain) to all other parts of the computer (the body), both mechanically and electrically.",
            "When installing the processor, please remember that the socket it will go in is called \"Zero Insertion Force\" for a reason. If you feel you need any force whatsoever to make the processor slide in, STOP immediately: you are doing it wrong!", "" +
            "To close the lever the CPU socket you will need some force. Just be careful, do it slowly and if you feel excessive resistance, ease off and check for errors.", "Never cheap out your Power Supply.",
            "Building a PC gives you a skill you’ll keep forever.", "PC Building skills can help you save time and money, which gives you an advantage over everyone else who doesn’t have those skills. You can also earn some money too.",
            "Building a PC allows you to choose your Operating System.", "Building a Computer is Fun.", "Following the PC market gives precisely what parts you want and why. But if that sounds nothing like you, you might find the part-picking process overwhelming — even more so if you haven’t got the slightest clue what you’re looking at.",
            "Having exta memory is recommended.", "If your PC is acting erratically, check your RAM.", "Stress-test early to avoid issues later.", "Advanced Micro Devices’ first in-house x86 processor was the K5 which was launched in 1996. The “K” was a reference to Kryptonite (Superman's weakness).",
            "AMD holds the record for the processor with Highest operating frequency at 8.24 GHz.", "AMD is currently one of the leaders in graphics industry after its acquisition of ATI.", "AMD was the first company to introduce integrated CPU and GPU with good performance.",
            "Currently AMD provides the core for all three major consoles.", "Intel's 4004 microprocessor was the first microprocessor and was invented in 1971. This processor had 2300 transistors. This chip was used on a calculator at the time.", "Intel's 8080 microprocessor was the world’s first 8-bit chip that kick-started the era of computer development.",
            "In 1984, Intel founded the ‘Intel Scientific Computers division’. The primary goal of this division was to design and develop parallel computers. Later in 1992, the name of the division was changed to the ‘Intel Supercomputing Systems Division’.",
            "Intel company invented the ATX form factor.", "Graphics card It is the one that will have the most impact on the computer's gaming performance. It also has its own built-in memory and specialized processors to do its job well.",
            "Flash memory is a type of permanent storage that is very convenient if you need to backup files or as a type of portable storage. Flash memory is used in memory cards and USB Flash Drive.",
            "Data can come from information stored in the computer's permanent storage, or it can come from peripheral devices, such as keyboards. Regardless of whether it is input through a peripheral device or taken from storage, RAM memory is where most data go through first.",
            "127 is the maximum number of peripheral devices which can be connected through a single USB port.", "Intel and AMD are the two most well known major manufacturers of computer processors.",
            "Ajay Bhatt, an Indian American working for Intel come up with a port which could be used by any peripheral universally. It was named the Universal Serial Bus, the first standard was published in 1996.",
            "Bill English invented Ball Mouse type of mouse.", "QWERTY is a keyboard design for Latin-script alphabets. The name comes from the order of the first six keys on the top left letter row of the keyboard ( Q W E R T Y ).",
            "The first substantial computer was built by John W. Mauchly and J. Presper Eckert at the University of Pennsylvania. ENIAC (Electrical Numerical Integrator and Calculator) used a word of 10 decimal digits instead of binary ones like previous automated calculators/computers."};

    ImageView feedback_close, feedback_send;
    ImageView[] feedback_rate = new ImageView[5];
    LinearLayout[] feedback_category = new LinearLayout[3];
    EditText feedback_edittext;
    int feedback;
    String feedback_category_category = "";
    ImageView profile_close;
    LinearLayout profile_edit_layout, profile_password_layout, profile_edit_password_layout, profile_edit_profile_buttons_layout_1, profile_edit_profile_buttons_layout_2;
    TextView profile_edit, profile_password, profile_name, profile_username, profile_email, profile_password_cancel, profile_password_submit, profile_edit_buttons_cancel, profile_edit_buttons_submit;
    EditText profile_edit_name, profile_edit_email, profile_edit_username, profile_password_old, profile_password_new, profile_password_new_confirm, profile_edit_password;
    int ui_flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    Dialog enroll_dialog;
    Dialog profile_dialog;
    LinearLayout setting_enroll;


    public Fragment_Menu_Main() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_main, container, false);

        database = new Database(getActivity());
        initialization(view);
        startUp();
        onClickListener();

        return view;
    }

    public void initialization(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        lesson = view.findViewById(R.id.menu_lesson);
        settings = view.findViewById(R.id.menu_settings);
        challenges = view.findViewById(R.id.menu_challenges);
        simulation = view.findViewById(R.id.menu_simulation);
        progress = view.findViewById(R.id.menu_progress);
        trivia = view.findViewById(R.id.menu_trivia);
        add_on = view.findViewById(R.id.menu_lesson_prof);
    }

    public void startUp() {
        switch (database.getTrivia()) {
            case "On":
                int ui_flags =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                View view = getLayoutInflater().inflate(R.layout.activity_main_trivia, null);
                Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                builder.setContentView(view);

                int random = new Random().nextInt((trivia_content.length - 1));
                ((TextView) view.findViewById(R.id.trivia_trivia)).setText(trivia_content[random]);

                view.findViewById(R.id.trivia_ok).setOnClickListener(new View.OnClickListener() {
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

                builder.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
                builder.show();
                break;
        }
        //Open the Prof Lesson
        if (database.get_Progress() == 100 && database.getClass_No() > 1) {
            add_on.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickListener() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.activity_main_setting, null);
                Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                builder.setContentView(view);

                view.findViewById(R.id.setting_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

                SwitchCompat setting_trivia = view.findViewById(R.id.setting_trivia);
                LinearLayout setting_feedback = view.findViewById(R.id.setting_feedback);
                setting_enroll = view.findViewById(R.id.setting_enroll);
                if (database.getRating() > 0) {
                    setting_feedback.setVisibility(View.GONE);
                }
                if (database.getClass_No() > 1) {
                    setting_enroll.setVisibility(View.GONE);
                }
                LinearLayout setting_profile = view.findViewById(R.id.setting_profile);
                LinearLayout setting_logout = view.findViewById(R.id.setting_logout);

                switch (database.getTrivia()) {
                    case "On":
                        setting_trivia.setChecked(true);
                        break;
                    case "Off":
                        setting_trivia.setChecked(false);
                        break;
                }
                //Close
                view.findViewById(R.id.setting_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                //Trivia
                setting_trivia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (setting_trivia.isChecked()) {
                            database.updateTrivia("On");
                        } else {
                            database.updateTrivia("Off");
                        }
                    }
                });
                //Feedback
                setting_feedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = getLayoutInflater().inflate(R.layout.activity_feedback, null);
                        Dialog builder_1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                        builder_1.setContentView(view);

                        feedback_close = view.findViewById(R.id.feedback_close);
                        for (int i = 0; i < feedback_rate.length; i++) {
                            int id = view.getResources().getIdentifier(
                                    "feedback_rating_" + (i + 1),
                                    "id",
                                    getActivity().getPackageName());
                            feedback_rate[i] = view.findViewById(id);
                        }
                        for (int i = 0; i < feedback_category.length; i++) {
                            int id = view.getResources().getIdentifier(
                                    "feedback_category_" + (i + 1),
                                    "id",
                                    getActivity().getPackageName());
                            feedback_category[i] = view.findViewById(id);
                        }
                        feedback_send = view.findViewById(R.id.feedback_send);
                        feedback_edittext = view.findViewById(R.id.feedback_textbox);

                        //close the feedback builder
                        feedback_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder_1.dismiss();
                            }
                        });
                        //feedback rate button
                        for (int i = 0; i < feedback_rate.length; i++) {
                            int final_i = i;
                            feedback_rate[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int c = 0; c < feedback_rate.length; c++) {
                                        feedback_rate[c].setImageResource(R.drawable.feedback_star_unselected);
                                    }
                                    for (int c = 0; c < (final_i + 1); c++) {
                                        feedback_rate[c].setImageResource(R.drawable.feedback_star_selected);
                                    }
                                    feedback = (final_i + 1) * 2;
                                }
                            });
                        }
                        //feedback category
                        for (int i = 0; i < feedback_category.length; i++) {
                            int final_i = i;
                            feedback_category[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int c = 0; c < feedback_category.length; c++) {
                                        feedback_category[c].setBackgroundResource(R.drawable.feedback_category_unselected);
                                    }
                                    feedback_category[final_i].setBackgroundResource(R.drawable.feedback_category_selected);
                                    switch (final_i + 1) {
                                        case 1:
                                            feedback_category_category = "Suggestion";
                                            break;
                                        case 2:
                                            feedback_category_category = "Something is not quite right";
                                            break;
                                        case 3:
                                            feedback_category_category = "Compliment";
                                            break;
                                        default:
                                            feedback_category_category = "";
                                            break;
                                    }
                                }
                            });
                        }

                        //feedback send button
                        feedback_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isConnectedToInternet() == true) {
                                    if (String.valueOf(feedback).equals("0") || feedback_edittext.getText().toString().equals("") || feedback_category_category == "") {
                                        Toast.makeText(getActivity(), "Please select and fill all the fields", Toast.LENGTH_LONG).show();
                                    } else {
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                        alertDialog.setTitle("Submission Success");
                                        alertDialog.setMessage("Thank you for giving us a feedback");
                                        alertDialog.show();
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                            @Override
                                            public void onCancel(DialogInterface dialog) {
                                                builder_1.dismiss();
                                            }
                                        });
                                        feedback_php(String.valueOf(feedback), feedback_edittext.getText().toString(), feedback_category_category);
                                        database.insertFeedback(feedback, feedback_edittext.getText().toString());
                                        setting_feedback.setVisibility(View.GONE);
                                    }
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                    alertDialog.setTitle("Internet Error");
                                    alertDialog.setMessage("Please connect to the internet.");
                                    alertDialog.show();
                                }
                            }
                        });

                        if (builder.getWindow() != null) {
                            builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }
                        builder_1.getWindow().
                                setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                        // Set full-sreen mode (immersive sticky):
                        builder_1.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
                        builder_1.getWindow().clearFlags(
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                        builder_1.show();
                    }
                });
                //Profile
                setting_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = getLayoutInflater().inflate(R.layout.activity_profile, null);
                        profile_dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                        profile_dialog.setContentView(view);

                        profile_close = view.findViewById(R.id.profile_close);
                        profile_edit_layout = view.findViewById(R.id.profile_edit_layout);
                        profile_password_layout = view.findViewById(R.id.profile_password_layout);
                        profile_edit = view.findViewById(R.id.profile_edit_editprofile);
                        profile_password = view.findViewById(R.id.profile_edit_changepassword);
                        profile_name = view.findViewById(R.id.profile_edit_name_text);
                        profile_username = view.findViewById(R.id.profile_edit_username_text);
                        profile_email = view.findViewById(R.id.profile_edit_email_text);
                        profile_edit_name = view.findViewById(R.id.profile_edit_name_edit);
                        profile_edit_email = view.findViewById(R.id.profile_edit_email_edit);
                        profile_edit_username = view.findViewById(R.id.profile_edit_username_edit);
                        profile_password_old = view.findViewById(R.id.profile_password_old);
                        profile_password_new = view.findViewById(R.id.profile_password_new);
                        profile_password_new_confirm = view.findViewById(R.id.profile_password_confirm);
                        profile_password_cancel = view.findViewById(R.id.profile_password_cancel);
                        profile_edit_profile_buttons_layout_1 = view.findViewById(R.id.profile_edit_profile_buttons_layout_1);
                        profile_edit_profile_buttons_layout_2 = view.findViewById(R.id.profile_edit_profile_buttons_layout_2);
                        profile_edit_password_layout = view.findViewById(R.id.profile_edit_password_layout);
                        profile_edit_buttons_cancel = view.findViewById(R.id.profile_cancel);
                        profile_edit_buttons_submit = view.findViewById(R.id.profile_submit);
                        profile_password_submit = view.findViewById(R.id.profile_password_submit);
                        profile_edit_password = view.findViewById(R.id.profile_edit_password);
                        profile_loading = view.findViewById(R.id.profile_loading);

                        //database
                        profile_name.setText(database.getName());
                        profile_username.setText(database.getUsername());
                        profile_email.setText(database.getEmail());
                        profile_edit_name.setText(database.getName());
                        profile_edit_email.setText(database.getEmail());
                        profile_edit_username.setText(database.getUsername());

                        //close the profile builder
                        profile_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                profile_dialog.dismiss();
                            }
                        });
                        //
                        profile_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                profile_edit_profile_buttons_layout_1.setVisibility(View.GONE);
                                profile_edit_profile_buttons_layout_2.setVisibility(View.VISIBLE);
                                profile_edit_password_layout.setVisibility(View.VISIBLE);
                                profile_name.setVisibility(View.GONE);
                                profile_username.setVisibility(View.GONE);
                                profile_email.setVisibility(View.GONE);
                                profile_password.setVisibility(View.GONE);
                                profile_edit_name.setVisibility(View.VISIBLE);
                                profile_edit_email.setVisibility(View.VISIBLE);
                                profile_edit_username.setVisibility(View.VISIBLE);
                            }
                        });
                        //
                        profile_edit_buttons_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                profile_edit_profile_buttons_layout_1.setVisibility(View.VISIBLE);
                                profile_edit_profile_buttons_layout_2.setVisibility(View.GONE);
                                profile_edit_password_layout.setVisibility(View.GONE);
                                profile_name.setVisibility(View.VISIBLE);
                                profile_username.setVisibility(View.VISIBLE);
                                profile_email.setVisibility(View.VISIBLE);
                                profile_password.setVisibility(View.VISIBLE);
                                profile_edit_name.setVisibility(View.GONE);
                                profile_edit_email.setVisibility(View.GONE);
                                profile_edit_username.setVisibility(View.GONE);
                            }
                        });
                        //
                        profile_password.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                profile_edit_layout.setVisibility(View.GONE);
                                profile_password_layout.setVisibility(View.VISIBLE);
                            }
                        });
                        //
                        profile_password_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                profile_edit_layout.setVisibility(View.VISIBLE);
                                profile_password_layout.setVisibility(View.GONE);
                            }
                        });
                        //Change Password Submit
                        profile_password_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isConnectedToInternet() == true) {
                                    if (profile_password_old.getText().toString().equals("") || profile_password_new.getText().toString().equals("") || profile_password_new_confirm.getText().toString().equals("")) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                        alertDialog.setTitle("Error");
                                        alertDialog.setMessage("Please fill all the fields");
                                        alertDialog.show();
                                        alertDialog.setCanceledOnTouchOutside(true);
                                    } else if (profile_password_new.getText().toString().equals(profile_password_new_confirm.getText().toString())) {
                                        profile_loading.setVisibility(View.VISIBLE);
                                        setPassword(profile_password_old.getText().toString(), profile_password_new.getText().toString());
                                    } else {
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                        alertDialog.setTitle("Error");
                                        alertDialog.setMessage("Password didn't match");
                                        alertDialog.show();
                                        alertDialog.setCanceledOnTouchOutside(true);
                                    }
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                    alertDialog.setTitle("Error");
                                    alertDialog.setMessage("Please connect to the internet to make changes");
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(true);
                                }
                            }
                        });
                        //Edit Profile Submit
                        profile_edit_buttons_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isConnectedToInternet() == true) {
                                    String email = profile_edit_email.getText().toString().trim();
                                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                                    if (profile_edit_username.getText().toString().equals("") || profile_edit_name.getText().toString().equals("")
                                            || profile_edit_email.getText().toString().equals("") || profile_edit_password.getText().toString().equals("")) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                        alertDialog.setTitle("Error");
                                        alertDialog.setMessage("Please fill out all the fields");
                                        alertDialog.show();
                                        alertDialog.setCanceledOnTouchOutside(true);
                                    } else if (!(email.matches(emailPattern))) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                        alertDialog.setTitle("Error");
                                        alertDialog.setMessage("Please give us a real email");
                                        alertDialog.show();
                                        alertDialog.setCanceledOnTouchOutside(true);
                                    } else {
                                        profile_loading.setVisibility(View.VISIBLE);
                                        editProfile(profile_edit_email.getText().toString(), profile_edit_name.getText().toString(), profile_edit_username.getText().toString(), profile_edit_password.getText().toString());
                                    }
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                    alertDialog.setTitle("Error");
                                    alertDialog.setMessage("Please connect to the internet to make changes");
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(true);
                                }
                            }
                        });

                        if (builder.getWindow() != null) {
                            builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }
                        profile_dialog.getWindow().
                                setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                        // Set full-sreen mode (immersive sticky):
                        profile_dialog.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
                        profile_dialog.getWindow().clearFlags(
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                        profile_dialog.show();
                    }
                });
                //Enroll
                setting_enroll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = getLayoutInflater().inflate(R.layout.activity_enroll, null);
                        enroll_dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                        enroll_dialog.setContentView(view);

                        TextView enroll_submit = view.findViewById(R.id.enroll_enroll);
                        EditText enroll_edittext = view.findViewById(R.id.enroll_edittext);
                        ImageView enroll_close = view.findViewById(R.id.enroll_close);
                        enroll_loading = view.findViewById(R.id.enroll_loading);

                        enroll_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                enroll_dialog.dismiss();
                            }
                        });
                        //

                        enroll_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isConnectedToInternet() == true) {
                                    if (enroll_edittext.getText().toString().matches("")) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                        alertDialog.setTitle("Error");
                                        alertDialog.setMessage("Field empty.");
                                        alertDialog.show();
                                    } else {
                                        enroll_loading.setVisibility(View.VISIBLE);
                                        enroll(enroll_edittext.getText().toString());
                                    }
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                    alertDialog.setTitle("Internet Error");
                                    alertDialog.setMessage("Please connect to the internet.");
                                    alertDialog.show();
                                }
                            }
                        });

                        if (builder.getWindow() != null) {
                            builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }
                        enroll_dialog.getWindow().
                                setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                        // Set full-sreen mode (immersive sticky):
                        enroll_dialog.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
                        enroll_dialog.getWindow().clearFlags(
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                        enroll_dialog.show();
                    }
                });
                //Logout
                setting_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().deleteDatabase("pc_assembly");
                        Intent i = new Intent(getActivity(), Intro.class);
                        startActivity(i);
                        getActivity().finish();
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

        trivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.activity_main_trivia, null);
                Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                builder.setContentView(view);

                int random = new Random().nextInt((trivia_content.length - 1) + 1) + 1;
                ((TextView) view.findViewById(R.id.trivia_trivia)).setText(trivia_content[random]);

                view.findViewById(R.id.trivia_ok).setOnClickListener(new View.OnClickListener() {
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

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Menu_Progress menu_progress = new Fragment_Menu_Progress();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Main.this);
                fragmentTransaction.add(android.R.id.content, menu_progress);
                fragmentTransaction.commit();
            }
        });

        lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Menu_Lesson menu_lesson = new Fragment_Menu_Lesson();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Main.this);
                fragmentTransaction.add(android.R.id.content, menu_lesson);
                fragmentTransaction.commit();
            }
        });

        simulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Menu_Simulation fragment = new Fragment_Menu_Simulation();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Main.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Menu_Challenge fragment = new Fragment_Menu_Challenge();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Main.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        if ((isConnectedToInternet() == true) && (database.getClass_No() >= 0)) {
            add_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment_Menu_AddOn_Lesson fragment = new Fragment_Menu_AddOn_Lesson();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.hide(Fragment_Menu_Main.this);
                    fragmentTransaction.add(android.R.id.content, fragment);
                    fragmentTransaction.commit();
                }
            });
        }
    }

    //PHP CODES
    private void feedback_php(String rating, String comment, String category) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_rating.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(rating, "UTF-8") + "&"
                            + URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(comment, "UTF-8") + "&"
                            + URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(category, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result = line;
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

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void setPassword(String old_password, String new_password) {

        class getPassword extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_update_profile.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("old_password", "UTF-8") + "=" + URLEncoder.encode(old_password, "UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(database.getUsername(), "UTF-8") + "&"
                            + URLEncoder.encode("new_password", "UTF-8") + "=" + URLEncoder.encode(new_password, "UTF-8");

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
                profile_loading.setVisibility(View.GONE);
                if (s.equals("Success")) {
                    updatePassword();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("" + s);
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(true);
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getPassword getJSON = new getPassword();
        getJSON.execute();

    }

    private void editProfile(String email, String name, String username, String password) {

        class getPassword extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_update_profile.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                            + URLEncoder.encode("old_username", "UTF-8") + "=" + URLEncoder.encode(database.getUsername(), "UTF-8") + "&"
                            + URLEncoder.encode("new_username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                            + URLEncoder.encode("old_email", "UTF-8") + "=" + URLEncoder.encode(database.getEmail(), "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

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
                profile_loading.setVisibility(View.GONE);
                if (s.equals("Success")) {
                    updateProfile(name, username, email);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Unsuccessful profile change");
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(true);
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getPassword getJSON = new getPassword();
        getJSON.execute();

    }

    private void enroll(String code) {

        class getPassword extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://pcad-gbbs.000webhostapp.com/android/android_enroll.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(database.getUsername(), "UTF-8");
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
                enroll_loading.setVisibility(View.GONE);
                if (s.matches("No class found")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("" + s);
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(true);
                } else {
                    enrollSuccess(Integer.valueOf(s));
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        getPassword getJSON = new getPassword();
        getJSON.execute();

    }
    //END

    //
    private void updateProfile(String name, String username, String email) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Profile Changed");
        alertDialog.setMessage("You have successfully changed your profile");
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                profile_dialog.dismiss();
            }
        });
        database.updateUser(name, username, email);
    }

    private void updatePassword() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Password Changed");
        alertDialog.setMessage("You have successfully changed your password");
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                profile_dialog.dismiss();
            }
        });
        //database change
        database.updateUser(profile_edit_name.getText().toString(), profile_edit_username.getText().toString(), profile_edit_email.getText().toString());
    }

    private void enrollSuccess(int id) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Enroll Success");
        alertDialog.setMessage("You have successfully enroll to a class");
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enroll_dialog.dismiss();
            }
        });
        database.setClass_No(id);
        setting_enroll.setVisibility(View.GONE);
    }
    //

    //INTERNET CONNECTION
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
    //END


}


package com.example.thesis;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import java.util.Random;


public class Fragment_Menu_Challenge_BuildPC extends Fragment {

    //Android widgets
    LinearLayout layout_question, layout_components, layout_done;
    TextView components_title, components_button, question_start, done_done, done_tryagain, done_percentage, done_specs;
    FrameLayout[] components_option = new FrameLayout[3];
    FrameLayout[] components_info = new FrameLayout[3];
    FrameLayout[] components_cancel = new FrameLayout[3];
    ImageView[] components_image = new ImageView[3];
    TextView[] components_text = new TextView[3];
    LinearLayout[] components_components = new LinearLayout[3];
    ScrollView scrollView;
    //
    Database database;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int id;
    int components = 1;
    int[] answers = new int[8];
    int currently_selected = 0;
    int percentage = 100;
    String done_string = "";
    Vibrator vibrator;

    public Fragment_Menu_Challenge_BuildPC() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_challenge_buildpc, container, false);

        database = new Database(getActivity());
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        initialization(view);
        onClickListener(view);
        return view;
    }

    public void initialization(View view) {
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        layout_question = view.findViewById(R.id.challenge_buildpc_question);
        layout_components = view.findViewById(R.id.challenge_buildpc_components);
        layout_done = view.findViewById(R.id.challenge_buildpc_done);
        components_title = view.findViewById(R.id.challenge_buildpc_components_title);
        components_button = view.findViewById(R.id.challenge_buildpc_components_next);
        question_start = view.findViewById(R.id.challenge_buildpc_question_start);
        done_done = view.findViewById(R.id.challenge_buildpc_done_done);
        done_tryagain = view.findViewById(R.id.challenge_buildpc_done_tryagain);
        scrollView = view.findViewById(R.id.challenge_buildpc_scroll);
        done_percentage = view.findViewById(R.id.challenge_buildpc_done_percentage);
        done_specs = view.findViewById(R.id.challenge_buildpc_done_specs);

        for (int i = 0; i < 3; i++) {
            id = view.getResources().getIdentifier(
                    "challenge_buildpc_components_option_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            components_option[i] = view.findViewById(id);

            id = view.getResources().getIdentifier(
                    "challenge_buildpc_components_info_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            components_info[i] = view.findViewById(id);

            id = view.getResources().getIdentifier(
                    "challenge_buildpc_components_picture_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            components_image[i] = view.findViewById(id);

            id = view.getResources().getIdentifier(
                    "challenge_buildpc_components_name_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            components_text[i] = view.findViewById(id);

            id = view.getResources().getIdentifier(
                    "challenge_buildpc_components_cancel_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            components_cancel[i] = view.findViewById(id);

            id = view.getResources().getIdentifier(
                    "challenge_buildpc_components_components_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            components_components[i] = view.findViewById(id);
        }
    }

    public void onClickListener(View view) {
        question_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_components.setVisibility(View.VISIBLE);
                layout_question.setVisibility(View.GONE);
            }
        });

        for (int i = 0; i < 3; i++) {
            int final_i = i;
            components_option[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currently_selected = final_i + 1;
                    components_cancel[final_i].setVisibility(View.VISIBLE);
                    components_components[final_i].setBackgroundResource(R.drawable.quiz_option_selected);
                    components_text[final_i].setTextColor(getResources().getColor(R.color.white));
                    for (int c = 0; c < 3; c++) {
                        components_info[c].setVisibility(View.GONE);
                        components_option[c].setClickable(false);
                    }
                }
            });

            components_cancel[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currently_selected > 0) {
                        currently_selected = 0;
                        components_components[final_i].setBackgroundResource(R.color.white);
                        components_cancel[final_i].setVisibility(View.GONE);
                        components_text[final_i].setTextColor(getResources().getColor(R.color.black_primary));

                        for (int c = 0; c < 3; c++) {
                            components_info[c].setVisibility(View.VISIBLE);
                            components_option[c].setClickable(true);
                        }
                    } else {
                        vibrator.vibrate(100);
                    }
                }
            });

            components_info[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ui_flags =
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                    View view = getLayoutInflater().inflate(R.layout.challenge_buildpc_info, null);
                    Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                    builder.setContentView(view);

                    Button info_close = view.findViewById(R.id.challenge_buildpc_info_close);
                    ImageView info_image = view.findViewById(R.id.challenge_buildpc_info_image);
                    ;
                    TextView info_text = view.findViewById(R.id.challenge_buildpc_info_text);
                    ;

                    info_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });

                    switch (components) {
                        case 1:
                            id = view.getResources().getIdentifier(
                                    "challenge_case_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            info_image.setImageResource(id);
                            id = view.getResources().getIdentifier(
                                    "spec_case_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                        case 2:
                            id = view.getResources().getIdentifier(
                                    "challenge_motherboard_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            info_image.setImageResource(id);
                            id = view.getResources().getIdentifier(
                                    "spec_motherboard_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                        case 3:
                            id = view.getResources().getIdentifier(
                                    "challenge_cpu_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            info_image.setImageResource(id);
                            id = view.getResources().getIdentifier(
                                    "spec_processor_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                        case 4:
                            id = view.getResources().getIdentifier(
                                    "challenge_gpu_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id == 0) {
                                info_image.setImageResource(R.drawable.challenge_none);
                            } else {
                                info_image.setImageResource(id);
                            }
                            id = view.getResources().getIdentifier(
                                    "spec_gpu_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                        case 5:
                            id = view.getResources().getIdentifier(
                                    "challenge_ram_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            info_image.setImageResource(id);
                            id = view.getResources().getIdentifier(
                                    "spec_ram_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                        case 6:
                            id = view.getResources().getIdentifier(
                                    "challenge_hdd_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            info_image.setImageResource(id);
                            id = view.getResources().getIdentifier(
                                    "spec_hdd_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                        case 7:
                            id = view.getResources().getIdentifier(
                                    "challenge_ssd_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id == 0) {
                                info_image.setImageResource(R.drawable.challenge_none);
                            } else {
                                info_image.setImageResource(id);
                            }
                            id = view.getResources().getIdentifier(
                                    "spec_ssd_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                        case 8:
                            id = view.getResources().getIdentifier(
                                    "challenge_psu_" + (final_i + 1),
                                    "drawable",
                                    getActivity().getPackageName());
                            info_image.setImageResource(id);
                            id = view.getResources().getIdentifier(
                                    "spec_psu_" + (final_i + 1),
                                    "string",
                                    getActivity().getPackageName());
                            info_text.setText(getResources().getString(id));
                            break;
                    }

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

        }

        components_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currently_selected > 0) {
                    components_components[currently_selected - 1].setBackgroundResource(R.color.white);
                    components_cancel[currently_selected - 1].setVisibility(View.GONE);
                    components_text[currently_selected - 1].setTextColor(getResources().getColor(R.color.black_primary));
                    answers[components - 1] = currently_selected;
                    currently_selected = 0;
                    scrollView.scrollTo(0, 0);

                    for (int c = 0; c < 3; c++) {
                        components_info[c].setVisibility(View.VISIBLE);
                        components_option[c].setClickable(true);
                    }

                    if (components == 8) {
                        layout_components.setVisibility(View.GONE);
                        layout_done.setVisibility(View.VISIBLE);
                        calculateAnswer();
                        done_percentage.setText("GREAT BUILD!\n Your score is: " + percentage);
                        if (percentage >= 90) {
                            done_percentage.setText("GREAT BUILD!\n Your score is: " + percentage);
                        } else if (percentage >= 75 && percentage < 90) {
                            done_percentage.setText("GOOD BUILD!\n Your score is: " + percentage);
                        } else if (percentage < 75) {
                            done_percentage.setText("POOR BUILD!\n Your score is: " + percentage + "\n You need to go back to the lessons again to learn how to build a great PC for 3D Modelling");
                        }

                        done_specs.setText(done_string);
                    } else {
                        components++;
                    }
                    changeLayout(view);
                } else {
                    vibrator.vibrate(100);
                }
            }
        });

        //RESULT BUTTONS
        done_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Menu_Challenge fragment = new Fragment_Menu_Challenge();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Challenge_BuildPC.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        done_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_components.setVisibility(View.VISIBLE);
                layout_done.setVisibility(View.GONE);
                components = 1;
                percentage = 100;
                done_string = "";
            }
        });

    }

    public void changeLayout(View view) {
        for (int i = 0; i < 3; i++) {
            int final_i = i;
            switch (components) {
                case 1:
                    components_title.setText("Case");
                    id = view.getResources().getIdentifier(
                            "challenge_case_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    components_image[final_i].setImageResource(id);
                    id = view.getResources().getIdentifier(
                            "name_case_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
                case 2:
                    components_title.setText("Motherboard");
                    id = view.getResources().getIdentifier(
                            "challenge_motherboard_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    components_image[final_i].setImageResource(id);
                    id = view.getResources().getIdentifier(
                            "name_motherboard_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
                case 3:
                    components_title.setText("Processor");
                    id = view.getResources().getIdentifier(
                            "challenge_cpu_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    components_image[final_i].setImageResource(id);
                    id = view.getResources().getIdentifier(
                            "name_processor_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
                case 4:
                    components_title.setText("GPU");
                    id = view.getResources().getIdentifier(
                            "challenge_gpu_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    if (id == 0) {
                        components_image[final_i].setImageResource(R.drawable.challenge_none);
                    } else {
                        components_image[final_i].setImageResource(id);
                    }
                    id = view.getResources().getIdentifier(
                            "name_gpu_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
                case 5:
                    components_title.setText("RAM");
                    id = view.getResources().getIdentifier(
                            "challenge_ram_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    components_image[final_i].setImageResource(id);
                    id = view.getResources().getIdentifier(
                            "name_ram_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
                case 6:
                    components_title.setText("HDD");
                    id = view.getResources().getIdentifier(
                            "challenge_hdd_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    components_image[final_i].setImageResource(id);
                    id = view.getResources().getIdentifier(
                            "name_hdd_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
                case 7:
                    components_title.setText("SSD");
                    id = view.getResources().getIdentifier(
                            "challenge_ssd_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    if (id == 0) {
                        components_image[final_i].setImageResource(R.drawable.challenge_none);
                    } else {
                        components_image[final_i].setImageResource(id);
                    }
                    id = view.getResources().getIdentifier(
                            "name_ssd_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
                case 8:
                    components_title.setText("PSU");
                    id = view.getResources().getIdentifier(
                            "challenge_psu_" + (final_i + 1),
                            "drawable",
                            getActivity().getPackageName());
                    components_image[final_i].setImageResource(id);
                    id = view.getResources().getIdentifier(
                            "name_psu_" + (final_i + 1),
                            "string",
                            getActivity().getPackageName());
                    components_text[final_i].setText(getResources().getString(id));
                    break;
            }
        }
    }

    public void calculateAnswer() {
        switch (answers[0]) {
            case 1:
                done_string += "Corsair 110R Tempered Glass Mid-Tower ATX Case - Great for future expansion, but need to upgrade the cooling systems.\n";
                percentage -= 5;
                if (answers[1] == 3) {
                    done_string += "MSI Z490-A PRO LGA 1200 - Motherboard fit perfectly on the case, have more RAM capacity than the other motherboards.\n";
                    if (answers[2] == 2) {
                        done_string += "Intel® Core™ i9-9900k - Best processor to pick since intel motherboard is only compatible with intel cpu's.\n";
                        if (answers[3] == 1) {
                            done_string = "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string += "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string += "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string += "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string += "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string += "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string += "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string += "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string += "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string += "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string += "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string += "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string += "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 2) {
                                done_string += "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 3) {
                                done_string += "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    } else {
                        done_string = "Motherboard and CPU is not compatible with each other\n";
                        percentage = 0;
                    }
                } else if (answers[1] == 1) {
                    done_string += "MSI MPG B550I - Motherboard is too small for the case, but have more airflow than the other.\n";
                    if (answers[2] == 2) {
                        done_string = "Motherboard and CPU is not compatible with each other.";
                        percentage = 0;
                    } else {
                        if (answers[2] == 1) {
                            done_string += "AMD RYZEN 9 3900X - Best Processor to use for rendering.\n";
                        } else if (answers[2] == 3) {
                            done_string += "AMD RYZEN 7 3700X - Best Processor to use for rendering.\n";
                        }
                        if (answers[3] == 1) {
                            done_string = "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string = "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string = "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string = "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string = "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string = "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string = "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string = "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string = "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string = "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string = "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string = "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string = "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 2) {
                                done_string = "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 3) {
                                done_string = "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    }
                } else if (answers[1] == 2) {
                    done_string += "ASUS Prime A320M-K AM4 - Motherboard is too small for the case, but will have a great airflow and cable managing, but have a less RAM capacity than the others.\n";
                    percentage -= 5;
                    if (answers[2] == 2) {
                        done_string = "Motherboard and CPU is not compatible with each other.";
                        percentage = 0;
                    } else {
                        if (answers[2] == 1) {
                            done_string += "AMD RYZEN 9 3900X - Best Processor to use for rendering.\n";
                        } else if (answers[2] == 3) {
                            done_string += "AMD RYZEN 7 3700X - Best Processor to use for rendering.\n";
                        }
                        if (answers[3] == 1) {
                            done_string = "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string += "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string += "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string += "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string += "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string += "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string += "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string += "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string += "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string += "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string += "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string += "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string += "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 2) {
                                done_string += "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 3) {
                                done_string += "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    }
                }
                break;
            case 2:
                done_string += "darkFlash DLM21 MESH Black Mico ATX - Case is too small, not great for cabling management and cooling system.\n";
                if (answers[1] == 3) {
                    done_string = "You have chosen a motherboard that doesn't fit with the case you have chosen.";
                    percentage = 0;
                } else if (answers[1] == 1) {
                    done_string += "MSI MPG B550I - Motherboard fit perfectly into the case.\n";
                    if (answers[2] == 2) {
                        done_string = "Motherboard and CPU is not compatible with each other.";
                        percentage = 0;
                    } else {
                        if (answers[2] == 1) {
                            done_string += "AMD RYZEN 9 3900X - Best Processor to use for rendering.\n";
                        } else if (answers[2] == 3) {
                            done_string += "AMD RYZEN 7 3700X - Best Processor to use for rendering.\n";
                        }
                        if (answers[3] == 1) {
                            done_string += "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string += "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string += "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string += "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string += "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string += "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string += "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string += "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string += "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string += "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string += "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string += "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string += "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 2) {
                                done_string += "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 3) {
                                done_string += "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    }
                } else if (answers[1] == 2) {
                    done_string += "ASUS Prime A320M-K AM4 - Motherboard fit perfectly into the case, but have less RAM capacity than the other motherboards.\n";
                    percentage -= 5;
                    if (answers[2] == 2) {
                        done_string = "Motherboard and CPU is not compatible with each other.";
                        percentage = 0;
                    } else {
                        if (answers[2] == 1) {
                            done_string += "AMD RYZEN 9 3900X - Best Processor to use for rendering.\n";
                        } else if (answers[2] == 3) {
                            done_string += "AMD RYZEN 7 3700X - Best Processor to use for rendering.\n";
                        }
                        if (answers[3] == 1) {
                            done_string = "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string += "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string += "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string += "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string += "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string += "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string += "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string += "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string += "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string += "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string += "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string += "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string += "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 3) {
                                done_string += "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 2) {
                                done_string += "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    }
                }
                break;
            case 3:
                done_string += "Corsaird iCUE 465X RGB Mid ATX - Great for future expansion, also have a great cooling system.\n";
                if (answers[1] == 3) {
                    done_string += "MSI Z490-A PRO LGA 1200 - Motherboard fit perfectly on the case, have more RAM capacity than the other motherboards.\n";
                    if (answers[2] == 2) {
                        done_string += "Intel® Core™ i9-9900k - Best processor to pick since intel motherboard is only compatible with intel cpu's.\n";
                        if (answers[3] == 1) {
                            done_string = "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string += "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string += "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string += "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string += "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string += "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string += "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string += "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string += "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string += "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string += "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string += "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string += "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 2) {
                                done_string += "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 3) {
                                done_string += "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    } else {
                        done_string = "Motherboard and CPU is not compatible with each other\n";
                        percentage = 0;
                    }
                } else if (answers[1] == 1) {
                    done_string += "MSI MPG B550I - Motherboard is too small for the case, but have more airflow than the other.\n";
                    if (answers[2] == 2) {
                        done_string = "Motherboard and CPU is not compatible with each other.";
                        percentage = 0;
                    } else {
                        if (answers[2] == 1) {
                            done_string += "AMD RYZEN 9 3900X - Best Processor to use for rendering.\n";
                        } else if (answers[2] == 3) {
                            done_string += "AMD RYZEN 7 3700X - Best Processor to use for rendering.\n";
                        }
                        if (answers[3] == 1) {
                            done_string = "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string += "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string += "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string += "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string += "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string += "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string += "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string += "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string += "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string += "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string += "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string += "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string += "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 2) {
                                done_string += "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 3) {
                                done_string += "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    }
                } else if (answers[1] == 2) {
                    done_string += "ASUS Prime A320M-K AM4 - Motherboard is too small for the case, but will have a great airflow and cable managing, but have a less RAM capacity than the others.\n";
                    percentage -= 5;
                    if (answers[2] == 2) {
                        done_string = "Motherboard and CPU is not compatible with each other.";
                        percentage = 0;
                    } else {
                        if (answers[2] == 1) {
                            done_string += "AMD RYZEN 9 3900X - Best Processor to use for rendering.\n";
                        } else if (answers[2] == 3) {
                            done_string += "AMD RYZEN 7 3700X - Best Processor to use for rendering.\n";
                        }
                        if (answers[3] == 1) {
                            done_string = "Since you picked for GPU \"Not Needed\", your PC will not be running or showing a display. You need a GPU or a processor that have a Video Graphics Built In.";
                            percentage = 0;
                        } else {
                            if (answers[3] == 2) {
                                done_string += "AMD Radeon RX580 - Great for 3D Modelling.\n";
                            } else if (answers[3] == 3) {
                                done_string += "GEFORCE RTX 2080 Ti - Great for 3D Modelling.\n";
                            }

                            if (answers[4] == 1) {
                                done_string += "CORSAIR DDR4-3200MHz - Great for 3D Modeling.\n";
                            } else if (answers[4] == 2) {
                                done_string += "TeamGroup T-Force Xtreem ARGB - RAM is okay for 3D modeling.\n";
                                percentage -= 5;
                            } else if (answers[4] == 3) {
                                done_string += "Kingston HyperX Fury  - RAM size is too low for 3D Modeling.\n";
                                percentage -= 10;
                            }

                            if (answers[5] == 1) {
                                done_string += "Seagate 1TB SATA BarraCuda - HDD is okay for saving projects.\n";
                                percentage -= 5;
                            } else if (answers[5] == 2) {
                                done_string += "Toshiba 500GB - This HDD size is too low, you might need a bigger capacity/size for saving projects.\n";
                                percentage -= 20;
                            } else if (answers[5] == 3) {
                                done_string += "Western Digital WD Blue 2TB - This HDD is great for saving projects.\n";
                            }

                            if (answers[6] == 1) {
                                done_string += "SSD - SSD is essential for 3d Modeling, since it makes passing data more faster.\n";
                                percentage -= 20;
                            } else if (answers[6] == 2) {
                                done_string += "KINGSTON A400 240GB SSD - You pick a much higher SSD capacity than the other, great choice.\n";
                            } else if (answers[6] == 3) {
                                done_string += "WD Green™ 120GB SSD - Having an SSD is quite essential.\n";
                            }

                            if (answers[7] == 1) {
                                done_string += "Corsair VS500 - This PSU is great for saving energy, but a higher watts is much okay.\n";
                                percentage -= 10;
                            } else if (answers[7] == 2) {
                                done_string += "Cooler Master Elite V3 - Since you'll using a GPU and will be maximizing it, you might need a higher watts.\n";
                                percentage -= 20;
                            } else if (answers[7] == 3) {
                                done_string += "Cooler Master MWE Bronze V2 - This PSU is great for saving energy.\n";
                            }
                        }
                    }
                }
                break;
        }
    }
}

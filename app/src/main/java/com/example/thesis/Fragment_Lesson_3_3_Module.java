package com.example.thesis;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Fragment_Lesson_3_3_Module extends Fragment {

    ImageView back, logo;
    ImageView module_button[] = new ImageView[2];
    LinearLayout module_title_layout;
    TextView module_title_text;
    LinearLayout module_content[] = new LinearLayout[2];
    LinearLayout module_previous_layout, module_next_layout;
    TextView module_previous_text, module_next_text;
    int previous_selected = 0;
    int id;
    ScrollView scrollView;

    public Fragment_Lesson_3_3_Module() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_3_3_module, container, false);

        initialization(view);
        onClickListener();

        return view;
    }

    public void initialization(View view) {
        scrollView = view.findViewById(R.id.module_3_3_scrollview);
        back = view.findViewById(R.id.module_3_3_back);
        logo = view.findViewById(R.id.module_3_3_logo);
        module_title_layout = view.findViewById(R.id.module_3_3_title_layout);
        module_title_text = view.findViewById(R.id.module_3_3_title_text);
        for (int i = 0; i < module_button.length; i++) {
            id = view.getResources().getIdentifier(
                    "module_3_3_button_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            module_button[i] = view.findViewById(id);
            id = view.getResources().getIdentifier(
                    "module_3_3_content_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            module_content[i] = view.findViewById(id);
        }
        module_previous_layout = view.findViewById(R.id.module_3_3_previous_layout);
        module_next_layout = view.findViewById(R.id.module_3_3_next_layout);
        module_previous_text = view.findViewById(R.id.module_3_3_previous_text);
        module_next_text = view.findViewById(R.id.module_3_3_next_text);

        if (previous_selected == 0) {
            module_previous_layout.setVisibility(View.INVISIBLE);
        }
    }

    public void onClickListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_Menu_Lesson menu_lesson = new Fragment_Menu_Lesson();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Lesson_3_3_Module.this);
                fragmentTransaction.add(android.R.id.content, menu_lesson);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.commit();
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_Menu_Main menu_main = new Fragment_Menu_Main();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Lesson_3_3_Module.this);
                fragmentTransaction.add(android.R.id.content, menu_main);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.commit();
            }
        });
        for (int i = -0; i < module_button.length; i++) {
            int finalI = i;
            module_button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollView.scrollTo(0, 0);
                    if (finalI == 0) {
                        module_previous_layout.setVisibility(View.GONE);
                    } else {
                        module_previous_layout.setVisibility(View.VISIBLE);
                    }
                    module_button[previous_selected].setImageDrawable(getResources().getDrawable(R.drawable.module_unselected));
                    module_content[previous_selected].setVisibility(View.GONE);
                    module_button[finalI].setImageDrawable(getResources().getDrawable(R.drawable.module_selected));
                    module_content[finalI].setVisibility(View.VISIBLE);
                    if (previous_selected == (module_content.length - 1)) {
                        if (finalI != previous_selected) {
                            module_title_layout.setVisibility(View.VISIBLE);
                            module_previous_text.setText("Previous");
                            module_next_text.setText("Next");
                        }
                    }
                    previous_selected = finalI;
                    if (previous_selected == (module_content.length - 1)) {
                        module_title_layout.setVisibility(View.GONE);
                        module_previous_text.setText("Go Back");
                        module_next_text.setText("Let's Go");
                    } else {
                        id = getResources().getIdentifier(
                                "module_3_3_" + (previous_selected + 1) + "_title",
                                "string",
                                getActivity().getPackageName());
                        module_title_text.setText(getResources().getString(id));
                    }
                }
            });
        }
        module_previous_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0, 0);
                if (previous_selected == 1) {
                    module_previous_layout.setVisibility(View.INVISIBLE);
                }

                module_content[previous_selected].setVisibility(View.GONE);
                module_button[previous_selected].setImageDrawable(getResources().getDrawable(R.drawable.module_unselected));
                previous_selected--;
                module_content[previous_selected].setVisibility(View.VISIBLE);
                module_button[previous_selected].setImageDrawable(getResources().getDrawable(R.drawable.module_selected));

                if (previous_selected == (module_content.length - 2)) {
                    module_previous_text.setText("Previous");
                    module_next_text.setText("Next");
                    module_title_layout.setVisibility(View.VISIBLE);
                }
                id = getResources().getIdentifier(
                        "module_3_3_" + (previous_selected + 1) + "_title",
                        "string",
                        getActivity().getPackageName());
                module_title_text.setText(getResources().getString(id));
            }
        });

        module_next_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((previous_selected + 1) == module_content.length) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putInt("lesson", 3);
                    bundle.putInt("module", 3);
                    bundle.putInt("number", 11);
                    Fragment_Lesson_Module_Quiz fragment = new Fragment_Lesson_Module_Quiz();
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.hide(Fragment_Lesson_3_3_Module.this);
                    fragmentTransaction.add(android.R.id.content, fragment);
                    fragmentTransaction.commit();


                } else {
                    previous_selected++;
                    scrollView.scrollTo(0, 0);
                    if (previous_selected > 0) {
                        module_previous_layout.setVisibility(View.VISIBLE);
                    }

                    module_content[previous_selected - 1].setVisibility(View.GONE);
                    module_button[previous_selected - 1].setImageDrawable(getResources().getDrawable(R.drawable.module_unselected));
                    module_content[previous_selected].setVisibility(View.VISIBLE);
                    module_button[previous_selected].setImageDrawable(getResources().getDrawable(R.drawable.module_selected));

                    if (previous_selected == (module_content.length - 1)) {
                        module_title_layout.setVisibility(View.GONE);
                        module_previous_text.setText("Go Back");
                        module_next_text.setText("Let's Go");
                    } else {
                        id = getResources().getIdentifier(
                                "module_3_3_" + (previous_selected + 1) + "_title",
                                "string",
                                getActivity().getPackageName());
                        module_title_text.setText(getResources().getString(id));
                    }
                }
            }
        });
    }

}

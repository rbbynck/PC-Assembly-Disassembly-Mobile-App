package com.example.thesis;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Fragment_Lesson_8_1_Module extends Fragment {

    VideoView videoView;
    ConstraintLayout video_layout;
    ImageView back, logo, play_button;
    ImageView module_button[] = new ImageView[7];
    LinearLayout module_title_layout;
    TextView module_title_text;
    LinearLayout module_content[] = new LinearLayout[7];
    LinearLayout module_previous_layout, module_next_layout;
    TextView module_previous_text, module_next_text;
    int previous_selected = 0;
    int id;
    ScrollView scrollView;

    public Fragment_Lesson_8_1_Module() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_8_1_module, container, false);

        initialization(view);
        onClickListener();

        return view;
    }

    public void initialization(View view) {
        video_layout = view.findViewById(R.id.module_8_1_video_layout);
        play_button = view.findViewById(R.id.module_8_1_videoplay);
        videoView = view.findViewById(R.id.module_8_1_video_0);
        scrollView = view.findViewById(R.id.module_8_1_scrollview);
        back = view.findViewById(R.id.module_8_1_back);
        logo = view.findViewById(R.id.module_8_1_logo);
        module_title_layout = view.findViewById(R.id.module_8_1_title_layout);
        module_title_text = view.findViewById(R.id.module_8_1_title_text);
        for (int i = 0; i < module_button.length; i++) {
            id = view.getResources().getIdentifier(
                    "module_8_1_button_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            module_button[i] = view.findViewById(id);
            id = view.getResources().getIdentifier(
                    "module_8_1_content_" + (i + 1),
                    "id",
                    getActivity().getPackageName());
            module_content[i] = view.findViewById(id);
        }
        module_previous_layout = view.findViewById(R.id.module_8_1_previous_layout);
        module_next_layout = view.findViewById(R.id.module_8_1_next_layout);
        module_previous_text = view.findViewById(R.id.module_8_1_previous_text);
        module_next_text = view.findViewById(R.id.module_8_1_next_text);

        if (previous_selected == 0) {
            module_previous_layout.setVisibility(View.INVISIBLE);
        }

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.stopPlayback();
                video_layout.setVisibility(View.GONE);
            }
        });
    }

    public void onClickListener() {
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
                fragment.getView().setFocusableInTouchMode(true);
                fragment.getView().requestFocus();
                fragment.getView().setOnKeyListener( new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey( View v, int keyCode, KeyEvent event )
                    {
                        if( keyCode == KeyEvent.KEYCODE_BACK )
                        {
                            videoView.stopPlayback();
                            video_layout.setVisibility(View.GONE);
                            return true;
                        }
                        return false;
                    }
                } );

                play_button.setClickable(false);
                video_layout.setVisibility(View.VISIBLE);
                String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.assemble;
                Uri uri = Uri.parse(videoPath);
                videoView.setVideoURI(uri);

                MediaController mediaController = new MediaController(getActivity());
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);

                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                            @Override
                            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                                    //first frame was bufered - do your stuff here
                                }
                                return false;
                            }
                        });
                        videoView.start();
                    }
                });
            }
        });
        //Back Button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_Menu_Lesson menu_lesson = new Fragment_Menu_Lesson();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Lesson_8_1_Module.this);
                fragmentTransaction.add(android.R.id.content, menu_lesson);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.commit();
            }
        });
        //Logo Button/Main Menu
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_Menu_Main menu_main = new Fragment_Menu_Main();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Lesson_8_1_Module.this);
                fragmentTransaction.add(android.R.id.content, menu_main);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.commit();
            }
        });
        //Module Buttons
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
                                "module_8_1_" + (previous_selected + 1) + "_title",
                                "string",
                                getActivity().getPackageName());
                        module_title_text.setText(getResources().getString(id));
                    }
                }
            });
        }
        //Previous Button
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
                        "module_8_1_" + (previous_selected + 1) + "_title",
                        "string",
                        getActivity().getPackageName());
                module_title_text.setText(getResources().getString(id));
            }
        });
        //Next Button
        module_next_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((previous_selected + 1) == module_content.length) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putInt("lesson", 8);
                    bundle.putInt("module", 1);
                    bundle.putInt("number", 27);
                    Fragment_Lesson_Module_Quiz fragment = new Fragment_Lesson_Module_Quiz();
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.hide(Fragment_Lesson_8_1_Module.this);
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
                                "module_8_1_" + (previous_selected + 1) + "_title",
                                "string",
                                getActivity().getPackageName());
                        module_title_text.setText(getResources().getString(id));
                    }
                }
            }
        });
    }

}

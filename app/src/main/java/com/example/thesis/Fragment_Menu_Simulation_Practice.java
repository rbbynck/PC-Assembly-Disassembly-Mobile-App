package com.example.thesis;


import android.app.Dialog;
import android.content.ClipData;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Menu_Simulation_Practice extends Fragment {

    ClipData clipData;
    boolean entered = false;
    boolean entered1 = false;
    boolean entered2 = false;
    boolean entered3 = false;

    String tag;

    String items_texts[] = {"Front and Back Case Lid Screws", "Front Case Lid", "Back Case Lid",
            "SSD Screws", "SSD", "HDD Screws", "HDD", "Sata Cables", "PSU Screws", "PSU", "Video Card Screws",
            "Video Card", "RAM", "Heat Sink Screws", "Heat Sink", "CPU", "Motherboard Screws", "Motherboard"};
    TextView items_text;
    //viewpager2
    List<Fragment_Menu_Simulation_Tutorial_SliderItem> sliderItems;
    List<Fragment_Menu_Simulation_Tutorial_SliderItem_1> sliderItems_1;
    ViewPager2 viewPager2;

    ConstraintLayout screw_layout;

    int screw_no = 0;
    int last_position = 0;
    int item_set[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    Database database;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ImageView exit, simulation_image, task_check;
    TextView task_text;
    Animation fade_in, fade_out;

    public Fragment_Menu_Simulation_Practice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_simulation_practice, container, false);

        database = new Database(getActivity());
        initialization(view);
        onClickListener(view);
        initializeViewPager(view);
        taskOne(view);
        return view;
    }

    public void initialization(View view) {
        fade_out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_out);
        fade_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_in);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        exit = view.findViewById(R.id.simulation_exit);
        simulation_image = view.findViewById(R.id.simulation_practice_image);
        screw_layout = view.findViewById(R.id.simulation_practice_case_screw_layout);
        items_text = view.findViewById(R.id.simulation_practice_item_text);
        items_text.setText("Front and Back Case Lid Screws");
    }

    public void onClickListener(View view) {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ui_flags =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                View view = getLayoutInflater().inflate(R.layout.simulation_exit, null);
                Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                builder.setContentView(view);

                if (builder.getWindow() != null) {
                    builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                builder.getWindow().
                        setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

                builder.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
                builder.show();

                view.findViewById(R.id.simulation_exit_check).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment frg = null;
                        frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
                        Fragment_Menu_Simulation fragment = new Fragment_Menu_Simulation();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Menu_Simulation_Practice.this);
                        fragmentTransaction.add(android.R.id.content, fragment);
                        fragmentTransaction.detach(frg);
                        fragmentTransaction.attach(frg);
                        fragmentTransaction.commit();
                        builder.dismiss();
                    }
                });

                view.findViewById(R.id.simulation_exit_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

                view.findViewById(R.id.simulation_exit_restart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment frg = null;
                        frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
                        Fragment_Menu_Simulation_Practice fragment = new Fragment_Menu_Simulation_Practice();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.hide(Fragment_Menu_Simulation_Practice.this);
                        fragmentTransaction.add(android.R.id.content, fragment);
                        fragmentTransaction.detach(frg);
                        fragmentTransaction.attach(frg);
                        fragmentTransaction.commit();
                        builder.dismiss();
                    }
                });
            }
        });
    }

    public void taskOne(View view) {
        simulation_image.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    simulation_image.setImageResource(R.drawable.simulation_image_1);
                    screw_layout.setVisibility(View.VISIBLE);
                    simulation_image.setEnabled(false);
                    taskTwo(view);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskTwo(View view) {
        char screws_gone[] = {'_', '_', '_', '_'};
        ImageView screw_1, screw_2, screw_3, screw_4;
        screw_1 = view.findViewById(R.id.simulation_practice_case_screw_1);
        screw_2 = view.findViewById(R.id.simulation_practice_case_screw_2);
        screw_3 = view.findViewById(R.id.simulation_practice_case_screw_3);
        screw_4 = view.findViewById(R.id.simulation_practice_case_screw_4);

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_1_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_1_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        screw_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3.setImageResource(R.drawable.simulation_image_1_item_3);
                screws_gone[2] = '3';
                tag = "3";
                int id = view.getResources().getIdentifier(
                        "simulation_image_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3.setImageResource(0);
                return true;
            }
        });

        screw_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_4.setImageResource(R.drawable.simulation_image_1_item_4);
                screws_gone[3] = '4';
                tag = "4";
                int id = view.getResources().getIdentifier(
                        "simulation_image_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_4.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 0) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                                case 2:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 3:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 4:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_4));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(0);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 0) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                                case 3:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 4:
                                    sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(0);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 0) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                                case "3":
                                    screws_gone[2] = '_';
                                    break;
                                case "4":
                                    screws_gone[3] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                id = view.getResources().getIdentifier(
                                        "simulation_image_1",
                                        "drawable",
                                        getActivity().getPackageName());
                                simulation_image.setImageResource(id);
                            }
                            simulation_image.setImageResource(id);
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        screw_3.setVisibility(View.GONE);
                                        break;
                                    case "4":
                                        screw_4.setVisibility(View.GONE);
                                        break;

                                }
                                item_set[0] = 1;

                                if (no_of_screw == 4) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_2);
                                                screw_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskThree(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                    case "3":
                                        screws_gone[2] = '_';
                                        break;
                                    case "4":
                                        screws_gone[3] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_1_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    id = view.getResources().getIdentifier(
                                            "simulation_image_1",
                                            "drawable",
                                            getActivity().getPackageName());
                                    simulation_image.setImageResource(id);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThree(View view) {
        ConstraintLayout frontcaselid_layout = view.findViewById(R.id.simulation_practice_frontcaselid_layout);
        ImageView frontcaselid_item = view.findViewById(R.id.simulation_practice_frontcaselid_item);
        frontcaselid_layout.setVisibility(View.VISIBLE);

        frontcaselid_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                frontcaselid_item.setImageResource(R.drawable.simulation_image_2_item);
                simulation_image.setImageResource(R.drawable.simulation_image_3);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                frontcaselid_item.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 1) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_caselid_front));
                            viewPager2.getAdapter().notifyItemChanged(1);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 1) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(1);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 1) {
                            simulation_image.setImageResource(R.drawable.simulation_image_2);
                        } else {
                            if (entered == true) {
                                item_set[1] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                frontcaselid_item.setVisibility(View.GONE);
                                frontcaselid_layout.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_4);
                                            simulation_image.setEnabled(false);
                                            taskFour(view);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_2);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFour(View view) {
        ConstraintLayout motherboard_psu_layout = view.findViewById(R.id.simulation_practice_motherboardpsucables_layout);
        ImageView four_pin = view.findViewById(R.id.simulation_practice_motherboardpsucables_4pin);
        ConstraintLayout motherboard_four_pin_psu_layout = view.findViewById(R.id.simulation_practice_motherboardpsucables4pin_layout);
        ImageView four_pin_item = view.findViewById(R.id.simulation_practice_motherboardpsucables4pin_item);
        ImageView twenty_four_pin = view.findViewById(R.id.simulation_practice_motherboardpsucables_24pin);
        ConstraintLayout motherboard_twenty_four_pin_psu_layout = view.findViewById(R.id.simulation_practice_motherboardpsucables24pin_layout);
        ImageView twenty_four_pin_item = view.findViewById(R.id.simulation_practice_motherboardpsucables24pin_item);
        motherboard_psu_layout.setVisibility(View.VISIBLE);

        four_pin.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    four_pin.setVisibility(View.GONE);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_1_0);
                    motherboard_psu_layout.setVisibility(View.GONE);
                    motherboard_four_pin_psu_layout.setVisibility(View.VISIBLE);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        four_pin_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewPager2.setEnabled(false);
                four_pin_item.setImageResource(R.drawable.simulation_image_4_1_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_4_1_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                four_pin_item.setImageResource(0);
                simulation_image.setEnabled(true);
                motherboard_four_pin_psu_layout.setVisibility(View.GONE);
                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            simulation_image.setEnabled(false);
                            if (twenty_four_pin.getVisibility() == View.VISIBLE) {
                                motherboard_psu_layout.setVisibility(View.VISIBLE);
                                simulation_image.setImageResource(R.drawable.simulation_image_4_1);
                            } else {
                                motherboard_psu_layout.setVisibility(View.GONE);
                                simulation_image.setImageResource(R.drawable.simulation_image_4_3);
                                taskFive(view);
                            }
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return true;
                    }
                });
                return true;
            }
        });

        twenty_four_pin.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    twenty_four_pin.setVisibility(View.GONE);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_2_0);
                    motherboard_psu_layout.setVisibility(View.GONE);
                    motherboard_twenty_four_pin_psu_layout.setVisibility(View.VISIBLE);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        twenty_four_pin_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewPager2.setEnabled(false);
                twenty_four_pin_item.setImageResource(R.drawable.simulation_image_4_2_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_4_2_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                twenty_four_pin_item.setImageResource(0);
                simulation_image.setEnabled(true);
                motherboard_twenty_four_pin_psu_layout.setVisibility(View.GONE);
                twenty_four_pin_item.setVisibility(View.GONE);
                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            simulation_image.setEnabled(false);
                            if (four_pin.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_2);
                                motherboard_psu_layout.setVisibility(View.VISIBLE);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_3);
                                taskFive(view);
                                motherboard_psu_layout.setVisibility(View.GONE);
                            }
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return true;
                    }
                });
                return true;
            }
        });
    }

    public void taskFive(View view) {
        simulation_image.setEnabled(false);
        ConstraintLayout motherboard_sata_layout = view.findViewById(R.id.simulation_practice_motherboardsata_layout);
        ImageView motherboard_sata = view.findViewById(R.id.simulation_practice_motherboardsata_item);
        ConstraintLayout motherboard_satacables_layout = view.findViewById(R.id.simulation_practice_motherboardpsatacables_layout);
        ImageView motherboard_satacables_1 = view.findViewById(R.id.simulation_practice_motherboardpsatacables_1_item);
        ImageView motherboard_satacables_2 = view.findViewById(R.id.simulation_practice_motherboardpsatacables_2_item);

        motherboard_sata_layout.setVisibility(View.VISIBLE);
        motherboard_sata.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    motherboard_sata_layout.setVisibility(View.GONE);
                    motherboard_sata.setVisibility(View.GONE);
                    motherboard_satacables_layout.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_4_0);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        motherboard_satacables_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                motherboard_satacables_1.setImageResource(R.drawable.simulation_image_4_4_1_item);
                if (motherboard_satacables_2.getVisibility() == View.GONE) {
                    simulation_image.setImageResource(R.drawable.simulation_image_4_4_3);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4);
                                taskSix(view);
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                } else {
                    simulation_image.setImageResource(R.drawable.simulation_image_4_4_2);
                }

                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                motherboard_satacables_1.setImageResource(0);
                motherboard_satacables_1.setVisibility(View.GONE);
                return true;
            }
        });
        motherboard_satacables_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                motherboard_satacables_2.setImageResource(R.drawable.simulation_image_4_4_2_item);
                if (motherboard_satacables_1.getVisibility() == View.GONE) {
                    simulation_image.setImageResource(R.drawable.simulation_image_4_4_3);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4);
                                taskSix(view);
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                } else {
                    simulation_image.setImageResource(R.drawable.simulation_image_4_4_1);
                }

                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                motherboard_satacables_2.setImageResource(0);
                motherboard_satacables_2.setVisibility(View.GONE);
                return true;
            }
        });
    }

    public void taskSix(View view) {
        simulation_image.setEnabled(false);
        ConstraintLayout motherboard_frontpanel_layout = view.findViewById(R.id.simulation_practice_motherboardfrontpanel_layout);
        ConstraintLayout motherboard_frontpanelcable_layout = view.findViewById(R.id.simulation_practice_motherboardfrontpanelcable_layout);
        ImageView motherboard_frontpanel = view.findViewById(R.id.simulation_practice_motherboardfrontpanel_item);
        ImageView motherboard_frontpanelcable = view.findViewById(R.id.simulation_practice_motherboardfrontpanelcable_item);
        motherboard_frontpanel_layout.setVisibility(View.VISIBLE);

        motherboard_frontpanel.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    motherboard_frontpanel_layout.setVisibility(View.GONE);
                    motherboard_frontpanel.setVisibility(View.GONE);
                    motherboard_frontpanelcable_layout.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_5_0);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        motherboard_frontpanelcable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                motherboard_frontpanelcable.setImageResource(R.drawable.simulation_image_4_5_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_4_5_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                motherboard_frontpanelcable.setImageResource(0);
                motherboard_frontpanelcable.setVisibility(View.GONE);
                motherboard_frontpanelcable_layout.setVisibility(View.GONE);
                simulation_image.setEnabled(true);
                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            simulation_image.setImageResource(R.drawable.simulation_image_4_5);
                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                    @Override
                                    public boolean onDoubleTap(MotionEvent e) {
                                        simulation_image.setImageResource(R.drawable.simulation_image_5);
                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                @Override
                                                public boolean onDoubleTap(MotionEvent e) {
                                                    simulation_image.setImageResource(R.drawable.simulation_image_6_0);
                                                    simulation_image.setEnabled(false);
                                                    taskSeven(view);
                                                    return super.onDoubleTap(e);
                                                }
                                            });

                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                gestureDetector.onTouchEvent(event);
                                                return true;
                                            }
                                        });
                                        return super.onDoubleTap(e);
                                    }
                                });

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    gestureDetector.onTouchEvent(event);
                                    return true;
                                }
                            });
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return true;
                    }
                });
                return true;
            }
        });
    }

    public void taskSeven(View view) {
        ConstraintLayout backviewcaselid_layout = view.findViewById(R.id.simulation_practice_backviewcaselid_layout);
        ImageView backviewcaselid_item = view.findViewById(R.id.simulation_practice_backviewcaselid_item);
        backviewcaselid_layout.setVisibility(View.VISIBLE);

        backviewcaselid_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                backviewcaselid_item.setImageResource(R.drawable.simulation_image_6_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_6_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                backviewcaselid_item.setImageResource(0);
                return true;
            }
        });

        viewPager2.setEnabled(true);
        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 2) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_caselid_back));
                            viewPager2.getAdapter().notifyItemChanged(2);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 2) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(2);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 2) {
                            simulation_image.setImageResource(R.drawable.simulation_image_6_0);
                        } else {
                            if (entered == true) {
                                item_set[2] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                backviewcaselid_layout.setVisibility(View.GONE);
                                backviewcaselid_item.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_7);
                                            viewPager2.setEnabled(false);
                                            simulation_image.setEnabled(false);
                                            taskEight(view);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_6_0);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskEight(View view) {
        ConstraintLayout storage_layout = view.findViewById(R.id.simulation_practice_storage_layout);
        ConstraintLayout ssd_cords_layout = view.findViewById(R.id.simulation_practice_ssd_cords_layout);
        ImageView storage = view.findViewById(R.id.simulation_practice_storage_item);
        ImageView ssd_psu_cords = view.findViewById(R.id.simulation_practice_ssd_cordspsu_item);
        ImageView ssd_sata_cords = view.findViewById(R.id.simulation_practice_ssd_cordssata_item);
        storage_layout.setVisibility(View.VISIBLE);
        storage.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    simulation_image.setImageResource(R.drawable.simulation_image_8_0);
                    storage_layout.setVisibility(View.GONE);
                    ssd_cords_layout.setVisibility(View.VISIBLE);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        ssd_psu_cords.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ssd_psu_cords.setImageResource(R.drawable.simulation_image_8_1_item);
                if (ssd_sata_cords.getVisibility() == View.GONE) {
                    simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                simulation_image.setImageResource(R.drawable.simulation_image_9);
                                ssd_cords_layout.setVisibility(View.GONE);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                                            ssd_cords_layout.setVisibility(View.GONE);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_10_1);
                                                        simulation_image.setEnabled(false);
                                                        viewPager2.setEnabled(true);
                                                        taskNine(view);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                } else {
                    simulation_image.setImageResource(R.drawable.simulation_image_8_2);
                }
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                ssd_psu_cords.setImageResource(0);
                ssd_psu_cords.setVisibility(View.GONE);
                return true;
            }
        });

        ssd_sata_cords.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ssd_sata_cords.setImageResource(R.drawable.simulation_image_8_2_item);
                if (ssd_psu_cords.getVisibility() == View.GONE) {
                    simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                simulation_image.setImageResource(R.drawable.simulation_image_9);
                                ssd_cords_layout.setVisibility(View.GONE);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                                            ssd_cords_layout.setVisibility(View.GONE);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_10_1);
                                                        simulation_image.setEnabled(false);
                                                        viewPager2.setEnabled(true);
                                                        taskNine(view);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                } else {
                    simulation_image.setImageResource(R.drawable.simulation_image_8_1);
                }
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                ssd_sata_cords.setImageResource(0);
                ssd_sata_cords.setVisibility(View.GONE);
                return true;
            }
        });
    }

    public void taskNine(View view) {
        char screws_gone[] = {'_', '_', '_', '_'};
        ConstraintLayout ssd_screw_layout = view.findViewById(R.id.simulation_practice_ssd_screw_layout);
        ImageView screw_1 = view.findViewById(R.id.simulation_practice_ssd_screw_1);
        ImageView screw_2 = view.findViewById(R.id.simulation_practice_ssd_screw_2);
        ImageView screw_3 = view.findViewById(R.id.simulation_practice_ssd_screw_3);
        ImageView screw_4 = view.findViewById(R.id.simulation_practice_ssd_screw_4);
        ssd_screw_layout.setVisibility(View.VISIBLE);

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_10_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_10_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_10_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_10_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        screw_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3.setImageResource(R.drawable.simulation_image_10_item_3);
                screws_gone[2] = '3';
                tag = "3";
                int id = view.getResources().getIdentifier(
                        "simulation_image_10_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3.setImageResource(0);
                return true;
            }
        });

        screw_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_4.setImageResource(R.drawable.simulation_image_10_item_4);
                screws_gone[3] = '4';
                tag = "4";
                int id = view.getResources().getIdentifier(
                        "simulation_image_10_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_4.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 3) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 3) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;

                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 3) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                                case "3":
                                    screws_gone[2] = '_';
                                    break;
                                case "4":
                                    screws_gone[3] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                id = view.getResources().getIdentifier(
                                        "simulation_image_10_1",
                                        "drawable",
                                        getActivity().getPackageName());
                                simulation_image.setImageResource(id);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        screw_3.setVisibility(View.GONE);
                                        break;
                                    case "4":
                                        screw_4.setVisibility(View.GONE);
                                        break;
                                }
                                item_set[viewPager2.getCurrentItem()] = 1;
                                if (no_of_screw == 4) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                                                ssd_screw_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskTen(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                    case "3":
                                        screws_gone[2] = '_';
                                        break;
                                    case "4":
                                        screws_gone[3] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_10_1_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    id = view.getResources().getIdentifier(
                                            "simulation_image_10_1",
                                            "drawable",
                                            getActivity().getPackageName());
                                    simulation_image.setImageResource(id);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTen(View view) {
        ConstraintLayout ssd_ssd_layout = view.findViewById(R.id.simulation_practice_ssd_ssd_layout);
        ImageView ssd_ssd = view.findViewById(R.id.simulation_practice_ssd_ssd_item);
        ssd_ssd_layout.setVisibility(View.VISIBLE);

        ssd_ssd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ssd_ssd.setImageResource(R.drawable.simulation_image_10_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_11);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                ssd_ssd.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 4) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 4) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 4) {
                            simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                        } else {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                ssd_ssd_layout.setVisibility(View.GONE);
                                ssd_ssd.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_12);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_13);
                                                        taskEleven(view);
                                                        simulation_image.setEnabled(false);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskEleven(View view) {
        viewPager2.setEnabled(false);
        ConstraintLayout storage_layout = view.findViewById(R.id.simulation_practice_storage_layout);
        ImageView storage = view.findViewById(R.id.simulation_practice_storage_item);
        storage_layout.setVisibility(View.VISIBLE);
        ConstraintLayout hdd_cords_layout = view.findViewById(R.id.simulation_practice_hdd_cords_layout);
        ImageView hdd_psu_cords = view.findViewById(R.id.simulation_practice_hdd_cordspsu_item);
        ImageView hdd_sata_cords = view.findViewById(R.id.simulation_practice_hdd_cordssata_item);

        storage.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    simulation_image.setImageResource(R.drawable.simulation_image_14);
                    storage_layout.setVisibility(View.GONE);
                    hdd_cords_layout.setVisibility(View.VISIBLE);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        hdd_psu_cords.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hdd_psu_cords.setImageResource(R.drawable.simulation_image_14_1_item);
                if (hdd_sata_cords.getVisibility() == View.GONE) {
                    simulation_image.setImageResource(R.drawable.simulation_image_14_3);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                hdd_cords_layout.setVisibility(View.GONE);
                                simulation_image.setImageResource(R.drawable.simulation_image_15);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_16);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_17_0);
                                                        simulation_image.setEnabled(false);
                                                        viewPager2.setEnabled(true);
                                                        taskTwelve(view);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                } else {
                    simulation_image.setImageResource(R.drawable.simulation_image_14_2);
                }
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                hdd_psu_cords.setImageResource(0);
                hdd_psu_cords.setVisibility(View.GONE);
                return true;
            }
        });

        hdd_sata_cords.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hdd_sata_cords.setImageResource(R.drawable.simulation_image_14_2_item);
                if (hdd_psu_cords.getVisibility() == View.GONE) {
                    simulation_image.setImageResource(R.drawable.simulation_image_14_3);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                simulation_image.setImageResource(R.drawable.simulation_image_15);
                                hdd_cords_layout.setVisibility(View.GONE);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_16);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_17_0);
                                                        simulation_image.setEnabled(false);
                                                        viewPager2.setEnabled(true);
                                                        taskTwelve(view);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                } else {
                    simulation_image.setImageResource(R.drawable.simulation_image_14_1);
                }
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                hdd_sata_cords.setImageResource(0);
                hdd_sata_cords.setVisibility(View.GONE);
                return true;
            }
        });
    }

    public void taskTwelve(View view) {
        char screws_gone[] = {'_', '_', '_'};
        ConstraintLayout hdd_screw_layout = view.findViewById(R.id.simulation_practice_hdd_screw_1_layout);
        ImageView screw_1 = view.findViewById(R.id.simulation_practice_hdd_screw_1_1);
        ImageView screw_2 = view.findViewById(R.id.simulation_practice_hdd_screw_1_2);
        ImageView screw_3 = view.findViewById(R.id.simulation_practice_hdd_screw_1_3);
        hdd_screw_layout.setVisibility(View.VISIBLE);

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_17_0_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_17_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_17_0_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_17_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        screw_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3.setImageResource(R.drawable.simulation_image_17_0_item_3);
                screws_gone[2] = '3';
                tag = "3";
                int id = view.getResources().getIdentifier(
                        "simulation_image_17_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_3));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 5) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                                case "3":
                                    screws_gone[2] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                id = view.getResources().getIdentifier(
                                        "simulation_image_17_0",
                                        "drawable",
                                        getActivity().getPackageName());
                                simulation_image.setImageResource(id);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        screw_3.setVisibility(View.GONE);
                                        break;
                                }
                                item_set[viewPager2.getCurrentItem()] = 1;

                                if (no_of_screw == 3) {
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_17_1);
                                                hdd_screw_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskThirteen(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                    case "3":
                                        screws_gone[2] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_17_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());

                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    id = view.getResources().getIdentifier(
                                            "simulation_image_17_0",
                                            "drawable",
                                            getActivity().getPackageName());
                                    simulation_image.setImageResource(id);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirteen(View view) {
        char screws_gone[] = {'_', '_', '_'};
        ConstraintLayout hdd_screw_layout = view.findViewById(R.id.simulation_practice_hdd_screw_2_layout);
        ImageView screw_1 = view.findViewById(R.id.simulation_practice_hdd_screw_2_1);
        ImageView screw_2 = view.findViewById(R.id.simulation_practice_hdd_screw_2_2);
        ImageView screw_3 = view.findViewById(R.id.simulation_practice_hdd_screw_2_3);
        hdd_screw_layout.setVisibility(View.VISIBLE);

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_17_1_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_17_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_17_1_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_17_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        screw_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3.setImageResource(R.drawable.simulation_image_17_1_item_3);
                screws_gone[2] = '3';
                tag = "3";
                int id = view.getResources().getIdentifier(
                        "simulation_image_17_1_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_6));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_4));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 5) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                                case "3":
                                    screws_gone[2] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                id = view.getResources().getIdentifier(
                                        "simulation_image_17_1",
                                        "drawable",
                                        getActivity().getPackageName());
                                simulation_image.setImageResource(id);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        screw_3.setVisibility(View.GONE);
                                        break;
                                }
                                if (no_of_screw == 3) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_18_0);
                                                hdd_screw_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskFourteen(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                    case "3":
                                        screws_gone[2] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_17_1_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());

                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    id = view.getResources().getIdentifier(
                                            "simulation_image_17_1",
                                            "drawable",
                                            getActivity().getPackageName());
                                    simulation_image.setImageResource(id);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFourteen(View view) {
        ConstraintLayout hdd_hdd_layout = view.findViewById(R.id.simulation_practice_hdd_hdd_layout);
        ImageView hdd_hdd = view.findViewById(R.id.simulation_practice_hdd_hdd_item);
        hdd_hdd_layout.setVisibility(View.VISIBLE);

        hdd_hdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hdd_hdd.setImageResource(R.drawable.simulation_image_18_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_18_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                hdd_hdd.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 6) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 6) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 6) {
                            simulation_image.setImageResource(R.drawable.simulation_image_18_0);
                        } else {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                hdd_hdd_layout.setVisibility(View.GONE);
                                hdd_hdd.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_19);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_20);
                                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                @Override
                                                                public boolean onDoubleTap(MotionEvent e) {
                                                                    simulation_image.setImageResource(R.drawable.simulation_image_21_0);
                                                                    taskFifteen(view);
                                                                    simulation_image.setEnabled(false);
                                                                    return super.onDoubleTap(e);
                                                                }
                                                            });

                                                            @Override
                                                            public boolean onTouch(View v, MotionEvent event) {
                                                                gestureDetector.onTouchEvent(event);
                                                                return true;
                                                            }
                                                        });
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_18_0);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFifteen(View view) {
        viewPager2.setEnabled(false);
        ConstraintLayout four_pin_layout = view.findViewById(R.id.simulation_practice_4pin_layout);
        ImageView four_pin = view.findViewById(R.id.simulation_practice_4pin_item);
        four_pin_layout.setVisibility(View.VISIBLE);

        four_pin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                four_pin.setImageResource(R.drawable.simulation_image_21_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_21_1);
                simulation_image.setEnabled(true);
                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            simulation_image.setImageResource(R.drawable.simulation_image_22);
                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                    @Override
                                    public boolean onDoubleTap(MotionEvent e) {
                                        simulation_image.setImageResource(R.drawable.simulation_image_23);
                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                @Override
                                                public boolean onDoubleTap(MotionEvent e) {
                                                    simulation_image.setImageResource(R.drawable.simulation_image_24_0);
                                                    taskSixteen(view);
                                                    simulation_image.setEnabled(false);
                                                    return super.onDoubleTap(e);
                                                }
                                            });

                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                gestureDetector.onTouchEvent(event);
                                                return true;
                                            }
                                        });
                                        return super.onDoubleTap(e);
                                    }
                                });

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    gestureDetector.onTouchEvent(event);
                                    return true;
                                }
                            });
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return true;
                    }
                });
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                four_pin.setImageResource(0);
                four_pin_layout.setVisibility(View.GONE);
                four_pin.setVisibility(View.GONE);
                return true;
            }
        });
    }

    public void taskSixteen(View view) {
        viewPager2.setEnabled(true);
        ConstraintLayout sata_layout = view.findViewById(R.id.simulation_practice_sata_layout);
        sata_layout.setVisibility(View.VISIBLE);
        ImageView sata_1 = view.findViewById(R.id.simulation_practice_sata_1);
        ImageView sata_2 = view.findViewById(R.id.simulation_practice_sata_2);

        sata_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sata_1.setImageResource(R.drawable.simulation_image_24_1_item);
                if (sata_2.getVisibility() == View.GONE) {
                    tag = "2";
                    simulation_image.setImageResource(R.drawable.simulation_image_24_3);

                } else {
                    tag = "1";
                    simulation_image.setImageResource(R.drawable.simulation_image_24_2);
                }
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                sata_1.setImageResource(0);
                return true;
            }
        });

        sata_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sata_2.setImageResource(R.drawable.simulation_image_24_2_item);
                if (sata_1.getVisibility() == View.GONE) {
                    tag = "2";
                    simulation_image.setImageResource(R.drawable.simulation_image_24_3);
                } else {
                    tag = "1";
                    simulation_image.setImageResource(R.drawable.simulation_image_24_1);
                }
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                sata_2.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 7) {
                            switch (tag) {
                                case "1":
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_sata_1));
                                    break;
                                case "2":
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_sata_2));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 7) {
                            switch (tag) {
                                case "1":
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case "2":
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_sata_1));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 7) {
                            switch (tag) {
                                case "1":
                                    simulation_image.setImageResource(R.drawable.simulation_image_24_0);
                                    break;
                                case "2":
                                    if (sata_1.getVisibility() == View.VISIBLE) {
                                        simulation_image.setImageResource(R.drawable.simulation_image_24_1);
                                    } else if (sata_2.getVisibility() == View.VISIBLE) {
                                        simulation_image.setImageResource(R.drawable.simulation_image_24_2);
                                    }
                                    break;
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        item_set[viewPager2.getCurrentItem()] = 1;
                                        if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_24_1).getConstantState()) {
                                            sata_2.setVisibility(View.GONE);
                                        } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_24_2).getConstantState()) {
                                            sata_1.setVisibility(View.GONE);
                                        }
                                        break;
                                    case "2":
                                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                        sata_layout.setVisibility(View.GONE);
                                        sata_1.setVisibility(View.GONE);
                                        sata_2.setVisibility(View.GONE);
                                        simulation_image.setEnabled(true);
                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                @Override
                                                public boolean onDoubleTap(MotionEvent e) {
                                                    sata_layout.setVisibility(View.GONE);
                                                    simulation_image.setImageResource(R.drawable.simulation_image_25_0);
                                                    simulation_image.setEnabled(false);
                                                    taskSeventeen(view);
                                                    return super.onDoubleTap(e);
                                                }
                                            });

                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                gestureDetector.onTouchEvent(event);
                                                return true;
                                            }
                                        });
                                        break;
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        simulation_image.setImageResource(R.drawable.simulation_image_24_0);
                                        break;
                                    case "2":
                                        if (sata_1.getVisibility() == View.VISIBLE) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_24_1);
                                        } else if (sata_2.getVisibility() == View.VISIBLE) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_24_2);
                                        }
                                        break;
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskSeventeen(View view) {
        char screws_gone[] = {'_', '_', '_', '_'};
        ConstraintLayout psu_screw_layout = view.findViewById(R.id.simulation_practice_psu_screw_layout);
        ImageView screw_1 = view.findViewById(R.id.simulation_practice_psu_screw_1);
        ImageView screw_2 = view.findViewById(R.id.simulation_practice_psu_screw_2);
        ImageView screw_3 = view.findViewById(R.id.simulation_practice_psu_screw_3);
        ImageView screw_4 = view.findViewById(R.id.simulation_practice_psu_screw_4);
        psu_screw_layout.setVisibility(View.VISIBLE);

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_10_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_25_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_10_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_25_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        screw_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3.setImageResource(R.drawable.simulation_image_10_item_3);
                screws_gone[2] = '3';
                tag = "3";
                int id = view.getResources().getIdentifier(
                        "simulation_image_25_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3.setImageResource(0);
                return true;
            }
        });

        screw_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_4.setImageResource(R.drawable.simulation_image_10_item_4);
                screws_gone[3] = '4';
                tag = "4";
                int id = view.getResources().getIdentifier(
                        "simulation_image_25_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_4.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 8) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;

                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 8) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;

                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 8) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                                case "3":
                                    screws_gone[2] = '_';
                                    break;
                                case "4":
                                    screws_gone[3] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                id = view.getResources().getIdentifier(
                                        "simulation_image_25_0",
                                        "drawable",
                                        getActivity().getPackageName());
                                simulation_image.setImageResource(id);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        screw_3.setVisibility(View.GONE);
                                        break;
                                    case "4":
                                        screw_4.setVisibility(View.GONE);
                                        break;
                                }
                                item_set[viewPager2.getCurrentItem()] = 1;

                                if (no_of_screw == 4) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_26);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_27);
                                                            psu_screw_layout.setVisibility(View.GONE);
                                                            simulation_image.setEnabled(false);
                                                            taskEighteen(view);
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                    case "3":
                                        screws_gone[2] = '_';
                                        break;
                                    case "4":
                                        screws_gone[3] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_25_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    id = view.getResources().getIdentifier(
                                            "simulation_image_25_0",
                                            "drawable",
                                            getActivity().getPackageName());
                                    simulation_image.setImageResource(id);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskEighteen(View view) {
        ConstraintLayout psu_layout = view.findViewById(R.id.simulation_practice_psu_layout);
        ImageView psu = view.findViewById(R.id.simulation_practice_psu_item);
        psu_layout.setVisibility(View.VISIBLE);

        psu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                psu.setImageResource(R.drawable.simulation_image_27_item);
                simulation_image.setImageResource(R.drawable.simulation_image_27_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                psu.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 9) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_psu));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 9) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 9) {
                            simulation_image.setImageResource(R.drawable.simulation_image_27);
                        } else {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                psu_layout.setVisibility(View.GONE);
                                psu.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_28);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_29);
                                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                @Override
                                                                public boolean onDoubleTap(MotionEvent e) {
                                                                    simulation_image.setImageResource(R.drawable.simulation_image_30);
                                                                    taskNineteen(view);
                                                                    simulation_image.setEnabled(false);
                                                                    return super.onDoubleTap(e);
                                                                }
                                                            });

                                                            @Override
                                                            public boolean onTouch(View v, MotionEvent event) {
                                                                gestureDetector.onTouchEvent(event);
                                                                return true;
                                                            }
                                                        });
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_27);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskNineteen(View view) {
        char screws_gone[] = {'_', '_'};
        ConstraintLayout vc_screw_layout = view.findViewById(R.id.simulation_practice_vc_screw_layout);
        ImageView screw_1 = view.findViewById(R.id.simulation_practice_vc_screw_1);
        ImageView screw_2 = view.findViewById(R.id.simulation_practice_vc_screw_2);
        ConstraintLayout mobo_vc_screw_layout = view.findViewById(R.id.simulation_practice_mobo_vcscrew_layout);
        mobo_vc_screw_layout.setVisibility(View.VISIBLE);
        ImageView mobo_vc_screw = view.findViewById(R.id.simulation_practice_mobo_vcscrew);
        mobo_vc_screw.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    simulation_image.setImageResource(R.drawable.simulation_image_31_0);
                    mobo_vc_screw_layout.setVisibility(View.GONE);
                    mobo_vc_screw.setVisibility(View.VISIBLE);
                    vc_screw_layout.setVisibility(View.VISIBLE);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_31_0_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_31_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_31_0_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_31_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 10) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_2));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 10) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_1));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 10) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_31_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                id = view.getResources().getIdentifier(
                                        "simulation_image_31_0",
                                        "drawable",
                                        getActivity().getPackageName());
                                simulation_image.setImageResource(id);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                }

                                item_set[viewPager2.getCurrentItem()] = 1;

                                if (no_of_screw == 2) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_32_0);
                                                mobo_vc_screw_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskTwenty(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_31_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    simulation_image.setImageResource(R.drawable.simulation_image_31_0);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTwenty(View view) {
        ConstraintLayout vc_layout = view.findViewById(R.id.simulation_practice_vc_layout);
        ImageView vc = view.findViewById(R.id.simulation_practice_vc_item);
        vc_layout.setVisibility(View.VISIBLE);

        vc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vc.setImageResource(R.drawable.simulation_image_32_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_32_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                vc.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 11) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 11) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 11) {
                            simulation_image.setImageResource(R.drawable.simulation_image_32_0);
                        } else {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                vc_layout.setVisibility(View.GONE);
                                vc.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskTwentyOne(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_33);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_32_0);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTwentyOne(View view) {
        ConstraintLayout mobo_ram_layout = view.findViewById(R.id.simulation_practice_mobo_ram_layout);
        mobo_ram_layout.setVisibility(View.VISIBLE);
        ImageView mobo_ram = view.findViewById(R.id.simulation_practice_mobo_ram_item);
        mobo_ram.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    simulation_image.setImageResource(R.drawable.simulation_image_34);
                    mobo_ram_layout.setVisibility(View.GONE);
                    mobo_ram.setVisibility(View.GONE);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                simulation_image.setImageResource(R.drawable.simulation_image_35_0);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_35_1);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_36);
                                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                @Override
                                                                public boolean onDoubleTap(MotionEvent e) {
                                                                    simulation_image.setImageResource(R.drawable.simulation_image_37_0);
                                                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                            @Override
                                                                            public boolean onDoubleTap(MotionEvent e) {
                                                                                simulation_image.setImageResource(R.drawable.simulation_image_37_1);
                                                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                                        @Override
                                                                                        public boolean onDoubleTap(MotionEvent e) {
                                                                                            simulation_image.setEnabled(false);
                                                                                            simulation_image.setImageResource(R.drawable.simulation_image_38_0);
                                                                                            taskTwentyTwo(view);
                                                                                            return super.onDoubleTap(e);
                                                                                        }
                                                                                    });

                                                                                    @Override
                                                                                    public boolean onTouch(View v, MotionEvent event) {
                                                                                        gestureDetector.onTouchEvent(event);
                                                                                        return true;
                                                                                    }
                                                                                });
                                                                                return super.onDoubleTap(e);
                                                                            }
                                                                        });

                                                                        @Override
                                                                        public boolean onTouch(View v, MotionEvent event) {
                                                                            gestureDetector.onTouchEvent(event);
                                                                            return true;
                                                                        }
                                                                    });
                                                                    return super.onDoubleTap(e);
                                                                }
                                                            });

                                                            @Override
                                                            public boolean onTouch(View v, MotionEvent event) {
                                                                gestureDetector.onTouchEvent(event);
                                                                return true;
                                                            }
                                                        });
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskTwentyTwo(View view) {
        char ram_gone[] = {'_', '_', '_', '_'};
        ConstraintLayout ram_layout = view.findViewById(R.id.simulation_practice_ram_layout);
        ImageView ram_1 = view.findViewById(R.id.simulation_practice_ram_1);
        ImageView ram_2 = view.findViewById(R.id.simulation_practice_ram_2);
        ImageView ram_3 = view.findViewById(R.id.simulation_practice_ram_3);
        ImageView ram_4 = view.findViewById(R.id.simulation_practice_ram_4);
        ram_layout.setVisibility(View.VISIBLE);

        ram_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ram_1.setImageResource(R.drawable.simulation_image_38_0_item_1);
                ram_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_38_0_" + String.valueOf(ram_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                ram_1.setImageResource(0);
                return true;
            }
        });

        ram_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ram_2.setImageResource(R.drawable.simulation_image_38_0_item_2);
                ram_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_38_0_" + String.valueOf(ram_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                ram_2.setImageResource(0);
                return true;
            }
        });

        ram_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ram_3.setImageResource(R.drawable.simulation_image_38_0_item_3);
                ram_gone[2] = '3';
                tag = "3";
                int id = view.getResources().getIdentifier(
                        "simulation_image_38_0_" + String.valueOf(ram_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                ram_3.setImageResource(0);
                return true;
            }
        });

        ram_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ram_4.setImageResource(R.drawable.simulation_image_38_0_item_4);
                ram_gone[3] = '4';
                tag = "4";
                int id = view.getResources().getIdentifier(
                        "simulation_image_38_0_" + String.valueOf(ram_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                ram_4.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < ram_gone.length; i++) {
                    if (ram_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 12) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_4));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 12) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_3));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 12) {
                            switch (tag) {
                                case "1":
                                    ram_gone[0] = '_';
                                    break;
                                case "2":
                                    ram_gone[1] = '_';
                                    break;
                                case "3":
                                    ram_gone[2] = '_';
                                    break;
                                case "4":
                                    ram_gone[3] = '_';
                                    break;

                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_38_0_" + String.valueOf(ram_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        ram_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        ram_2.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        ram_3.setVisibility(View.GONE);
                                        break;
                                    case "4":
                                        ram_4.setVisibility(View.GONE);
                                        break;

                                }
                                item_set[viewPager2.getCurrentItem()] = 1;
                                if (no_of_screw == 4) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_39);
                                                ram_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskTwentyThree(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        ram_gone[0] = '_';
                                        break;
                                    case "2":
                                        ram_gone[1] = '_';
                                        break;
                                    case "3":
                                        ram_gone[2] = '_';
                                        break;
                                    case "4":
                                        ram_gone[3] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_38_0_" + String.valueOf(ram_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    simulation_image.setImageResource(R.drawable.simulation_image_38_0);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTwentyThree(View view) {
        viewPager2.setEnabled(false);
        ConstraintLayout heatsink_layout = view.findViewById(R.id.simulation_practice_heatsink_cable_layout);
        ImageView heatsink = view.findViewById(R.id.simulation_practice_heatsink_cable_item);
        ConstraintLayout mobo_heatsink_layout = view.findViewById(R.id.simulation_practice_mobo_heatsink_layout);
        mobo_heatsink_layout.setVisibility(View.VISIBLE);
        ImageView mobo_heatsink = view.findViewById(R.id.simulation_practice_mobo_heatsink_item);
        mobo_heatsink.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    simulation_image.setImageResource(R.drawable.simulation_image_40_0);
                    mobo_heatsink_layout.setVisibility(View.GONE);
                    mobo_heatsink.setVisibility(View.GONE);
                    heatsink_layout.setVisibility(View.VISIBLE);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        heatsink.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                heatsink.setImageResource(R.drawable.simulation_image_40_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_40_1);
                simulation_image.setEnabled(true);
                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            simulation_image.setImageResource(R.drawable.simulation_image_41_0);
                            heatsink_layout.setVisibility(View.GONE);
                            simulation_image.setEnabled(false);
                            taskTwentyFour(view);
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return true;
                    }
                });
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                heatsink.setImageResource(0);
                heatsink.setVisibility(View.GONE);
                return true;
            }
        });
    }

    public void taskTwentyFour(View view) {
        viewPager2.setEnabled(true);
        char screws_gone[] = {'_', '_'};
        ConstraintLayout heatink_screw_layout = view.findViewById(R.id.simulation_practice_heatsink_screw_1_layout);
        heatink_screw_layout.setVisibility(View.VISIBLE);
        ImageView screw_1 = view.findViewById(R.id.simulation_practice_heatsink_screw_1_1);
        ImageView screw_2 = view.findViewById(R.id.simulation_practice_heatsink_screw_1_2);

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_41_0_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_41_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_41_0_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_41_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 13) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_2));
                                    break;

                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 13) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_1));
                                    break;

                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 13) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_41_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                }

                                item_set[viewPager2.getCurrentItem()] = 1;

                                if (no_of_screw == 2) {
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_42_0);
                                                heatink_screw_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskTwentyFive(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_41_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    simulation_image.setImageResource(R.drawable.simulation_image_41_0);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTwentyFive(View view) {
        char screws_gone[] = {'_', '_'};
        ConstraintLayout heatink_screw_layout = view.findViewById(R.id.simulation_practice_heatsink_screw_2_layout);
        heatink_screw_layout.setVisibility(View.VISIBLE);
        ImageView screw_1 = view.findViewById(R.id.simulation_practice_heatsink_screw_2_1);
        ImageView screw_2 = view.findViewById(R.id.simulation_practice_heatsink_screw_2_2);

        screw_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1.setImageResource(R.drawable.simulation_image_42_0_item_1);
                screws_gone[0] = '1';
                tag = "1";
                int id = view.getResources().getIdentifier(
                        "simulation_image_42_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1.setImageResource(0);
                return true;
            }
        });

        screw_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2.setImageResource(R.drawable.simulation_image_42_0_item_2);
                screws_gone[1] = '2';
                tag = "2";
                int id = view.getResources().getIdentifier(
                        "simulation_image_42_0_" + String.valueOf(screws_gone),
                        "drawable",
                        getActivity().getPackageName());
                simulation_image.setImageResource(id);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                int no_of_screw = 0;
                for (int i = 0; i < screws_gone.length; i++) {
                    if (screws_gone[i] != '_') {
                        no_of_screw++;
                    }
                }

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 13) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_3));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_4));
                                    break;

                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 13) {
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_3));
                                    break;

                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 13) {
                            switch (tag) {
                                case "1":
                                    screws_gone[0] = '_';
                                    break;
                                case "2":
                                    screws_gone[1] = '_';
                                    break;
                            }
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_42_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            if (id > 0) {
                                simulation_image.setImageResource(id);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0);
                            }
                        } else {
                            if (entered == true) {
                                switch (tag) {
                                    case "1":
                                        screw_1.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        screw_2.setVisibility(View.GONE);
                                        break;
                                }

                                item_set[viewPager2.getCurrentItem()] = 1;

                                if (no_of_screw == 2) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_43_0);
                                                heatink_screw_layout.setVisibility(View.GONE);
                                                simulation_image.setEnabled(false);
                                                taskTwentySix(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            } else {
                                switch (tag) {
                                    case "1":
                                        screws_gone[0] = '_';
                                        break;
                                    case "2":
                                        screws_gone[1] = '_';
                                        break;
                                }
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_42_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id > 0) {
                                    simulation_image.setImageResource(id);
                                } else {
                                    simulation_image.setImageResource(R.drawable.simulation_image_42_0);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTwentySix(View view) {
        ConstraintLayout hs_layout = view.findViewById(R.id.simulation_practice_heatsink_layout);
        ImageView hs = view.findViewById(R.id.simulation_practice_heatsink_item);
        hs_layout.setVisibility(View.VISIBLE);

        hs.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                hs.setImageResource(R.drawable.simulation_image_43_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_43_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                hs.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 14) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 14) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 14) {
                            simulation_image.setImageResource(R.drawable.simulation_image_43_0);
                        } else {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                hs_layout.setVisibility(View.GONE);
                                hs.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskTwentySeven(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_44_0);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_43_0);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTwentySeven(View view) {
        ConstraintLayout handle_layout = view.findViewById(R.id.simulation_practice_cpu_handle_layout);
        ImageView handle = view.findViewById(R.id.simulation_practice_cpu_handle);
        handle_layout.setVisibility(View.VISIBLE);

        handle.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
                simulation_image.setImageResource(R.drawable.simulation_image_44_1);
                simulation_image.setEnabled(true);
                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            taskTwentyEight(view);
                            simulation_image.setEnabled(false);
                            simulation_image.setImageResource(R.drawable.simulation_image_45_0);
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return true;
                    }
                });
            }
        });
    }

    public void taskTwentyEight(View view) {
        ConstraintLayout cpu_layout = view.findViewById(R.id.simulation_practice_cpu_layout);
        ImageView cpu = view.findViewById(R.id.simulation_practice_cpu);
        cpu_layout.setVisibility(View.VISIBLE);

        cpu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                cpu.setImageResource(R.drawable.simulation_image_45_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_45_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                cpu.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 15) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_cpu));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 15) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 15) {
                            simulation_image.setImageResource(R.drawable.simulation_image_45_0);
                        } else {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 1;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                cpu_layout.setVisibility(View.GONE);
                                cpu.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskTwentyNine(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_46_0);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_45_0);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskTwentyNine(View view) {
        char screws_gone[] = {'_', '_', '_'};
        ConstraintLayout screw_1_1_layout = view.findViewById(R.id.simulation_practice_mobo_screws_1_1_layout);
        ConstraintLayout screw_1_2_layout = view.findViewById(R.id.simulation_practice_mobo_screws_1_2_layout);
        ConstraintLayout screw_1_3_layout = view.findViewById(R.id.simulation_practice_mobo_screws_1_3_layout);
        ConstraintLayout screw_2_1_layout = view.findViewById(R.id.simulation_practice_mobo_screws_2_1_layout);
        ConstraintLayout screw_2_2_layout = view.findViewById(R.id.simulation_practice_mobo_screws_2_2_layout);
        ConstraintLayout screw_3_1_layout = view.findViewById(R.id.simulation_practice_mobo_screws_3_1_layout);
        ConstraintLayout screw_3_2_layout = view.findViewById(R.id.simulation_practice_mobo_screws_3_2_layout);
        ConstraintLayout screw_3_3_layout = view.findViewById(R.id.simulation_practice_mobo_screws_3_3_layout);
        ImageView screw_1_1 = view.findViewById(R.id.simulation_practice_mobo_screws_1_1);
        ImageView screw_1_2 = view.findViewById(R.id.simulation_practice_mobo_screws_1_2);
        ImageView screw_1_3 = view.findViewById(R.id.simulation_practice_mobo_screws_1_3);
        ImageView screw_2_1 = view.findViewById(R.id.simulation_practice_mobo_screws_2_1);
        ImageView screw_2_2 = view.findViewById(R.id.simulation_practice_mobo_screws_2_2);
        ImageView screw_3_1 = view.findViewById(R.id.simulation_practice_mobo_screws_3_1);
        ImageView screw_3_2 = view.findViewById(R.id.simulation_practice_mobo_screws_3_2);
        ImageView screw_3_3 = view.findViewById(R.id.simulation_practice_mobo_screws_3_3);
        ConstraintLayout layout = view.findViewById(R.id.simulation_practice_mobo_screws_layout);
        ImageView one = view.findViewById(R.id.simulation_practice_mobo_screws_1);
        ImageView two = view.findViewById(R.id.simulation_practice_mobo_screws_2);
        ImageView three = view.findViewById(R.id.simulation_practice_mobo_screws_3);
        layout.setVisibility(View.VISIBLE);

        one.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    one.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    screws_gone[0] = '1';
                    screw_1_1_layout.setVisibility(View.VISIBLE);
                    screw_1_1.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_47_1_1_0);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        two.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    two.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    screws_gone[1] = '2';
                    screw_2_1_layout.setVisibility(View.VISIBLE);
                    screw_2_1.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_47_2_1_0);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        three.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    three.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    screws_gone[2] = '3';
                    screw_3_1_layout.setVisibility(View.VISIBLE);
                    screw_3_1.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_47_3_1_0);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        screw_1_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1_1.setImageResource(R.drawable.simulation_image_47_1_1_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_1_1_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1_1.setImageResource(0);
                return true;
            }
        });

        screw_1_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1_2.setImageResource(R.drawable.simulation_image_47_1_2_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_1_2_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1_2.setImageResource(0);
                return true;
            }
        });

        screw_1_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_1_3.setImageResource(R.drawable.simulation_image_47_1_3_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_1_3_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_1_3.setImageResource(0);
                return true;
            }
        });

        screw_2_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2_1.setImageResource(R.drawable.simulation_image_47_2_1_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_2_1_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2_1.setImageResource(0);
                return true;
            }
        });

        screw_2_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_2_2.setImageResource(R.drawable.simulation_image_47_2_2_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_2_2_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_2_2.setImageResource(0);
                return true;
            }
        });

        screw_3_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3_1.setImageResource(R.drawable.simulation_image_47_3_1_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_3_1_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3_1.setImageResource(0);
                return true;
            }
        });

        screw_3_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3_2.setImageResource(R.drawable.simulation_image_47_3_2_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_3_2_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3_2.setImageResource(0);
                return true;
            }
        });

        screw_3_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                screw_3_3.setImageResource(R.drawable.simulation_image_47_3_3_0_item);
                simulation_image.setImageResource(R.drawable.simulation_image_47_3_3_1);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                screw_3_3.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 16) {
                            if (screw_1_1.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_1_1_0);
                            } else if (screw_1_2.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_1_2_0);
                            } else if (screw_1_3.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_1_3_0);
                            } else if (screw_2_1.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_2_1_0);
                            } else if (screw_2_1.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_2_2_0);
                            } else if (screw_3_1.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_3_1_0);
                            } else if (screw_3_2.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_3_2_0);
                            } else if (screw_3_3.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_47_3_3_0);
                            }
                        } else {
                            if (entered == true) {
                                screw_no++;
                                item_set[viewPager2.getCurrentItem()] = 1;
                                if (screw_1_1.getVisibility() == View.VISIBLE) {
                                    screw_1_1_layout.setVisibility(View.GONE);
                                    screw_1_1.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                screw_1_2_layout.setVisibility(View.VISIBLE);
                                                screw_1_2.setVisibility(View.VISIBLE);
                                                simulation_image.setEnabled(false);
                                                simulation_image.setImageResource(R.drawable.simulation_image_47_1_2_0);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                } else if (screw_1_2.getVisibility() == View.VISIBLE) {
                                    screw_1_2_layout.setVisibility(View.GONE);
                                    screw_1_2.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                screw_1_3_layout.setVisibility(View.VISIBLE);
                                                screw_1_3.setVisibility(View.VISIBLE);
                                                simulation_image.setEnabled(false);
                                                simulation_image.setImageResource(R.drawable.simulation_image_47_1_3_0);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                } else if (screw_1_3.getVisibility() == View.VISIBLE) {
                                    screw_1_3_layout.setVisibility(View.GONE);
                                    screw_1_3.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setEnabled(false);
                                                int id = view.getResources().getIdentifier(
                                                        "simulation_image_46_0_" + String.valueOf(screws_gone),
                                                        "drawable",
                                                        getActivity().getPackageName());
                                                if (id == R.drawable.simulation_image_46_0_123) {
                                                    taskThirty(view);
                                                } else {
                                                    layout.setVisibility(View.VISIBLE);
                                                }
                                                simulation_image.setImageResource(id);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                } else if (screw_2_1.getVisibility() == View.VISIBLE) {
                                    screw_2_1_layout.setVisibility(View.GONE);
                                    screw_2_1.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                screw_2_2_layout.setVisibility(View.VISIBLE);
                                                screw_2_2.setVisibility(View.VISIBLE);
                                                simulation_image.setEnabled(false);
                                                simulation_image.setImageResource(R.drawable.simulation_image_47_2_2_0);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                } else if (screw_2_2.getVisibility() == View.VISIBLE) {
                                    screw_2_2_layout.setVisibility(View.GONE);
                                    screw_2_2.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setEnabled(false);
                                                int id = view.getResources().getIdentifier(
                                                        "simulation_image_46_0_" + String.valueOf(screws_gone),
                                                        "drawable",
                                                        getActivity().getPackageName());
                                                if (id == R.drawable.simulation_image_46_0_123) {
                                                    taskThirty(view);
                                                } else {
                                                    layout.setVisibility(View.VISIBLE);
                                                }
                                                simulation_image.setImageResource(id);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                } else if (screw_3_1.getVisibility() == View.VISIBLE) {
                                    screw_3_1_layout.setVisibility(View.GONE);
                                    screw_3_1.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                screw_3_2_layout.setVisibility(View.VISIBLE);
                                                screw_3_2.setVisibility(View.VISIBLE);
                                                simulation_image.setEnabled(false);
                                                simulation_image.setImageResource(R.drawable.simulation_image_47_3_2_0);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                } else if (screw_3_2.getVisibility() == View.VISIBLE) {
                                    screw_3_2_layout.setVisibility(View.GONE);
                                    screw_3_2.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                screw_3_3_layout.setVisibility(View.VISIBLE);
                                                screw_3_3.setVisibility(View.VISIBLE);
                                                simulation_image.setEnabled(false);
                                                simulation_image.setImageResource(R.drawable.simulation_image_47_3_3_0);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                } else if (screw_3_3.getVisibility() == View.VISIBLE) {
                                    screw_3_3_layout.setVisibility(View.GONE);
                                    screw_3_3.setVisibility(View.GONE);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setEnabled(false);
                                                int id = view.getResources().getIdentifier(
                                                        "simulation_image_46_0_" + String.valueOf(screws_gone),
                                                        "drawable",
                                                        getActivity().getPackageName());
                                                if (id == R.drawable.simulation_image_46_0_123) {
                                                    taskThirty(view);
                                                } else {
                                                    layout.setVisibility(View.VISIBLE);
                                                }
                                                simulation_image.setImageResource(id);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }

                                if (screw_no == 8) {
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                }
                            } else {
                                if (screw_1_1.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_1_1_0);
                                } else if (screw_1_2.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_1_2_0);
                                } else if (screw_1_3.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_1_3_0);
                                } else if (screw_2_1.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_2_1_0);
                                } else if (screw_2_2.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_2_2_0);
                                } else if (screw_3_1.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_3_1_0);
                                } else if (screw_3_2.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_3_2_0);
                                } else if (screw_3_3.getVisibility() == View.VISIBLE) {
                                    simulation_image.setImageResource(R.drawable.simulation_image_47_3_3_0);
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirty(View view) {
        ConstraintLayout layout = view.findViewById(R.id.simulation_practice_motherboard_layout);
        layout.setVisibility(View.VISIBLE);
        ImageView image = view.findViewById(R.id.simulation_practice_motherboard_item);

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                image.setImageResource(R.drawable.simulation_image_46_0_123_item);
                simulation_image.setImageResource(R.drawable.simulation_image_48);
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                image.setImageResource(0);
                return true;
            }
        });

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 17) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_motherboard));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 17) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() != 17) {
                            simulation_image.setImageResource(R.drawable.simulation_image_46_0_123);
                        } else {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 1;
                                layout.setVisibility(View.GONE);
                                image.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_49);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        taskThirtyOne(view);
                                                        simulation_image.setEnabled(false);
                                                        simulation_image.setImageResource(R.drawable.simulation_image_48);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_46_0_123);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirtyOne(View view) {
        ImageView item_real = view.findViewById(R.id.simulation_practice_item);
        item_real.setVisibility(View.VISIBLE);
        ImageView item = view.findViewById(R.id.simulation_practice_item_image);

        viewPager2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                }
                return true;
            }
        });

        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch (viewPager2.getCurrentItem()) {
                    case 0:
                        if (item_set[0] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_1_item_1);
                        }
                        break;
                    case 1:
                        if (item_set[1] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_2_item);
                        }
                        break;
                    case 2:
                        if (item_set[2] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_6_0_item);
                        }
                        break;
                    case 3:
                        if (item_set[3] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_10_item_1);
                        }
                        break;
                    case 4:
                        if (item_set[4] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_10_0_item);
                        }
                        break;
                    case 5:
                        if (item_set[5] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_17_1_item_1);
                        }
                        break;
                    case 6:
                        if (item_set[6] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_18_0_item);
                        }
                        break;
                    case 7:
                        if (item_set[7] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_24_1_item);
                        }
                        break;
                    case 8:
                        if (item_set[8] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_10_item_1);
                        }
                        break;
                    case 9:
                        if (item_set[9] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_27_item);
                        }
                        break;
                    case 10:
                        if (item_set[10] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_31_0_item_1);
                        }
                        break;
                    case 11:
                        if (item_set[11] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_32_0_item);
                        }
                        break;
                    case 12:
                        if (item_set[12] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_38_0_item_1);
                        }
                        break;
                    case 13:
                        if (item_set[13] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_42_0_item_1);
                        }
                        break;
                    case 14:
                        if (item_set[14] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_43_0_item);
                        }
                        break;
                    case 15:
                        if (item_set[15] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_45_0_item);
                        }
                        break;
                    case 16:
                        if (item_set[16] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_47_1_1_0_item);
                        }
                        break;
                    case 17:
                        if (item_set[17] == 1) {
                            item_real.setImageResource(R.drawable.simulation_image_46_0_123_item);
                        }
                        break;
                }
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(item_real);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                item_real.setImageResource(0);
                return true;
            }
        });

        taskThirtyTwo(view);
    }

    public void taskThirtyTwo(View view) {
        View motherboard_drag = view.findViewById(R.id.simulation_practice_motherboard_disassemble);
        motherboard_drag.setVisibility(View.VISIBLE);

        motherboard_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 17) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_46_0_123);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 17) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_motherboard));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_48);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 17) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                motherboard_drag.setVisibility(View.GONE);
                                taskThirtyThree(view);
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirtyThree(View view) {
        char screws_gone[] = {'1', '2', '3'};
        screw_no--;
        View motherboard_screw_1 = view.findViewById(R.id.simulation_practice_mobo_screws_1_disassemble);
        View motherboard_screw_2 = view.findViewById(R.id.simulation_practice_mobo_screws_2_disassemble);
        View motherboard_screw_3 = view.findViewById(R.id.simulation_practice_mobo_screws_3_disassemble);
        motherboard_screw_1.setVisibility(View.VISIBLE);
        motherboard_screw_2.setVisibility(View.VISIBLE);
        motherboard_screw_3.setVisibility(View.VISIBLE);
        View screw_1_1 = view.findViewById(R.id.simulation_practice_mobo_screws_1_1_disassemble);
        View screw_1_2 = view.findViewById(R.id.simulation_practice_mobo_screws_1_2_disassemble);
        View screw_1_3 = view.findViewById(R.id.simulation_practice_mobo_screws_1_3_disassemble);
        View screw_2_1 = view.findViewById(R.id.simulation_practice_mobo_screws_2_1_disassemble);
        View screw_2_2 = view.findViewById(R.id.simulation_practice_mobo_screws_2_2_disassemble);
        View screw_3_1 = view.findViewById(R.id.simulation_practice_mobo_screws_3_1_disassemble);
        View screw_3_2 = view.findViewById(R.id.simulation_practice_mobo_screws_3_2_disassemble);
        View screw_3_3 = view.findViewById(R.id.simulation_practice_mobo_screws_3_3_disassemble);

        motherboard_screw_1.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    motherboard_screw_1.setVisibility(View.GONE);
                    motherboard_screw_2.setVisibility(View.GONE);
                    motherboard_screw_3.setVisibility(View.GONE);
                    screws_gone[0] = '_';
                    screw_1_1.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_47_1_1_1);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        motherboard_screw_2.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    motherboard_screw_1.setVisibility(View.GONE);
                    motherboard_screw_2.setVisibility(View.GONE);
                    motherboard_screw_3.setVisibility(View.GONE);
                    screws_gone[1] = '_';
                    screw_2_1.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_47_2_1_1);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        motherboard_screw_3.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    motherboard_screw_1.setVisibility(View.GONE);
                    motherboard_screw_2.setVisibility(View.GONE);
                    motherboard_screw_3.setVisibility(View.GONE);
                    screws_gone[2] = '_';
                    screw_3_1.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_47_3_1_1);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        screw_1_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_1_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_1_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                screw_1_1.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            screw_no--;
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_2_1);
                                            screw_1_2.setVisibility(View.VISIBLE);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_1_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_2_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_2_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                screw_1_2.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            screw_no--;
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_3_1);
                                            screw_1_3.setVisibility(View.VISIBLE);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_1_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_3_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_1_3_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                screw_1_3.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_46_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id == 0) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                }
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            if (screw_no > 0) {
                                                screw_no--;
                                            }
                                            simulation_image.setEnabled(false);
                                            if (id == 0) {
                                                simulation_image.setEnabled(true);
                                                simulation_image.setImageResource(R.drawable.simulation_image_46_0);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setEnabled(false);
                                                            simulation_image.setImageResource(R.drawable.simulation_image_45_1);
                                                            taskThirtyFour(view);
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                            } else {
                                                if (screws_gone[1] != '_') {
                                                    motherboard_screw_2.setVisibility(View.VISIBLE);
                                                }
                                                if (screws_gone[2] != '_') {
                                                    motherboard_screw_3.setVisibility(View.VISIBLE);
                                                }
                                                simulation_image.setImageResource(id);
                                            }
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_2_1_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_2_1_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                simulation_image.setEnabled(true);
                                screw_2_1.setVisibility(View.GONE);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            screw_no--;
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_47_2_2_1);
                                            screw_2_2.setVisibility(View.VISIBLE);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_2_2_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_2_2_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                simulation_image.setEnabled(true);
                                screw_2_2.setVisibility(View.GONE);
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_46_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id == 0) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                }
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            if (screw_no > 0) {
                                                screw_no--;
                                            }
                                            simulation_image.setEnabled(false);
                                            if (id == 0) {
                                                simulation_image.setEnabled(true);
                                                simulation_image.setImageResource(R.drawable.simulation_image_46_0);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_45_1);
                                                            taskThirtyFour(view);
                                                            simulation_image.setEnabled(false);
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                            } else {
                                                if (screws_gone[0] != '_') {
                                                    motherboard_screw_1.setVisibility(View.VISIBLE);
                                                }
                                                if (screws_gone[2] != '_') {
                                                    motherboard_screw_3.setVisibility(View.VISIBLE);
                                                }
                                                simulation_image.setImageResource(id);
                                            }

                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }

                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_3_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_1_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_1_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                simulation_image.setEnabled(true);
                                screw_3_1.setVisibility(View.GONE);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            screw_no--;
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_2_1);
                                            screw_3_2.setVisibility(View.VISIBLE);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_3_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_2_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_2_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                simulation_image.setEnabled(true);
                                screw_3_2.setVisibility(View.GONE);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            screw_no--;
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_3_1);
                                            screw_3_3.setVisibility(View.VISIBLE);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_3_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_3_0);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 16) {
                            switch (screw_no) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_1));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_4));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_5));
                                    break;
                                case 5:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_6));
                                    break;
                                case 6:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_7));
                                    break;
                                case 7:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_mobo_8));
                                    break;
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                            simulation_image.setImageResource(R.drawable.simulation_image_47_3_3_1);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 16) {
                            if (entered == true) {
                                simulation_image.setEnabled(true);
                                screw_3_3.setVisibility(View.GONE);
                                int id = view.getResources().getIdentifier(
                                        "simulation_image_46_0_" + String.valueOf(screws_gone),
                                        "drawable",
                                        getActivity().getPackageName());
                                if (id == 0) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                }
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            if (screw_no > 0) {
                                                screw_no--;
                                            }
                                            simulation_image.setEnabled(false);
                                            if (id == 0) {
                                                simulation_image.setEnabled(true);
                                                simulation_image.setImageResource(R.drawable.simulation_image_46_0);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_45_1);
                                                            taskThirtyFour(view);
                                                            simulation_image.setEnabled(false);
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                            } else {
                                                if (screws_gone[1] != '_') {
                                                    motherboard_screw_2.setVisibility(View.VISIBLE);
                                                }
                                                if (screws_gone[0] != '_') {
                                                    motherboard_screw_1.setVisibility(View.VISIBLE);
                                                }
                                                simulation_image.setImageResource(id);
                                            }

                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirtyFour(View view) {
        View cpu_drag = view.findViewById(R.id.simulation_practice_cpu_disassemble);
        cpu_drag.setVisibility(View.VISIBLE);

        cpu_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 15) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_45_0);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 15) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_cpu));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_45_1);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 15) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                cpu_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskThirtyFive(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_44_1);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirtyFive(View view) {
        View handle = view.findViewById(R.id.simulation_practice_cpu_handle_disassemble);
        handle.setVisibility(View.VISIBLE);

        handle.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeBottom() {
                simulation_image.setImageResource(R.drawable.simulation_image_44_0);
                simulation_image.setEnabled(true);
                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            handle.setVisibility(View.GONE);
                            taskThirtySix(view);
                            simulation_image.setEnabled(false);
                            simulation_image.setImageResource(R.drawable.simulation_image_43_1);
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return true;
                    }
                });
            }
        });

    }

    public void taskThirtySix(View view) {
        View heatsink_drag = view.findViewById(R.id.simulation_practice_heatsink_item_disassemble);
        heatsink_drag.setVisibility(View.VISIBLE);

        heatsink_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 14) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_43_0);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 14) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_cpu));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_43_1);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 14) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                heatsink_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskThirtySeven(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_42_0_12);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirtySeven(View view) {
        View screw_1 = view.findViewById(R.id.simulation_practice_heatsink_screw_2_1_disassemble);
        View screw_2 = view.findViewById(R.id.simulation_practice_heatsink_screw_2_2_disassemble);
        screw_1.setVisibility(View.VISIBLE);
        screw_2.setVisibility(View.VISIBLE);

        screw_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw_2.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_3));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0__2);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw_2.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_4));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0_12);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_3));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0_1_);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (entered == true) {
                                screw_1.setVisibility(View.GONE);
                                if (screw_2.getVisibility() == View.GONE) {
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_41_0_12);
                                                simulation_image.setEnabled(false);
                                                taskThirtyEight(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw_1.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_3));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0_1_);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw_1.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_4));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0_12);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_3));
                                simulation_image.setImageResource(R.drawable.simulation_image_42_0__2);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (entered1 == true) {
                            screw_2.setVisibility(View.GONE);
                            if (screw_1.getVisibility() == View.GONE) {
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_41_0_12);
                                            simulation_image.setEnabled(false);
                                            taskThirtyEight(view);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                            entered1 = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirtyEight(View view) {
        View screw1 = view.findViewById(R.id.simulation_practice_heatsink_screw_1_1_disassemble);
        View screw2 = view.findViewById(R.id.simulation_practice_heatsink_screw_1_2_disassemble);
        screw1.setVisibility(View.VISIBLE);
        screw2.setVisibility(View.VISIBLE);

        screw1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw2.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0__2);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw2.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0_12);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0_1_);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (entered == true) {
                                screw1.setVisibility(View.GONE);
                                if (screw2.getVisibility() != View.VISIBLE) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_40_1);
                                                simulation_image.setEnabled(false);
                                                taskThirtyNine(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw1.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0_1_);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (screw1.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0_12);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_heatsink_screw_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_41_0__2);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 13) {
                            if (entered1 == true) {
                                screw2.setVisibility(View.GONE);
                                if (screw1.getVisibility() == View.GONE) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_40_1);
                                                simulation_image.setEnabled(false);
                                                taskThirtyNine(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                            entered1 = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskThirtyNine(View view) {
        ImageView cable = view.findViewById(R.id.simulation_practice_heatsink_cable_item_disassemble);
        View cable_drag = view.findViewById(R.id.simulation_practice_heatsink_cable_drag_disassemble);
        cable_drag.setVisibility(View.VISIBLE);
        cable.setVisibility(View.VISIBLE);

        cable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                cable.setImageResource(0);
                return true;
            }
        });

        cable_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        simulation_image.setImageResource(R.drawable.simulation_image_40_0);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        simulation_image.setImageResource(R.drawable.simulation_image_40_1);
                        cable.setImageResource(R.drawable.simulation_image_40_0_item);
                        entered = false;
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (entered == true) {
                            cable.setVisibility(View.GONE);
                            cable_drag.setVisibility(View.GONE);
                            simulation_image.setEnabled(true);
                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                    @Override
                                    public boolean onDoubleTap(MotionEvent e) {
                                        taskForty(view);
                                        simulation_image.setEnabled(false);
                                        simulation_image.setImageResource(R.drawable.simulation_image_39);
                                        return super.onDoubleTap(e);
                                    }
                                });

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    gestureDetector.onTouchEvent(event);
                                    return true;
                                }
                            });
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskForty(View view) {
        View mobo_ram = view.findViewById(R.id.simulation_practice_mobo_ram_item_disassemble);
        mobo_ram.setVisibility(View.VISIBLE);
        mobo_ram.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    taskFortyOne(view);
                    mobo_ram.setVisibility(View.GONE);
                    simulation_image.setImageResource(R.drawable.simulation_image_38_0_1234);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    public void taskFortyOne(View view) {
        View ram1 = view.findViewById(R.id.simulation_practice_ram_disassemble);
        ram1.setVisibility(View.VISIBLE);

        ram1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();
                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 12) {
                            if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0_1234).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_3));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0__234);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0__234).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0___34);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0___34).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0____4);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0____4).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 12) {
                            if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0__234).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_4));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0_1234);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0___34).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_3));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0__234);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0____4).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0___34);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ram_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_38_0____4);
                            }

                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 12) {
                            if (entered == true) {
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_38_0).getConstantState()) {
                                    ram1.setVisibility(View.GONE);
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_37_1);
                                                taskFortyTwo(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFortyTwo(View view) {
        simulation_image.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    simulation_image.setImageResource(R.drawable.simulation_image_37_0);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                simulation_image.setImageResource(R.drawable.simulation_image_36);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_35_1);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_35_0);
                                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                @Override
                                                                public boolean onDoubleTap(MotionEvent e) {
                                                                    simulation_image.setImageResource(R.drawable.simulation_image_34);
                                                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                            @Override
                                                                            public boolean onDoubleTap(MotionEvent e) {
                                                                                simulation_image.setImageResource(R.drawable.simulation_image_33);
                                                                                simulation_image.setEnabled(false);
                                                                                taskFortyThree(view);
                                                                                return super.onDoubleTap(e);
                                                                            }
                                                                        });

                                                                        @Override
                                                                        public boolean onTouch(View v, MotionEvent event) {
                                                                            gestureDetector.onTouchEvent(event);
                                                                            return true;
                                                                        }
                                                                    });
                                                                    return super.onDoubleTap(e);
                                                                }
                                                            });

                                                            @Override
                                                            public boolean onTouch(View v, MotionEvent event) {
                                                                gestureDetector.onTouchEvent(event);
                                                                return true;
                                                            }
                                                        });
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskFortyThree(View view) {
        View vc = view.findViewById(R.id.simulation_practice_videocard_disassemble);
        vc.setVisibility(View.VISIBLE);
        vc.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    vc.setVisibility(View.GONE);
                    simulation_image.setImageResource(R.drawable.simulation_image_32_1);
                    taskFortyFour(view);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskFortyFour(View view) {
        View videocard = view.findViewById(R.id.simulation_practice_vc_item_disassemble);
        videocard.setVisibility(View.VISIBLE);

        videocard.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 11) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_32_0);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 11) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_32_1);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 11) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                videocard.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskFortyFive(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_31_0_12);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFortyFive(View view) {
        View screw1 = view.findViewById(R.id.simulation_practice_vc_screw_1_disassemble);
        View screw2 = view.findViewById(R.id.simulation_practice_vc_screw_2_disassemble);
        screw1.setVisibility(View.VISIBLE);
        screw2.setVisibility(View.VISIBLE);

        screw1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 10) {
                            if (screw2.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0__2);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 10) {
                            if (screw2.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0_12);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0_1_);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 10) {
                            if (entered == true) {
                                screw1.setVisibility(View.GONE);
                                if (screw2.getVisibility() == View.GONE) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_30);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_29);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_28);
                                                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                                @Override
                                                                                public boolean onDoubleTap(MotionEvent e) {
                                                                                    simulation_image.setImageResource(R.drawable.simulation_image_27_1);
                                                                                    simulation_image.setEnabled(false);
                                                                                    taskFortySix(view);
                                                                                    return super.onDoubleTap(e);
                                                                                }
                                                                            });

                                                                            @Override
                                                                            public boolean onTouch(View v, MotionEvent event) {
                                                                                gestureDetector.onTouchEvent(event);
                                                                                return true;
                                                                            }
                                                                        });
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 10) {
                            if (screw1.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0_1_);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 10) {
                            if (screw1.getVisibility() == View.VISIBLE) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_2));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0_12);
                            } else {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_videocard_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_31_0__2);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 10) {
                            if (entered1 == true) {
                                screw2.setVisibility(View.GONE);

                                if (screw1.getVisibility() == View.GONE) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_30);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_29);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_28);
                                                                        simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                                @Override
                                                                                public boolean onDoubleTap(MotionEvent e) {
                                                                                    simulation_image.setImageResource(R.drawable.simulation_image_27_1);
                                                                                    simulation_image.setEnabled(false);
                                                                                    taskFortySix(view);
                                                                                    return super.onDoubleTap(e);
                                                                                }
                                                                            });

                                                                            @Override
                                                                            public boolean onTouch(View v, MotionEvent event) {
                                                                                gestureDetector.onTouchEvent(event);
                                                                                return true;
                                                                            }
                                                                        });
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered1 = false;
                        break;
                    default:
                }
                return true;
            }
        });
    }

    public void taskFortySix(View view) {
        View psu = view.findViewById(R.id.simulation_practice_psu_item_disassemble);
        psu.setVisibility(View.VISIBLE);

        psu.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 9) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_27);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 9) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_psu));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_27_1);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 9) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                psu.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_26);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_25_0_1234);
                                                        taskFortySeven(view);
                                                        simulation_image.setEnabled(false);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFortySeven(View view) {
        char screws_gone[] = {'1', '2', '3', '4'};
        View screw_1 = view.findViewById(R.id.simulation_practice_psu_screw_1_disassemble);
        View screw_2 = view.findViewById(R.id.simulation_practice_psu_screw_2_disassemble);
        View screw_3 = view.findViewById(R.id.simulation_practice_psu_screw_3_disassemble);
        View screw_4 = view.findViewById(R.id.simulation_practice_psu_screw_4_disassemble);
        screw_1.setVisibility(View.VISIBLE);
        screw_2.setVisibility(View.VISIBLE);
        screw_3.setVisibility(View.VISIBLE);
        screw_4.setVisibility(View.VISIBLE);

        screw_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[0] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_25_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[0] = '1';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 8) {
                            if (entered == true) {
                                screw_1.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_25_0).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_24_3);
                                                simulation_image.setEnabled(false);
                                                taskFortyEight(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[1] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_25_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[1] = '2';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 8) {
                            if (entered1 == true) {
                                screw_2.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_25_0).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_24_3);
                                                simulation_image.setEnabled(false);
                                                taskFortyEight(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                            entered1 = false;
                        }
                        break;
                }
                return true;
            }
        });

        screw_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[2] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_25_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[2] = '3';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 8) {
                            if (entered2 == true) {
                                screw_3.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_25_0).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_24_3);
                                                simulation_image.setEnabled(false);
                                                taskFortyEight(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered2 = false;
                        break;
                }
                return true;
            }
        });

        screw_4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[3] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_25_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered3 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 8) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[3] = '4';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_25_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered3 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 8) {
                            if (entered3 == true) {
                                screw_4.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_25_0).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_24_3);
                                                simulation_image.setEnabled(false);
                                                taskFortyEight(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                            entered3 = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskFortyEight(View view) {
        View sata = view.findViewById(R.id.simulation_practice_sata_1_disassemble);
        sata.setVisibility(View.VISIBLE);

        sata.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 7) {
                            if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_24_3).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_sata_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_24_1);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_24_1).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                simulation_image.setImageResource(R.drawable.simulation_image_24_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 7) {
                            if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_24_1).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_sata_1));
                                simulation_image.setImageResource(R.drawable.simulation_image_24_3);
                            } else if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_24_0).getConstantState()) {
                                sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                simulation_image.setImageResource(R.drawable.simulation_image_24_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 7) {
                            if (entered == true) {
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_24_0).getConstantState()) {
                                    sata.setVisibility(View.GONE);
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_23);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_22);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_21_1);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFortyNine(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFortyNine(View view) {
        ImageView cable = view.findViewById(R.id.simulation_practice_4pin_disassemble);
        View cable_drag = view.findViewById(R.id.simulation_practice_4pin_item_disassemble);
        cable_drag.setVisibility(View.VISIBLE);
        cable.setVisibility(View.VISIBLE);

        cable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                cable.setImageResource(0);
                return true;
            }
        });

        cable_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        simulation_image.setImageResource(R.drawable.simulation_image_21_0);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        simulation_image.setImageResource(R.drawable.simulation_image_21_1);

                        entered = false;
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (entered == true) {
                            cable.setVisibility(View.GONE);
                            cable_drag.setVisibility(View.GONE);
                            simulation_image.setEnabled(true);
                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                    @Override
                                    public boolean onDoubleTap(MotionEvent e) {
                                        taskFifty(view);
                                        simulation_image.setEnabled(false);
                                        simulation_image.setImageResource(R.drawable.simulation_image_20);
                                        return super.onDoubleTap(e);
                                    }
                                });

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    gestureDetector.onTouchEvent(event);
                                    return true;
                                }
                            });
                        } else {
                            cable.setImageResource(R.drawable.simulation_image_21_0_item);
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFifty(View view) {
        View storage = view.findViewById(R.id.simulation_practice_storage_item_disassemble);
        storage.setVisibility(View.VISIBLE);
        storage.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    storage.setVisibility(View.GONE);
                    simulation_image.setImageResource(R.drawable.simulation_image_19);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                storage.setVisibility(View.GONE);
                                taskFiftyOne(view);
                                simulation_image.setImageResource(R.drawable.simulation_image_18_1);
                                simulation_image.setEnabled(false);
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskFiftyOne(View view) {
        View hdd = view.findViewById(R.id.simulation_practice_hdd_hdd_item_disassemble);
        hdd.setVisibility(View.VISIBLE);

        hdd.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 6) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_18_0);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 6) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_18_1);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 6) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                hdd.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_17_1_123);
                                            taskFiftyTwo(view);
                                            simulation_image.setEnabled(false);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFiftyTwo(View view) {
        char screws_gone[] = {'1', '2', '3'};
        View screw_1 = view.findViewById(R.id.simulation_practice_hdd_screw_2_1_disassemble);
        View screw_2 = view.findViewById(R.id.simulation_practice_hdd_screw_2_2_disassemble);
        View screw_3 = view.findViewById(R.id.simulation_practice_hdd_screw_2_3_disassemble);
        screw_1.setVisibility(View.VISIBLE);
        screw_2.setVisibility(View.VISIBLE);
        screw_3.setVisibility(View.VISIBLE);

        screw_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                            }

                            screws_gone[0] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_17_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_6));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_4));
                                    break;
                            }
                            screws_gone[0] = '1';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 5) {
                            if (entered == true) {
                                screw_1.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_17_1).getConstantState()) {
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_17_0_123);
                                                simulation_image.setEnabled(false);
                                                taskFiftyThree(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                            }

                            screws_gone[1] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_17_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_6));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_4));
                                    break;
                            }
                            screws_gone[1] = '2';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 5) {
                            if (entered1 == true) {
                                screw_2.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_17_1).getConstantState()) {
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_17_0_123);
                                                simulation_image.setEnabled(false);
                                                taskFiftyThree(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered1 = false;
                        break;
                }
                return true;
            }
        });

        screw_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                            }

                            screws_gone[2] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_17_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_6));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_5));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_4));
                                    break;
                            }
                            screws_gone[2] = '3';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 5) {
                            if (entered2 == true) {
                                screw_3.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_17_1).getConstantState()) {
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_17_0_123);
                                                simulation_image.setEnabled(false);
                                                taskFiftyThree(view);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered2 = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFiftyThree(View view) {
        char screws_gone[] = {'1', '2', '3'};
        View screw_1 = view.findViewById(R.id.simulation_practice_hdd_screw_1_1_disassemble);
        View screw_2 = view.findViewById(R.id.simulation_practice_hdd_screw_1_2_disassemble);
        View screw_3 = view.findViewById(R.id.simulation_practice_hdd_screw_1_3_disassemble);
        screw_1.setVisibility(View.VISIBLE);
        screw_2.setVisibility(View.VISIBLE);
        screw_3.setVisibility(View.VISIBLE);

        screw_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }

                            screws_gone[0] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_17_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_3));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_1));
                                    break;
                            }
                            screws_gone[0] = '1';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 5) {
                            if (entered == true) {
                                screw_1.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_17_0).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_16);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_15);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_14_3);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFiftyFour(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }

                            screws_gone[1] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_17_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_3));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_1));
                                    break;
                            }
                            screws_gone[1] = '2';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 5) {
                            if (entered1 == true) {
                                screw_2.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_17_0).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_16);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_15);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_14_3);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFiftyFour(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered1 = false;
                        break;
                }
                return true;
            }
        });

        screw_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }

                            screws_gone[2] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_17_0);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 5) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_3));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_2));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_hdd_1));
                                    break;
                            }
                            screws_gone[2] = '3';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_17_0_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 5) {
                            if (entered2 == true) {
                                screw_3.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_17_0).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_16);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_15);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_14_3);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFiftyFour(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered2 = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskFiftyFour(View view) {
        ImageView psu = view.findViewById(R.id.simulation_practice_hdd_cordspsu_disassemble);
        ImageView sata = view.findViewById(R.id.simulation_practice_hdd_cordssata_disassemble);
        View psu_drag = view.findViewById(R.id.simulation_practice_hdd_cordspsu_item_disassemble);
        View sata_drag = view.findViewById(R.id.simulation_practice_hdd_cordssata_item_disassemble);
        psu.setVisibility(View.VISIBLE);
        sata.setVisibility(View.VISIBLE);
        psu_drag.setVisibility(View.VISIBLE);
        sata_drag.setVisibility(View.VISIBLE);

        psu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tag = "psu";
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                psu.setImageResource(0);
                return true;
            }
        });

        sata.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tag = "sata";
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                sata.setImageResource(0);
                return true;
            }
        });

        psu_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (tag == "psu") {
                            if (sata.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_1);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_0);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (tag == "psu") {
                            if (sata.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_3);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_2);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (tag == "psu") {
                            if (entered == true) {
                                psu.setVisibility(View.GONE);
                                psu_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskFiftyFive(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_13);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                psu.setImageResource(R.drawable.simulation_image_14_1_item);
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });

        sata_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (tag == "sata") {
                            if (psu.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_2);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_0);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (tag == "sata") {
                            if (psu.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_3);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_14_1);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (tag == "sata") {
                            if (entered == true) {
                                sata.setVisibility(View.GONE);
                                sata_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskFiftyFive(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_13);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                sata.setImageResource(R.drawable.simulation_image_14_2_item);
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskFiftyFive(View view) {
        View storage = view.findViewById(R.id.simulation_practice_storage_item_disassemble);
        storage.setVisibility(View.VISIBLE);
        storage.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    storage.setVisibility(View.GONE);
                    simulation_image.setImageResource(R.drawable.simulation_image_12);
                    simulation_image.setEnabled(true);
                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                storage.setVisibility(View.GONE);
                                taskFiftySix(view);
                                simulation_image.setImageResource(R.drawable.simulation_image_11);
                                simulation_image.setEnabled(false);
                                return super.onDoubleTap(e);
                            }
                        });

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskFiftySix(View view) {
        View ssd = view.findViewById(R.id.simulation_practice_ssd_ssd_item_disassemble);
        ssd.setVisibility(View.VISIBLE);

        ssd.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 4) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 4) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_11);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 4) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                ssd.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_10_1_1234);
                                            taskFiftySeven(view);
                                            simulation_image.setEnabled(false);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskFiftySeven(View view) {
        char screws_gone[] = {'1', '2', '3', '4'};
        View screw_1 = view.findViewById(R.id.simulation_practice_ssd_screw_1_disassemble);
        View screw_2 = view.findViewById(R.id.simulation_practice_ssd_screw_2_disassemble);
        View screw_3 = view.findViewById(R.id.simulation_practice_ssd_screw_3_disassemble);
        View screw_4 = view.findViewById(R.id.simulation_practice_ssd_screw_4_disassemble);
        screw_1.setVisibility(View.VISIBLE);
        screw_2.setVisibility(View.VISIBLE);
        screw_3.setVisibility(View.VISIBLE);
        screw_4.setVisibility(View.VISIBLE);

        screw_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[0] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_10_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[0] = '1';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 3) {
                            if (entered == true) {
                                screw_1.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_10_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_9);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFiftyEight(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[1] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_10_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[1] = '2';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 3) {
                            if (entered1 == true) {
                                screw_2.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_10_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_9);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFiftyEight(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                            entered1 = false;
                        }
                        break;
                }
                return true;
            }
        });

        screw_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[2] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_10_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[2] = '3';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 3) {
                            if (entered2 == true) {
                                screw_3.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_10_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_9);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFiftyEight(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered2 = false;
                        break;
                }
                return true;
            }
        });

        screw_4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[3] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_10_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered3 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 3) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_ssd_1));
                                    break;
                            }
                            screws_gone[3] = '4';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_10_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered3 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 3) {
                            if (entered3 == true) {
                                screw_4.setVisibility(View.GONE);

                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_10_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                simulation_image.setImageResource(R.drawable.simulation_image_10_0);
                                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                        @Override
                                                        public boolean onDoubleTap(MotionEvent e) {
                                                            simulation_image.setImageResource(R.drawable.simulation_image_9);
                                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                                    @Override
                                                                    public boolean onDoubleTap(MotionEvent e) {
                                                                        simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                                                                        simulation_image.setEnabled(false);
                                                                        taskFiftyEight(view);
                                                                        return super.onDoubleTap(e);
                                                                    }
                                                                });

                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    gestureDetector.onTouchEvent(event);
                                                                    return true;
                                                                }
                                                            });
                                                            return super.onDoubleTap(e);
                                                        }
                                                    });

                                                    @Override
                                                    public boolean onTouch(View v, MotionEvent event) {
                                                        gestureDetector.onTouchEvent(event);
                                                        return true;
                                                    }
                                                });
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                            entered3 = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskFiftyEight(View view) {
        ImageView psu = view.findViewById(R.id.simulation_practice_ssd_cordspsu_disassemble);
        ImageView sata = view.findViewById(R.id.simulation_practice_ssd_cordssata_disassemble);
        View psu_drag = view.findViewById(R.id.simulation_practice_ssd_cordspsu_item_disassemble);
        View sata_drag = view.findViewById(R.id.simulation_practice_ssd_cordssata_item_disassemble);
        psu.setVisibility(View.VISIBLE);
        sata.setVisibility(View.VISIBLE);
        psu_drag.setVisibility(View.VISIBLE);
        sata_drag.setVisibility(View.VISIBLE);

        psu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tag = "psu";
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                psu.setImageResource(0);
                return true;
            }
        });

        sata.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tag = "sata";
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                sata.setImageResource(0);
                return true;
            }
        });

        psu_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (tag == "psu") {
                            if (sata.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_1);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_0);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (tag == "psu") {
                            if (sata.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_2);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (tag == "psu") {
                            if (entered == true) {
                                psu.setVisibility(View.GONE);
                                psu_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_7);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        taskFiftyNine(view);
                                                        simulation_image.setEnabled(false);
                                                        simulation_image.setImageResource(R.drawable.simulation_image_6_1);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                psu.setImageResource(R.drawable.simulation_image_8_1_item);
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });

        sata_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (tag == "sata") {
                            if (psu.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_2);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_0);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (tag == "sata") {
                            if (psu.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_3);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_8_1);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (tag == "sata") {
                            if (entered == true) {
                                sata.setVisibility(View.GONE);
                                sata_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_7);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        taskFiftyNine(view);
                                                        simulation_image.setEnabled(false);
                                                        simulation_image.setImageResource(R.drawable.simulation_image_6_1);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                sata.setImageResource(R.drawable.simulation_image_8_2_item);
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskFiftyNine(View view) {
        View backcase_lid = view.findViewById(R.id.simulation_practice_backviewcaselid_item_disassemble);
        backcase_lid.setVisibility(View.VISIBLE);

        backcase_lid.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 2) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_6_0);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 2) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_caselid_back));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_6_1);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 2) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                backcase_lid.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_5);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_4_5);
                                                        taskSixty(view);
                                                        simulation_image.setEnabled(false);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskSixty(View view) {
        View frontcable = view.findViewById(R.id.simulation_practice_motherboardfrontpanel_item_disassemble);
        frontcable.setVisibility(View.VISIBLE);
        frontcable.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    frontcable.setVisibility(View.GONE);
                    taskSixtyOne(view);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_5_1);
                    simulation_image.setEnabled(true);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskSixtyOne(View view) {
        ImageView frontcable = view.findViewById(R.id.simulation_practice_motherboardfrontpanelcable_disassemble);
        View frontcable_drag = view.findViewById(R.id.simulation_practice_motherboardfrontpanelcable_item_disassemble);
        frontcable.setVisibility(View.VISIBLE);
        frontcable_drag.setVisibility(View.VISIBLE);

        frontcable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                frontcable.setImageResource(0);
                return true;
            }
        });

        frontcable_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        simulation_image.setImageResource(R.drawable.simulation_image_4_5_0);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        simulation_image.setImageResource(R.drawable.simulation_image_4_5_1);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (entered == true) {
                            frontcable.setVisibility(View.GONE);
                            frontcable_drag.setVisibility(View.GONE);
                            simulation_image.setEnabled(true);
                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                    @Override
                                    public boolean onDoubleTap(MotionEvent e) {
                                        taskSixtyTwo(view);
                                        simulation_image.setImageResource(R.drawable.simulation_image_4_4);
                                        simulation_image.setEnabled(false);
                                        return super.onDoubleTap(e);
                                    }
                                });

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    gestureDetector.onTouchEvent(event);
                                    return true;
                                }
                            });
                        } else {
                            frontcable.setImageResource(R.drawable.simulation_image_4_5_0_item);
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskSixtyTwo(View view) {
        View sata = view.findViewById(R.id.simulation_practice_motherboardsata_item_disassemble);
        sata.setVisibility(View.VISIBLE);
        sata.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    sata.setVisibility(View.GONE);
                    taskSixtyThree(view);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_4_3);
                    simulation_image.setEnabled(true);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void taskSixtyThree(View view) {
        ImageView sata_1 = view.findViewById(R.id.simulation_practice_motherboardpsatacables_1_disassemble);
        ImageView sata_2 = view.findViewById(R.id.simulation_practice_motherboardpsatacables_2_disassemble);
        View sata_1_drag = view.findViewById(R.id.simulation_practice_motherboardpsatacables_1_item_disassemble);
        View sata_2_drag = view.findViewById(R.id.simulation_practice_motherboardpsatacables_2_item_disassemble);
        sata_1.setVisibility(View.VISIBLE);
        sata_2.setVisibility(View.VISIBLE);
        sata_1_drag.setVisibility(View.VISIBLE);
        sata_2_drag.setVisibility(View.VISIBLE);

        sata_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tag = "sata_1";
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                sata_1.setImageResource(0);
                return true;
            }
        });

        sata_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tag = "sata_2";
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                sata_2.setImageResource(0);
                return true;
            }
        });

        sata_1_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (tag == "sata_1") {
                            if (sata_2.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_1);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_0);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (tag == "sata_1") {
                            if (sata_2.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_3);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_2);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (tag == "sata_1") {
                            if (entered == true) {
                                sata_1.setVisibility(View.GONE);
                                sata_1_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskSixtyFour(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_4_3);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                sata_1.setImageResource(R.drawable.simulation_image_4_4_1_item);
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });

        sata_2_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (tag == "sata_2") {
                            if (sata_1.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_2);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_0);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (tag == "sata_2") {
                            if (sata_1.getVisibility() == View.VISIBLE) {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_3);
                            } else {
                                simulation_image.setImageResource(R.drawable.simulation_image_4_4_1);
                            }
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (tag == "sata_2") {
                            if (entered == true) {
                                sata_2.setVisibility(View.GONE);
                                sata_2_drag.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            taskSixtyFour(view);
                                            simulation_image.setEnabled(false);
                                            simulation_image.setImageResource(R.drawable.simulation_image_4_3);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                sata_2.setImageResource(R.drawable.simulation_image_4_4_2_item);
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskSixtyFour(View view) {
        View four_pin = view.findViewById(R.id.simulation_practice_motherboardpsucables_4pin_disassemble);
        View twentyfour_pin = view.findViewById(R.id.simulation_practice_motherboardpsucables_24pin_disassemble);
        four_pin.setVisibility(View.VISIBLE);
        twentyfour_pin.setVisibility(View.VISIBLE);
        View twentyfour_pin_drag = view.findViewById(R.id.simulation_practice_motherboardpsucables24pin_item_disassemble);
        View four_pin_drag = view.findViewById(R.id.simulation_practice_motherboardpsucables4pin_item_disassemble);
        ImageView twentyfour_pin_item = view.findViewById(R.id.simulation_practice_motherboardpsucables24pin_disassemble);
        ImageView four_pin_item = view.findViewById(R.id.simulation_practice_motherboardpsucables4pin_disassemble);

        four_pin.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    twentyfour_pin.setEnabled(false);
                    four_pin.setEnabled(false);
                    four_pin_drag.setVisibility(View.VISIBLE);
                    four_pin_item.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_1_1);
                    simulation_image.setEnabled(true);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        twentyfour_pin.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    twentyfour_pin.setEnabled(false);
                    four_pin.setEnabled(false);
                    twentyfour_pin_drag.setVisibility(View.VISIBLE);
                    twentyfour_pin_item.setVisibility(View.VISIBLE);
                    simulation_image.setImageResource(R.drawable.simulation_image_4_2_1);
                    simulation_image.setEnabled(true);
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        twentyfour_pin_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                twentyfour_pin_item.setImageResource(0);
                return true;
            }
        });

        twentyfour_pin_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        simulation_image.setImageResource(R.drawable.simulation_image_4_2_0);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        simulation_image.setImageResource(R.drawable.simulation_image_4_2_1);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (entered == true) {
                            twentyfour_pin_drag.setVisibility(View.GONE);
                            twentyfour_pin_item.setVisibility(View.GONE);
                            twentyfour_pin.setVisibility(View.GONE);
                            simulation_image.setEnabled(true);
                            if (four_pin.getVisibility() == View.VISIBLE) {
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_4_1);
                                            four_pin.setEnabled(true);
                                            simulation_image.setEnabled(false);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_4);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_3);
                                                        taskSixtyFive(view);
                                                        simulation_image.setEnabled(false);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        } else {
                            twentyfour_pin_item.setImageResource(R.drawable.simulation_image_4_2_0_item);
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        four_pin_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myDragShadowBuilder = new View.DragShadowBuilder(v);
                clipData = ClipData.newPlainText(v.getTag().toString(), null);
                v.startDrag(clipData, myDragShadowBuilder, v, 0);
                four_pin_item.setImageResource(0);
                return true;
            }
        });

        four_pin_drag.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        simulation_image.setImageResource(R.drawable.simulation_image_4_1_0);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        simulation_image.setImageResource(R.drawable.simulation_image_4_1_1);
                        entered = true;
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (entered == true) {
                            four_pin_drag.setVisibility(View.GONE);
                            four_pin_item.setVisibility(View.GONE);
                            four_pin.setVisibility(View.GONE);

                            simulation_image.setEnabled(true);
                            if (twentyfour_pin.getVisibility() == View.VISIBLE) {
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_4_2);
                                            twentyfour_pin.setEnabled(true);
                                            simulation_image.setEnabled(false);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            } else {
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_4);
                                            simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                                private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                                    @Override
                                                    public boolean onDoubleTap(MotionEvent e) {
                                                        simulation_image.setImageResource(R.drawable.simulation_image_3);
                                                        taskSixtyFive(view);
                                                        simulation_image.setEnabled(false);
                                                        return super.onDoubleTap(e);
                                                    }
                                                });

                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    gestureDetector.onTouchEvent(event);
                                                    return true;
                                                }
                                            });
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                        } else {
                            twentyfour_pin_item.setImageResource(R.drawable.simulation_image_4_2_0_item);
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });
    }

    public void taskSixtyFive(View view) {
        View frontcase_lid = view.findViewById(R.id.simulation_practice_frontcaselid_item_disassemble);
        frontcase_lid.setVisibility(View.VISIBLE);

        frontcase_lid.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int state = event.getAction();

                switch (state) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 1) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_2);
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 1) {
                            sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_caselid_front));
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            simulation_image.setImageResource(R.drawable.simulation_image_3);
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 1) {
                            if (entered == true) {
                                item_set[viewPager2.getCurrentItem()] = 0;
                                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                                frontcase_lid.setVisibility(View.GONE);
                                simulation_image.setEnabled(true);
                                simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                        @Override
                                        public boolean onDoubleTap(MotionEvent e) {
                                            simulation_image.setImageResource(R.drawable.simulation_image_1_1234);
                                            taskSixtySix(view);
                                            simulation_image.setEnabled(false);
                                            return super.onDoubleTap(e);
                                        }
                                    });

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        gestureDetector.onTouchEvent(event);
                                        return true;
                                    }
                                });
                            }
                            entered = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void taskSixtySix(View view) {
        char screws_gone[] = {'1', '2', '3', '4'};
        View screw_1 = view.findViewById(R.id.simulation_practice_case_screw_1_disassemble);
        View screw_2 = view.findViewById(R.id.simulation_practice_case_screw_2_disassemble);
        View screw_3 = view.findViewById(R.id.simulation_practice_case_screw_3_disassemble);
        View screw_4 = view.findViewById(R.id.simulation_practice_case_screw_4_disassemble);
        screw_1.setVisibility(View.VISIBLE);
        screw_2.setVisibility(View.VISIBLE);
        screw_3.setVisibility(View.VISIBLE);
        screw_4.setVisibility(View.VISIBLE);

        screw_1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[0] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                            }
                            screws_gone[0] = '1';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 0) {
                            if (entered == true) {
                                screw_1.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                done();
                                                simulation_image.setImageResource(R.drawable.simulation_image_0);
                                                simulation_image.setEnabled(false);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered = false;
                        break;
                }
                return true;
            }
        });

        screw_2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[1] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                            }
                            screws_gone[1] = '2';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered1 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 0) {
                            if (entered1 == true) {
                                screw_2.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                done();
                                                simulation_image.setImageResource(R.drawable.simulation_image_0);
                                                simulation_image.setEnabled(false);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                            entered1 = false;
                        }
                        break;
                }
                return true;
            }
        });

        screw_3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[2] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                            }
                            screws_gone[2] = '3';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered2 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 0) {
                            if (entered2 == true) {
                                screw_3.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                done();
                                                simulation_image.setImageResource(R.drawable.simulation_image_0);
                                                simulation_image.setEnabled(false);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                        }
                        entered2 = false;
                        break;
                }
                return true;
            }
        });

        screw_4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 0:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                                    break;
                            }
                            screws_gone[3] = '_';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            if (id == 0) {
                                simulation_image.setImageResource(R.drawable.simulation_image_1);
                            }
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered3 = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (viewPager2.getCurrentItem() == 0) {
                            int no_of_screw = 0;
                            for (int i = 0; i < screws_gone.length; i++) {
                                if (screws_gone[i] == '_') {
                                    no_of_screw++;
                                }
                            }
                            switch (no_of_screw) {
                                case 1:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_4));
                                    break;
                                case 2:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_3));
                                    break;
                                case 3:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_2));
                                    break;
                                case 4:
                                    sliderItems.set(viewPager2.getCurrentItem(), new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.simulation_items_screw_1));
                                    break;
                            }
                            screws_gone[3] = '4';
                            int id = view.getResources().getIdentifier(
                                    "simulation_image_1_" + String.valueOf(screws_gone),
                                    "drawable",
                                    getActivity().getPackageName());
                            simulation_image.setImageResource(id);
                            viewPager2.getAdapter().notifyItemChanged(viewPager2.getCurrentItem());
                            entered3 = false;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        if (viewPager2.getCurrentItem() == 0) {
                            if (entered3 == true) {
                                screw_4.setVisibility(View.GONE);
                                if (simulation_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.simulation_image_1).getConstantState()) {
                                    item_set[viewPager2.getCurrentItem()] = 0;
                                    simulation_image.setEnabled(true);
                                    simulation_image.setOnTouchListener(new View.OnTouchListener() {
                                        private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onDoubleTap(MotionEvent e) {
                                                done();
                                                simulation_image.setImageResource(R.drawable.simulation_image_0);
                                                simulation_image.setEnabled(false);
                                                return super.onDoubleTap(e);
                                            }
                                        });

                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            gestureDetector.onTouchEvent(event);
                                            return true;
                                        }
                                    });
                                }
                            }
                            entered3 = false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void done() {
        int ui_flags =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        View view = getLayoutInflater().inflate(R.layout.congrats, null);
        Dialog builder = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        builder.setContentView(view);

        if (builder.getWindow() != null) {
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        builder.getWindow().
                setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        builder.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
        builder.show();

        String status = database.viewSimulationStatus("Practice");
        switch (status) {
            case "Active":
                database.updateSimulationStatus("Practice", "Done");
                break;
        }

        TextView text = view.findViewById(R.id.congrats_text);
        text.setText("Congratulation!!");
        view.findViewById(R.id.congrats_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = null;
                frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
                Fragment_Menu_Simulation fragment = new Fragment_Menu_Simulation();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(Fragment_Menu_Simulation_Practice.this);
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.detach(frg);
                fragmentTransaction.attach(frg);
                fragmentTransaction.commit();
                builder.dismiss();
            }
        });
    }

    public void initializeViewPager(View view) {
        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);

        sliderItems = new ArrayList<>();
        sliderItems_1 = new ArrayList<>();

        for (int i = 0; i < items_texts.length; i++) {
            sliderItems.add(new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.item_background));
        }

        for (int i = 0; i < items_texts.length; i++) {
            sliderItems_1.add(new Fragment_Menu_Simulation_Tutorial_SliderItem_1(R.drawable.item_background));
        }

        viewPager2.setAdapter(new Fragment_Menu_Simulation_Tutorial_SliderAdapter(sliderItems, sliderItems_1, viewPager2));


        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        sliderItems_1.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem_1(R.drawable.item_background_1));
        sliderItems.set(0, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
        viewPager2.setCurrentItem(1);
        viewPager2.setCurrentItem(0);
        
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position > (last_position + 1)) {
                    position = last_position + 1;
                    viewPager2.setCurrentItem(position);
                } else if (position < (last_position - 1)) {
                    position = last_position - 1;
                    viewPager2.setCurrentItem(position);
                }

                last_position = position;

                if (position > 0) {
                    sliderItems_1.set(position - 1, new Fragment_Menu_Simulation_Tutorial_SliderItem_1(R.drawable.item_background));
                    viewPager2.getAdapter().notifyItemChanged(position - 1);
                }

                if (position < (items_texts.length - 1)) {
                    sliderItems_1.set(position + 1, new Fragment_Menu_Simulation_Tutorial_SliderItem_1(R.drawable.item_background));
                    viewPager2.getAdapter().notifyItemChanged(position + 1);
                }

                if (item_set[position] == 0) {
                    sliderItems.set(position, new Fragment_Menu_Simulation_Tutorial_SliderItem(R.drawable.white));
                }

                items_text.setText(items_texts[position]);
                sliderItems_1.set(position, new Fragment_Menu_Simulation_Tutorial_SliderItem_1(R.drawable.item_background_1));
                viewPager2.getAdapter().notifyItemChanged(position);
            }
        });


    }
}







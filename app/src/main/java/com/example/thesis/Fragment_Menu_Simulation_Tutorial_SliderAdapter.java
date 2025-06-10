package com.example.thesis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class Fragment_Menu_Simulation_Tutorial_SliderAdapter extends RecyclerView.Adapter<Fragment_Menu_Simulation_Tutorial_SliderAdapter.SliderViewHolder> {

    private List<Fragment_Menu_Simulation_Tutorial_SliderItem> sliderItems;
    private List<Fragment_Menu_Simulation_Tutorial_SliderItem_1> sliderItems_1;
    private ViewPager2 viewPager2;

    Fragment_Menu_Simulation_Tutorial_SliderAdapter(List<Fragment_Menu_Simulation_Tutorial_SliderItem> sliderItems, List<Fragment_Menu_Simulation_Tutorial_SliderItem_1> sliderItems_1, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.sliderItems_1 = sliderItems_1;
        this.viewPager2 = viewPager2;
    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.slider_item_container,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        holder.setBackground(sliderItems_1.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }


    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewMain);
        }

        void setImage(Fragment_Menu_Simulation_Tutorial_SliderItem sliderItem) {
            imageView.setImageResource(sliderItem.getImage());
        }

        void setBackground(Fragment_Menu_Simulation_Tutorial_SliderItem_1 image) {
            imageView.setBackgroundResource(image.getImage());
        }
    }
}

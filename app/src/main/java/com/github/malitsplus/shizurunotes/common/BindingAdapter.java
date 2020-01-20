package com.github.malitsplus.shizurunotes.common;

import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.allen.library.SuperTextView;
import com.squareup.picasso.Picasso;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter(value = {"bind:imageUrl", "bind:placeHolder"}, requireAll = false)
    public static void loadImage(ImageView view, String imageUrl, Integer placeHolder) {
        if (placeHolder != null){
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(placeHolder)
                    .into(view);
        }else {
            Picasso.get()
                    .load(imageUrl)
                    .into(view);
        }
    }

    @androidx.databinding.BindingAdapter("bind:src")
    public static void loadSrc(ImageView view, Integer src) {
        view.setImageResource(src);
    }

    @androidx.databinding.BindingAdapter("bind:sRightText")
    public static void setRightString(SuperTextView view, String src) {
        view.setRightString(src);
    }
}

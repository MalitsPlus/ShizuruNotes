package com.github.malitsplus.shizurunotes.ui.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter(value = ["imageUrl", "placeHolder", "errorHolder"], requireAll = false)
fun loadImage(view: ImageView, imageUrl: String?, placeHolder: Int?, errorHolder: Int?) {
    when{
        !imageUrl.isNullOrEmpty() && placeHolder != null && errorHolder != null ->
            Glide.with(view.context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .error(errorHolder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        !imageUrl.isNullOrEmpty() && placeHolder != null ->
            Glide.with(view.context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        !imageUrl.isNullOrEmpty() && errorHolder != null ->
            Glide.with(view.context)
                .load(imageUrl)
                .error(errorHolder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        !imageUrl.isNullOrEmpty() ->
            Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

@BindingAdapter("src")
fun loadSrc(view: ImageView, src: Int) {
    view.setImageResource(src)
}

@BindingAdapter("sRightText")
fun setRightString(view: SuperTextView, src: String) {
    view.setRightString(src)
}
package com.example.ebcom.utility.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.ebcom.R


object Binding {
    @BindingAdapter("image_loader")
    fun loadImage(iv: ImageView, url: String?) {
        Glide
            .with(iv.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.mipmap.ic_launcher)
            .diskCacheStrategy(DiskCacheStrategy.ALL) // catch images
            .transform(RoundedCorners(10))  // corner radius
            .into(iv)
    }
}
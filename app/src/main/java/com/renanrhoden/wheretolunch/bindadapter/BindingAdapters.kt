package com.renanrhoden.wheretolunch.bindadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.renanrhoden.wheretolunch.mapper.PlacesPhotosMapper.toPlacesPhotosUrl
import com.squareup.picasso.Picasso

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, url: String) {
    if (view.drawable != null) return
    Picasso
        .get().apply {
            isLoggingEnabled = true
        }
        .load(url)
        .fit()
        .centerCrop()
        .into(view)
}

@BindingAdapter("app:photoReference")
fun loadImageFromReference(view: ImageView, reference: String) {
    if (view.drawable != null) return
    Picasso
        .get().apply {
            isLoggingEnabled = true
        }
        .load(toPlacesPhotosUrl(reference))
        .fit()
        .centerCrop()
        .into(view)
}

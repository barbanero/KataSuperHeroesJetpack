package com.karumi.jetpack.superheroes.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

fun ImageView.setImageBackground(path: String?) {
    if (path != null) {
        Picasso.get().load(path).fit().centerCrop().fit().into(this)
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView,url: String?){
    if(!url.isNullOrBlank()){
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .fit()
            .into(view)
    }
}
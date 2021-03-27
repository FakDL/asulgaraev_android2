package com.example.mobilecourse2

import android.widget.ImageView
import com.bumptech.glide.RequestManager

class DownloadImageHelper(
    private val requestManager: RequestManager
) {
    fun setImage(imageView: ImageView, imageUrl: String) {
        requestManager
            .load(imageUrl)
            .into(imageView)
    }

}

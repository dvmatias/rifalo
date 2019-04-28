package com.bluespark.raffleit.common.utils

import android.app.Activity

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageLoader(var activity: Activity) {

	private var defaultRequestOptions: RequestOptions = RequestOptions().centerInside()

	fun loadImage(iv: ImageView, url: String) {
		Glide.with(activity).load(url).apply(defaultRequestOptions).into(iv)
	}
}
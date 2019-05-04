package com.bluespark.raffleit.common.injection.presentation

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.bluespark.raffleit.common.mvp.BaseView
import com.bluespark.raffleit.common.utils.ImageLoader
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.google.gson.Gson
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule(
	private var view: BaseView,
	private var activity: AppCompatActivity
) {

	@Provides
	fun getView(): BaseView = view

	@Provides
	fun getActivity(): AppCompatActivity = activity

	@Provides
	fun getContext(): Context = activity

	@Provides
	fun getFragmentManager(): FragmentManager = activity.supportFragmentManager

	@Provides
	fun getImageLoader(): ImageLoader = ImageLoader(getActivity())

}
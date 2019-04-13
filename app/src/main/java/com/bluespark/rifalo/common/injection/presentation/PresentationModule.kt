package com.bluespark.rifalo.common.injection.presentation

import android.support.v7.app.AppCompatActivity
import com.bluespark.rifalo.common.mvp.BaseView
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(
	private var view: BaseView,
	private var activity: AppCompatActivity
) {

	@Provides
	fun getView(): BaseView = view

	@Provides
	fun getActivity(): AppCompatActivity = activity

}
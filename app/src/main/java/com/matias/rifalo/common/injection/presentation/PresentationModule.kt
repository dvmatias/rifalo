package com.matias.rifalo.common.injection.presentation

import android.support.v7.app.AppCompatActivity
import com.matias.rifalo.common.mvp.BaseView
import dagger.Module

@Module
class PresentationModule(
	private var view: BaseView,
	private var activity: AppCompatActivity
) {
}
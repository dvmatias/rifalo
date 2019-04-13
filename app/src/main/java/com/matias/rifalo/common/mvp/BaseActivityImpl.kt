package com.matias.rifalo.common.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.matias.rifalo.common.injection.application.ApplicationComponent
import com.matias.rifalo.common.injection.application.MyApplication
import com.matias.rifalo.common.injection.presentation.PresentationComponent
import com.matias.rifalo.common.injection.presentation.PresentationModule

/**
 * MVP - Base Activity.
 *
 * Every Activity shall extend this class.
 *
 * @author matias.delv.dom@gmail.com
 */

abstract class BaseActivityImpl : AppCompatActivity(), BaseView {

	private var isInjectorUsed: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	protected fun getPresentationComponent(): PresentationComponent {
		if (isInjectorUsed) {
			throw RuntimeException("There is no need to use injector more than once")
		}
		isInjectorUsed = true
		return getApplicationComponent().newPresentationComponent(
			PresentationModule(this, this)
		)
	}

	private fun getApplicationComponent(): ApplicationComponent {
		return (application as MyApplication).getApplicationComponent()
	}

}
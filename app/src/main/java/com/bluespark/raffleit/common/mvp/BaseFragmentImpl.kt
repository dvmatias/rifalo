package com.bluespark.raffleit.common.mvp

import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.bluespark.raffleit.common.injection.application.ApplicationComponent
import com.bluespark.raffleit.common.injection.application.MyApplication
import com.bluespark.raffleit.common.injection.presentation.PresentationComponent
import com.bluespark.raffleit.common.injection.presentation.PresentationModule

/**
 * MVP - Base Fragment.
 *
 * Every Fragment shall extend this class.
 *
 * @author matias.delv.dom@gmail.com
 */

abstract class BaseFragmentImpl : Fragment(), BaseView {
	private var isInjectorUsed: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	@UiThread
	protected fun getPresentationComponent(): PresentationComponent {
		if (isInjectorUsed) {
			throw RuntimeException("There is no need to use injector more than once")
		}
		isInjectorUsed = true
		return getApplicationComponent().newPresentationComponent(
			PresentationModule(this, activity!! as AppCompatActivity)
		)
	}

	private fun getApplicationComponent(): ApplicationComponent {
		return (activity!!.application as MyApplication).getApplicationComponent()
	}
}
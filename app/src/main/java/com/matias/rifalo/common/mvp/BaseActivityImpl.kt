package com.matias.rifalo.common.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * MVP - Base Activity.
 *
 * Every Activity shall extend this class.
 *
 * @author matias.delv.dom@gmail.com
 */

abstract class BaseActivityImpl : AppCompatActivity(), BaseView {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

}
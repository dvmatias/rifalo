package com.bluespark.raffleit.screens.register

import android.os.Bundle
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivityImpl() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_register)
	}
}
